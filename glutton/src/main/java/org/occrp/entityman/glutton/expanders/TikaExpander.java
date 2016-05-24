package org.occrp.entityman.glutton.expanders;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ParserDecorator;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.model.IngestedFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;


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
