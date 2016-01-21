package org.occrp.entityman.glutton.filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.model.entities.AEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StreamUtils;

public class DictionaryFilter extends AFilter{

	private String whitelistResource;

	private String blacklistResource;
	
	private String whitelistCsv;
	
	private String blacklistCsv;
	
	private List<String> whitelist;
	private List<String> blacklist;

	protected Logger log = LogManager.getLogger(getClass().getName());
	
	public String getWhitelistResource() {
		return whitelistResource;
	}

	private void fillListFromResource(List<String> list, String resource) {
		try (InputStream is = getClass().getResourceAsStream(resource)) {
			List<String> lines = new BufferedReader(new InputStreamReader(is,
					StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
			for (String line : lines) {
				line = line.trim();
				if (line.length() > 0)
					list.add(line);
			}
		} catch (Exception e) {
			log.error("Failed to read data from resource : {}", resource, e);
		}
	}
	
	private void fillListFromCsv(List<String> list, String csv) {
		try {
			for (String t : StringUtils.split(csv, ',')) {
				t = t.trim();
				if (t.length()>0) list.add(t);
			}
		} catch (Exception e) {
			log.error("Failed to read data from string : {}", csv, e);
		}
	}

	public void setWhitelistResource(String whitelistResource) {
		this.whitelistResource = whitelistResource;
		whitelist = new LinkedList<>();
		fillListFromResource(whitelist, whitelistResource);
	}

	public String getBlacklistResource() {
		return blacklistResource;
	}

	public void setBlacklistResource(String blacklistResource) {
		this.blacklistResource = blacklistResource;
		blacklist = new LinkedList<>();
		fillListFromResource(blacklist, blacklistResource);
	}

	public String getWhitelistCsv() {
		return whitelistCsv;
	}

	public void setWhitelistCsv(String whitelistCsv) {
		this.whitelistCsv = whitelistCsv;
		whitelist = new LinkedList<>();
		fillListFromCsv(whitelist, whitelistCsv);
	}

	public String getBlacklistCsv() {
		return blacklistCsv;
	}

	public void setBlacklistCsv(String blacklistCsv) {
		this.blacklistCsv = blacklistCsv;
		blacklist = new LinkedList<>();
		fillListFromCsv(blacklist, blacklistCsv);
	}

	private boolean isInList(AEntity ae, List<String> list) {
		boolean res = false;
		try {
			Field field = ReflectionUtils.findField(ae.getClass(), fieldName);
			ReflectionUtils.makeAccessible(field);
			
			String o = String.valueOf(field.get(ae));
			
			res = isValueInList(o, list, true);
			
		} catch (Exception e) {
			log.warn("Failed to do list filtering on field {} : {}", fieldName, ae,e);
		}
		
		return res;
	}
	
	private boolean isWhite(AEntity ae) {
		return isInList(ae, whitelist);
	}
	
	private boolean isBlack(AEntity ae) {
		return isInList(ae, blacklist);
	}

	private boolean isValueInList(String value, List<String> list, boolean ignoreCase) {
		boolean res = false;
		String val = " "+(ignoreCase ? value.toLowerCase() : value) + " ";
		for (String s : list) {
			String ss = " "+(ignoreCase ? s.toLowerCase() : s)+" ";
			if (val.indexOf(ss)>=0) {
				res = true;
				break;
			}
		}
		return res;
	}
	
	@Override
	public List<AEntity> filter(List<AEntity> entities) {
		List<AEntity> tmp = new LinkedList<>();
		
		if (whitelist!=null && whitelist.size()>0) {
			for (AEntity ae : entities) {
				if (entityType==null || ae.getClass().getSimpleName().equalsIgnoreCase(entityType)) {
					if (isWhite(ae)) tmp.add(ae);
				} else {
					tmp.add(ae);
				}
			}
			entities = tmp;
		} 

		tmp = new LinkedList<>();
		if (blacklist!=null && blacklist.size()>0) {
			for (AEntity ae : entities) {
				if (entityType==null || ae.getClass().getSimpleName().equalsIgnoreCase(entityType)) {
					if (!isBlack(ae)) tmp.add(ae);
				} else {
					tmp.add(ae);
				}
			}
			entities = tmp;
		} 
		
		return entities;
	}

}
