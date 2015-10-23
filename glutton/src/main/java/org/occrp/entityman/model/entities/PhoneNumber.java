package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;
import org.springframework.data.mongodb.core.index.Indexed;

@Entity
public class PhoneNumber extends AEntity {
	
	@Indexed
	private String phoneNumber;

	@Override
	public void updateKey() {
		setKey(phoneNumber.toLowerCase());
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String getLabel() {
		return phoneNumber;
	}
}
