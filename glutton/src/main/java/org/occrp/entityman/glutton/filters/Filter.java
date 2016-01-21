package org.occrp.entityman.glutton.filters;

import java.util.List;

import org.occrp.entityman.model.entities.AEntity;

public interface Filter {
	
	public List<AEntity> filter(List<AEntity> entities);
	
	public String getFilterName();

}
