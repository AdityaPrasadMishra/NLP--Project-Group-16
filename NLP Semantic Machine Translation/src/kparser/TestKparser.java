package kparser;

import static org.junit.Assert.assertNotNull;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.jws.soap.SOAPBinding;

import module.Convert;
import module.Convert2;
import module.HindiGenerator;
import module.HindiHelper;
import module.HindiTranslater;
import module.JapaneseGenerator;
import module.JapaneseTranslater;
import module.MultiwordExpressionFinder;
import module.Transliteration;
import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;
import module.graph.helper.JAWSutility;
import module.graph.helper.NEObject;
import module.graph.resources.DependencyParserResource;
import module.graph.resources.InputDependencies;
import module.graph.resources.NamedEntityTagger;
import edu.mit.jmwe.data.IMWE;
import edu.mit.jmwe.data.IToken;
import edu.mit.jmwe.data.Token;
import edu.mit.jmwe.detect.Consecutive;
import edu.mit.jmwe.detect.IMWEDetector;
import edu.mit.jmwe.index.IMWEIndex;
import edu.mit.jmwe.index.MWEIndex;

public class TestKparser {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String sentences = "Jonh works for IBM at Aizona ? ";
//		SentenceToGraph stg = new SentenceToGraph();
//		GraphPassingNode gpn = stg.extractGraph("Tom went to the market.", false, true,false);
//		System.out.println(gpn.getposMap());
//		System.exit(0);

		NEObject neo = NamedEntityTagger.tagNamedEntities(sentences);
		HashMap<String, String> sMap = neo.getNamedEntityMap();
		System.out.println( "here" +sMap);
//		String sent = NamedEntityTagger.getModifiedSentence();
//		HashMap<String, String> sMap = NamedEntityTagger.getStringToNamedEntityMap();
		 
		String translation1  = HindiTranslater.hinTranslate(sentences, sMap);
		System.out.println(translation1);
//		String translation1 = HindiTranslater.hinTranslate(sentences, sMap);
//		System.out.println(translation1);

		Convert.wordMap.clear();
		
		String translation = JapaneseTranslater.jpnTranslate(sentences,sMap);
		System.out.println(translation);

		  	
	}

}
