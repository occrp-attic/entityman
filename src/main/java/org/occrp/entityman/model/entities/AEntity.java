package org.occrp.entityman.model.entities;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;


import org.occrp.entityman.model.AMongoObject;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class AEntity extends AMongoObject{
	
	@Indexed
	private String extractor;
	
	@Indexed
	private Set<BigInteger> fileIds = new HashSet<BigInteger>();

	@Indexed
	private String key;
	
	@Transient
	private Fact fact;
	
	public abstract String getLabel();

	public abstract void updateKey();

	public String getKey() {
		updateKey();
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getExtractor() {
		return extractor;
	}

	public void setExtractor(String extractor) {
		this.extractor = extractor;
	}
	public Set<BigInteger> getFileIds() {
		return fileIds;
	}
	public void setFileIds(Set<BigInteger> fileIds) {
		this.fileIds = fileIds;
	}

	public Fact getFact() {
		return fact;
	}

	public void setFact(Fact fact) {
		this.fact = fact;
	}

}
