package org.occrp.entityman.glutton.ets;

import java.util.Map;

import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.utils.EntitymanUtils;


public abstract class AExtractor implements Extractor {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
	Map<String,Class<AEntity>> entityMap = null;
	
	private int excerptRadius = 50;
	
	public String findExcerpt(String s, int start, int end) {
		return EntitymanUtils.findExcerpt(s, start, end, excerptRadius);
	}

}

