package org.occrp.entityman.ingester;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.dao.FactRepository;
import org.occrp.entityman.dao.IngestedFileRepository;
import org.occrp.entityman.ingester.ets.Extractor;
import org.occrp.entityman.ingester.expanders.Expander;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.service.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This is the worker class that does actual File processing
 * 
 * 
 * @author iciubara
 *
 */
public class Worker {

	protected Logger log = LogManager.getLogger(getClass().getName());

	private List<Expander> expanders;
	
	private List<Extractor> extractors;
	
	private IngestedFile file=null;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	IngestedFileRepository ingestedFileRepository;
	
	public Worker() {
	}

	public Worker(IngestedFile file) {
		this.file = file;
	}

	public List<AEntity> call(IngestedFile file) throws Exception {
		ingestedFileRepository.save(file);
		
		List<AEntity> res = new LinkedList<AEntity>();
		
		for (Expander e : expanders) {
			try {
				log.debug("{} expanding : {}",e.getName(),file.getFileUri());
				e.expand(file);
			} catch (Exception ex) {
				log.error("Failed expander : {}",e.getName(),ex);
			}
		}
		
		for (Extractor e : extractors) {
			try {
				log.debug("{} expanding : {}",e.getName(),file.getFileUri());
				res.addAll(e.extract(file));
				
			} catch (Exception ex) {
				log.error("Failed expander : {}",e.getName(),ex);
			}
		}

		res = entityManager.merge(res);

		// and after completing the worker will move the file to complete/error folder
		
		return res;
	}

	public List<Expander> getExpanders() {
		return expanders;
	}

	public void setExpanders(List<Expander> expanders) {
		this.expanders = expanders;
	}

	public List<Extractor> getExtractors() {
		return extractors;
	}

	public void setExtractors(List<Extractor> extractors) {
		this.extractors = extractors;
	}

	public IngestedFile getFile() {
		return file;
	}

	public void setFile(IngestedFile file) {
		this.file = file;
	}

}
