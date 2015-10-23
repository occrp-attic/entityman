package org.occrp.entityman.glutton;

import java.util.ArrayList;
import java.util.List;

import org.occrp.entityman.glutton.ets.Extractor;
import org.occrp.entityman.glutton.ets.RegexpExtractor;
import org.occrp.entityman.glutton.ets.StanfordExtractor;
import org.occrp.entityman.glutton.expanders.Expander;
import org.occrp.entityman.glutton.expanders.OpenocrExpander;
import org.occrp.entityman.glutton.expanders.TikaExpander;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GluttonyConfig {

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

	@Bean
	public StanfordExtractor extractorStanford() {
		StanfordExtractor stanfordExtractor = new StanfordExtractor();
		stanfordExtractor.setName("Stanford extractor");
		return stanfordExtractor;
	}

	@Bean
	public List<Extractor> extractors() {
		List<Extractor> extractors = new ArrayList<>();
		extractors.add(extractorEmail());
		extractors.add(extractorPhone());
		extractors.add(extractorPlotnumber());
		extractors.add(extractorPersonIdno());
		extractors.add(extractorStanford());
		
		return extractors;
	}
	
}