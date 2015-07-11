package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;
import org.springframework.data.mongodb.core.index.Indexed;

@Entity
public class PersonIdno extends AEntity {

	@Indexed
	private String idno;
	
	@Override
	public String getLabel() {
		return idno;
	}

	@Override
	public void updateKey() {
		setKey(idno.toLowerCase());
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}
	
}
