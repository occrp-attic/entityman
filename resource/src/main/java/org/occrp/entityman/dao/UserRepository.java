package org.occrp.entityman.dao;

import java.math.BigInteger;

import org.occrp.entityman.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User,BigInteger> {

}
