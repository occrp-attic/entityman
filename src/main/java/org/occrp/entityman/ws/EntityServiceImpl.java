package org.occrp.entityman.ws;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import org.apache.cxf.jaxrs.model.wadl.Description;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.annotation.Entity;
import org.reflections.Reflections;
import org.springframework.util.ReflectionUtils;

@WebService
@Path("/entities/")
public class EntityServiceImpl implements EntityService {

	Set<String> entitySet = null;
	
	@Override
	@GET
	@Path("/types/")
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Description("Returns currently available Entity types")
	public Set<String> getEntityTypes() {
		// TODO improve (is dirty)
		if (entitySet==null) {
			Reflections reflections = new Reflections(
					"org.occrp.entityman.model");
			
			Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
			entitySet = new HashSet<String>();
			for (Class c : classes) {
				entitySet.add(c.getSimpleName());
			}
		}
		
		return entitySet;
	}

	@Override
	@GET
	@Path("/workspaces/")
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Description("Returns currently available workspaces")
	public Set<String> getWorkspaces() {
		Set<String> res = new HashSet<String>();
		
		res.add("default");

		return res;
	}

	@Override
	@POST
	@Path("/ingest/{workspace}")
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Description("Ingest Files")
	public ServiceResult<String> ingestAsync(
			@Description("Workspace to ingest into ") @PathParam("workspace") @DefaultValue("default") String workspace) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@POST
	@Path("/ingestSync/{workspace}")
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Description("Ingest Files")
	public ServiceResult<String> ingestSync(
			@Description("Workspace to ingest into ") @PathParam("workspace") @DefaultValue("default") String workspace) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
