package org.occrp.entityman.glutton.ets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.ahocorasick.trie.Trie.TrieBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.AExtractor;
import org.occrp.entityman.glutton.EntityUtils;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;

import javassist.compiler.ast.Keyword;

/**
 * Uses the Aho-Corasick algorithm to find string[] matches in text
 * 
 * @author iciubara
 *
 */
public class AhoCorasickExtractor extends AExtractor {

	private String dictionary;
	private List<String> list;

	private String entityName;
	private String entityKey;
	
	Trie trie=null;
	
	protected Logger log = LogManager.getLogger(getClass().getName());

	@Override
	public List<AEntity> extractSuper(IngestedFile file) {
		
		Object o = file.getExpandedData().get(AExpander.EXPKEY_SIMPLETEXT);
		
		List<AEntity> res = new ArrayList<AEntity>();
		
		if (o!=null) {
			String s = String.valueOf(o);

			Collection<Emit> emits = trie.parseText(s);
			
			for (Emit emit : emits) {
				res.add(doShit(emit, s, file));
			}
		}
		
		return res;
	}

	private AEntity doShit(Emit emit, String s, IngestedFile file) {
		AEntity ae = EntityUtils.createNewEntity(entityName);
		try {
			EntityUtils.entitySetField(ae, entityKey, s.substring(emit.getStart(), emit.getEnd()+1));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("Failed to set field : {}",entityKey,e);
		}
		ae.getFileIds().add(file.getId());
		ae.setExtractor(getName());
		ae.setWorkspace(file.getWorkspace());
				
		Fact fact = new Fact();
//		fact.setEntity(entityName);
//		fact.setFileId(file.getId());
		fact.getFileIds().add(file.getId());
		fact.setPosition(emit.getStart());
		fact.setPositionEnd(emit.getEnd()+1);
		fact.setWorkspace(file.getWorkspace());
		fact.getData().put(Fact.KEY_EXCERPT, 
			findExcerpt(s, emit.getStart(), emit.getEnd()));
		
		ae.setFact(fact);
		
		return ae;
	}
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
		
		if (dictionary==null || dictionary.trim().length()==0) return;
		log.info("Loading dictionary {} ...",dictionary);
		try (InputStream is = new FileInputStream(dictionary)) {
			List<String> lines = new BufferedReader(new InputStreamReader(is,
					StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
			
			TrieBuilder tb = Trie.builder().removeOverlaps().onlyWholeWords().caseInsensitive();
			
			long cntKeywords = 0; 
			for (String line : lines) {
				line = line.trim();
				if (line.length() > 0) {
					tb.addKeyword(line);
					cntKeywords++;
				}
			}
			
			trie = tb.build();
			log.info("Dictionary loaded with success {} ({})",dictionary,cntKeywords);
		} catch (Exception e) {
			log.error("Failed to read data from resource : {}", dictionary, e);
		}
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
		
		TrieBuilder tb = Trie.builder().removeOverlaps().onlyWholeWords().caseInsensitive();
		
		for (String s : list) {
			tb.addKeyword(s);
		}
		
		trie = tb.build();
	}

	
}
