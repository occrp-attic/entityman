package org.occrp.entityman.ingester;

import java.io.File;
import java.util.List;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.occrp.entityman.ingester.Worker;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.PhoneNumber;
import org.occrp.entityman.service.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/beans.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkerTest {

	@Autowired
	Worker worker;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	public void testWorker() throws Exception {
		entityManager.dropAll();
		List<PhoneNumber> phones =  
				entityManager.findAllEntities(PhoneNumber.class);
		Assert.assertEquals("Clean db", 0, phones.size());
		
		File f = new File("src/test/resources/input0.txt");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		
		worker.setFile(fi);
		
		worker.call();
		
		phones = entityManager.findAllEntities(PhoneNumber.class);
		
		Assert.assertEquals("Expected 2 phones", 2, phones.size());
	}
	

}
