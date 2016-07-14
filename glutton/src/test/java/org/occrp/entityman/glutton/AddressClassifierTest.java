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
		
		File f = new File(".");
		for (String s : f.list()) {
			System.out.println(s);
		}
		
		AddressClassifier ac = new AddressClassifier(
				EntityUtils.readFile(dicAddressHelpers), 
				EntityUtils.readFile(dicAddressParts));
		
		String test = "mun. Chişinău, sect. Buiucani, str. Ion Pelivan , 18/16, ap. 10";
		
		List<String> sentence = AddressClassifier.getWords(test);
		
		List<Integer> res = ac.classify(sentence);

		List<Integer> expected = Arrays.asList(
				AddressClassifier.CLASS_ADDRESS_HELPER,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_ADDRESS_PART,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_ADDRESS_HELPER,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_ADDRESS_PART,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_ADDRESS_HELPER,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_CAPITAL_FIRST,
				AddressClassifier.CLASS_CAPITAL_FIRST,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_NUMBER,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_NUMBER,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_ADDRESS_HELPER,
				AddressClassifier.CLASS_SEPARATOR_SOFT,
				AddressClassifier.CLASS_NUMBER
				);
		
		System.out.println(res);

		Assert.assertEquals("Total entities", sentence.size(), res.size());

		for (int i =0 ; i < sentence.size(); i ++) {
			System.out.println(sentence.get(i)+ " : "+res.get(i));
		}
		
		Assert.assertEquals("Good entities", expected, res);

	}

}
