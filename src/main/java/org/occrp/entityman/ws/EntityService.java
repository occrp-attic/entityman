package org.occrp.entityman.ws;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.entities.AEntity;

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

	@GET
	@Path("/workspace/{name}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns currently available workspaces")
	public ServiceResult<Map<String,Object>> getWorkspace(
			@Description("Workspace name to retrieve") @PathParam("name")
			@DefaultValue("default") String name);	

	@GET
	@Path("/all/{entityName}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns currently available workspaces")
	public ServiceResult<List<List<Object>>> getAllEntities(
			@Description("Entity type to retrieve") @PathParam("entityName")
			@DefaultValue("IngestedFile") String entityName);	

	@GET
	@Path("/byId/{entityName}/{id}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns currently available workspaces")
	public ServiceResult<AEntity> getEntity(
			@Description("Entity type to retrieve") @PathParam("entityName")
			@DefaultValue("IngestedFile") String entityName,
			@Description("Entity id to retrieve") @PathParam("id")
			String id);	

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
	@Consumes("multipart/form-data")
	@Description("Ingest Files")
	public ServiceResult<List<AEntity>> ingestSync(
			MultipartBody body,
		    @Context HttpServletRequest request,			
			@Description("Workspace to ingest into ") @PathParam("workspace") 
			@DefaultValue("default") String workspace);	
	
	@GET
	@Path("/getFile/{id}")
	@Description("Retrieve File by ID")
	public Response getFile(
			@Description("Id of the file") @PathParam("id") 
			String fileId);
}
