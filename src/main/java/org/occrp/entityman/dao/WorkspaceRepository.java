package org.occrp.entityman.dao;

import org.occrp.entityman.model.Workspace;
import org.springframework.data.mongodb.repository.Query;

public interface WorkspaceRepository extends AMongoObjectRepository<Workspace>{

	@Query(value="{ 'name' : ?0 }")
	public Workspace findBy(String name);
	
}
