package org.occrp.entityman.ingester;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.occrp.entityman.model.AMongoObject;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/beans.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkerTest {

	// This variable is populated from surfire and reserve port maven plugin
    @Value("#{systemProperties['basePath'] ?: \"http://localhost:18080/\"}")
    private String basePath;

    // I assume that you have in your spring context the rest server
    @Autowired
    private JAXRSServerFactoryBean serverFactory;

    private Server server;

    @Before
    public void beforeMethod() {
        serverFactory.setBindingId(JAXRSBindingFactory.JAXRS_BINDING_ID);
        // Specify where your rest service will be deployed
        serverFactory.setAddress(basePath);
        server = serverFactory.create();
        server.start();
    }

    @After
    public void afterMethod() {
        server.stop();
        server.destroy();
    }	
	
	@Autowired
	Worker worker;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	public void test001Worker() throws Exception {
		entityManager.dropAll();
		List<PhoneNumber> phones =  
				entityManager.findAllEntities(PhoneNumber.class, "default");
		Assert.assertEquals("Clean db", 0, phones.size());
		
		File f = new File("src/test/resources/input0.txt");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		fi.setWorkspace("default");
		
		List<AEntity> aes = worker.call(fi);
		
		Assert.assertEquals("Total entities", 6, aes.size());
		
		System.out.println(aes);
		
		phones = entityManager.findAllEntities(PhoneNumber.class, "default");
		
		Assert.assertEquals("Expected 2 phones", 2, phones.size());
		Assert.assertEquals("Expected 3 persons", 3, 
				entityManager.findAllEntities(Person.class, "default").size());
		Assert.assertEquals("Expected 6 facts", 6, 
				entityManager.findAllEntities(Fact.class, "default").size());
	}
	
	@Ignore
	@Test
	public void test002Worker() throws Exception {
		entityManager.dropAll();
		List<PhoneNumber> phones =  
				entityManager.findAllEntities(PhoneNumber.class, "default");
		Assert.assertEquals("Clean db", 0, phones.size());
		
		File f = new File("src/test/resources/input1.pdf");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		
		List<AEntity> aes = worker.call(fi);
		
		Assert.assertEquals("Total entities", 9, aes.size());
		
		System.out.println(aes);
		
		phones = entityManager.findAllEntities(PhoneNumber.class, "default");
		
		Assert.assertEquals("Expected 9 facts", 9, 
				entityManager.findAllEntities(Fact.class, "default").size());
		Assert.assertEquals("Expected 1 phones", 1, phones.size());
		Assert.assertEquals("Expected 2 persons", 2, 
				entityManager.findAllEntities(Person.class, "default").size());
		Assert.assertEquals("Expected 2 email", 2, 
				entityManager.findAllEntities(Email.class, "default").size());
		Assert.assertEquals("Expected 1 location", 1, 
				entityManager.findAllEntities(Location.class, "default").size());
	}

	@Autowired
	EntityService apiClient;
	

	@Test
	public void test003PostSync() throws Exception {
		entityManager.dropAll();
		Assert.assertEquals("Clean db", 0, 
				entityManager.findAllEntities(PhoneNumber.class, "default").size());
		
		File f = new File("src/test/resources/input0.txt");

		List<Attachment> atts = new LinkedList<Attachment>();
        atts.add(new Attachment("test.txt", "application/octet-stream",
        		new FileInputStream(f)));
	      
        ServiceResult<List<AEntity>> sr = apiClient.
        		ingestSync(new MultipartBody(atts, true), null, "default");
		
		Assert.assertEquals("Total entities", 7, sr.getO().size());
		
		System.out.println(sr);
		
		Assert.assertEquals("Expected 2 phones", 2, 
				entityManager.findAllEntities(PhoneNumber.class, "default").size());
		Assert.assertEquals("Expected 3 persons", 3, 
				entityManager.findAllEntities(Person.class, "default").size());
		Assert.assertEquals("Expected 6 facts", 6, 
				entityManager.findAllEntities(Fact.class, "default").size());
	}

	@Test
	public void test004GetFile() throws Exception {
		entityManager.dropAll();
		Assert.assertEquals("Clean db", 0, 
				entityManager.findAllEntities(IngestedFile.class, "default").size());
		
		File f = new File("src/test/resources/input0.txt");

		List<Attachment> atts = new LinkedList<Attachment>();
        atts.add(new Attachment(f.getName(), "application/octet-stream",
        		new FileInputStream(f)));
	      
        ServiceResult<List<AEntity>> sr = apiClient.
        		ingestSync(new MultipartBody(atts, true), null, "default");
		
		Assert.assertEquals("Total entities", 7, sr.getO().size());
		
		System.out.println(sr);
		
		Assert.assertEquals("Expected 2 phones", 2, 
				entityManager.findAllEntities(PhoneNumber.class, "default").size());
		Assert.assertEquals("Expected 3 persons", 3, 
				entityManager.findAllEntities(Person.class, "default").size());
		Assert.assertEquals("Expected 6 facts", 6, 
				entityManager.findAllEntities(Fact.class, "default").size());
		
		List<IngestedFile> ifs = entityManager.findAllEntities(IngestedFile.class, "default");
		
		for (IngestedFile fi : ifs) {
			Response response = apiClient.getFile(fi.getId().toString());
			Assert.assertTrue("Expect ok", (response.getStatus() / 100) == 2);
		}
	}

	@Test
	public void test005ListEntities() throws Exception {
		entityManager.dropAll();
		Assert.assertEquals("Clean db", 0, 
				entityManager.findAllEntities(IngestedFile.class, "default").size());
		
		File f = new File("src/test/resources/input0.txt");

		List<Attachment> atts = new LinkedList<Attachment>();
        atts.add(new Attachment(f.getName(), "application/octet-stream",
        		new FileInputStream(f)));
	      
        ServiceResult<List<AEntity>> sr = apiClient.
        		ingestSync(new MultipartBody(atts, true), null, "default");
		
		Assert.assertEquals("Total entities", 7, sr.getO().size());
		
		System.out.println(sr);
		
		Assert.assertEquals("Expected 2 phones", 2, 
				entityManager.findAllEntities(PhoneNumber.class, "default").size());
		Assert.assertEquals("Expected 3 persons", 3, 
				entityManager.findAllEntities(Person.class, "default").size());
		Assert.assertEquals("Expected 6 facts", 6, 
				entityManager.findAllEntities(Fact.class, "default").size());
		
		ServiceResult<List<List<Object>>> sr1 = apiClient.getAllEntities("Fact","default");
		
		System.out.println(sr1);
		
		Assert.assertEquals("Number of Facts 6", 6, sr1.getO().size());
		Assert.assertEquals("2 items per item", 2, sr1.getO().get(0).size());
	}

	@Test
	public void test005GetEntityDetailed() throws Exception {
		entityManager.dropAll();
		Assert.assertEquals("Clean db", 0, 
				entityManager.findAllEntities(IngestedFile.class, "default").size());
		
		File f = new File("src/test/resources/input0.txt");
	
		List<Attachment> atts = new LinkedList<Attachment>();
	    atts.add(new Attachment(f.getName(), "application/octet-stream",
	    		new FileInputStream(f)));
	      
	    ServiceResult<List<AEntity>> sr = apiClient.
	    		ingestSync(new MultipartBody(atts, true), null, "default");
		
		Assert.assertEquals("Total entities", 7, sr.getO().size());
		
		System.out.println(sr);
		
		Assert.assertEquals("Expected 2 phones", 2, 
				entityManager.findAllEntities(PhoneNumber.class, "default").size());
		Assert.assertEquals("Expected 3 persons", 3, 
				entityManager.findAllEntities(Person.class, "default").size());
		Assert.assertEquals("Expected 6 facts", 6, 
				entityManager.findAllEntities(Fact.class, "default").size());
		
		ServiceResult<List<List<Object>>> sr1 = apiClient.getAllEntities("Fact","default");
		
		System.out.println(sr1);
		
		Assert.assertEquals("Number of Facts 6", 6, sr1.getO().size());
		Assert.assertEquals("2 items per item", 2, sr1.getO().get(0).size());
		
		ServiceResult<AEntity> sr2 = apiClient.getEntity("Fact", 
				String.valueOf(sr1.getO().get(0).get(0)));
		
		Assert.assertEquals("Id is the same ", 
				String.valueOf(sr1.getO().get(0).get(0)), 
				String.valueOf(sr2.getO().getId()));
	}

}
