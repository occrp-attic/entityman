package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;
import org.springframework.data.mongodb.core.index.Indexed;


@Entity
public class Company extends AEntity {

	@Indexed
	private String name;
	
	@Indexed
	private String address;
	
	@Indexed
	private String idno;

	@Override
	public void updateKey() {
		setKey((name+" "+idno).toLowerCase());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	@Override
	public String getLabel() {
		return name;
	}
}
