package org.occrp.entityman.model.entities;

import java.util.LinkedList;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY)
@com.fasterxml.jackson.annotation.JsonTypeInfo(
		use=com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, 
		include=com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY)
public class EntityList extends LinkedList<AEntity>{

}
