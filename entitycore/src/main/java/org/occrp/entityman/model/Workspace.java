package org.occrp.entityman.model;

public class Workspace extends AMongoObject {

	private String name;

	private Long ingestCount = 0L;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIngestCount() {
		return ingestCount;
	}

	public void setIngestCount(Long ingestCount) {
		this.ingestCount = ingestCount;
	}
	
}
