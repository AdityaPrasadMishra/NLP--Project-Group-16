package module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import module.graph.helper.JAWSutility;
import module.graph.resources.DependencyParserResource;
import module.graph.resources.InputDependencies;
import edu.mit.jmwe.data.IMWE;
import edu.mit.jmwe.data.IToken;
import edu.mit.jmwe.data.Token;
import edu.mit.jmwe.detect.Consecutive;
import edu.mit.jmwe.detect.IMWEDetector;
import edu.mit.jmwe.index.IMWEIndex;
import edu.mit.jmwe.index.MWEIndex;

public class MultiwordExpressionFinder {
	
	public static HashMap<String, String> mweFinder(String sentences){
		DependencyParserResource dps = new DependencyParserResource();
		InputDependencies idpt = dps.extractDependencies(sentences, false, 0);
		HashMap<String, String> posMap = idpt.getPosMap();
		HashMap<String, String> phraseMap = new HashMap<String, String>();
		
		sentences = sentences.replace(".","");
		String words[] = sentences.split(" ");
		JAWSutility j = new JAWSutility();
		
		File idxData = new File("./Resources/edu.mit.jmwe_1.0.1_all/mweindex_wordnet3.0_semcor1.6.data");
		IMWEIndex index = new MWEIndex(idxData); 
		try {
			index.open();
			IMWEDetector detector = new Consecutive(index);
			
			List <IToken > sentence = new ArrayList <IToken >();
			for(int i=0; i<words.length;i++){
				String key = words[i]+"-"+ (i+1);
				if(posMap.get(key).equals("VBD")||posMap.get(key).equals("VBG")||posMap.get(key).equals("VBN")||posMap.get(key).equals("VBP")||posMap.get(key).equals("VBZ"))
					sentence.add(new Token(words[i],posMap.get(key),j.getBaseForm(words[i],"v")));
				else
					sentence.add(new Token(words[i],posMap.get(key)));
			}
			
			List<IMWE<IToken>> mwes = detector.detect(sentence);
			for(IMWE <IToken > mwe : mwes){
				String phwords[] = mwe.getForm().split("_");
				String phrase = "";
				for(int k=0; k<phwords.length;k++){
					phrase = phrase + phwords[k] + " ";
				}
				phrase = phrase.substring(0, phrase.length()-1);
				String pattern = mwe.getForm();
				//System.out.println(phrase);
				//System.out.println(pattern);
				sentences = sentences.replaceAll(phrase, pattern);
				//System.out.println(sentences);
				
				pattern = pattern.replaceAll("_", "");
				phraseMap.put(pattern,phrase);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return phraseMap;
	}
}
