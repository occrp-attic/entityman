package org.occrp.entityman.dao;

import java.math.BigInteger;
import java.util.List;


import org.occrp.entityman.model.AMongoObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.core.EntityInformation;

public class AMongoObjectRepositoryImpl<T extends AMongoObject> extends 
	SimpleMongoRepository<T, BigInteger> 
	implements AMongoObjectRepository<T> {
	MongoOperations mongoOperations;
	MongoEntityInformation<T, BigInteger> metadata;

	public AMongoObjectRepositoryImpl(
			MongoEntityInformation<T, BigInteger> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
		this.mongoOperations = mongoOperations;
		this.metadata = metadata;
	}

	@Override
	public void delete(BigInteger id) {
		mongoOperations.updateFirst(
				new Query(Criteria.where("_id").is(id)),
				Update.update("deleted", Boolean.TRUE),
				metadata.getCollectionName());
	}

	@Override
	public void delete(T entity) {
		delete(entity.getId());
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		for (AMongoObject amo : entities) {
			delete(amo.getId());
		}
	}

//	@Override
//	public void deleteTrue(T entity) {
//		deleteTrue(entity.getId());
//	}

	public void deleteTrue(BigInteger id) {
		mongoOperations.remove(
				new Query(Criteria.where("_id").is(id)),
				metadata.getCollectionName());
	}
	
	// TODO improve
//	@Override
//	public List<T> findAllAll() {
//		return super.findAll();
//	}
	
	@Override
	public List<T> findAll(Sort sort) {
		Query query = Query.query(Criteria.where("deleted").ne(Boolean.TRUE));
		if (sort!=null) query = query.with(sort);
		return mongoOperations.find(query,
				metadata.getJavaType(), metadata.getCollectionName());
	}

	@Override
	public List<T> findAll() {
		return findAll((Sort)null);
	}
	
}
