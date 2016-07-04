package org.occrp.entityman.glutton.ets;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.AExpander;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.Fact;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;

public class StanfordExtractor extends AStanfordExtractor {

	protected Logger log = LogManager.getLogger(getClass().getName());

	AbstractSequenceClassifier<CoreMap> ner;
	
	public StanfordExtractor() {
	    ner = CRFClassifier.getDefaultClassifier();
	}
	
	@Override
	public List<AEntity> extractSuper(IngestedFile file) {

	    List<AEntity> res = new ArrayList<AEntity>();
		
		Object o = file.getExpandedData().get(AExpander.EXPKEY_SIMPLETEXT);
		
		if (o!=null) {
			String text = String.valueOf(o);
		    List<Triple<String, Integer, Integer>> entities = 
		    		ner.classifyToCharacterOffsets(text);
		    
		    for (Triple<String, Integer, Integer> entity : entities) {
		    	String t = text.substring(entity.second, entity.third); 
		    	log.debug("Found {} entity : {}", entity.first,t);
		    	
		    	EntityCreator ec = ecMapping.get(entity.first);
		    	
		    	if (ec!=null) {
		    		AEntity ae = ec.createEntity(t);
		    		ae.setExtractor(getName());
		    		ae.getFileIds().add(file.getId());
		    		ae.setWorkspace(file.getWorkspace());
		    		
		    		Fact fact = new Fact();
		    		fact.getFileIds().add(file.getId());
//		    		fact.setFileId(file.getId());
		    		fact.setWorkspace(file.getWorkspace());
					fact.setPosition(entity.second);
					fact.getData().put(Fact.KEY_EXCERPT, 
							findExcerpt(text, entity.second(), entity.third()));
					
					ae.setFact(fact);
					
		    		res.add(ae);
		    	} else {
		    		log.warn("EntityCreator missing for : {}",entity.first);
		    	}
		    }
		}
		
		return res;
		
		//	    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
//	    Properties props = new Properties();
//	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//	    
//	    // read some text in the text variable
//	    String text = "Some text with names like Bob Marley and places like Paris and companies like Apple"; // Add your text here!
//	    
//	    // create an empty Annotation just with the given text
//	    Annotation document = new Annotation(text);
//	    
//	    // run all Annotators on this text
//	    pipeline.annotate(document);
//	    
//	    // these are all the sentences in this document
//	    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
//	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//	    
//	    for(CoreMap sentence: sentences) {
//	    	
//	      // traversing the words in the current sentence
//	      // a CoreLabel is a CoreMap with additional token-specific methods
//	      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
//	        // this is the text of the token
//	        String word = token.get(TextAnnotation.class);
//	        // this is the POS tag of the token
//	        String pos = token.get(PartOfSpeechAnnotation.class);
//	        // this is the NER label of the token
//	        String ne = token.get(NamedEntityTagAnnotation.class);
//	        
//	        log.info(" ------------- {} - {} - {}",word,pos,ne);
//	      }
//
//	      // this is the parse tree of the current sentence
//	      Tree tree = sentence.get(TreeAnnotation.class);
//
//	      // this is the Stanford dependency graph of the current sentence
//	      SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//	    }
//
//	    // This is the coreference link graph
//	    // Each chain stores a set of mentions that link to each other,
//	    // along with a method for getting the most representative mention
//	    // Both sentence and token offsets start at 1!
//	    Map<Integer,CorefChain> graph = document.get(CorefChainAnnotation.class);

	}

	public List<AEntity> extract(String text) {

	    List<AEntity> res = new ArrayList<AEntity>();
		
		if (text!=null) {
		    List<Triple<String, Integer, Integer>> entities = 
		    		ner.classifyToCharacterOffsets(text);
		    
		    for (Triple<String, Integer, Integer> entity : entities) {
		    	String t = text.substring(entity.second, entity.third); 
		    	log.debug("Found {} entity : {}", entity.first,t);
		    	
		    	EntityCreator ec = ecMapping.get(entity.first);
		    	
		    	if (ec!=null) {
		    		AEntity ae = ec.createEntity(t);
		    		ae.setExtractor(getName());
//		    		ae.getFileIds().add(file.getId());
//		    		ae.setWorkspace(file.getWorkspace());
		    		
		    		Fact fact = new Fact();
//		    		fact.getFileIds().add(file.getId());
//		    		fact.setWorkspace(file.getWorkspace());
					fact.setPosition(entity.second);
					fact.setPositionEnd(entity.third);
					fact.getData().put(Fact.KEY_EXCERPT, 
							findExcerpt(text, entity.second(), entity.third()));
					
					ae.setFact(fact);
					
		    		res.add(ae);
		    	} else {
		    		log.warn("EntityCreator missing for : {}",entity.first);
		    	}
		    	
		    	// TODO save entities
		    }
		}
		
		return res;

		
		//	    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
//	    Properties props = new Properties();
//	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//	    
//	    // read some text in the text variable
//	    String text = "Some text with names like Bob Marley and places like Paris and companies like Apple"; // Add your text here!
//	    
//	    // create an empty Annotation just with the given text
//	    Annotation document = new Annotation(text);
//	    
//	    // run all Annotators on this text
//	    pipeline.annotate(document);
//	    
//	    // these are all the sentences in this document
//	    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
//	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//	    
//	    for(CoreMap sentence: sentences) {
//	    	
//	      // traversing the words in the current sentence
//	      // a CoreLabel is a CoreMap with additional token-specific methods
//	      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
//	        // this is the text of the token
//	        String word = token.get(TextAnnotation.class);
//	        // this is the POS tag of the token
//	        String pos = token.get(PartOfSpeechAnnotation.class);
//	        // this is the NER label of the token
//	        String ne = token.get(NamedEntityTagAnnotation.class);
//	        
//	        log.info(" ------------- {} - {} - {}",word,pos,ne);
//	      }
//
//	      // this is the parse tree of the current sentence
//	      Tree tree = sentence.get(TreeAnnotation.class);
//
//	      // this is the Stanford dependency graph of the current sentence
//	      SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//	    }
//
//	    // This is the coreference link graph
//	    // Each chain stores a set of mentions that link to each other,
//	    // along with a method for getting the most representative mention
//	    // Both sentence and token offsets start at 1!
//	    Map<Integer,CorefChain> graph = document.get(CorefChainAnnotation.class);

	}
	
}
