package org.occrp.entityman.glutton;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.Expander;
import org.occrp.entityman.Extractor;
import org.occrp.entityman.glutton.filters.Filter;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Configuration
@Import({GluttonyConfig.class})
@Component
public class Gluttony {

	protected Logger log = LogManager.getLogger(getClass().getName());

	@Autowired
	private List<Expander> expanders;
	
	@Autowired
	private List<Extractor> extractors;

	@Autowired
	private List<Filter> filters;

	public List<AEntity> call(IngestedFile file) throws Exception {
		
		List<AEntity> res = new LinkedList<>();
		
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

		if (filters!=null) {
			for (Filter f : filters) {
				try {
					log.debug("{} filtering entities : {}",f.getFilterName(),res.size());
					res = f.filter(res);
				} catch (Exception ex) {
					log.error("Failed filter : {}",f.getFilterName(),ex);
				}
			}
			
		}

		//res = entityManager.merge(res);
		
		return res;
	}

}
