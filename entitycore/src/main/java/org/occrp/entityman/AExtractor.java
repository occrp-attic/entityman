package org.occrp.entityman;

import java.util.List;
import java.util.Map;

import org.occrp.entityman.Extractor;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.utils.EntitymanUtils;


public abstract class AExtractor implements Extractor {

	private String name;

	protected List<AFilter> filters;

	protected List<Enricher> enrichers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
	Map<String,Class<AEntity>> entityMap = null;
	
	private int excerptRadius = 50;
	
	@Override
	final public List<AEntity> extract(IngestedFile file) {
		file.getAppliedExtractors().add(name);
		file.setCntExtractors(file.getCntExtractors()+1);
		List<AEntity> entities = extractSuper(file);
		
		entities = doEnrich(entities,
				String.valueOf(file.getExpandedData().get(AExpander.EXPKEY_SIMPLETEXT)));
		entities = doFilter(entities);
		
		return entities;
	}

	abstract public List<AEntity> extractSuper(IngestedFile file);

	public List<AEntity> doFilter(List<AEntity> entities) {
		List<AEntity> res = entities;
		if (filters!=null) {
			for (Filter filter : filters) {
				res = filter.filter(res);
			}
		}
		return res;
	}
	
	public List<AEntity> doEnrich(List<AEntity> aes, String src) {
		List<AEntity> res = aes;
		if (enrichers!=null) {
			for (AEntity ae : res) {
				for (Enricher enricher : enrichers) {
					enricher.tryEnrich(ae, src);
				}
			}
		}
		
		return res;
	}

	public String findExcerpt(String s, int start, int end) {
		return EntitymanUtils.findExcerpt(s, start, end, excerptRadius);
	}

	public List<AFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<AFilter> filters) {
		this.filters = filters;
	}

	public int getExcerptRadius() {
		return excerptRadius;
	}

	public void setExcerptRadius(int excerptRadius) {
		this.excerptRadius = excerptRadius;
	}

	public List<Enricher> getEnrichers() {
		return enrichers;
	}

	public void setEnrichers(List<Enricher> enrichers) {
		this.enrichers = enrichers;
	}

}

