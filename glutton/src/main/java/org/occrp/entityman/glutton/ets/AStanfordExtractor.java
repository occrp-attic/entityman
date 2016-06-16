package org.occrp.entityman.glutton.ets;

import java.util.HashMap;
import java.util.Map;

import org.occrp.entityman.AExtractor;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Company;
import org.occrp.entityman.model.entities.Location;
import org.occrp.entityman.model.entities.Other;
import org.occrp.entityman.model.entities.Person;

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
		put("PERS",get("PERSON"));
		put("ORGANIZATION",new EntityCreator() {
			@Override
			public AEntity createEntity(String name) {
				Company c = new Company();
				c.setName(name);
				return c;
			}
		});
		put("ORG",get("ORGANIZATION"));
		put("LOCATION",new EntityCreator() {
			@Override
			public AEntity createEntity(String name) {
				Location l = new Location();
				l.setName(name);
				return l;
			}
		});
		put("OTROS",new EntityCreator() {
			@Override
			public AEntity createEntity(String name) {
				Other o = new Other();
				o.setName(name);
				return o;
			}
		});
	}};
	
}
