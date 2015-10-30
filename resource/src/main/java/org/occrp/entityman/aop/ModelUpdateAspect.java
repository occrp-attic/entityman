package org.occrp.entityman.aop;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.occrp.entityman.model.AMongoObject;
import org.occrp.entityman.model.Sequence;
import org.occrp.entityman.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Aspect
public class ModelUpdateAspect implements Ordered {

	private int order;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired 
	private MongoTemplate mongoTemplate;
	
	@Pointcut("execution(* org.occrp.entityman.dao.*Repository.save(..))")
	public void saveMongoDbCalls() {
	}

	private Long getNextSequence(AMongoObject a) {
		String name = a.getClass().getSimpleName();
		Sequence sequence = mongoTemplate.findAndModify(
				  new Query(Criteria.where("name").is(name)), 
				  new Update().inc("counter", 1), 
				  new FindAndModifyOptions().returnNew(true), Sequence.class);
		
		if (sequence==null) {
			sequence = new Sequence();
			sequence.setName(name);
			mongoTemplate.save(sequence);
		}
		
		return sequence.getCounter();
	}
	
	private void updateBeforeSave(AMongoObject a, User user){
		
		
		if (user!=null) {
			a.setUom(user.getUsername());
			if (a.getUoc() == null) {
				a.setUoc(user.getUsername());
			}
		}
		
		a.setDom(new Date());
		if (a.getDob() == null) {
			a.setDob(a.getDom());
		}
		if (a.getLongId()==null) {
			a.setLongId(getNextSequence(a));
		}

	}
	
	private void updateBeforeSave(Collection c, User user) {
		for (Object o : c) {
			if (o instanceof AMongoObject) {
				updateBeforeSave((AMongoObject)o, user);
			} else if (o instanceof Collection) {
				updateBeforeSave((Collection)o, user);
			}
		}
	}

	@Around("saveMongoDbCalls()")
	public Object doDbOperation(ProceedingJoinPoint pjp) throws Throwable {

		User user = null;

		updateBeforeSave(Arrays.asList(pjp.getArgs()),user);

		return pjp.proceed();
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
