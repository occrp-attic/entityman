package org.occrp.entityman.ws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.measure.quantity.AmountOfSubstance;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.dao.WorkspaceRepository;
import org.occrp.entityman.ingester.Worker;
import org.occrp.entityman.model.AMongoObject;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.Workspace;
import org.occrp.entityman.model.annotation.Entity;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;
import org.occrp.entityman.service.EntityManager;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@WebService
@Path("/entities/")
public class EntityServiceImpl implements EntityService {

	protected Logger log = LogManager.getLogger(getClass().getName());

	@Autowired
	private Worker worker;
	
	@Autowired
	private WorkspaceRepository workspaceRepository;
	
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
		
		List<Workspace> ws = workspaceRepository.findAll();
		
		for (Workspace w : ws) {
			res.add(w.getName());
		}

		return res;
	}

	@Override
	public ServiceResult<List<List<Object>>> getAllEntities(
			String entityName, String workspace) {
		ServiceResult<List<List<Object>>> sr = new ServiceResult<>();
		
		List<List<Object>> res = new ArrayList<>();
		
		Class entityClass =entityManager.getEntityClass(entityName); 
		
		if (entityClass==null) {
			sr.setC(ServiceResult.CODE_ERROR);
			sr.setM("Entity Type Not found");
		} else {
			List<AEntity> aes = entityManager.findAllEntities(entityClass, workspace);
			
			for (AEntity ae : aes) {
				List<Object> t = new ArrayList<>();
				t.add(ae.getId());
				t.add(ae.getLabel());
				res.add(t);
			}
			sr.setO(res);
		}
		
		return sr;
	}

	@Override
	public ServiceResult<AEntity> getEntity(String entityName, String id) {
		ServiceResult<AEntity> sr = new ServiceResult<>();
		
		sr.setO(entityManager.findEntityById(entityManager.getEntityClass(entityName), 
				new BigInteger(id)));

		return sr;
	}

	@Override
	public ServiceResult<String> ingestAsync(String workspace) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Value("${folder.input:/opt/entityman/input}")
	private String folder_input;
	@Value("${folder.complete:/opt/entityman/complete}")
	private String folder_complete;
	@Value("${folder.error:/opt/entityman/error}")
	private String folder_error;
	@Value("${folder.tmp:/opt/entityman/tmp}")
	private String folder_tmp;

	public File createFilePath(String workspaceName,String filename) throws IOException {
		String path = folder_tmp + 
				(folder_tmp.endsWith("/") ? "":"/") + workspaceName;
		FileUtils.forceMkdir(new File(path));
		for (int i = -1; i < 1000000; i++) {
			File file = new File(path+"/"+(i<0?"":"a"+i+"_")+filename);
			if (file.exists()==false) return file;
		}
		return null;
	}
	
	@Override
	public ServiceResult<List<AEntity>> ingestSync(MultipartBody body,
			HttpServletRequest request, String workspace) {

		ServiceResult<List<AEntity>> sr = new ServiceResult<>();
		
		Workspace w = workspaceRepository.findBy(workspace);
		if (w==null) {
			w = new Workspace();
			w.setName(workspace);
		}

		w.setIngestCount(w.getIngestCount()+1);
		workspaceRepository.save(w);

		List<IngestedFile> files = new ArrayList<IngestedFile>();
		List<AEntity> aes = new ArrayList<>();
		
		try {
			for (Attachment a : body.getAllAttachments()) {
				InputStream is = a.getObject(InputStream.class);
				
				for (String s : a.getHeaders().keySet()) {
					log.info("Header : {} : {}",s,a.getHeader(s));
				}
				
				String filename = a.getContentId();
				if (a.getContentDisposition()!=null && 
						a.getContentDisposition().getParameter("filename")!=null) {
					filename = a.getContentDisposition().getParameter("filename"); 
				}
				
				File of = createFilePath(workspace, filename);
				log.debug("Storing file : {}",of);
				
				FileUtils.copyInputStreamToFile(is, of);
				
				IngestedFile file = new IngestedFile();
				file.setWorkspace(workspace);
				file.setOriginalFilename(filename);
				file.setFile(of);
				file.setFileUri(of.toString());
				
				aes.addAll(worker.call(file));
				aes.add(file);
			}

			sr.setO(aes);
			sr.setC(ServiceResult.CODE_OK);
		} catch (Exception e) {
			sr.setC(ServiceResult.CODE_ERROR);
			sr.setM("Failed to ingest files : "+e.getMessage());
			log.error("Failed to post files",e);
		}
		
		// TODO 
		// 1. store the file 
		// 2. create an IngestedFile object
		// 3. call worker
		// 4. return result
		
		return sr;
	}

	@Autowired
	EntityManager entityManager;
	
	@Override
	public ServiceResult<Map<String, Object>> getWorkspace(String name) {
		
		ServiceResult<Map<String, Object>> sr = 
				new ServiceResult<Map<String,Object>>();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Workspace w = workspaceRepository.findBy(name);
		
		if (w!=null) map.put("workspace", w);

		Set<Class> entityClasses = new HashSet<Class>();
		entityClasses.addAll(entityManager.getEntityMap().values());
		entityClasses.add(Fact.class);
		entityClasses.add(IngestedFile.class);
		
		for (Class c : entityClasses) {
			List<AEntity> aes = entityManager.findAllEntities(c,name);
			map.put(c.getSimpleName(), aes);
		}
		
		sr.setO(map);
		
		return sr;
	}

	@Override
	public Response getFile(String fileId) {
		IngestedFile fi = entityManager.findEntityById(IngestedFile.class, 
				new BigInteger(fileId));
		
		ResponseBuilder response=Response.noContent();
		
		if (fi!=null) {
			File file = new File(fi.getFileUri());
			response = Response.ok((Object) file)
				.header("Content-Disposition",
					"attachment; filename="+fi.getOriginalFilename())
				.type(MediaType.APPLICATION_OCTET_STREAM);
		}
		return response.build();		
	}

	@Override
	public ServiceResult<List<AEntity>> getEntitiesByFileId(String entityName, String fileId) {
		ServiceResult<List<AEntity>> sr = new ServiceResult<>();
		
		Class<AEntity> clazz = entityManager.getEntityClass(entityName);
		
		if (clazz==null) {
			sr.setC(ServiceResult.CODE_ERROR);
			sr.setM("Entity Type not found");
		} else {
			List<AEntity> aes = entityManager.findAllEntitiesByFileId(clazz, new BigInteger(fileId));
			sr.setO(aes);
		}
		
		return sr;
	}

	@Override
	public ServiceResult<Map<String,List<AEntity>>> getEntitiesByFileId(String fileId) {
		ServiceResult<Map<String, List<AEntity>>> sr = new ServiceResult<>();
		
		Map<String,List<AEntity>> map = new HashMap<>();
		
//		Workspace w = workspaceRepository.findBy(name);
//		
//		if (w!=null) map.put("workspace", w);

		Set<Class> entityClasses = new HashSet<Class>();
		entityClasses.addAll(entityManager.getEntityMap().values());
		entityClasses.add(Fact.class);
		entityClasses.add(IngestedFile.class);
		
		BigInteger id = new BigInteger(fileId);
		
		for (Class c : entityClasses) {
			List<AEntity> aes = entityManager.findAllEntitiesByFileId(c, id);
			map.put(c.getSimpleName(), aes);
		}
		
		sr.setO(map);
		
		return sr;
	}
	
	
}
