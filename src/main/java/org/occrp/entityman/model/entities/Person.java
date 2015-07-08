package org.occrp.entityman.model.entities;

import java.util.List;

import org.occrp.entityman.model.annotation.Entity;


@Entity
public class Person extends AEntity {

	private String name;
	
	private List<String> altNames;

	@Override
	public void updateKey() {
		setKey(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
