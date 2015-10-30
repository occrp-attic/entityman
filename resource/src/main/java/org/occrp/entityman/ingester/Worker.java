package org.occrp.entityman.ingester;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.dao.IngestedFileRepository;
import org.occrp.entityman.glutton.Gluttony;
import org.occrp.entityman.glutton.ets.Extractor;
import org.occrp.entityman.glutton.expanders.Expander;
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
	
	@Autowired
	Gluttony gluttony;

	protected Logger log = LogManager.getLogger(getClass().getName());

//	private List<Expander> expanders;
//	
//	private List<Extractor> extractors;
	
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
		
		List<AEntity> res = gluttony.call(file);
		
		res = entityManager.merge(res);
		
		ingestedFileRepository.save(file);

		// TODO and after completing the worker will move the file to complete/error folder
		
		return res;
	}

	public IngestedFile getFile() {
		return file;
	}

	public void setFile(IngestedFile file) {
		this.file = file;
	}

}
