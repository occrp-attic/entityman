package org.occrp.entityman.glutton.ets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Company;
import org.occrp.entityman.model.entities.EntityList;
import org.occrp.entityman.model.entities.Fact;
import org.occrp.entityman.model.entities.GenericEntity;
import org.occrp.entityman.model.entities.Location;
import org.occrp.entityman.model.entities.Person;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;

public class RestStanfordExtractor extends AStanfordExtractor {

	protected Logger log = LogManager.getLogger(getClass().getName());

	public RestStanfordExtractor() {
	}
	
	private String nerWrapperUrl = "http://127.0.0.1:8080/nerwrapper/app/extractjson";
	
	@Override
	public List<AEntity> extract(IngestedFile file) {
		
	    List<AEntity> res = new ArrayList<AEntity>();
		
		Object o = file.getExpandedData().get(AExpander.EXPKEY_SIMPLETEXT);
		
		if (o!=null) {
			String text = String.valueOf(o);

			RestTemplate restTemplate = new RestTemplate();
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			Map<String,Object> map = new HashMap<>();
			map.put("text", text);
	        ServiceResult<List<AEntity>> sr = restTemplate.postForObject(nerWrapperUrl, 
	        		map, ServiceResult.class);

	        if (sr.getC()==ServiceResult.CODE_OK && sr.getO()!=null) {
		        for (AEntity e : sr.getO()) {
		        	GenericEntity ge = (GenericEntity) e;
			    	log.debug("Found {} entity : {}", ge.getType(), ge.getValue());
			    	
			    	EntityCreator ec = ecMapping.get(ge.getType());
			    	
			    	if (ec!=null) {
			    		AEntity ae = ec.createEntity(ge.getValue());
			    		ae.setExtractor(getName());
			    		ae.getFileIds().add(file.getId());
			    		ae.setWorkspace(file.getWorkspace());
			    		
			    		Fact fact = ge.getFact();
			    		fact.getFileIds().add(file.getId());
			    		fact.setWorkspace(file.getWorkspace());
						
						ae.setFact(fact);
						
			    		res.add(ae);
			    	} else {
			    		log.warn("EntityCreator missing for : {}", ge.getType());
			    	}
		        	
		        }
	        }
	        
		}
		
		return res;
	}

	public String getNerWrapperUrl() {
		return nerWrapperUrl;
	}

	public void setNerWrapperUrl(String nerWrapperUrl) {
		this.nerWrapperUrl = nerWrapperUrl;
	}
	
}
