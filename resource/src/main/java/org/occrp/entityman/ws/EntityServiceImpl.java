package org.occrp.entityman.ws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
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
import org.occrp.entityman.AExpander;
import org.occrp.entityman.dao.FactRepository;
import org.occrp.entityman.dao.IngestedFileRepository;
import org.occrp.entityman.dao.WorkspaceRepository;
import org.occrp.entityman.ingester.Worker;
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
import org.springframework.data.domain.Sort;

@WebService
@Path("/entities/")
public class EntityServiceImpl implements EntityService {

	
	
	protected Logger log = LogManager.getLogger(getClass().getName());

	@Autowired
	private Worker worker;
	
	@Autowired
	private WorkspaceRepository workspaceRepository;
	
	@Autowired
	private FactRepository factRepository;
	
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

	Sort sortDescDom = new Sort(new Sort.Order(Sort.Direction.DESC,"dom"));
	Sort sortDescDob = new Sort(new Sort.Order(Sort.Direction.DESC,"dob"));
	
	@Override
	@GET
	@Path("/workspaces/")
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Description("Returns currently available workspaces")
	public Set<Workspace> getWorkspaces() {
		Set<Workspace> res = new LinkedHashSet<>();
		
		//TODO user
		String user= "";
		
		List<Workspace> ws = workspaceRepository.findAll(sortDescDom);
		
		res.addAll(ws);

		if (res.size()==0) {
			res.add(makeWorkspace(WORKSPACE_DEFAULT));
		}
		
		return res;
	}

	
	@Override
	public Workspace makeWorkspace(String name) {
		// TODO user
		String user = "";
		Workspace w = workspaceRepository.findBy(name); 

		if (w == null ) {
			w = new Workspace();
			w.setName(name);
			w.setUser(user);
			w.setPath(createWorkspacePath(name));
			workspaceRepository.save(w);
		}
		
		return w;
	}

	
	
	@Override
	public Workspace removeWorkspace(String name) {
		// TODO user
		String user = "";
		
		Workspace w = workspaceRepository.findBy(name);
		
		if (w!=null) {
			log.info("Removing workspace : {}",w);

			Set<Class> entityClasses = new HashSet<Class>();
			entityClasses.addAll(entityManager.getEntityMap().values());
			entityClasses.add(Fact.class);
			entityClasses.add(IngestedFile.class);
			
			Long countEntities = 0L; 
			for (Class c : entityClasses) {
				log.info("removing entities from workspaces by type : {}",c.getSimpleName());
				countEntities += entityManager.removeAllEntities(c,name);
			}
			
			log.info("Removing folder for workspace {} {}",name,w.getPath());
			if (w.getPath()!=null) {
				try {
					FileUtils.deleteDirectory(new File(w.getPath()));
				} catch (IOException e) {
					log.error("Failed to remove files from workspace path : {}", w.getPath(),e);
				}
			}
			
			w.setEntitiesCount(countEntities);
			
			workspaceRepository.delete(w.getId());
		}
		
		return w;
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
		
		AEntity entity = entityManager.findEntityById(entityManager.getEntityClass(entityName), 
				new BigInteger(id)); 
		
		if (entity instanceof IngestedFile) {
			IngestedFile infi = (IngestedFile) entity;
			infi.getEntities().put("simpleText", infi.getExpandedData().get(AExpander.EXPKEY_SIMPLETEXT));
		}
		
		sr.setO(entity);

		return sr;
	}

	@Override
	public ServiceResult<String> ingestAsync(String workspace) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Value("${folder.input:/opt/entityman/input}")
//	private String folder_input;
//	@Value("${folder.complete:/opt/entityman/complete}")
//	private String folder_complete;
//	@Value("${folder.error:/opt/entityman/error}")
//	private String folder_error;
//	@Value("${folder.tmp:/opt/entityman/tmp}")
//	private String folder_tmp;

	@Value("${folder.data:/opt/entityman/data}")
	private String folder_data;

	public String createWorkspacePath(String workspaceName) {
		String path = folder_data + 
				(folder_data.endsWith("/") ? "":"/") + workspaceName;
		return path;
	}

	public File createFilePath(String workspaceName,String filename) throws IOException {
		String path = createWorkspacePath(workspaceName);
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
		
		String user="";
		Workspace w = workspaceRepository.findBy(workspace);
		if (w==null) {
			w = new Workspace();
			w.setName(workspace);
			w.setUser(user);
			w.setPath(createWorkspacePath(workspace));
		}

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
				
//				FileUtils.copyInputStreamToFile(is, of);
				Files.copy(is, Paths.get(of.toURI()),StandardCopyOption.REPLACE_EXISTING);
				
				
				IngestedFile file = new IngestedFile();
				file.setWorkspace(workspace);
				file.setOriginalFilename(filename);
				file.setFile(of);
				file.setFileUri(of.toString());
				
				aes.addAll(worker.call(file));
				aes.add(file);
				w.setIngestCount(w.getIngestCount()+1);
			}

			workspaceRepository.save(w);

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

	private Map<String,List<AEntity>> getAllEntities(String workspaceName){
		Map<String,List<AEntity>> map = new HashMap<>();
		Set<Class> entityClasses = new HashSet<Class>();
		entityClasses.addAll(entityManager.getEntityMap().values());
		entityClasses.add(Fact.class);
		entityClasses.add(IngestedFile.class);
		
		for (Class c : entityClasses) {
			List<AEntity> aes = entityManager.findAllEntities(c,workspaceName);
			map.put(c.getSimpleName(), aes);
		}
		return map;
	}
	
	@Override
	public ServiceResult<Map<String, Object>> getWorkspace(String name) {
		
		ServiceResult<Map<String, Object>> sr = 
				new ServiceResult<Map<String,Object>>();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Workspace w = workspaceRepository.findBy(name);
		
		if (w!=null) {
			Long entitiesCount = 0L;
			
			map.put("workspace", w);
			Set<Class> entityClasses = new HashSet<Class>();
			entityClasses.addAll(entityManager.getEntityMap().values());
			entityClasses.add(Fact.class);
			entityClasses.add(IngestedFile.class);
			
			for (Class c : entityClasses) {
				List<AEntity> aes = entityManager.findAllEntities(c,name);
				map.put(c.getSimpleName(), aes);
				entitiesCount+=aes.size();
			}
			
			w.setEntitiesCount(entitiesCount);
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
			
			String fileName = fi.getOriginalFilename();
			try {
				fileName = URLEncoder.encode(fi.getOriginalFilename(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			} 
			response = Response.ok((Object) file).encoding("UTF-8")
				.header("Content-Disposition",
					"attachment; filename="+ fileName)
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
//		entityClasses.add(Person.class);
//		entityClasses.add(PhoneNumber.class);
//		entityClasses.add(Location.class);
//		entityClasses.add(Email.class);
		entityClasses.add(IngestedFile.class);
		
		BigInteger id = new BigInteger(fileId);
		
		for (Class c : entityClasses) {
			List<AEntity> aes = entityManager.findAllEntitiesByFileId(c, id);
			map.put(c.getSimpleName(), aes);
		}
		
		sr.setO(map);
		
		return sr;
	}

	@Override
	public ServiceResult<List<Fact>> getFactsForEntity(String entityType, 
			String entityId) {
		ServiceResult<List<Fact>> sr = new ServiceResult<>();
		
		sr.setO(factRepository.findByEntity(entityType.toLowerCase(), 
				new BigInteger(entityId)));
		
		return sr;
	}

	@Override
	public ServiceResult<Map<String, Map<String,Integer>>> getFactStats(String name) {
		List<Fact> facts = factRepository.findAll();

		Map<String,Map<String,Integer>> res = new HashMap<>();
		for (Fact fact : facts) {
			String entityId = fact.getEntityId().toString(); 
			Map<String,Integer> m1 = res.get(fact.getEntity());
			if (m1 == null) {
				m1 = new HashMap<>();
				res.put(fact.getEntity(), m1);
			}
			
			Integer c = m1.get(entityId);
			if (c == null) {
				c = 0;
			}
			c++;
			m1.put(entityId, c);
		}
		
		ServiceResult<Map<String,Map<String,Integer>>> sr = new ServiceResult<>();
		
		sr.setO(res);
		return sr;
	}

	@Autowired
	IngestedFileRepository ingestedFileRepository;
	
	@Override
	public ServiceResult<String> removeFile(String fileId) {
		// TODO user
		String user = "";

		String res = "File not found"; 
		ServiceResult<String> sr = new ServiceResult<>();
		sr.setC(ServiceResult.CODE_ERROR);
		
		IngestedFile ifile = ingestedFileRepository.findOne(new BigInteger(fileId));
		
		if (ifile!=null) {
			log.info("Removing File : {}",ifile);
			
			Workspace w = workspaceRepository.findBy(ifile.getWorkspace());

			if (w!=null) {
				try {
					FileUtils.forceDelete(new File(ifile.getFileUri()));
				} catch (IOException e) {
					log.error("Failed to delete file : {}",ifile,e);
				}
				
				ingestedFileRepository.delete(ifile);
				
				w.setIngestCount(w.getIngestCount()-1);
				
				workspaceRepository.save(w);
				sr.setC(ServiceResult.CODE_OK);
				res = "File was removed :"+fileId;
			} else {
				res = "Workspace not found";
			}
		}
		
		sr.setO(res);
		return sr;
	}

	
}
