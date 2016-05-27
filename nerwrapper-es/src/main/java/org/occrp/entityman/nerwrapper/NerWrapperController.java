package org.occrp.entityman.nerwrapper;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.entities.EntityList;
import org.occrp.entityman.model.entities.Fact;
import org.occrp.entityman.model.entities.GenericEntity;
import org.occrp.entityman.utils.EntitymanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;

@RestController
public class NerWrapperController {
	
	protected Logger log = LogManager.getLogger(getClass().getName());

	@RequestMapping(value = "/extract", method = RequestMethod.GET)
	public @ResponseBody ServiceResult<String> consume() {
		return new ServiceResult<String>(ServiceResult.CODE_OK, null, "Post files to this URL");
	}

	@RequestMapping(value = "/extract", method = RequestMethod.POST, consumes="*")
	public @ResponseBody ServiceResult<EntityList> extract(
			@RequestParam(value = "lang", defaultValue = "en") String lang,
			@RequestParam(value = "text") String body) {

		ServiceResult<EntityList> res = new ServiceResult<>();
		EntityList resEntities = new EntityList();
		try {
			EntityList entities = extract(body); 
					//se.extract(body);

			if (entities != null && entities.size() > 0) {
				resEntities.addAll(entities);
			}
			res.setC(ServiceResult.CODE_OK);
			res.setO(resEntities);
		} catch (Exception e) {
			log.error("Error extracting data from : {}", body, e);
		}

		return res;
	}

	@Autowired
	private ObjectMapper objectMapper;
	
	@RequestMapping(value = "/extractjson", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody ServiceResult<EntityList> extractjson(
			@RequestBody Map<String,Object> map) {

		log.debug("received request /extractjson : {}", map);
		
		ServiceResult<EntityList> res = new ServiceResult<>();
		EntityList resEntities = new EntityList();
		try {
//			Map<String,Object> map = objectMapper.readValue(body, Map.class);

			String text = (String)map.get("text");
			
			EntityList entities = extract(text); 
					//se.extract(body);
			
			if (entities != null && entities.size() > 0) {
				resEntities.addAll(entities);
			}
			res.setC(ServiceResult.CODE_OK);
			res.setO(resEntities);
		} catch (Exception e) {
			log.error("Error extracting data from : {}", map, e);
		}


		return res;
	}

	public static final String CLASSIFIER = "/edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz";

	AbstractSequenceClassifier<CoreMap> ner = CRFClassifier.getJarClassifier(CLASSIFIER,null);
	
	private Integer excerptRadius = 50;
	
	public EntityList extract(String text) {
	    EntityList res = new EntityList();
		
		if (text!=null) {
		    List<Triple<String, Integer, Integer>> entities = 
		    		ner.classifyToCharacterOffsets(text);
		    
		    for (Triple<String, Integer, Integer> entity : entities) {
		    	String t = text.substring(entity.second, entity.third); 
		    	log.debug("Found {} entity : {}", entity.first,t);
		    	
		    	GenericEntity e = new GenericEntity();
		    	e.setType(entity.first);
		    	e.setValue(t);
		    	
		    	Fact fact = new Fact();
				fact.setPosition(entity.second);
				fact.getData().put(Fact.KEY_EXCERPT, 
						EntitymanUtils.findExcerpt(text, entity.second(), entity.third(),excerptRadius));
				
				e.setFact(fact);
		    	
		    	res.add(e);
		    }
		}
		
		return res;
	}

}
