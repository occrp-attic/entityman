package org.occrp.entityman.glutton.filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.model.entities.AEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StreamUtils;

public class DictionaryBstFilter extends AFilter{

	private String whitelistResource;

	private String blacklistResource;
	
	private String whitelistCsv;
	
	private String blacklistCsv;
	
	private TreeMap<String,Integer> whitelist;
	private TreeMap<String,Integer> blacklist;

	private boolean ignoreCase = true;
	
	private boolean doSplit = true;
	
	protected Logger log = LogManager.getLogger(getClass().getName());
	
	public String getWhitelistResource() {
		return whitelistResource;
	}

	private void fillListFromResource(TreeMap<String,Integer> dic, String resource) {
		if (resource==null || resource.trim().length()==0) return;
		log.info("Loading dictionary {} ...",resource);
//		try (InputStream is = getClass().getResourceAsStream(resource)) {
		try (InputStream is = new FileInputStream(resource)) {
			List<String> lines = new BufferedReader(new InputStreamReader(is,
					StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
			for (String line : lines) {
				line = line.trim();
				if (line.length() > 0)
					dic.put(ignoreCase ? line.toLowerCase() : line, 0);
			}
			log.info("Dictionary loaded with success {} ({})",resource,dic.size());
		} catch (Exception e) {
			log.error("Failed to read data from resource : {}", resource, e);
		}
	}
	
	private void fillListFromCsv(TreeMap<String,Integer> dic, String csv) {
		try {
			for (String t : StringUtils.split(csv, ',')) {
				t = t.trim();
				if (t.length()>0) 
					dic.put(ignoreCase ? t.toLowerCase() : t, 0);
			}
		} catch (Exception e) {
			log.error("Failed to read data from string : {}", csv, e);
		}
	}

	public void setWhitelistResource(String whitelistResource) {
		this.whitelistResource = whitelistResource;
		whitelist = new TreeMap<>();
		fillListFromResource(whitelist, whitelistResource);
	}

	public String getBlacklistResource() {
		return blacklistResource;
	}

	public void setBlacklistResource(String blacklistResource) {
		this.blacklistResource = blacklistResource;
		blacklist = new TreeMap<>();
		fillListFromResource(blacklist, blacklistResource);
	}

	public String getWhitelistCsv() {
		return whitelistCsv;
	}

	public void setWhitelistCsv(String whitelistCsv) {
		this.whitelistCsv = whitelistCsv;
		whitelist = new TreeMap<>();
		fillListFromCsv(whitelist, whitelistCsv);
	}

	public String getBlacklistCsv() {
		return blacklistCsv;
	}

	public void setBlacklistCsv(String blacklistCsv) {
		this.blacklistCsv = blacklistCsv;
		blacklist = new TreeMap<>();
		fillListFromCsv(blacklist, blacklistCsv);
	}

	private boolean isInDic(AEntity ae, TreeMap<String,Integer> dic) {
		boolean res = false;
		try {
			Field field = ReflectionUtils.findField(ae.getClass(), fieldName);
			ReflectionUtils.makeAccessible(field);
			
			String o = String.valueOf(field.get(ae));
			
			res = isValueInDic(o, dic);
			
		} catch (Exception e) {
			log.warn("Failed to do list filtering on field {} : {}", fieldName, ae,e);
		}
		
		return res;
	}
	
	private boolean isWhite(AEntity ae) {
		return isInDic(ae, whitelist);
	}
	
	private boolean isBlack(AEntity ae) {
		return isInDic(ae, blacklist);
	}

	private String[] split(String s) {
		String[] res = null;
		if (doSplit) {
			res = s.split("[^a-zA-Z0-9]+");
		}
		if (res==null) res = new String[]{s};
		return res;
	}
	
	private boolean isValueInDic(String value, TreeMap<String,Integer> dic) {
		boolean res = false;
		String val = ignoreCase ? value.toLowerCase() : value;
		for (String s : split(val)) {
			if (dic.containsKey(s)) {
				dic.put(s, dic.get(s)+1);
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
