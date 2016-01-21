package org.occrp.entityman.glutton.ets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.glutton.expanders.AExpander;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Company;
import org.occrp.entityman.model.entities.Fact;
import org.occrp.entityman.model.entities.Location;
import org.occrp.entityman.model.entities.Person;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;

public abstract class AStanfordExtractor extends AExtractor {

	public interface EntityCreator {
		public AEntity createEntity(String name);
	}
	
	protected Map<String, EntityCreator> ecMapping = new HashMap<String,EntityCreator>() {{
		put("PERSON",new EntityCreator() {
			@Override
			public AEntity createEntity(String name) {
				Person p = new Person();
				p.setName(name);
				return p;
			}
		});
		put("ORGANIZATION",new EntityCreator() {
			@Override
			public AEntity createEntity(String name) {
				Company c = new Company();
				c.setName(name);
				return c;
			}
		});
		put("LOCATION",new EntityCreator() {
			@Override
			public AEntity createEntity(String name) {
				Location l = new Location();
				l.setName(name);
				return l;
			}
		});
	}};
	
}
