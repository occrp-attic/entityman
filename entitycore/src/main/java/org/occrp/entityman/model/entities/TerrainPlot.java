package org.occrp.entityman.model.entities;

import org.occrp.entityman.model.annotation.Entity;
import org.springframework.data.mongodb.core.index.Indexed;

@Entity
public class TerrainPlot extends AEntity {

	@Indexed
	private String plotNumber;

	@Override
	public void updateKey() {
		setKey(plotNumber);
	}

	public String getPlotNumber() {
		return plotNumber;
	}

	public void setPlotNumber(String plotNumber) {
		this.plotNumber = plotNumber;
	}

	@Override
	public String getLabel() {
		return plotNumber;
	}
}
