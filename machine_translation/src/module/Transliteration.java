/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;
import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.icu.text.Transliterator;
import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;
import module.graph.helper.JAWSutility;
import module.graph.resources.NamedEntityTagger;

/**
 *
 * @author apradha7
 */
public class Transliteration {

    /**
     * @param args the command line arguments
     */	
	public static void main(String[] args){
		System.out.println(latinToDevanagari("Sheila"));
	}
    public static String latinToDevanagari(String latinString) {

        final String ENG_TO_DEV = "Latin-Devanagari";

        Transliterator toDevnagiri = Transliterator.getInstance(ENG_TO_DEV);
        String devnagiri = toDevnagiri.transliterate(latinString);

        return devnagiri;
    }
    public static String latinToJapanese(String latinString) {

        final String ENG_TO_JAP = "Latin-Katakana";

        Transliterator toJapanese = Transliterator.getInstance(ENG_TO_JAP);
        String japanese = toJapanese.transliterate(latinString);

        return japanese;
    }
    
}
