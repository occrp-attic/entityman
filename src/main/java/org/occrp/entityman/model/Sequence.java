package org.occrp.entityman.model;

import org.springframework.data.mongodb.core.index.Indexed;

public class Sequence extends AMongoObject {
	
	@Indexed(unique=true)
	private String name;
	
	private Long counter=1L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}
	
}
