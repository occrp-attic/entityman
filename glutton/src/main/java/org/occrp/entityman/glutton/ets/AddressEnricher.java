package org.occrp.entityman.glutton.ets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.ahocorasick.trie.Trie;
import org.ahocorasick.trie.Trie.TrieBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.Enricher;
import org.occrp.entityman.glutton.EntityUtils;
import org.occrp.entityman.model.entities.AEntity;

public class AddressEnricher implements Enricher {

	protected Logger log = LogManager.getLogger(getClass().getName());

	private String fieldName;

	private int maxLenght = 100;


	private String fileAddressParts;
	private String fileAddressHelpers;
	
	private AddressClassifier ac;
	
	private AddressClassifier getAddressClassifier() {
		if (ac == null) {
			ac = new AddressClassifier(
					EntityUtils.readFile(fileAddressHelpers), 
					EntityUtils.readFile(fileAddressParts));
		}
		
		return ac;
	}
	
	@Override
	public void tryEnrich(AEntity ae, String src) {
		
		
		int start = (int)ae.getFact().getPosition();
		int end = (int)ae.getFact().getPositionEnd();
		
		String prefix = src.substring(start - maxLenght < 0 ? 0 : start - maxLenght, start).trim();
		String suffix = src
				.substring(end, end + maxLenght > src.length() ? src.length() : end + maxLenght)
				.trim();

		// List must be ordered by length

		String newValue = null;

		List<String> prefixSentence = AddressClassifier.getWords(prefix);
		List<String> suffixSentence = AddressClassifier.getWords(suffix);
		
		List<Integer> prefixClasses = getAddressClassifier().classify(prefixSentence);
		List<Integer> suffixClasses = getAddressClassifier().classify(suffixSentence);
		
		for (int i = prefixClasses.size() ; i > 0 ; i--) {
			// search for start
			
			 // TODO search HOLES
		}
		
		if (newValue!=null) {
			try {
				EntityUtils.entitySetField(ae, fieldName, newValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error("Failed to set new value",e);
			}
		}
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

}