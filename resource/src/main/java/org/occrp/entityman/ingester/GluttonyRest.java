package org.occrp.entityman.ingester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.EntityList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class GluttonyRest {

	protected Logger log = LogManager.getLogger(getClass().getName());

	@Value("${gluttonwar.url:http://127.0.0.1:18080/gluttonwar/app/consume}")
	private String urlGluttony;
	
	public List<AEntity> call(IngestedFile file) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		restTemplate.getInterceptors().addAll(Arrays.asList(new LoggingRequestInterceptor()));
        
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("file", new FileSystemResource(file.getFile()));
		parts.add("fileId", file.getId().toString());
		parts.add("workspace", file.getWorkspace());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ServiceResult<EntityList> sr = restTemplate.postForObject(urlGluttony, 
        		parts, ServiceResult.class);

        if (sr.getC()==ServiceResult.CODE_OK && sr.getO()!=null) {
        	List<AEntity> res = sr.getO();
        	
//    		for (AEntity ae : res) {
//    			ae.getFileIds().add(file.getId());
//    			ae.setWorkspace(file.getWorkspace());
//    			ae.getFact().setWorkspace(file.getWorkspace());
//    			ae.getFact().getFileIds().add(file.getId());
//    		}
    		
    		return res;
        } else {
        	log.error("Failed to query gluttonwar {}",sr);
        	return new ArrayList<>();
        }
        
	}
}
