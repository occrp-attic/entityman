package org.occrp.entityman.dao;

import java.math.BigInteger;

import org.occrp.entityman.model.entities.Fact;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FactRepository extends MongoRepository<Fact,BigInteger> {

}