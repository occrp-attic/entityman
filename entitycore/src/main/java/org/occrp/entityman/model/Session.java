package org.occrp.entityman.model;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

public class Session extends AMongoObject {
	
	@Indexed
	private String sid; 
	
	@Indexed
	private BigInteger userId;

	@Indexed
	private Date expiry;
	
	@Indexed
	private boolean active;
	
	
	// TODO request count, ...
}
