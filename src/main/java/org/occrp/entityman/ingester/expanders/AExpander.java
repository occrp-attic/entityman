package org.occrp.entityman.ingester.expanders;

import org.occrp.entityman.model.IngestedFile;

public abstract class AExpander implements Expander {

	static public final String EXPKEY_SIMPLETEXT="simpleText";
	
	@Override
	public void expand(IngestedFile file) {
		file.getAppliedExpanders().add(name);
		file.setCntExpanders(file.getCntExpanders()+1);
		expandSuper(file);
	}
	
	public abstract void expandSuper(IngestedFile file);

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
