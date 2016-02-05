package org.occrp.entityman.glutton;

import java.util.ArrayList;
import java.util.List;

import org.occrp.entityman.glutton.filters.DictionaryFilter;
import org.occrp.entityman.glutton.filters.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import({Gluttony.class})
@PropertySource("classpath:application.properties")
public class TestConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer  propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DictionaryFilter filterPersonNameRo() {
		DictionaryFilter filter = new DictionaryFilter();
		filter.setFilterName("Person Entity Name Filter");
		filter.setEntityType("person");
		filter.setFieldName("name");
//		filter.setWhitelistResource("dic_firstnames_ro_small.txt");
		filter.setWhitelistResource("dic_firstnames_ro.txt");
		filter.setBlacklistResource("blacklist_person_name.txt");
		return filter;
	}
	

}
