package org.occrp.entityman.glutton;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.AExtractor;
import org.occrp.entityman.Extractor;
import org.occrp.entityman.glutton.ets.AddressClassifier;
import org.occrp.entityman.glutton.ets.AhoCorasickExtractor;
import org.occrp.entityman.glutton.expanders.OpenocrExpander;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.Workspace;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;
import org.occrp.entityman.model.entities.Person;
import org.occrp.entityman.model.entities.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(classes={TestConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressClassifierTest {

	@Autowired
	Gluttony gluttony;
	
	@Value("${ace.dictionary.address.parts}")
	String dicAddressParts;
	
	@Value("${ace.dictionary.address.helpers}")
	String dicAddressHelpers;

	@Test
	public void test001() throws Exception{
		
		AddressClassifier ac = new AddressClassifier(
				EntityUtils.readFile(dicAddressHelpers), 
				EntityUtils.readFile(dicAddressParts));
		
		String test = "mun. Chişinău, sect. Buiucani, str. Ion Pelivan , 18/16, ap. 10";
		
		List<String> sentence = AddressClassifier.getWords(test);
		
		List<Integer> res = ac.classify(sentence);
		
		System.out.println(res);

		Assert.assertEquals("Total entities", sentence.size(), res.size());
		
	}

}
