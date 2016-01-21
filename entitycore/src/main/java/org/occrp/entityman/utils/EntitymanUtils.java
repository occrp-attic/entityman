package org.occrp.entityman.utils;

public class EntitymanUtils {

	static public String findExcerpt(String s, int start, int end, int radius) {
		start = start - radius;
		end = end + radius;
		
		return s.substring(start < 0 ? 0 : start, 
				end > s.length() ? s.length() : end);
	}

}
