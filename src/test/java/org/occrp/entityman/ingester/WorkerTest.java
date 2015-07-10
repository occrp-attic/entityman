package org.occrp.entityman.ingester;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Email;
import org.occrp.entityman.model.entities.Fact;
import org.occrp.entityman.model.entities.Location;
import org.occrp.entityman.model.entities.Person;
import org.occrp.entityman.model.entities.PhoneNumber;
import org.occrp.entityman.service.EntityManager;
import org.occrp.entityman.ws.EntityService;
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
		
		List<AEntity> aes = worker.call(fi);
		
		Assert.assertEquals("Total entities", 6, aes.size());
		
		System.out.println(aes);
		
		phones = entityManager.findAllEntities(PhoneNumber.class);
		
		Assert.assertEquals("Expected 2 phones", 2, phones.size());
		Assert.assertEquals("Expected 3 persons", 3, 
				entityManager.findAllEntities(Person.class).size());
		Assert.assertEquals("Expected 6 facts", 6, 
				entityManager.findAllEntities(Fact.class).size());
	}
	
	@Ignore
	@Test
	public void testWorker0() throws Exception {
		entityManager.dropAll();
		List<PhoneNumber> phones =  
				entityManager.findAllEntities(PhoneNumber.class);
		Assert.assertEquals("Clean db", 0, phones.size());
		
		File f = new File("src/test/resources/input1.pdf");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		
		List<AEntity> aes = worker.call(fi);
		
		Assert.assertEquals("Total entities", 9, aes.size());
		
		System.out.println(aes);
		
		phones = entityManager.findAllEntities(PhoneNumber.class);
		
		Assert.assertEquals("Expected 9 facts", 9, 
				entityManager.findAllEntities(Fact.class).size());
		Assert.assertEquals("Expected 1 phones", 1, phones.size());
		Assert.assertEquals("Expected 2 persons", 2, 
				entityManager.findAllEntities(Person.class).size());
		Assert.assertEquals("Expected 2 email", 2, 
				entityManager.findAllEntities(Email.class).size());
		Assert.assertEquals("Expected 1 location", 1, 
				entityManager.findAllEntities(Location.class).size());
	}

	@Autowired
	EntityService apiClient;
	
	// sync upload file
	@Ignore
	@Test
	public void testWorker1() throws Exception {
		entityManager.dropAll();
		Assert.assertEquals("Clean db", 0, 
				entityManager.findAllEntities(PhoneNumber.class).size());
		
		File f = new File("src/test/resources/input0.txt");

		List<Attachment> atts = new LinkedList<Attachment>();
        atts.add(new Attachment("test.txt", "application/octet-stream",
        		new FileInputStream(f)));
	      
        ServiceResult<List<AEntity>> sr = apiClient.
        		ingestSync(new MultipartBody(atts, true), null, "default");
		
		Assert.assertEquals("Total entities", 6, sr.getO().size());
		
		System.out.println(sr);
		
		Assert.assertEquals("Expected 2 phones", 2, 
				entityManager.findAllEntities(PhoneNumber.class));
		Assert.assertEquals("Expected 3 persons", 3, 
				entityManager.findAllEntities(Person.class).size());
		Assert.assertEquals("Expected 6 facts", 6, 
				entityManager.findAllEntities(Fact.class).size());
	}

}
