package org.occrp.entityman.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.dao.FactRepository;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.annotation.Entity;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

@Component
public class EntityManager {

	protected Logger log = LogManager.getLogger(getClass().getName());

	@Autowired
	MongoOperations mongoOperations;
	
	@Autowired
	FactRepository factRepository;
	
	Map<String,Class<AEntity>> entityMap = null;
	
	public Map<String,Class<AEntity>> getEntityMap(){
	
		Map<String,Class<AEntity>> res = new ConcurrentHashMap<String, Class<AEntity>>();
		
		Reflections reflections = new Reflections(
				"org.occrp.entityman.model");
		
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
		for (Class c : classes) {
			res.put(c.getSimpleName().toLowerCase(),c);
		}
		
		return res;
	}
	
	public Class<AEntity> getEntityClass(String name) {
		// TODO improve this
		synchronized (this) {
			if (entityMap==null) {
				entityMap = getEntityMap();
			}
		}
		
		Class<AEntity> ae = entityMap.get(name.toLowerCase());

		if (ae==null) {
			log.warn("No Entity class found for : {}",name);
		}
		return ae;
	}
	
	public AEntity createNewEntity(String name) {
		Class<AEntity> clazz = getEntityClass(name);
		
		AEntity res = null;
		try {
			if (clazz!=null) res = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error(e);
		}
		return res;
	}
	
	public void entitySetField(AEntity ae, String field, Object value) throws IllegalArgumentException, IllegalAccessException {
		Field f = ReflectionUtils.findField(ae.getClass(), field);
		ReflectionUtils.makeAccessible(f);
		f.set(ae, value);
	}
	
	public Object entityGetField(AEntity ae, String field) throws IllegalArgumentException, IllegalAccessException {
		Field f = ReflectionUtils.findField(ae.getClass(), field);
		ReflectionUtils.makeAccessible(f);
		return f.get(ae);
	}
	
	/**
	 * Merges the entity into the collection
	 *  
	 * @param ae
	 * @return
	 */
	public AEntity merge(AEntity ae) {
		// TODO reentrant lock by Composite Entity Key
		
		// 1. lookup the database for existing Entity with this key
		List<? extends AEntity> relatedEntites = mongoOperations.find(
				Query.query(Criteria.where("key").is(ae.getKey())), ae.getClass());

		// 2. merge new fields/data into the old entity
		if (relatedEntites!=null && relatedEntites.size()>0) {
			ae = mergeIn(relatedEntites.get(0),ae);
		}
		
		// 3. update/store old entity
		mongoOperations.save(ae);
		
		// 4. return old entity
		return ae;
	}
	
	public AEntity mergeIn(AEntity aeOld, AEntity aeNew) {
		if (aeOld == null) return aeNew;

		aeOld.getFileIds().addAll(aeNew.getFileIds());
		
		// TODO field by field merging
//		ReflectionUtils.doWithFields(aeOld.getClass(), new FieldCallback() {
//			
//			@Override
//			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		return aeOld;
	}
	
	public long rateData(Object o) {
		// TODO
		return 0;
	}

	public void dropAll() {
		for (Class c : getEntityMap().values()) {
			mongoOperations.dropCollection(c);
		}
		
		mongoOperations.dropCollection(IngestedFile.class);
		mongoOperations.dropCollection(Fact.class);
	}
	
	public <T> List<T> findAllEntities(Class<T> clazz) {
		return mongoOperations.findAll(clazz);
	}
	
}
