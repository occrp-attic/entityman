package org.occrp.entityman.model.entities;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.occrp.entityman.model.AMongoObject;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
public abstract class AEntity extends AMongoObject{
	
	@Indexed
	private String extractor;
	
	@Indexed
	private Set<BigInteger> fileIds = new HashSet<BigInteger>();

	@Indexed
	private String key;
	
	@Transient
	private Fact fact;
	
	@Indexed
	private String workspace;

	@Transient
	private String label;
	
	public abstract String getLabel();
	public void setLabel(){
	}

	@JsonIgnore
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

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

}
