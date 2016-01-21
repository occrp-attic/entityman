package org.occrp.entityman.model.entities;

public class GenericEntity extends AEntity {
	
	private String type;
	private String value;
	
	@Override
	public String getLabel() {
		return null;
	}

	@Override
	public void updateKey() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
