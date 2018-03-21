package module;
 
import java.util.HashMap;
import java.util.Map;

//import com.ibm.icu.util.CharsTrie.Entry;
//import com.ibm.icu.util.CharsTrie.Iterator;

import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;

public class JapaneseTranslater {
	
	public static String jpnTranslate(String sentences, HashMap<String, String> sMap) throws Exception{
		SentenceToGraph s= new SentenceToGraph();
		//HashMap<String, String> phraseMap = MultiwordExpressionFinder.mweFinder(sentences);
		HashMap<String, String> phraseMap = new HashMap<String, String>();
		//From the phraseMap, update the original sentences.
		/*
		for(Map.Entry<String, String> e: phraseMap.entrySet()){
			String phrase = e.getValue();
			String pattern = phrase.replace(" ", "_");
			sentences = sentences.replace(phrase, pattern);
		}
		*/
		GraphPassingNode gpn = s.extractGraph(sentences, false, true,false);
		
		GraphPassingNode newgpn = Convert.convert(sentences, gpn,"jpn",phraseMap,sMap);
		/*
		for(String asp: newgpn.getAspGraph()){
			System.out.println(asp);
		}
		*/
		
//		GraphPassingNode gpnfinal2 = Transliteration.runTransliterator("jpn",newgpn, sMap);
		
		String translation = JapaneseGenerator.jpnGenerator(newgpn);
		return translation;
	}
}
