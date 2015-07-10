package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;
import org.springframework.data.mongodb.core.index.Indexed;

@Entity
public class Location extends AEntity {

	@Indexed
	private String name;
	
	@Override
	public void updateKey() {
		// TODO Auto-generated method stub
		setKey(name.toLowerCase());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getLabel() {
		return name;
	}
	
}
