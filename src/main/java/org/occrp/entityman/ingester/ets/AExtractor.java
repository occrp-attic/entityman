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
	
	private int excerptRadius = 50;
	
	public String findExcerpt(String s, int start, int end) {
		start = start - excerptRadius;
		end = end + excerptRadius;
		
		return s.substring(start < 0 ? 0 : start, 
				end > s.length() ? s.length() : end);
	}
	
}

