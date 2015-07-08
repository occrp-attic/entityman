package org.occrp.entityman.ingester.expanders;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.occrp.entityman.model.IngestedFile;


public class TikaExpander extends  AExpander {

	protected Logger log = LogManager.getLogger(getClass().getName());

	@Override
	public void expandSuper(IngestedFile file) {
		
	    Tika tika = new Tika();
	    FileInputStream fis=null;
	    try {
	    	fis = new FileInputStream(file.getFile());
	        String s = tika.parseToString(fis);
	        file.getExpandedData().put(AExpander.EXPKEY_SIMPLETEXT, s);
	    } catch (Exception e) {
	    	log.error("Failed to expand {} file : ",getName(),file.getFileUri(),e);
	    	
	    } finally {
	        if (fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					log.error(e);
				}
	        }
	    }
		
	}


}
