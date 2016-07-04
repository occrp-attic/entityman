package org.occrp.entityman.glutton;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.AExtractor;
import org.occrp.entityman.Extractor;
import org.occrp.entityman.glutton.ets.PersonNameEnricher;
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
public class GluttonyQualityTest {

	@Autowired
	Gluttony gluttony;

	@Value("${qualitytest.input.path}")
	private String pathInputData;
	
	@Test
	public void test001() throws Exception{
		File inputFolder = new File(pathInputData);
		
		List<AEntity> aes = new LinkedList<>();
		
		if (inputFolder.exists() && inputFolder.isDirectory()) {
			for (File file : inputFolder.listFiles()) {

				IngestedFile fi = new IngestedFile();
				fi.setFile(file);
				fi.setWorkspace("default");
				fi.getEntities().put("doOcr", Boolean.FALSE);
				
				aes.addAll(gluttony.call(fi));
			}
		}
		
		for (AEntity ae : aes) {
			System.out.println(
				String.format("%s\t%s\t%s\t%s", ae.getClass().getSimpleName(), ae.getExtractor(), ae.getLabel(), ae.getFact()));
			
		}
	}

	@Autowired
	PersonNameEnricher pne;
	
	@Test
	public void testPersonNameEnricher001() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare Nume "+name+" in formatul de prefix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", "Nume "+name, p.getName());
		
	}

	@Test
	public void testPersonNameEnricher002() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare Banana Nume "+name+" in formatul de prefix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", "Banana Nume "+name, p.getName());
		
	}

	@Test
	public void testPersonNameEnricher003() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare Banana N. "+name+" in formatul de prefix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", "Banana N. "+name, p.getName());
		
	}

	@Test
	public void testPersonNameEnricher004() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare B. Nume "+name+" in formatul de prefix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", "B. Nume "+name, p.getName());
		
	}

	@Test
	public void testPersonNameEnricher005() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare Andrei Vasile, Nume "+name+" in formatul de prefix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", "Nume "+name, p.getName());
		
	}

	@Test
	public void testPersonNameEnricher006() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare Andrei. Nume "+name+" in formatul de prefix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", "Nume "+name, p.getName());
		
	}

	@Test
	public void testPersonNameEnricher007() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare Nume "+name+" Banana in formatul de prefix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", "Nume "+name+" Banana", p.getName());
		
	}

	@Test
	public void testPersonNameEnricherSufix001() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare "+name+" Nume in formatul de sufix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", name+" Nume", p.getName());
		
	}

	@Test
	public void testPersonNameEnricherSufix002() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare "+name+" Nume Banana in formatul de sufix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", name+" Nume Banana", p.getName());
		
	}

	@Test
	public void testPersonNameEnricherSufix003() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare "+name+" N. Banana in formatul de sufix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", name+" N. Banana", p.getName());
		
	}

	@Test
	public void testPersonNameEnricherSufix004() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare "+name+" N., Banana Nicolae in formatul de sufix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", name+" N.", p.getName());
		
	}

	@Test
	public void testPersonNameEnricherSufix005() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare "+name+" Nume, Banana Nicolae in formatul de sufix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", name+" Nume", p.getName());
		
	}

	@Test
	public void testPersonNameEnricherSufix006() throws Exception{

		String name = "Ion";
		
		String text = " trebuie de testat un oarecare "+name+" Nume. Banana Nicolae in formatul de sufix";
		Person p = new Person();
		p.setName(name);
		
		Fact f = new Fact();
		f.setPosition(text.indexOf(name));
		f.setPositionEnd(text.indexOf(name)+name.length());
		
		p.setFact(f);
		
		pne.tryEnrich(p, text);
		
		System.out.println("Resulted "+p);
		
		Assert.assertEquals("name enriched", name+" Nume", p.getName());
		
	}

}
