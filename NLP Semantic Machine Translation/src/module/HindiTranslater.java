package module;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;

public class HindiTranslater {

	public static String hinTranslate(String sentences, HashMap<String, String> sMap) {
		SentenceToGraph s= new SentenceToGraph();
		int question = 0;
        if(sentences.contains("?")){
        	int ind = sentences.lastIndexOf("?");
        	sentences = new StringBuilder(sentences).replace(ind, ind+1,".").toString();        	
        	question = 1; 
        	System.out.println(sentences);
        }
        HashMap<String, String> phraseMap = new HashMap<String, String>();
//		HashMap<String, String> phraseMap = MultiwordExpressionFinder.mweFinder(sentences);
//		HashMap<String, String> prep = new HashMap<String,String>()
//		{{
//			put("in","में");
//			put("inside","अंदर");
//			put("into","के अंदर");
//			put("like","की तरह");
//			put("of","का");
//			put("off","पर से");
//			put("on","पर");
//			put("onto","पर");
//			put("over","ऊपर");
//			put("through","के ज़रिए");
//			put("towards","तरफ़");
//			put("to","को");
//			put("from","से");
//			put("about","के बारे में");
//			put("above","ऊपर");
//			put("after","बाद");
//			put("against","के खिलाफ");
//			put("along","साथ");
//			put("at","पर");
//			put("before","पहले");
//			put("behind","पीछे");
//			put("beneath","नीचे");
//			put("between","बीच");
//			put("by","तक");
//			put("down","नीचे");
//			put("for","लिए");
//			put("with","साथ");
//			put("the","the");
//		}};
//		ArrayList<String> check = new ArrayList<String>();
//		HashMap<String,String> map2 = new HashMap<String,String>();
//		Convert.gpn = s.extractGraph(sentences, false, true,false);
//		
//		/*for(String k: Convert.gpn.getAspGraph()){
//			System.out.println(k);
//		}*/
//		for (String k : Convert.gpn.getAspGraph()) {
//			// System.out.println(k);
//			String processStr = k.replace("has(", "");
//			processStr = processStr.replace(").", "");
//			String rules[] = processStr.split(",");
//			String roots[] = rules[0].split("-");
//			String roles[] = rules[2].split("-");
//			if(rules[1].equals("objective") || rules[1].equals("next_event")
//			|| rules[1].equals("supporting_verb") || rules[1].equals("modal_verb")
//			|| rules[1].equals("modifier") || rules[1].equals("trait")
//			|| rules[1].equals("identifier") || rules[1].equals("is_participant_in")
//			|| rules[1].equals("is_possessed_by") || rules[1].equals("is_related_to")
//			|| rules[1].equals("destination") || rules[1].equals("size")
//			|| rules[1].equals("in_conjunction_with") || rules[1].equals("negative")){
//				check.add(roles[0]);			
//			}
//			if(rules[1].equals("agent") || rules[1].equals("recipient") || rules[1].equals("attachment")){
//				check.add(roots[0]);
//			}
//		}
//		/*System.out.println(check);*/
//		
//		//From the phraseMap, update the original sentences.
//		for(Map.Entry<String, String> e: phraseMap.entrySet()){
//			String phrase = e.getValue();						
//			String pattern = phrase.replace(" ", "_");
//			String word[] = phrase.split(" ");
//			int flag = 0;			
//			for(String wd: word){
//				if(prep.containsKey(wd)){
//					flag = 1;
//				}	
//				if(check.contains(wd)){
//					flag = 1;
//				}
//			}			
//			if(flag == 0){
//				sentences = sentences.replace(phrase, pattern);	
//			}			
//		}
		
					
        String lang = "hin";
        Convert.language = lang;
        Convert.question = "?";
        String wor[] = sentences.split(" ");
        if(wor[0].equalsIgnoreCase("what")){        	
        	Convert.question = "what";
        } else if(wor[0].equalsIgnoreCase("when")){
        	Convert.question = "when";
        } else if(wor[0].equalsIgnoreCase("where")){
        	Convert.question = "where";
        } else if(wor[0].equalsIgnoreCase("who")){
        	Convert.question = "who";
        } else if(wor[0].equalsIgnoreCase("why")){
        	Convert.question = "why";
        } else if(wor[0].equalsIgnoreCase("whose")){
        	Convert.question = "whose";
        } else if(wor[0].equalsIgnoreCase("how")){
        	if(wor[1].equalsIgnoreCase("much")){
        		Convert.question = "how much";
        	} else if(wor[1].equalsIgnoreCase("many")){
        		Convert.question = "how many";
        	} else{
        		Convert.question = "how";
        	}        	
        }
        
        if(question == 1){
        	int ind = sentences.lastIndexOf(".");
        	sentences = new StringBuilder(sentences).replace(ind, ind+1,"?").toString();        	        	
        }
        try{
        Convert.gpn = s.extractGraph(sentences, false, true,false);   
        System.out.println(Convert.gpn);
        	/*for(String k: Convert.gpn.getAspGraph()){
        		System.out.println(k);
			}*/
        }
        catch(Exception ex){
        	return "Error in parsing.";
        }
        
        
        Convert.sentence = sentences;      
        HindiHelper.words = Convert.sentence.split(" ");
        HindiHelper.posMap = Convert.gpn.getposMap();
        
        GraphPassingNode newgpn = Convert.convert(sentences, Convert.gpn, lang,phraseMap,sMap);
        
        /*for(String key: newgpn.getAspGraph()){
        	System.out.println(key);
        }*/
        
        GraphPassingNode gpnfinal = HindiHelper.mainModule(sMap);        
        
        /*for(String key: gpnfinal.getAspGraph()){
        	System.out.println(key);
        }*/       
                
        String output = HindiGenerator.hinGenerator(gpnfinal,HindiHelper.classMap,HindiHelper.prepsMap,sMap,question);
        
        clean_run();
		return output;        
	}



	public static void clean_run() {		
		HindiHelper.classMap.clear();
		HindiHelper.prepsMap.clear();
		HindiHelper.willflag = 0;
		HindiHelper.shouldflag = 0;
		HindiHelper.wouldflag = 0;
		HindiHelper.shallflag = 0;
		HindiHelper.mustflag = 0;
		HindiHelper.mightflag = 0;
		HindiHelper.mayflag = 0;
		HindiHelper.haveflag = 0;
		HindiHelper.havingflag = 0;
		HindiHelper.doflag = 0;
		HindiHelper.didflag = 0;
		HindiHelper.doesflag = 0;
		HindiHelper.doingflag = 0;
		HindiHelper.couldflag = 0;
		HindiHelper.canflag = 0;
		HindiHelper.beflag = 0;
		HindiHelper.amflag = 0;
		
		
		
		HindiHelper.position_h = null;
		HindiHelper.words = null;
		HindiHelper.posMap.clear();
		HindiHelper.wordMapOld.clear();
		Convert.gpn.getposMap().clear();
		Convert.gpn.getWordSenseMap().clear();
		Convert.gpn.getAspGraph().clear();
		Convert.gpn.getWordSenseMap().clear();
		Convert.gpn = null;
		Convert.language = null;
		Convert.question = "?";
		Convert.sentence = null;
		HindiGenerator.temp = "";
		HindiGenerator.sentence = "";
		HindiGenerator.check = "";
		HindiGenerator.emotion_flag = false;
		HindiGenerator.same_word_count.clear();
		HindiGenerator.same_word_count_g.clear();
		HindiGenerator.verb_g.clear();
		HindiGenerator.verb_g_output.clear();
		HindiGenerator.non_verb_g.clear();
		HindiGenerator.non_verb_g_output.clear();
		HindiGenerator.non_verb_g_output_final.clear();
		HindiGenerator.pronoun_flag = false;
	}

}

// modify main code - generate hin sent
//write rules
//work done




