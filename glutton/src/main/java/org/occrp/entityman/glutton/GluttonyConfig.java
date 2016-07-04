package org.occrp.entityman.glutton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.occrp.entityman.Expander;
import org.occrp.entityman.Extractor;
import org.occrp.entityman.Filter;
import org.occrp.entityman.glutton.ets.AhoCorasickExtractor;
import org.occrp.entityman.glutton.ets.PersonNameEnricher;
import org.occrp.entityman.glutton.ets.PrefixPostfixListEnricher;
import org.occrp.entityman.glutton.ets.RegexpExtractor;
import org.occrp.entityman.glutton.ets.RestStanfordExtractor;
import org.occrp.entityman.glutton.ets.StanfordExtractor;
import org.occrp.entityman.glutton.expanders.OpenocrExpander;
import org.occrp.entityman.glutton.expanders.TikaExpander;
import org.occrp.entityman.glutton.filters.DictionaryBstFilter;
import org.occrp.entityman.glutton.filters.DictionaryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
public class GluttonyConfig {

//	@Autowired
//	private Environment env;
	
	
	@Bean
	public TikaExpander expanderTika(){
		TikaExpander tikaExpander = new TikaExpander();
		tikaExpander.setName("tika");
		return tikaExpander;
	}
	
	@Bean
	public OpenocrExpander expanderOpenOcr(){
		OpenocrExpander expander = new OpenocrExpander();
		expander.setName("openocr");
		return expander;
	}

	@Bean
	public List<Expander> expanders() {
		List<Expander> expanders = new ArrayList<>();
		expanders.add(expanderTika());
		expanders.add(expanderOpenOcr());
		
		return expanders;
	}
	
	@Bean 
	public RegexpExtractor extractorEmail() {
		RegexpExtractor regexpExtractor = new RegexpExtractor();
		regexpExtractor.setName("Email extractor");
		regexpExtractor.setEntityName("Email");
		regexpExtractor.setEntityKey("email");
		regexpExtractor.setPattern("\\s[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\s");
		return regexpExtractor;
	}

	@Bean 
	public RegexpExtractor extractorPhone() {
		RegexpExtractor regexpExtractor = new RegexpExtractor();
		regexpExtractor.setName("Phone number extractor");
		regexpExtractor.setEntityName("PhoneNumber");
		regexpExtractor.setEntityKey("phoneNumber");
		List<String> patterns = new ArrayList<>();
		patterns.add("\\b(0|)[1-9][0-9](\\s|)[0-9]{6}\\b");
		patterns.add("^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$");
		regexpExtractor.setPatterns(patterns);
		return regexpExtractor;
	}

	@Bean 
	public RegexpExtractor extractorPlotnumber() {
		RegexpExtractor regexpExtractor = new RegexpExtractor();
		regexpExtractor.setName("PlotNumber extractor");
		regexpExtractor.setEntityName("TerrainPlot");
		regexpExtractor.setEntityKey("plotNumber");
		regexpExtractor.setPattern("(\\s|^)\\d{7}\\.\\d{3,4}(\\s|$)");
		return regexpExtractor;
	}

	@Bean 
	public RegexpExtractor extractorPersonIdno() {
		RegexpExtractor regexpExtractor = new RegexpExtractor();
		regexpExtractor.setName("PersonIdno extractor");
		regexpExtractor.setEntityName("PersonIdno");
		regexpExtractor.setEntityKey("idno");
		regexpExtractor.setPattern("\\b\\d{13,14}\\b");
		return regexpExtractor;
	}

//	@Bean
//	public StanfordExtractor extractorStanford() {
//		StanfordExtractor stanfordExtractor = new StanfordExtractor();
//		stanfordExtractor.setName("Stanford extractor");
//		return stanfordExtractor;
//	}


	@Value("${nerwrapper.url}")
	String urlNerwrapper;

	@Value("${nerwrapper-es.url}")
	String urlNerwrapperSpanish;

	@Bean
	public RestStanfordExtractor extractorStanford() {
		RestStanfordExtractor stanfordExtractor = new RestStanfordExtractor();
		stanfordExtractor.setName("Stanford extractor");
		stanfordExtractor.setNerWrapperUrl(urlNerwrapper);
		return stanfordExtractor;
	}

	@Bean
	public RestStanfordExtractor extractorStanfordSpanish() {
		RestStanfordExtractor stanfordExtractor = new RestStanfordExtractor();
		stanfordExtractor.setName("Stanford extractor Spanish");
		stanfordExtractor.setNerWrapperUrl(urlNerwrapperSpanish);
		return stanfordExtractor;
	}

	@Value("${ace.dictionary.company.md}")
	String aceDictionaryCompaniesMd;

	@Value("${ace.prefixes.company.md}")
	String acePrefixesCompanyMd;

	@Bean
	public AhoCorasickExtractor aceCompaniesMd() {
		AhoCorasickExtractor ace = new AhoCorasickExtractor();
		
		ace.setDictionary(aceDictionaryCompaniesMd);
		ace.setName("aceCompaniesMd");
		ace.setEntityName("Company");
		ace.setEntityKey("name");
		
		PrefixPostfixListEnricher pple = new PrefixPostfixListEnricher();
		pple.setFieldName("name");
		pple.setFile(acePrefixesCompanyMd);
		
		ace.setEnrichers(Arrays.asList(pple));
		
		DictionaryFilter df = new DictionaryFilter();
		df.setEntityType("Company");
		df.setFieldName("name");
		df.setFilterName("Company prefix filter");
		df.setWhitelistResource(acePrefixesCompanyMd);

		ace.setFilters(Arrays.asList(df));
		
		return ace;
	}

	@Value("${ace.dictionary.persons}")
	String aceDictionaryPersons;

	@Bean
	public PersonNameEnricher pne() {
		PersonNameEnricher pne = new PersonNameEnricher();
		pne.setFieldName("name");

		return pne;
	}
	
	@Bean
	public AhoCorasickExtractor acePerson() {
		AhoCorasickExtractor ace = new AhoCorasickExtractor();
		
		ace.setDictionary(aceDictionaryPersons);
		ace.setName("acePersons");
		ace.setEntityName("Person");
		ace.setEntityKey("name");
		
		ace.setEnrichers(Arrays.asList(pne()));
		
		DictionaryBstFilter dbf = new DictionaryBstFilter();
		dbf.setEntityType("Person");
		dbf.setFieldName("name");
		dbf.setFilterName("Person name filter");
		dbf.setWhitelistResource(aceDictionaryPersons);

		ace.setFilters(Arrays.asList(dbf));
		
		return ace;
	}

	@Bean
	public List<Extractor> extractors() {
		List<Extractor> extractors = new ArrayList<>();
		extractors.add(extractorEmail());
		extractors.add(extractorPhone());
		extractors.add(extractorPlotnumber());
		extractors.add(extractorPersonIdno());
		extractors.add(extractorStanford());
		extractors.add(extractorStanfordSpanish());
		extractors.add(aceCompaniesMd());
		extractors.add(acePerson());
		
		return extractors;
	}

	@Value("${filter.person.name.whitelist}")
	String resourceWhitelist;
	@Value("${filter.person.name.blacklist}")
	String resourceBlacklist;
	
	@Bean
	public DictionaryBstFilter filterPersonNameWhiteList() {
		DictionaryBstFilter filter = new DictionaryBstFilter();
		filter.setFilterName("Person Entity Name Whitelist Filter");
		filter.setEntityType("person");
		filter.setFieldName("name");
		filter.setWhitelistResource(resourceWhitelist);
//		filter.setBlacklistResource(resourceBlacklist);
		return filter;
	}

	@Bean
	public DictionaryFilter filterPersonNameBlackList() {
		DictionaryFilter filter = new DictionaryFilter();
		filter.setFilterName("Person Entity Name Blacklist Filter");
		filter.setEntityType("person");
		filter.setFieldName("name");
//		filter.setWhitelistResource(resourceWhitelist);
		filter.setBlacklistResource(resourceBlacklist);
		return filter;
	}
	
	@Bean
	public List<Filter> filters() {
		List<Filter> filters = new ArrayList<>();
		filters.add(filterPersonNameBlackList());
		filters.add(filterPersonNameWhiteList());
		
		return filters;
	}
	
}
