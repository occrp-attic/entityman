package org.occrp.entityman.dao;

import java.math.BigInteger;

import org.occrp.entityman.model.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository  extends MongoRepository<Session	,BigInteger> {

}
