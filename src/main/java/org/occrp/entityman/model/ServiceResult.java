package org.occrp.entityman.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
@XmlRootElement(name = "ServiceResult")
public class ServiceResult<T> extends AObject{

	final static public int CODE_NEW=0;
	final static public int CODE_ERROR=-1;
	final static public int CODE_OK=1;
	
	
	private int c;
	private String m;

	@XmlElement
	private T o;
	
	public ServiceResult() {
	}
	
	public ServiceResult(int code, String message, T object) {
		this.c=code;
		this.m=message;
		this.o=object;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public T getO() {
		return o;
	}

	public void setO(T o) {
		this.o = o;
	}
	
}
