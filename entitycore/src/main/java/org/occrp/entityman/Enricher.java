package org.occrp.entityman;

import org.occrp.entityman.model.entities.AEntity;

public interface Enricher {
	public void tryEnrich(AEntity ae, String src);
}