package org.occrp.entityman.model;

import java.math.BigInteger;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@JsonTypeInfo(use=org.codehaus.jackson.annotate.JsonTypeInfo.Id.CLASS, include=As.PROPERTY, property="class")
//@JsonSerialize(using = BigIntegerSerializer.class)
public abstract class AMongoObject extends AObject {
	
	@Id
	private BigInteger id;
	
	@JsonIgnore
	private Date dob;
	
	@JsonIgnore
	private Date dom;

	/**
	 * User Of Creation, created by user
	 */
	@Indexed
	@JsonIgnore
	private String uoc;

	/**
	 * Modified by user
	 */
	@Indexed
	@JsonIgnore
	private String uom;
	
	@Indexed
	@JsonIgnore
	@com.fasterxml.jackson.annotation.JsonIgnore
	private boolean deleted = false;
	
	@Indexed
	@JsonIgnore
	private Long longId=null;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDom() {
		return dom;
	}

	public void setDom(Date dom) {
		this.dom = dom;
	}

	public String getUoc() {
		return uoc;
	}

	public void setUoc(String uoc) {
		this.uoc = uoc;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getLongId() {
		return longId;
	}

	public void setLongId(Long longId) {
		this.longId = longId;
	}

}