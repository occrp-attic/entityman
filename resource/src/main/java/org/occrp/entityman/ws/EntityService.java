package org.occrp.entityman.ws;

import java.math.BigInteger;
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
import org.occrp.entityman.model.AMongoObject;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;

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
	@Description("Returns all entities in the workspace")
	public ServiceResult<Map<String,Object>> getWorkspace(
			@Description("Workspace name to retrieve") @PathParam("name")
			@DefaultValue("default") String name);	

	@GET
	@Path("/factstats/{name}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns all entities in the workspace")
	public ServiceResult<Map<String, Map<String,Integer>>> getFactStats(
			@Description("Workspace name to retrieve") @PathParam("name")
			@DefaultValue("default") String name);	

	@GET
	@Path("/all/{entityName}/{workspace}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns currently available workspaces")
	public ServiceResult<List<List<Object>>> getAllEntities(
			@Description("Entity type to retrieve") @PathParam("entityName")
			@DefaultValue("IngestedFile") String entityName,
			@Description("Workspace to retrieve") @PathParam("workspace")
			@DefaultValue("default") String workspace);	

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

	@GET
	@Path("/byFileId/{entityName}/{fileId}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns entities by file id")
	public ServiceResult<List<AEntity>> getEntitiesByFileId(
			@Description("Entity type to retrieve") @PathParam("entityName")
			@DefaultValue("Fact") String entityName,
			@Description("File id") @PathParam("fileId")
			String fileId);	

	@GET
	@Path("/AllByFileId/{fileId}")
	@Produces(defaultMimeType)
	@Consumes(defaultMimeType)
	@Description("Returns entities by file id")
	public ServiceResult<Map<String,List<AEntity>>> getEntitiesByFileId(
			@Description("File id") @PathParam("fileId")
			String fileId);	

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
	
	@GET
	@Path("/getFacts/{entityType}/{id}")
	@Produces(defaultMimeType)
	@Description("Retrieve the facts by entity")
	public ServiceResult<List<Fact>> getFactsForEntity(
			@Description("Entity Type") @PathParam("entityType") 
			String entityType,
			@Description("Id of the entity") @PathParam("id") 
			String entityId);
	
}
