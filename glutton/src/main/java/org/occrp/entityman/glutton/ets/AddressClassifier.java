package org.occrp.entityman.glutton.ets;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class AddressClassifier {
	public final int CLASS_UNKNOWN = 0;
	public final int CLASS_SEPARATOR_HARD = 1;
	public final int CLASS_SEPARATOR_SOFT = 2;
	public final int CLASS_ADDRESS_PART = 3;
	public final int CLASS_ADDRESS_HELPER = 4;
	public final int CLASS_NUMBER = 5;
	
	
	public static List<String> getWords(String text) {
	    List<String> words = new ArrayList<>();
	    BreakIterator breakIterator = BreakIterator.getWordInstance();
	    breakIterator.setText(text);
	    int lastIndex = breakIterator.first();
	    while (BreakIterator.DONE != lastIndex) {
	        int firstIndex = lastIndex;
	        lastIndex = breakIterator.next();
	        if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
	            words.add(text.substring(firstIndex, lastIndex));
	        }
	    }

	    return words;
	}

	public AddressClassifier(List<String> addressHelpers, List<String> addressParts) {
		for (String s : addressHelpers) {
			this.addressHelpers.put(s, new Integer(0));
		}
		
		for (String s : addressParts) {
			// TODO only 1st word
			for (String w : getWords(s)) {
				Set<String> set = this.addressParts.get(w);
				if (set == null) {
					set = new HashSet<>();
					this.addressParts.put(w.toLowerCase(), set);
				}
				set.add(s);
			}
		}
	}
	
	TreeMap<String,Integer> addressHelpers = new TreeMap<>();

	TreeMap<String,Set<String>> addressParts = new TreeMap<>();
	
	public boolean isNumber(String s) {
		return StringUtils.isNumeric(s);
	}
	
	public boolean isSeparatorHard(String s) {
		return ".".equals(s) || ";".equals(s);
	}
	
	public boolean isSeparatorSoft(String s) {
		return ",".equalsIgnoreCase(s);
	}

	public boolean isAddressHelper(String part) {
		return addressHelpers.containsKey(part);
	}

	/**
	 * Returns true if the parts contains the subparts at position pos
	 * 
	 * @param parts
	 * @param subparts
	 * @param pos
	 * @return
	 */
	public boolean isSubpart(List<String> parts, List<String> subparts, int pos) {
		boolean res = parts.size()>= subparts.size() + pos;
		for (int i=0;i<subparts.size();i++) {
			if (!parts.get(pos+i).equalsIgnoreCase(subparts.get(i))) {
				res = false;
				break;
			}
		}
		return res;
	}
	
	public int isAddressPart(String part, List<String> parts, int pos) {
		int res = 0;
		if (addressParts.containsKey(part.toLowerCase())) {
			Set<String> set = addressParts.get(part);
			
			for (String s : set) {
				List<String> words = getWords(s);
				// search for longest match
				if (isSubpart(parts, words, pos) && words.size() > res) {
					res = words.size();
				}
			}
		}
		return res;
	}
	
	public List<Integer> classify(List<String> parts) {
		List<Integer> res = new ArrayList<>();
		
		for (int i =0 ; i< parts.size();i++) {
			String part = parts.get(i);
			Integer type = CLASS_UNKNOWN;
			
			if (isNumber(part)) {
				type = CLASS_NUMBER;
			} 
			if (isSeparatorHard(part)) {
				type = CLASS_SEPARATOR_HARD;
			} 
			if (isSeparatorSoft(part)) {
				type = CLASS_SEPARATOR_SOFT;
			} 
			if (isAddressHelper(part)) {
				type = CLASS_ADDRESS_HELPER;
			} 
			
			int k = isAddressPart(part, parts, i);
			if (k>0) {
				type = CLASS_ADDRESS_PART;
				for (int j=1; j < k ; j++) res.add(type);
				i = i + k -1 ;
			}

			res.add(type);
			
		}

		return res;
	}
	
}
