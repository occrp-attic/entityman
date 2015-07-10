package org.occrp.entityman.model;

import java.io.IOException;
import java.math.BigInteger;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class BigIntegerSerializer extends JsonSerializer<BigInteger> 
	implements InitializingBean {

	@Autowired
	JacksonJsonProvider jsonProvider;
	
	@Autowired
	ObjectMapper jacksonMapper;
	
	@Autowired
	CustomSerializerFactory serializerFactory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		jsonProvider. setMapper(jacksonMapper);
		jacksonMapper.setSerializerFactory(serializerFactory);
		jacksonMapper.setSerializationInclusion(Inclusion.NON_NULL);
		serializerFactory.addSpecificMapping(BigInteger.class, this);
	}

	@Override
	public void serialize(BigInteger value, org.codehaus.jackson.JsonGenerator jgen,
			SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(value.toString());
	}
}
