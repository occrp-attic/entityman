package org.occrp.entityman.glutton;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.model.AMongoObject;
import org.occrp.entityman.model.annotation.Entity;
import org.occrp.entityman.model.entities.AEntity;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class EntityUtils {

	static protected Logger log = LogManager.getLogger(EntityUtils.class);

	static Map<String,Class<AEntity>> entityMap = null;
	
	static public Map<String,Class<AEntity>> getEntityMap(){
	
		Map<String,Class<AEntity>> res = new ConcurrentHashMap<String, Class<AEntity>>();
		
		Reflections reflections = new Reflections(
				"org.occrp.entityman.model");
		
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
		for (Class c : classes) {
			res.put(c.getSimpleName().toLowerCase(),c);
		}
		
		return res;
	}

	static Map<String,Class<AEntity>> amoMap = null;

	public Map<String,Class<? extends AMongoObject>> getAMongoObjectMap(){
		
		Map<String,Class<? extends AMongoObject>> res = 
				new ConcurrentHashMap<>();
		
		Reflections reflections = new Reflections(
				"org.occrp.entityman.model");
		
		Set<Class<? extends AMongoObject>> classes = reflections.getSubTypesOf(AMongoObject.class);
		for (Class c : classes) {
			res.put(c.getSimpleName().toLowerCase(),c);
		}
		
		return res;
	}

	static public Class<AEntity> getAmoClass(String name) {
		// TODO improve this
		synchronized (EntityUtils.class) {
			if (amoMap==null) {
				amoMap = getEntityMap();
			}
		}
		
		Class<AEntity> ae = amoMap.get(name.toLowerCase());

		if (ae==null) {
			log.warn("No Entity class found for : {}",name);
		}
		return ae;
	}

	static public Class<AEntity> getEntityClass(String name) {
		// TODO improve this
		synchronized (EntityUtils.class) {
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
	
	static public AEntity createNewEntity(String name) {
		Class<AEntity> clazz = getEntityClass(name);
		
		AEntity res = null;
		try {
			if (clazz!=null) res = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error(e);
		}
		return res;
	}
	
	static public void entitySetField(AEntity ae, String field, Object value) throws IllegalArgumentException, IllegalAccessException {
		Field f = ReflectionUtils.findField(ae.getClass(), field);
		ReflectionUtils.makeAccessible(f);
		f.set(ae, value);
	}
	
	static public Object entityGetField(AEntity ae, String field) throws IllegalArgumentException, IllegalAccessException {
		Field f = ReflectionUtils.findField(ae.getClass(), field);
		ReflectionUtils.makeAccessible(f);
		return f.get(ae);
	}

	static public AEntity mergeIn(AEntity aeOld, AEntity aeNew) {
		if (aeOld == null) return aeNew;

		aeOld.getFileIds().addAll(aeNew.getFileIds());
		aeOld.setFact(aeNew.getFact());
		
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
	
	static public long rateData(Object o) {
		// TODO
		return 0;
	}

}
