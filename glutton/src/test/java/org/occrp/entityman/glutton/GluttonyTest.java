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
public class GluttonyTest {

	@Autowired
	Gluttony gluttony;
	
	@Test
	public void test001GluttonySimple() throws Exception{
		File f = new File("src/test/resources/input0.txt");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		fi.setWorkspace("default");
		
		List<AEntity> aes = gluttony.call(fi);
		
		System.out.println(aes);

		Assert.assertEquals("Total entities", 6, aes.size());
		
	}

	@Autowired
	private OpenocrExpander ooe;
	
	@Test
	public void test011TestImageOcr() throws Exception {
		File f = new File("src/test/resources/monumente.pdf");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		fi.setWorkspace("default");
		fi.setOriginalFilename(f.getName());

		List<AEntity> aes = gluttony.call(fi);
		
		System.out.println("-------- OCR Rating : "+ooe.rateOcr(
				String.valueOf(fi.getExpandedData().get(AExpander.EXPKEY_OCREDTEXT))));
		System.out.println(fi.getExpandedData());
		System.out.println(aes);

		Assert.assertTrue("Total entities > 0", aes.size() > 0);
		
	}

	@Test
	public void test011TestImageOcrCustomGray() throws Exception {
		List<Integer> oldValue = ooe.getGrayMidpoints();
		ooe.setGrayMidpoints(Arrays.asList(65,50,35));
		
		File f = new File("src/test/resources/monumente.pdf");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		fi.setWorkspace("default");
		fi.setOriginalFilename(f.getName());

		List<AEntity> aes = gluttony.call(fi);
		
		System.out.println("-------- OCR Rating : "+ooe.rateOcr(
				String.valueOf(fi.getExpandedData().get(AExpander.EXPKEY_OCREDTEXT))));
		System.out.println(fi.getExpandedData());
		System.out.println(aes);

		ooe.setGrayMidpoints(oldValue);

		Assert.assertTrue("Total entities > 0", aes.size() > 0);
	}

	@Test
	public void test012TestImageOcr() throws Exception {
		File f = new File("src/test/resources/input0001.pdf");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		fi.setWorkspace("default");
		fi.setOriginalFilename(f.getName());
		
		List<AEntity> aes = gluttony.call(fi);
		
		System.out.println("-------- OCR Rating : "+ooe.rateOcr(
				String.valueOf(fi.getExpandedData().get(AExpander.EXPKEY_OCREDTEXT))));
		System.out.println(fi.getExpandedData());
		System.out.println(aes);

		Assert.assertTrue("Total entities > 0", aes.size() > 0);
		
	}
	
	@Test
	public void test012TestImageOcrCustomGray() throws Exception {
		List<Integer> oldValue = ooe.getGrayMidpoints();
		ooe.setGrayMidpoints(Arrays.asList(65,50,35));

		File f = new File("src/test/resources/input0001.pdf");
		
		IngestedFile fi = new IngestedFile();
		fi.setFile(f);
		fi.setWorkspace("default");
		fi.setOriginalFilename(f.getName());
		
		List<AEntity> aes = gluttony.call(fi);
		
		System.out.println("-------- OCR Rating : "+ooe.rateOcr(
				String.valueOf(fi.getExpandedData().get(AExpander.EXPKEY_OCREDTEXT))));
		System.out.println(fi.getExpandedData());
		System.out.println(aes);

		ooe.setGrayMidpoints(oldValue);

		Assert.assertTrue("Total entities > 0", aes.size() > 0);
		
	}
}
