package org.occrp.entityman.dao;

import java.math.BigInteger;
import java.util.List;

import org.occrp.entityman.model.entities.Fact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface FactRepository extends MongoRepository<Fact,BigInteger> {

	@Query(value="{ 'entity' : ?0, entityId: ?1 }")
	public List<Fact> findByEntity(String entityType, BigInteger id);
}