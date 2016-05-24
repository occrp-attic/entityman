package org.occrp.entityman.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Workspace extends AMongoObject {

	private String name;
	private String user;

	private Long ingestCount = 0L;
	private Long entitiesCount = 0L;
	private String description;

	@JsonIgnore
	private String path;
	
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getEntitiesCount() {
		return entitiesCount;
	}

	public void setEntitiesCount(Long entitiesCount) {
		this.entitiesCount = entitiesCount;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
		
}
