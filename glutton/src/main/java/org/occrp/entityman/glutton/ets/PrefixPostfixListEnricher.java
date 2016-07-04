package org.occrp.entityman.glutton.ets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.ahocorasick.trie.Trie;
import org.ahocorasick.trie.Trie.TrieBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.Enricher;
import org.occrp.entityman.glutton.EntityUtils;
import org.occrp.entityman.model.entities.AEntity;

public class PrefixPostfixListEnricher implements Enricher {

	protected Logger log = LogManager.getLogger(getClass().getName());

	private String fieldName;

	private int maxLenght = 100;

	private List<String> list;

	private String file;

	public boolean isSeparator(char ch) {
		return ch==' ' || ch==',' || ch=='.' || ch=='\t' || ch=='\n' || ch==';' || ch==':';
	}
	
	@Override
	public void tryEnrich(AEntity ae, String src) {
		int start = (int)ae.getFact().getPosition();
		int end = (int)ae.getFact().getPositionEnd();
		
		String prefix = src.substring(start - maxLenght < 0 ? 0 : start - maxLenght, start).trim();
		String sufix = src
				.substring(end, end + maxLenght > src.length() ? src.length() : end + maxLenght)
				.trim();

		// List must be ordered by length

		String newValue = null;

		for (String s : list) {
			try {
				if (prefix.toLowerCase().endsWith(s) && 
					isSeparator(prefix.charAt(prefix.length() - s.length()))) {
					newValue = s + " " + EntityUtils.entityGetField(ae, fieldName);
				}
				if (sufix.toLowerCase().startsWith(s) && 
					isSeparator(sufix.charAt(s.length()))) {
					newValue = EntityUtils.entityGetField(ae, fieldName) + " " + s;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error("Failed to get old value",e);
			}
		}
		
		if (newValue!=null) {
			try {
				EntityUtils.entitySetField(ae, fieldName, newValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error("Failed to set new value",e);
			}
		}
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;

		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
		});
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getMaxLenght() {
		return maxLenght;
	}

	public void setMaxLenght(int maxLenght) {
		this.maxLenght = maxLenght;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
		
		if (file==null || file.trim().length()==0) return;
		log.info("Loading list {} ...",file);
		try (InputStream is = new FileInputStream(file)) {
			List<String> lines = new BufferedReader(new InputStreamReader(is,
					StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
			
			list = new LinkedList<>();
			for (String line : lines) {
				line = line.toLowerCase().trim();
				if (line.length() > 0) {
					list.add(line);
				}
			}
			
			Collections.sort(list, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return Integer.compare(o1.length(), o2.length());
				}
			});
			log.info("Dictionary loaded with success {} ({})",file,lines.size());
		} catch (Exception e) {
			log.error("Failed to read data from resource : {}", file, e);
		}

	}

	
}