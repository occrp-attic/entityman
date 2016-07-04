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
import org.occrp.entityman.glutton.ets.AhoCorasickExtractor;
import org.occrp.entityman.glutton.expanders.OpenocrExpander;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.Workspace;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;
import org.occrp.entityman.model.entities.Person;
import org.occrp.entityman.model.entities.PhoneNumber;
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
public class AhoCorasickTest {

	@Autowired
	Gluttony gluttony;
	
	@Autowired
	AhoCorasickExtractor aceCompaniesMd;
	
	
	
	@Test
	public void test001Simple() throws Exception{
		File f = new File("src/test/resources/input_ace_0.txt");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		fi.setWorkspace("default");
		
		gluttony.doExpand(fi);
		
		List<AEntity> aes = aceCompaniesMd.extract(fi);
		
		System.out.println(aes);

		Assert.assertEquals("Total entities", 3, aes.size());
		
	}

}
