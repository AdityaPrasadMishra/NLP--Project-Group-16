package module;
/*
 * HindiPos.java
 *  
 * Reused code from Examples.java, Copyright IIT Bombay
 * The Hindi Wordnet 1.2, has been developed for the purpose of
 * facilitating Natural Language Processing applications. 
 * This example code provides the API to use the Wordnet using JAVA.
 */



import java.util.List;
import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;

public class HindiPos {
	public static void demonstration(String hinword, List<String> posCheck) {


		JHWNL.initialize();	
		//long[] synsetOffsets;
		
		try {
				IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(hinword);								
				IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
				demoIndexWord  = demoIWSet.getIndexWordArray();
				for ( int i = 0;i < demoIndexWord.length;i++ ) {
					int size = demoIndexWord[i].getSenseCount();
					//synsetOffsets = demoIndexWord[i].getSynsetOffsets();
					for ( int k = 0 ;k < size; k++ ) {		
					}

					Synset[] synsetArray = demoIndexWord[i].getSenses(); 
					for ( int k = 0;k < size;k++ ) {
						posCheck.add(synsetArray[k].getPOS().toString());
					}
				}	
		}
		catch (JHWNLException e) {
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		}
		
	}
}
