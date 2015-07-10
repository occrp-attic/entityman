package org.occrp.entityman.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

public class IngestedFile extends AMongoObject {

	public final static int STATUS_UNKN = -1;
	public final static int STATUS_NEW = 0;
	public final static int STATUS_INPROGRESS = 1;
	public final static int STATUS_COMPLETE = 2;
	public final static int STATUS_ERROR = 3;
	
	@Indexed
	private String workspace;
	
	@Indexed
	private int status = STATUS_UNKN;
	
	@Indexed
	public int cntExpanders = 0;
	
	private List<String> appliedExpanders = new ArrayList<String>(); 

	@Indexed
	public int cntExtractors = 0;
	
	private List<String> appliedExtractors = new ArrayList<String>(); 
	
	private Map<String,?> entities = new HashMap<String, Object>();
	
	private Map<String,Object> expandedData = new HashMap<String, Object>();

	@Indexed
	private String fileUri;
	
	private List<String> fileType = new ArrayList<String>();
	
	@Transient
	private File file;

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCntExpanders() {
		return cntExpanders;
	}

	public void setCntExpanders(int cntExpanders) {
		this.cntExpanders = cntExpanders;
	}

	public List<String> getAppliedExpanders() {
		return appliedExpanders;
	}

	public void setAppliedExpanders(List<String> appliedExpanders) {
		this.appliedExpanders = appliedExpanders;
	}

	public int getCntExtractors() {
		return cntExtractors;
	}

	public void setCntExtractors(int cntExtractors) {
		this.cntExtractors = cntExtractors;
	}

	public List<String> getAppliedExtractors() {
		return appliedExtractors;
	}

	public void setAppliedExtractors(List<String> appliedExtractors) {
		this.appliedExtractors = appliedExtractors;
	}

	public Map<String, ?> getEntities() {
		return entities;
	}

	public void setEntities(Map<String, ?> entities) {
		this.entities = entities;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public List<String> getFileType() {
		return fileType;
	}

	public void setFileType(List<String> fileType) {
		this.fileType = fileType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Map<String, Object> getExpandedData() {
		return expandedData;
	}

	public void setExpandedData(Map<String, Object> expandedData) {
		this.expandedData = expandedData;
	}
}