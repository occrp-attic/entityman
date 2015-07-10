package org.occrp.entityman.model;

import java.math.BigInteger;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@JsonTypeInfo(use=org.codehaus.jackson.annotate.JsonTypeInfo.Id.CLASS, include=As.PROPERTY, property="class")
public abstract class AMongoObject extends AObject {
	
	@Id
	private BigInteger id;
	
	private Date dob;
	
	private Date dom;

	/**
	 * User Of Creation, created by user
	 */
	@Indexed
	private String uoc;

	/**
	 * Modified by user
	 */
	@Indexed
	private String uom;
	
	@Indexed
	private boolean deleted = false;
	
	@Indexed
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
