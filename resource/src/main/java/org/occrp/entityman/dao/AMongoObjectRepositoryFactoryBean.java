package org.occrp.entityman.dao;

import java.math.BigInteger;


import org.occrp.entityman.model.AMongoObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

public class AMongoObjectRepositoryFactoryBean<T extends AMongoObject> extends 
	MongoRepositoryFactoryBean<AMongoObjectRepository<T>, T, BigInteger>{

	@Override
	protected RepositoryFactorySupport getFactoryInstance(
			MongoOperations operations) {
		
		return new AMongoObjectRepositoryFactory(operations);
	}

	
	public class AMongoObjectRepositoryFactory extends MongoRepositoryFactory {
		
		MongoOperations operations;
		
		public AMongoObjectRepositoryFactory(MongoOperations mongoOperations) {
			super(mongoOperations);
			operations = mongoOperations;
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return AMongoObjectRepository.class;
		}

		@Override
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			TypeInformation<T> information = 
					ClassTypeInformation.from((Class<T>)metadata.getDomainType());
            MongoPersistentEntity<T> pe = 
            		new BasicMongoPersistentEntity<T>(information);
            MongoEntityInformation<T,BigInteger> mongometa = 
            		new MappingMongoEntityInformation<T, BigInteger>(pe);

			return new AMongoObjectRepositoryImpl(mongometa, operations);
		}
		
	}
	
}
