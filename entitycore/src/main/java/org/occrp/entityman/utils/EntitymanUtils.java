package org.occrp.entityman.utils;

public class EntitymanUtils {

	static public String findExcerpt(String s, int start, int end, int radius) {
		start = start - radius;
		end = end + radius;
		
		String text = s.substring(start < 0 ? 0 : start, 
				end > s.length() ? s.length() : end);
		
		text = text.replace("\n", " ").replace("\r", " ")
				.replace("\t", " ");
		
		while (text.indexOf("  ")>=0) text = text.replace("  ", " ");
		
		return text;
	}

}
