package org.occrp.entityman.ingester.ets;

import java.util.List;

import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;


/**
 * Entity handler, extracts entities from an IngestedFile
 * 
 * @author iciubara
 *
 */
public interface Extractor {
	
	public String getName();
	
	public List<AEntity> extract(IngestedFile file);

}
