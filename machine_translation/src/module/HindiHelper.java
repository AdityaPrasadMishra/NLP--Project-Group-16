package module;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

import module.graph.helper.GraphPassingNode;
import module.graph.helper.JAWSutility;

public class HindiHelper {
	public static ArrayList<String> pronoun = new ArrayList<String>
	(Arrays.asList("i", "we", "you", "she", "he", "they", "her", "him",
			       "them", "my", "mine", "your","our", "ours", "their",
			       "theirs", "it", "yours", "me", "us"));
	
	public static HashMap<String, String> wordMapOld = new HashMap<String, String>();
	public static final int VERB_ROW = 23; //no. of rows in lightverb.txt
	public static final int VERB_COL = 4; //no. of columns in lightverb.txt
	public static int willflag = 0; // to check whether will is present as a future tense in sentence
	public static int shouldflag = 0; // to check whether should is present as a future tense in sentence
	public static int wouldflag = 0;
	public static int shallflag = 0;
	public static int mustflag = 0;
	public static int mightflag = 0;
	public static int mayflag = 0;
	public static int haveflag = 0;
	public static int havingflag = 0;
	public static int doflag = 0;
	public static int didflag = 0;
	public static int doesflag = 0;
	public static int doingflag = 0;
	public static int couldflag = 0;
	public static int canflag = 0;
	public static int beflag = 0;
	public static int amflag = 0;
	public static ArrayList<String> cop_verbs = new ArrayList<String>(Arrays.asList("will", 
			"should", "could","would","shall","must","might","may","do", "did","does",
			"doing","could","can","be","am"));
	static String position_h;
    static ArrayList<String> preps = new ArrayList<String>(Arrays.asList("to", "from", "the","about","above","after","against","along","at","before","behind","beneath","between","by","down","for","with","towards","through","over","onto","on","off","of","like","into","inside","in","since","ago","past","till","until","beside","under","below"));
    public static HashMap<Integer, String> classMap = new HashMap<Integer, String>();
    public static HashMap<Integer, String> prepsMap = new HashMap<Integer, String>();    
	public static String[][] verb = new String[VERB_ROW][VERB_COL];
	public static String words[] = Convert.sentence.split(" ");
	public static HashMap<String, String> posMap = Convert.gpn.getposMap(); 	
	
	
	public static GraphPassingNode mainModule(HashMap<String, String> sMap){		    
        HindiHelper.preps_HindiModule(Convert.sentence);
        HindiHelper.class_HindiModule(Convert.gpn);
        String sentences_main = Convert.sentence;
        sentences_main = sentences_main.replace(".", "");
        String word[] = sentences_main.split(" ");        
        String cop_verb = "";
        for(int i=0;i< word.length; i++){
        	if(cop_verbs.contains(word[i])){
        		int x = i+1;
        		cop_verb = word[i] + "-" + x;        		
        	}
        	
        	
        	
        	
        } 
        
        if(posMap.get(cop_verb) != null) 
        {        	
        	if(cop_verb.contains("will")){
        	if(posMap.get(cop_verb).equals("MD"))        	
        		willflag = 1;
        	}
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("should"))        	
        		shouldflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("would"))        	
        		wouldflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("shall"))
        		shallflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("must"))
        		mustflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("might"))
        		mightflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("may"))
        		mayflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("have"))
        		haveflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("having"))
        		havingflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("do"))
        		doflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("did"))
        		didflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {   
        
        	if(cop_verb.contains("does"))
        		doesflag = 1;
        
        	
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("doing"))
        		doingflag = 1;
        	        	
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("could"))
        		couldflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("can"))
        		canflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("be"))
        		beflag = 1;
        }
        if(posMap.get(cop_verb) != null) 
        {
        	if(cop_verb.contains("am"))
        		amflag = 1;
        }
        HindiHelper.readVerb();
        HindiHelper.verbDisambiguate();
        return HindiHelper.createfinalgpn(Convert.gpn,sMap);
	}
	
	
	 public static void readVerb(){
	    	
	        
	    	try
	        {
	    		
	            Scanner inputStream = new Scanner(new File("./resources/lightverb.txt"));
	            int row = 0;
	            while (inputStream.hasNextLine()){
		            String line = inputStream.nextLine();
		            String[] word = line.split(" ");
	                for (int column = 0; column < VERB_COL; column++) {
	                    verb[row][column] = word[column];
	                }
	                row++;
	        	}
	            inputStream.close();
	        }
	        catch(FileNotFoundException e)
	        {
	            System.out.println("Error opening the file lightverb.txt");
	            System.exit(0);
	        }
	    }
	 
	 public static void verbDisambiguate(){		 
			for (Entry<String, ArrayList<String>> ee : Convert.wordMap.entrySet()) {
		        String key = ee.getKey();
		        JAWSutility j = new JAWSutility();
		        for(String k : words)
		        { 		        	
		        	k = j.getBaseForm(k,"v");
		        	if(k.contains("?")){
		        		int ind = k.lastIndexOf("?");
		            	k = new StringBuilder(k).replace(ind, ind+1,"").toString();
		        	}
		        	if(k.contains(".")){
		        		int ind = k.lastIndexOf(".");
		            	k = new StringBuilder(k).replace(ind, ind+1,"").toString();
		        	}
		        	String pos = posMap.get(k);		 
		        	if(pos == null){			        		
		        		continue;
		        	}
		        	else if( pos.equals("VB")|| pos.equals("VBD")||pos.equals("VBG")||
							pos.equals("VBN")||pos.equals("VBP")||pos.equals("VBZ")){		        		
		            	if(key.equals(k)){		            		
		            	ArrayList<String> values = ee.getValue();		            	
		            	rules(key, pos, values);
		            	break;
		            	}
		            }
		        	else if(pos.equals("PRP")) {
		        		if(key.equalsIgnoreCase(k)){	
			        		ArrayList<String> values = ee.getValue();		 
			        		rules(key,pos,values);
			        		break;
		        		}
		        	}
		        }
		    } 
		}
	    
	    public static void preps_HindiModule(String sentence){
	        int prepIndex = 0;        
	        for (String word : sentence.split("\\s+")) {
	            prepIndex++;
	            for (String test : preps){
	                if(test.equalsIgnoreCase(word)){
	                    prepsMap.put(prepIndex, word);  
	                }
	            }           
	        }
	    }
	    public static void class_HindiModule(GraphPassingNode gpn){
	        for(String k:gpn.getAspGraph()){
	            String processStr = k.replace("has(", "");
	            processStr = processStr.replace(").","");
	            String rules_h[] = processStr.split(",");
	            if(rules_h[1].equals("instance_of")){
	                position_h = rules_h[0].split("-")[1];
	            } 
	            if(rules_h[1].equals("is_subclass_of")){
	                if(rules_h[2].equals("person")){	                	
	                    if(rules_h[0].toLowerCase().equals("he")||rules_h[0].toLowerCase().equals("him")||rules_h[0].toLowerCase().equals("his")){
	                        rules_h[2] = "male";	 
	                    }
	                    else{
	                        rules_h[2] = "female";
	                    }	                    
	                }
	                if(position_h != null){
	                
	                	classMap.put(Integer.parseInt(position_h), rules_h[2]);
	                }
	            } 
	            
	        }
	    }
	    
	    public static void copularverbs(int i,String key, String pos, ArrayList<String> values){
	    			//if he will
	    			if(Convert.wordMap.containsKey("he") && (shouldflag == 1 ||mustflag ==1 )&& pos.equals("PRP")) {
	    				String sm = values.get(i);
	    				sm = "उसे";
	    				Collections.replaceAll(values, values.get(i), sm);
	    				
	    			}
	    			if(Convert.wordMap.containsKey("i") && (shouldflag == 1 ||mustflag ==1 )&& pos.equals("PRP")) {
	    				String sm = values.get(i);
	    				sm = "मुझे";
	    				Collections.replaceAll(values, values.get(i), sm);
	    				
	    			}
	    			if(Convert.wordMap.containsKey("they") && (shouldflag == 1 ||mustflag ==1 )&& pos.equals("PRP")) {
	    				String sm = values.get(i);
	    				sm = "उन्हें";
	    				Collections.replaceAll(values, values.get(i), sm);
	    				
	    			}
	    			if(Convert.wordMap.containsKey("you") && (shouldflag == 1 ||mustflag ==1 )&& pos.equals("PRP")) {
	    				String sm = values.get(i);
	    				sm = "तुम्हे";
	    				Collections.replaceAll(values, values.get(i), sm);
	    				
	    			}
	    			
	    			
					if(Convert.wordMap.containsKey("he") && willflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she will
					else if(Convert.wordMap.containsKey("she") && willflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i will
					else if(Convert.wordMap.containsKey("i") && willflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोऊँगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाऊँगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ुंगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they will
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && willflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोऐंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you will
						else if(Convert.wordMap.containsKey("you") && willflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat("ोगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat("ाओगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat("ोगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(willflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if he should
					if(Convert.wordMap.containsKey("he") && shouldflag == 1 ){						
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){							
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){							
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){							
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she should
					else if(Convert.wordMap.containsKey("she") && shouldflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i should
					else if(Convert.wordMap.containsKey("i") && shouldflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they should
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && shouldflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you should
						else if(Convert.wordMap.containsKey("you") && shouldflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat("ोना चाहिए");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat("ाना चाहिए");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat("ना चाहिए");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(shouldflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}	


							//if he would
					if(Convert.wordMap.containsKey("he") && wouldflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she would
					else if(Convert.wordMap.containsKey("she") && wouldflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i would
					else if(Convert.wordMap.containsKey("i") && wouldflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोऊँगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाऊँगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ुंगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they would
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && wouldflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोऐंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you would
						else if(Convert.wordMap.containsKey("you") && wouldflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat("ोगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat("ाओगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat("ोगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(wouldflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}	

							//if he shall
					if(Convert.wordMap.containsKey("he") && shallflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she shall
					else if(Convert.wordMap.containsKey("she") && shallflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगी");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i shall
					else if(Convert.wordMap.containsKey("i") && shallflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोऊँगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाऊँगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ुंगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they shall
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && shallflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोऐंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेंगे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you shall
						else if(Convert.wordMap.containsKey("you") && shallflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat("ोगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat("ाओगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat("ोगे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(shallflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोएगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ायेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ेगा");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}	

							//if he must
					if(Convert.wordMap.containsKey("he") && mustflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she must
					else if(Convert.wordMap.containsKey("she") && mustflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i must
					else if(Convert.wordMap.containsKey("i") && mustflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they must
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && mustflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you must
						else if(Convert.wordMap.containsKey("you") && mustflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat("ोना चाहिए");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ाना चाहिए");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ना चाहिए");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(mustflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ोना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ाना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ना चाहिए");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}	

							//if he might
					if(Convert.wordMap.containsKey("he") && mightflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she might
					else if(Convert.wordMap.containsKey("she") && mightflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i might
					else if(Convert.wordMap.containsKey("i") && mightflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they might
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && mightflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you might
						else if(Convert.wordMap.containsKey("you") && mightflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat(" सकते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat(" सकते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat(" सकते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(mightflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}	

							//if he may
					if(Convert.wordMap.containsKey("he") && mayflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she may
					else if(Convert.wordMap.containsKey("she") && mayflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i may
					else if(Convert.wordMap.containsKey("i") && mayflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they may
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && mayflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you may
						else if(Convert.wordMap.containsKey("you") && mayflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat(" सकते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat(" सकते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat(" सकते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(mayflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}	


					// i do
					if(Convert.wordMap.containsKey("i") && doflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
		
					// if we/they do
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && doflag == 1 ){
						String sm = values.get(i);
						
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						
					}
						// if you do
						else if(Convert.wordMap.containsKey("you") && doflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat("ते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat("ते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat("ते है");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(doflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}	

							//if he did
					if(Convert.wordMap.containsKey("he")&& (didflag ==1)){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][1]);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
					
					
					//if she
					else if(Convert.wordMap.containsKey("she") && (didflag == 1)){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][2]);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ी");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ी");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
					//by default case gender from name can't be known
					
					// i did
					else if(Convert.wordMap.containsKey("i") && didflag == 1 ){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][1]);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
		
					// if we/they did
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && didflag == 1 ){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][3].concat("थे"));
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat(" े");
								sm= sm.concat("थे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat(" े");
								sm= sm.concat("थे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
						
					}
						// if you did
						else if(Convert.wordMap.containsKey("you") && didflag == 1 ){
							String sm = values.get(i);							
							
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){								
								sm=sm.substring(0, last3);
								sm=sm.concat("चुके ते");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){								
								sm=sm.substring(0, last2);
								sm=sm.concat("चुके ते");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){								
								sm=sm.substring(0, last);
								sm=sm.concat("चुके ते");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}							
						}
					
					//if gender not known
					else if(didflag == 1 ){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][1]);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}	

							//if he does
					if(Convert.wordMap.containsKey("he") && doesflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she does
					else if(Convert.wordMap.containsKey("she") && doesflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i does

					//if gender not known
					else if(doesflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

							//if he doing

							//if he could


							//if he can
					if(Convert.wordMap.containsKey("he") && (canflag == 1 || couldflag==1) ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकते हैं");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकते हैं");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकते हैं");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					//if she can
					else if(Convert.wordMap.containsKey("she") && (canflag == 1 || couldflag==1) ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकती हे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकती हे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकती हे");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// i can
					else if(Convert.wordMap.containsKey("i") && (canflag == 1 || couldflag==1) ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता हू");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकता हू");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकता हू");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

					// if we/they can
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && (canflag == 1 || couldflag==1) ){
						String sm = values.get(i);

						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकते है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}

					}
						// if you can
						else if(Convert.wordMap.containsKey("you") && (canflag == 1 || couldflag==1) ){
							String sm = values.get(i);

							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							int last3 = sm.lastIndexOf("ोना");
							if(last3 != -1){
								sm=sm.substring(0, last3);
								sm=sm.concat(" सकते हो");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat(" सकते हो");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat(" सकते हो");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}

					//if gender not known
					else if(canflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" सकता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}


							//if he be

							//if he am
					// i am
					if(Convert.wordMap.containsKey("i") && amflag == 1 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						int last2 = sm.lastIndexOf("ाना");
						int last3 = sm.lastIndexOf("ोना");
						if(last3 != -1){
							sm=sm.substring(0, last3);
							sm=sm.concat(" रहा हू");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last2 != -1){
							sm=sm.substring(0, last2);
							sm=sm.concat(" रहा हू");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
						else if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" रहा हू");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}

		    		
	    }

	    public static void rules(String key, String pos, ArrayList<String> values) 
	    {	    	
	    	 
	    	for(int i =0; i< values.size();i++){
	    		if(pos.equals("PRP")) {
	    			if(shouldflag == 1 || mustflag == 1) {
	    				copularverbs(i, key, pos, values);
	    			}
	    		}
	    		
		    	if(pos.equals("VB")){		    		
		    		if (willflag == 1 || shouldflag == 1 || wouldflag == 1 || shallflag == 1 || mustflag == 1 || mightflag == 1 || mayflag ==1 || haveflag == 1 || havingflag == 1
		    			|| doflag == 1 || didflag == 1 || doesflag == 1 || doingflag == 1 || couldflag == 1 || canflag == 1 || beflag == 1 || amflag == 1) {
		    			copularverbs(i,key,pos,values);
					}
		    	}
				if(pos.equals("VBD")){
					if (willflag == 1 || shouldflag == 1 || wouldflag == 1 || shallflag == 1 || mustflag == 1 || mightflag == 1 || mayflag ==1 || haveflag == 1 || havingflag == 1
			    			|| doflag == 1 ||  doesflag == 1 || doingflag == 1 || couldflag == 1 || canflag == 1 || beflag == 1 || amflag == 1) {
			    			copularverbs(i,key,pos,values);
						}
					else{
					//if he
					if(Convert.wordMap.containsKey("he")){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if(verb[row][0] == null) {
								break;
							}
							if( values.get(i).contains(verb[row][0]) ){
								String sm = verb[row][1].concat(" था");
								Collections.replaceAll(values, values.get(i), sm);
								flag = 1;
								break;
							}
							
		
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ा था");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ा था");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							
						}
					}
															
					//if she
					else if(Convert.wordMap.containsKey("she")){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if(verb[row][0] == null) {
								break;
							}
							if( values.get(i).contains(verb[row][0]) ){
								String sm = verb[row][2].concat(" थी");
								Collections.replaceAll(values, values.get(i), sm);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if (last2!=-1){
								sm=sm.substring(0, last);
								sm=sm.concat("ी थी");
								Collections.replaceAll(values, values.get(i), sm);
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ी थी");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they"))){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][3].concat("थे"));
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat(" े");
								sm= sm.concat("थे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat(" े");
								sm= sm.concat("थे");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
						
					}
					//by default case gender from name can't be known
					else{
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								String sm = verb[row][1].concat(" था");
								Collections.replaceAll(values, values.get(i), sm);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ा था");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ा था");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
					}
				    		
				}
				if(pos.equals("VBZ")){					
					if (willflag == 1 || shouldflag == 1 || wouldflag == 1 || shallflag == 1 || mustflag == 1 || mightflag == 1 || mayflag ==1 || haveflag == 1 || havingflag == 1
			    			|| doflag == 1 || didflag == 1 || doesflag == 1 || doingflag == 1 || couldflag == 1 || canflag == 1 || beflag == 1 || amflag == 1) {
						
			    			copularverbs(i,key,pos,values);
						}
					else{
					//if he					
					if(Convert.wordMap.containsKey("he")){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					//if she
					else if(Convert.wordMap.containsKey("she")){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ती है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
							
					    }
					}
					//by default it's he as it's difficult to analyze gender from name
					else{
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ता है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					}
				}
				if(pos.equals("VBP")){
					if (willflag == 1 || shouldflag == 1 || wouldflag == 1 || shallflag == 1 || mustflag == 1 || mightflag == 1 || mayflag ==1 || haveflag == 1 || havingflag == 1
			    			|| doflag == 1 || didflag == 1 || doesflag == 1 || doingflag == 1 || couldflag == 1 || canflag == 1 || beflag == 1 || amflag == 1) {
			    			copularverbs(i,key,pos,values);
						}
					else{
					//if I
					if(Convert.wordMap.containsKey("i")){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ता हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					// if we/they
					else if(Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they") ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat("ते हैं");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						  }
					    }
						//if you
						else if(Convert.wordMap.containsKey("you")){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ते हो");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							  }
						    }
					}
				}
				if(pos.equals("VBG")){					
					//if any flag is equal to 1
					if (willflag == 1 || shouldflag == 1 || wouldflag == 1 || shallflag == 1 || mustflag == 1 || mightflag == 1 || mayflag ==1 || haveflag == 1 || havingflag == 1
		    			|| doflag == 1 || didflag == 1 || doesflag == 1 || doingflag == 1 || couldflag == 1 || canflag == 1 || beflag == 1 || amflag == 1) {
						
						copularverbs(i,key, pos, values);
					}
					else{
											//if he
					if(Convert.wordMap.containsKey("he") && willflag == 0){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" रहा है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					
					
					//if she
					else if(Convert.wordMap.containsKey("she") && willflag == 0){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" रही है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
							
					    }
					}
					

					//if I
					else if(Convert.wordMap.containsKey("i") && willflag == 0 ){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" रहा हूँ");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}
					
					// if we/they
					else if( (Convert.wordMap.containsKey("we") ||Convert.wordMap.containsKey("they")) && willflag == 0){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" रहे हैं");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						  }
					    }
					
						//if you
						else if(Convert.wordMap.containsKey("you") && willflag == 0 ){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat(" रहे हो");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							  }
						    }
						
					//if by default case gender from name can't be known
						else if(willflag == 0){
						String sm = values.get(i);
						int last = sm.lastIndexOf("ना");
						if(last != -1){
							sm=sm.substring(0, last);
							sm=sm.concat(" रहा है");
							Collections.replaceAll(values, values.get(i), sm);
							//System.out.println(values.get(i));
						}
					}					
					



					}					
					
						
				}
				if(pos.equals("VBN")){
					if (willflag == 1 || shouldflag == 1 || wouldflag == 1 || shallflag == 1 || mustflag == 1 || mightflag == 1 || mayflag ==1 || haveflag == 1 || havingflag == 1
			    			|| doflag == 1 || didflag == 1 || doesflag == 1 || doingflag == 1 || couldflag == 1 || canflag == 1 || beflag == 1 || amflag == 1) {
			    			copularverbs(i,key,pos,values);
						}
					else{
											
					//if he
					if(Convert.wordMap.containsKey("he")){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][1]);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
					
					
					//if she
					else if(Convert.wordMap.containsKey("she")){
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][2]);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ी");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ी");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
					//by default case gender from name can't be known
					else{
						int flag = 0;
						for(int row =0; row<VERB_ROW; row++){
							if( values.get(i).equals(verb[row][0]) ){
								Collections.replaceAll(values, values.get(i), verb[row][1]);
								flag = 1;
								break;
							}
						}
						if(flag == 0){
							String sm = values.get(i);
							int last = sm.lastIndexOf("ना");
							int last2 = sm.lastIndexOf("ाना");
							if(last2 != -1){
								sm=sm.substring(0, last2);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
							else if(last != -1){
								sm=sm.substring(0, last);
								sm=sm.concat("ा");
								Collections.replaceAll(values, values.get(i), sm);
								//System.out.println(values.get(i));
							}
						}
					}
					}
				}
			}		
		}
	   
	    
	    
	    public static GraphPassingNode createfinalgpn(GraphPassingNode gpn, HashMap<String, String> sMap){
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
					String left = roots[0];
					String right = roles[0];
					
					try {
						String pos1 = posMap.get(rules[0]);
						//System.out.println(pos1);
						
						if(pos1.equals("NNS")||pos1.equals("NNPS")){
							String single = j.getBaseForm(roots[0],"n");
							String single1 = "";
							if(sMap.containsKey(single)){
								single1 = Transliteration.latinToDevanagari(single);																
							}
							else{
								single1 = Convert.wordMap.get(single.toLowerCase()).get(0);	
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
							String single1 = Convert.wordMap.get(roots[0].toLowerCase()).get(0);
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
					
						if(sMap.containsKey(roots[0])){
							left = Transliteration.latinToDevanagari(roots[0]);																
						}
						else{
							left = Convert.wordMap.get(roots[0].toLowerCase()).get(0);	
						}						
							
						//}
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						String pos2 = posMap.get(rules[2]);
						if(pos2.equals("null")||pos2.equals("NNS")){
							roles[0] = j.getBaseForm(roles[0],"n");
							String single1 = "";
							if(sMap.containsKey(roles[0])){
								single1 = Transliteration.latinToDevanagari(roles[0]);																
							}
							else{
								single1 = Convert.wordMap.get(roles[0].toLowerCase()).get(0);	
							}							
							knew2 = "has("+single1+"-"+roles[1]+","+pos2+","+right+"-"+roles[1]+").";
							//System.out.println(knew2);
							if(transAspGraph.indexOf(knew2)==-1)
								transAspGraph.add(knew2);
							
						}else if(pos2.equals("VBD")||pos2.equals("VBG")||
								pos2.equals("VBN")||pos2.equals("VBP")||pos2.equals("VBZ")){
							roles[0] = j.getBaseForm(roles[0],"v");
							
							String single1 = Convert.wordMap.get(roles[0].toLowerCase()).get(0);
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
						if(sMap.containsKey(roles[0])){
							right = Transliteration.latinToDevanagari(roles[0]);																
						}
						else{
							right = Convert.wordMap.get(roles[0].toLowerCase()).get(0);	
							if (right.contentEquals("नॉट"))
								right = "नहीं";
						}							
								

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
	    	GraphPassingNode gpnfinal = new GraphPassingNode(transAspGraph,gpn.getposMap(),gpn.getSentence(),gpn.getWordSenseMap());
	    	
	    	return gpnfinal;
	    }
	    
	    
	
	
}
