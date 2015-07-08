package org.occrp.entityman.ingester.ets;

import java.util.Map;

import org.occrp.entityman.model.entities.AEntity;


public abstract class AExtractor implements Extractor {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
	Map<String,Class<AEntity>> entityMap = null;
	
	
}
