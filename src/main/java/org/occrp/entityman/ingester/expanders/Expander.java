package org.occrp.entityman.ingester.expanders;

import org.occrp.entityman.model.IngestedFile;

/**
 * Expander is extending current IngestedFile with additional data. 
 *  
 * @author iciubara
 *
 */
public interface Expander {
	
//	public void setName(String name);
	public String getName();

	public void expand(IngestedFile file);
}
