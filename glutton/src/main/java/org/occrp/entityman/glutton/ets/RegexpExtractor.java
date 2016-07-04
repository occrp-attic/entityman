package org.occrp.entityman.glutton.ets;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.AExtractor;
import org.occrp.entityman.glutton.EntityUtils;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;

/**
 * Uses the "simpleText" data (existing from the expander) to apply regexes
 * 
 * @author iciubara
 *
 */
public class RegexpExtractor extends AExtractor {

	private List<String> patterns;
	private List<Pattern> ps;

	private String pattern;
	private Pattern p;
	
	private String entityName;
	private String entityKey;
	
	protected Logger log = LogManager.getLogger(getClass().getName());

	@Override
	public List<AEntity> extractSuper(IngestedFile file) {
		
		Object o = file.getExpandedData().get(AExpander.EXPKEY_SIMPLETEXT);
		
		List<AEntity> res = new ArrayList<AEntity>();
		
		if (o!=null) {
			String s = String.valueOf(o);

			List<Pattern> allPatterns = new ArrayList<Pattern>();
			if (p!=null) allPatterns.add(p);
			if (ps!=null) allPatterns.addAll(ps);
			
			for (Pattern x : allPatterns) {
				res.addAll(doPatternShit(x, s, file));
			}
		}
		
		return res;
	}

	private List<AEntity> doPatternShit(Pattern p, String s, IngestedFile file) {
		List<AEntity> res = new ArrayList<AEntity>();
		
		Matcher m = p.matcher(s);
		while (m.find()) {
			log.debug("Extractor {} found entity : {}", getName(),m.group());
			try {
				AEntity ae = EntityUtils.createNewEntity(entityName);
				EntityUtils.entitySetField(ae, entityKey, m.group());
				ae.getFileIds().add(file.getId());
				ae.setExtractor(getName());
				ae.setWorkspace(file.getWorkspace());
				
				Fact fact = new Fact();
//				fact.setEntity(entityName);
//				fact.setFileId(file.getId());
				fact.getFileIds().add(file.getId());
				fact.setPosition(m.start());
				fact.setPositionEnd(m.end());
				fact.setWorkspace(file.getWorkspace());
				fact.getData().put(Fact.KEY_EXCERPT, 
						findExcerpt(s, m.start(), m.end()));
				
				ae.setFact(fact);
				
				res.add(ae);
			} catch (Exception e) {
				log.warn("Error in entity storing : {} ", getName(), e);
			}
		}
		return res;
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		p = Pattern.compile(pattern);
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
		ps = new ArrayList<Pattern>();
		for (String s : patterns) {
			ps.add(Pattern.compile(s));
		}
	}
	
	
}
