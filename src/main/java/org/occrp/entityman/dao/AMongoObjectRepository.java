package org.occrp.entityman.dao;

import java.math.BigInteger;
import java.util.List;


import org.occrp.entityman.model.AMongoObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AMongoObjectRepository<T extends AMongoObject> extends MongoRepository<T, BigInteger> {
	
	// TODO WTF @Query is not working here ???
	public List<T> findAll();
	
	public List<T> findAll(Sort sort);

//	public List<T> findAllAll();

	public void delete(T amo);

//	public void deleteTrue(T amo);
}
