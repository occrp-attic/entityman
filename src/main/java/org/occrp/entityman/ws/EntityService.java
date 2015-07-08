package org.occrp.entityman.ws;

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

@WebService
@Description("Entity accessing services")
public interface EntityService extends ARestService{

	@GET
	@Path("/entityTypes/")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns currently available Entity types")
	public Set<String> getEntityTypes();	
	
	@GET
	@Path("/workspaces/")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns currently available workspaces")
	public Set<String> getWorkspaces();	

	@POST
	@Path("/ingest/{workspace}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Ingest Files")
	public ServiceResult<String> ingestAsync( 
			@Description("Workspace to ingest into ") @PathParam("workspace")
			@DefaultValue("default") String workspace);	

	@POST
	@Path("/ingestSync/{workspace}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Ingest Files")
	public ServiceResult<String> ingestSync( 
			@Description("Workspace to ingest into ") @PathParam("workspace")
			@DefaultValue("default") String workspace);	
	
}
