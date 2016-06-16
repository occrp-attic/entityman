package org.occrp.entityman.glutton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.occrp.entityman.glutton.filters.DictionaryBstFilter;
import org.occrp.entityman.glutton.filters.DictionaryFilter;
import org.occrp.entityman.glutton.filters.Filter;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Company;
import org.occrp.entityman.model.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(classes={TestConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FilterTest {

	protected Logger log = LogManager.getLogger(getClass().getName());

	@Autowired
	private List<Filter> filters;
	
	@Autowired
	private DictionaryFilter filterPersonNameBlackList;

	@Autowired
	private DictionaryBstFilter filterPersonNameWhiteList;
	
	
	@Test
	public void test001() {
		List<AEntity> entities = new ArrayList<>();
		Person person1 = new Person("Ion Vasile");
		Person person2 = new Person("ion popov");
		Person person3 = new Person("vasile rusu");
		Person person4 = new Person("rusu vasile");
		Person person5 = new Person("SRL Ion Popov");
		
		Company company = new Company();
		company.setName("SRL Codrita");
		
		entities.addAll(Arrays.asList(person1,person2,person3,person4,person5,company));
		
		List<AEntity> filtered = filterPersonNameBlackList.filter(entities);
		log.info("Filtered entitites : {}", filtered);
		
		Assert.assertEquals("Entity count", 5, filtered.size());
		Assert.assertEquals("Entity list", 
				Arrays.asList(person1,person2,person3,person4,company), filtered);
		
	}

	@Test
	public void test002() {
		List<AEntity> entities = new ArrayList<>();
		Person person1 = new Person("Ion Vasile");
		Person person2 = new Person("ion popov");
		Person person3 = new Person("vasile rusu");
		Person person4 = new Person("rusu vasile");
		Person person5 = new Person("SRL Ion Popov");
		
		Company company = new Company();
		company.setName("SRL Codrita");
		
		entities.addAll(Arrays.asList(person1,person2,person3,person4,person5,company));
		
		List<AEntity> filtered = filterPersonNameBlackList.filter(entities);
		log.info("Filtered entitites : {}", filtered);
		
		Assert.assertEquals("Entity count", 5, filtered.size());
		Assert.assertEquals("Entity list", 
				Arrays.asList(person1,person2,person3,person4,company), filtered);
		
	}

	@Test
	public void test003() {
		List<AEntity> entities = new ArrayList<>();
		Person person1 = new Person("Ion Vasile");
		Person person2 = new Person("ion popov");
		Person person3 = new Person("vasile rusu");
		Person person4 = new Person("rusu vasile");
		Person person5 = new Person("SRL Ion Popov");
		
		Company company = new Company();
		company.setName("SRL Codrita");
		
		entities.addAll(Arrays.asList(person1,person2,person3,person4,person5,company));
		
		List<AEntity> filtered = filterPersonNameWhiteList.filter(entities);
		log.info("Filtered entitites : {}", filtered);
		
		Assert.assertEquals("Entity count", 6, filtered.size());
		Assert.assertEquals("Entity list", 
				Arrays.asList(person1,person2,person3,person4,person5,company), filtered);
		
	}

	@Test
	public void test004() {
		List<AEntity> entities = new ArrayList<>();
		Person person1 = new Person("Ion Vasile");
		Person person2 = new Person("ion popov");
		Person person3 = new Person("vasile rusu");
		Person person4 = new Person("rusu vasile");
		Person person5 = new Person("SRL Ion Popov");
		Person person6 = new Person("i0n p0pov");
		
		Company company = new Company();
		company.setName("SRL Codrita");
		
		entities.addAll(Arrays.asList(person1,person2,person3,person4,person5,person6,company));
		
		List<AEntity> filtered = entities;
		for (Filter f : filters) {
			filtered = f.filter(filtered);
			log.info("------------------------------ Filtered entitites {} : {}", f.getFilterName(), filtered);
		}
		log.info("Filtered entitites : {}", filtered);
		
		Assert.assertEquals("Entity count", 5, filtered.size());
		Assert.assertEquals("Entity list", 
				Arrays.asList(person1,person2,person3,person4,company), filtered);
		
	}

}
