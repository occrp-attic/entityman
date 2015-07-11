package org.occrp.entityman.model.entities;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.occrp.entityman.model.annotation.Entity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

@Entity
public class Fact extends AEntity {

	@Transient
	public static final String KEY_EXCERPT = "excerpt";
	
	@Indexed
	private String entity;
	
	@Indexed
	private BigInteger entityId;
	
//	@Indexed
//	private BigInteger fileId;
	
	private long position = -1;
	
	private Map<String,Object> data = new HashMap<String, Object>();

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public BigInteger getEntityId() {
		return entityId;
	}

	public void setEntityId(BigInteger entityId) {
		this.entityId = entityId;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String getLabel() {
		return entity+":"+position;
	}

	@Override
	public void updateKey() {
		setKey(getId().toString(16));
	}
}
