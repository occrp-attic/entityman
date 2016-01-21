package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;

@Entity
public class Email extends AEntity {
	
	private String email;

	@Override
	public void updateKey() {
		setKey(email.trim().toLowerCase());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getLabel() {
		return email;
	}
}
