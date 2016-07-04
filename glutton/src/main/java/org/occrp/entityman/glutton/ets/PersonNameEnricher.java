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

public class PersonNameEnricher implements Enricher {

	protected Logger log = LogManager.getLogger(getClass().getName());

	private String fieldName;

	private int maxLenght = 100;

	private List<String> list;

	private String file;

	public boolean isSeparator(char ch) {
		return ch==' ' || ch==',' || ch=='.' || ch=='\t' || ch=='\n' || ch==';' || ch==':';
	}

	public boolean isHardSeparator(char ch) {
		return ch==',' || ch==';';
	}

	@Override
	public void tryEnrich(AEntity ae, String src) {
		int start = (int)ae.getFact().getPosition();
		int end = (int)ae.getFact().getPositionEnd();
		
		String prefix = src.substring(start - maxLenght < 0 ? 0 : start - maxLenght, start).trim();
		String sufix = src
				.substring(end, end + maxLenght > src.length() ? src.length() : end + maxLenght)
				.trim();

		String newValue;
		try {
			newValue = (String) EntityUtils.entityGetField(ae, fieldName);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("Failed",e);
			return;
		}

		StringBuilder sb= new StringBuilder();
		boolean cont = true;
		char lastCh=' ';
		boolean lastWordAcronym = false;
		for (int i=0;i<sufix.length();i++) {
			char ch = sufix.charAt(i);
			if ( lastCh==' ' && ch>='A' && ch<='Z') cont = true;
			
			if (isSeparator(ch)) {
				// word complete
				if (sb.length()>0) {
					lastWordAcronym = false;
					if (sb.charAt(0)>='A' && sb.charAt(0)<='Z') {
						newValue = newValue+ " "+sb.toString();
					}
					if (sb.length()==1 && ch=='.') {
						lastWordAcronym = true;
						newValue = newValue + ".";
					}
				}
				cont=false;
				sb = new StringBuilder();
			} else {
				sb.append(ch);
			}
			
			if ((isSeparator(lastCh) && ch!=' ' && !cont) ||
					isHardSeparator(lastCh) ||
					(!lastWordAcronym && lastCh=='.') 
					) break;
			
			lastCh = ch;
		}

		
		// do prefix shit
		sb= new StringBuilder();
		cont = true;
		lastCh=' ';
		for (int i=prefix.length()-1;i>0;i--) {
			char ch = prefix.charAt(i);
			if ( ch==' ' && lastCh>='A' && lastCh<='Z') cont = true;
			
			if (isSeparator(ch)) {
				// word complete
				if (sb.length()>0) {
					if (lastCh>='A' && lastCh<='Z') {
						if (sb.charAt(sb.length()-1)=='.' && sb.length()>2) {
							// stop
						} else {
							newValue = sb.toString() + " " + newValue;
						}
					}
				}
				cont= false;
				if (lastCh>='A' && lastCh<='Z') cont=true;
				sb = new StringBuilder();
				if (ch=='.' && sb.length()==0) sb.append(ch);
			} else {
				sb.insert(0, ch);
			}
			
			if ((isSeparator(ch) && lastCh!=' ' && !cont) ||
					isHardSeparator(ch) ) break;
			
			lastCh = ch;
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