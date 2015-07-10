package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;

@Entity
public class Email extends AEntity {
	
	private String email;

	@Override
	public void updateKey() {
		setKey(email);
	}

	public String getPhoneNumber() {
		return email;
	}

	public void setPhoneNumber(String email) {
		this.email = email;
	}
	
	@Override
	public String getLabel() {
		return email;
	}
}
