package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;

@Entity
public class PhoneNumber extends AEntity {
	
	private String phoneNumber;

	@Override
	public void updateKey() {
		setKey(phoneNumber);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
