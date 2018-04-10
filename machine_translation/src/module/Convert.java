package module;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;
import module.graph.helper.JAWSutility;
import module.graph.resources.NamedEntityTagger;

public class Convert {
	
	
	public static HashMap<String, ArrayList<String>> wordMap = new HashMap<String, ArrayList<String>>();
	public static GraphPassingNode gpn;
	
	public static String language = "hin";
	public static String sentence = "I am doing this now.";
	public static String question = "?";
	
	public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        SentenceToGraph s= new SentenceToGraph();
        
        String sentences = sentence;
        
        //HashMap<String, String> phraseMap = MultiwordExpressionFinder.mweFinder(sentences);
        HashMap<String, String> phraseMap = new HashMap<String,String>();
		//From the phraseMap, update the original sentences.
		for(Map.Entry<String, String> e: phraseMap.entrySet()){
			String phrase = e.getValue();
			String pattern = phrase.replace(" ", "_");
			System.out.println(phrase);
			System.out.println(pattern);
			sentences = sentences.replace(phrase, pattern);
		}
      
        
        gpn = s.extractGraph(sentence, false, true,false);
        
        
       // String words[] = sentence.split(" ");
        GraphPassingNode newgpn = convert(sentence, gpn, language, phraseMap,phraseMap);
        
        if(language.equals("hin")){
        	GraphPassingNode gpnfinal = HindiHelper.mainModule(phraseMap);
        	for(String k:gpnfinal.getAspGraph()){
        		System.out.println(k);
        	}
        	/* NOTE for the hindi Module pass the arguments as below*/
            System.out.println(HindiGenerator.hinGenerator(gpnfinal,HindiHelper.classMap,HindiHelper.prepsMap,phraseMap,0)); 
        }
        else
        {
	        for(String k:newgpn.getAspGraph()){
	    		System.out.println(k);
	    	}
        }
        
        for (Entry<String, ArrayList<String>> ee : wordMap.entrySet()) {
            String key = ee.getKey();
            ArrayList<String> values = ee.getValue();
            System.out.println(key + values);
            // TODO: Do something.
        } 
          
    }

    
    public static GraphPassingNode convert(String engSent, GraphPassingNode gpn, String lang, HashMap<String, String> phraseMap, HashMap<String, String> sMap){
      	
    	if(lang.equals("hin")){
            HindiHelper.class_HindiModule(gpn);
        }
    	ArrayList<String> transAspGraph = new ArrayList<String>();
    	
    	
    	for(String k:gpn.getAspGraph()){
    		//System.out.println(k);
    		String processStr = k.replace("has(", "");
			processStr = processStr.replace(").","");
			String rules[] = processStr.split(",");
			String roots[] = rules[0].split("-");
			String roles[] = rules[2].split("-");
			String knew = k;
			String knew2 = null;
			JAWSutility j = new JAWSutility();
			HashMap<String, String> posMap = gpn.getposMap();
			

			
			//System.out.println(j.getBaseForm("birds", "n"));
			if(rules[1].equals("instance_of")||rules[1].equals("is_subclass_of")||rules[1].equals("semantic_role")||rules[1].equals("prototype_of")){
				
			}else{
				//String left = roots[0];
				//String right = roles[0];
				String left, right;
				if(phraseMap.get(roots[0])!=null){
					left = phraseMap.get(roots[0]);
					/*System.out.println(left);*/
				}else{
					left = roots[0];
				}
				
				if(phraseMap.get(roles[0])!=null){
					right = phraseMap.get(roles[0]);
					/*System.out.println(right);*/
				}else{
					right = roles[0];
				}
				
				
				try {
					String pos1 = posMap.get(rules[0]);
					//System.out.println(pos1);
					
					if(pos1.equals("NNS")||pos1.equals("NNPS")){
						String single = j.getBaseForm(roots[0],"n");
						String single1 = "";
						if(sMap.containsKey(single)){
							if(lang.equals("hin")){
								single1 = Transliteration.latinToDevanagari(single);	
							}
							else{
								single1 = Transliteration.latinToJapanese(single);
							}								
						}
						else{
							single1 = translate(single.toLowerCase(), lang, "NOUN",single,rules[0]);	
						}						
						knew2 = "has("+single1+"-"+roots[1]+","+pos1+","+left+"-"+roots[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1){
							transAspGraph.add(knew2);
						}
						/*
						if(single.equals(roots[0])){
							single = j.getBaseForm(roots[0],"v");
						
							
						}
						
						*/
						roots[0]=single;
					}else if(pos1.equals("VBD")||pos1.equals("VBG")||
							pos1.equals("VBN")||pos1.equals("VBP")||pos1.equals("VBZ")){
						roots[0] = j.getBaseForm(roots[0],"v");
						String single1 = translate(roots[0].toLowerCase(), lang, "VERB",roots[0],rules[0]);
						knew2 = "has("+single1+"-"+roots[1]+","+pos1+","+left+"-"+roots[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1)
							transAspGraph.add(knew2);
					
					}
					
					/* To handle named entity cases - but other approach to be used
					 * if(pos1.equals("NNP")||pos1.equals("NNPS")){
						left = translate(roots[0], lang, "NOUN");
						if(left==""){
							left = roots[0];
						}
					}
					else{*/
					String leftrole = roots[0]+"-"+roots[1];
						String leftpos = posMap.get(roots[0]);
						if(leftpos == null){
							leftpos = posMap.get(roots[0]+"-"+roots[1]);
						}
						if(leftpos.equals("NN") || leftpos.equals("NNS") || 
								leftpos.equals("NNP") || leftpos.equals("NNPS")){
							if(sMap.containsKey(roots[0])){
								if(lang.equals("hin")){
									left = Transliteration.latinToDevanagari(roots[0]);	
								}
								else{
									left = Transliteration.latinToJapanese(roots[0]);
								}								
							}
							else{
								left = translate(roots[0].toLowerCase(), lang, "NOUN",roots[0],leftrole);	
							}
						}							
						
						else if(leftpos.equals("VB") || leftpos.equals("VBD")||leftpos.equals("VBG")||
								leftpos.equals("VBN")||leftpos.equals("VBP")||leftpos.equals("VBZ"))
							left = translate(roots[0].toLowerCase(), lang, "VERB",roots[0],leftrole);
						else
							left = translate(roots[0].toLowerCase(), lang, "OTHER",roots[0],leftrole);
					//}
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					String pos2 = posMap.get(rules[2]);
					if(pos2.equals("null")||pos2.equals("NNS")){
						roles[0] = j.getBaseForm(roles[0],"n");
						String single1 = translate(roles[0].toLowerCase(), lang, "NOUN",roles[0],rules[2]);
						knew2 = "has("+single1+"-"+roles[1]+","+pos2+","+right+"-"+roles[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1)
							transAspGraph.add(knew2);
						
					}else if(pos2.equals("VBD")||pos2.equals("VBG")||
							pos2.equals("VBN")||pos2.equals("VBP")||pos2.equals("VBZ")){
						roles[0] = j.getBaseForm(roles[0],"v");
						String single1 = translate(roles[0].toLowerCase(), lang, "VERB",roles[0],rules[2]);
						knew2 = "has("+single1+"-"+roles[1]+","+pos2+","+right+"-"+roles[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1)
							transAspGraph.add(knew2);
						
						
					}
					
					/* To handle named entity cases - but other approach to be used
					 * if(pos2.equals("NNP")||pos2.equals("NNPS")){
					 
						right = translate(roles[0], lang, "NOUN");
						if(right==""){
							right = roles[0];
						}
					}

					else{*/
						String rightrole = roles[0]+"-"+roles[1];
						String rightpos = posMap.get(roles[0]);
						if(rightpos == null){							
							rightpos = posMap.get(roles[0]+"-"+roles[1]);
						}
						if(rightpos.equals("NN") || rightpos.equals("NNS") || 
								rightpos.equals("NNP") || rightpos.equals("NNPS")){
							if(sMap.containsKey(roles[0])){
								if(lang.equals("hin")){
									right = Transliteration.latinToDevanagari(roles[0]);	
								}
								else{
									right = Transliteration.latinToJapanese(roles[0]);
								}								
							}
							else{
								right = translate(roles[0].toLowerCase(), lang, "NOUN",roles[0],rightrole);	
							}							
						}
						else if(rightpos.equals("VB") || rightpos.equals("VBD")||rightpos.equals("VBG")||
								rightpos.equals("VBN")||rightpos.equals("VBP")||rightpos.equals("VBZ"))
							right = translate(roles[0].toLowerCase(), lang, "VERB",roles[0],rightrole);
						else
							right = translate(roles[0].toLowerCase(), lang, "OTHER",roles[0],rightrole);
					// }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				knew = "has("+left+"-"+roots[1]+","+rules[1]+","+right+"-"+roles[1]+").";
				//System.out.println(knew);
			}
			transAspGraph.add(knew);
    	}
    	GraphPassingNode newgpn = new GraphPassingNode(transAspGraph,gpn.getposMap(),gpn.getSentence(),gpn.getWordSenseMap());
    	
    	return newgpn;
    }

 

	

    public static String translate(String phrase, String lang, String POS,String main_phrase,String main_role) throws MalformedURLException
    {	
	
    	// Actually, here we need to handle the disambiguation	
    	 if(wordMap.containsKey(phrase)) 
    	 {    		
    		 if(!wordMap.get(phrase).isEmpty()){
    			 
    		 return wordMap.get(phrase).get(0);
    		 }
    	 }   	 
    	
    	 
    	 String temp = " ";
    	 if(phrase.equals("?") && lang.equals("hin")){    		 
    		 phrase = question;    		     		 
    		 temp = "?";
    	 }
    	 
    	 JSONObject phras;
         String res1="";
         ArrayList<String> wordList = new ArrayList<String>();
         
        // build a URL
    	StringBuilder sb = new StringBuilder();
    	sb.append("https://glosbe.com/gapi/translate?from=eng&dest=");
    	sb.append(lang);
    	sb.append("&format=json&phrase=");
    	sb.append(phrase);
    	sb.append("&pretty=true");
        String s = sb.toString();
        URL url;
		try {
			url = new URL(s);
			// read from the URL
	        Scanner scan = new Scanner(url.openStream());
	        String str = new String();
	        while (scan.hasNext())
	            str += scan.nextLine();
	        scan.close();
	     
	        // build a JSON object
	        JSONObject obj;
			try {
				obj = new JSONObject(str);
				if (! obj.getString("result").equals("ok"))
				{ System.out.println("Object not found"); return null;}
				
				JSONArray tuc = obj.getJSONArray("tuc");
								
				for(int i =0; i<tuc.length();i++){ 
					List<String> posCheck = new ArrayList<String>();
					JSONObject res = tuc.getJSONObject(i);					
					if(res.has("phrase")){
						String posnum = "";
						if(lang.equals("hin")){
							posnum = HindiHelper.posMap.get(phrase);
							 
						}
						
						phras = res.getJSONObject("phrase");
		        		res1 = phras.getString("text");
		        		if(posnum == null){
		        			posnum = HindiHelper.posMap.get(main_phrase);
		        			if(posnum == null){
			        			posnum = HindiHelper.posMap.get(main_role);
			        		}	 
		        		}		        				        		
		        		
		        				        				        		
		        		if(lang.equals("hin")){
		        			
		        			
		        			HindiPos.demonstration(res1, posCheck);		        			
			        		//System.out.println("Should be POS:" + POS);
		        			/*if(posnum.equals("CD") || posnum.equals("MD"))
		        				wordList.add(res1);
		        			else if(HindiHelper.pronoun.contains(phrase))
			        			wordList.add(res1);*/		        			
		        			
		        			if(HindiHelper.pronoun.contains(phrase) || posnum.equals("CD") || posnum.equals("MD"))
		        			{
		        			
			        			wordList.add(res1);
		        			}
		        			
		        			if(posCheck.contains(POS) && !POS.equals("OTHER"))
		        			{	
		        				
			        			wordList.add(res1);
		        			}
			        		else if(POS.equals("OTHER") && posCheck.size()==0)
			        		{			        			
			        			
			        			wordList.add(res1);
			        		}
		        			if(temp.equalsIgnoreCase("?")){
		        				if(res1.equals("जब")){
		        					wordList.add("कब");
		        				}
		        				else{
		        					wordList.add(res1);	
		        				}		        				
		        			}
		        			
		        					        			
		        		}
		        		else
		        			wordList.add(res1);
		        		
	
					}
					
				}
				
				if(lang.equals("hin")){
					if (phrase.equalsIgnoreCase("what") || phrase.equalsIgnoreCase("when")
							|| phrase.equalsIgnoreCase("where") || phrase.equalsIgnoreCase("who")
							|| phrase.equalsIgnoreCase("why") || phrase.equalsIgnoreCase("whose")
							|| phrase.equalsIgnoreCase("how") || phrase.equalsIgnoreCase("how much")
							|| phrase.equalsIgnoreCase("how many")) {
						wordMap.put("?", wordList);
					} else {
						wordMap.put(phrase, wordList);
					}	
				}
				else{
					wordMap.put(phrase, wordList);	
				}

				if(!wordList.isEmpty()){					
					res1 = wordList.get(0);	
				}
				else
				{	
					try{
						JSONObject res_main = tuc.getJSONObject(0);
						phras = res_main.getJSONObject(phrase);
						res1 = phras.getString("text");
						wordList.add(res1);
						wordMap.put(phrase, wordList);
					}
					catch(Exception ex){	        														
							if(lang.equals("hin")){							
								res1 = Transliteration.latinToDevanagari(phrase);
								wordList.add(res1);
				        		wordMap.put(phrase, wordList);
							}
							
					}
					
				}
								
				
				//wordMapOld.put(phrase, res1);
				
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return res1;	
    }
}

