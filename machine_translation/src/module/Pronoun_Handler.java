package module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class Pronoun_Handler {
	public static ArrayList<GenNode> hinPronoun(ArrayList<GenNode> non_verb, HashMap<Integer, String> classMap, HashMap<Integer, String> prepsMap, boolean pronoun_flag, boolean emotion_flag){
		ArrayList<String> pronoun = new ArrayList<String>(Arrays.asList("मई","हम","आप", "वह", "वे", "इस", "उनके", "मेरा", "आपका", "उसका", "हमारा", "उनका", "यह", "तुम्हारा", "मुझ", "अपन्"));
		ArrayList<String> pr_1 = new ArrayList<String>(Arrays.asList("male","female","animal","organization","location"));
		ListIterator<GenNode> sub_key_m = non_verb.listIterator();
		int count = 1;
		int x = 1;
		int flag = 0;
		int word_id = 0;
		for (Iterator<GenNode> sub_key = non_verb.iterator(); sub_key.hasNext();sub_key.next()) {
			ListIterator<GenNode> sub_key_1 = non_verb.listIterator();
			ListIterator<GenNode> sub_key_conj = non_verb.listIterator();
			while(x < count){
				sub_key_1.next();
				sub_key_conj.next();
				x++;
			}				
			GenNode temp_m = sub_key_1.next();
			
			
			
			GenNode temp_conj = sub_key_conj.next();
			if(sub_key_conj.hasNext()){
				temp_conj = sub_key_conj.next();
				if(temp_conj.getSemanticRole()!=null){					
					while(temp_conj.getSemanticRole().equals("hindi_conjunction") || temp_conj.getSemanticRole().equals("hindi_conjunction_comma")){
						temp_m = sub_key.next();
						if (temp_conj.getSemanticRole().equals("hindi_conjunction_comma")){
//							temp = sub_key.next();
							temp_conj = sub_key_conj.next();	
						}
						temp_m = sub_key.next();
						temp_conj = sub_key_conj.next();
					}		
				}					
			}			
//				
			if (temp_m.getLabel().equals("मई")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();						
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							//debugging line							
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){																	
									sub_key_m.next().setLabel("मैंने");
								}
							}
														
							if(pr_check && pr_check1){		
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}
							}													
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("मैंने");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("मैंने");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("मैंने");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}				
			} else if (temp_m.getLabel().equals("हम")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("हमने");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){	
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
								}
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
								}
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("हमसे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("हमको");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("हमे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			} else if (temp_m.getLabel().equals("आप")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("आपने");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}									
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("आपसे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("आपको");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("आप");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			} else if (temp_m.getLabel().equals("वह")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){														
								if(pr_check1 && pr_check2){																		
									sub_key_m.next().setLabel("वह");									
								}
							}							
							if(pr_check && pr_check1){	
								if(!temp.getLabel().equals("यह")){	
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
								}
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){								
							sub_key_m.next().setLabel("उससे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसको");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			} else if (temp_m.getLabel().equals("वे")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("उन्होंने");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}									
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उन्से");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उन्को");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उन्हें");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			} else if (temp_m.getLabel().equals("इस")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("उसने");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}	
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
													
							if(pr_check && pr_check1){
								
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}			
							if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("उससे");	
							}							
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसको");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			} else if (temp_m.getLabel().equals("उनके")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("उनको");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}		
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उनसे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उनको");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उनको");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			}  else if (temp_m.getLabel().equals("मेरा")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("मेरा");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}		
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("मेरा");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("मेरा");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("मेरा");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			}  else if (temp_m.getLabel().equals("आपका")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("आपका");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}		
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("आपका");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("आपका");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("आपका");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			}  else if (temp_m.getLabel().equals("उसका")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("उसका");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}		
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसका");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसका");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसका");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}								
			}  else if (temp_m.getLabel().equals("हमारा")){				
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("हमारा");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}		
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}			
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("हमारा");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("हमारा");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("हमारा");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}										
			}  else if (temp_m.getLabel().equals("यह")){				
				flag++;
				
				if(flag == 1){
					
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(!pronoun_flag && !emotion_flag){
								if(pr_check1 && pr_check2){
								sub_key_m.next().setLabel("उसने");
								}
							}							
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}		
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){				
					
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("यह");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){			
					
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}			
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("यह");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel("उसे");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}										
			}
			else if (pr_1.contains(classMap.get(temp_m.getId()))){  // check if person or thing etc
				flag++;
				if(flag == 1){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){													
							if(!pronoun_flag && !emotion_flag){								
								if(pr_check1 && pr_check2){									
								sub_key_m.next().setLabel(temp_m.getLabel() + " ने");
								}
							}													
							if(pr_check && pr_check1){
								if(!temp.getLabel().equals("यह")){																									
									String pt = temp.getLabel();
									pt = proAppend(pt);
									temp.setLabel(pt);
									non_verb.set(ind, temp);
								}		
							}										
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 2){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){						
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel(temp_m.getLabel() + " से");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else if(flag == 3){
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){
							if(pr_check && pr_check1){
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel(temp_m.getLabel() + " को");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				} else {
					if(sub_key_1.hasNext()){
						int ind = sub_key_1.nextIndex();
						GenNode temp = sub_key_1.next();
						String prep_check = prepsMap.get(temp.getId()-1);
						String prep_check2 = null;
						if(word_id > 1){
							prep_check2 =  prepsMap.get(word_id-1);	
						}						
						boolean pr_check = true;
						boolean pr_check2 = true;
						if(prep_check!=null){
							if(prep_check.equalsIgnoreCase("the")){
								prep_check = prepsMap.get(temp.getId()-2);								
								if(prep_check != null){
									pr_check = false;
								}								
							}
							else{
								pr_check = false;
							}
						}
						if(prep_check2!=null){
							if(prep_check2.equalsIgnoreCase("the")){
								if(word_id > 2 ){
									prep_check2 = prepsMap.get(word_id-2);
									if(prep_check2 != null){
										pr_check2 = false;
									}
								}								
							}
							else{
								pr_check2 = false;
							}
						}
						String prep_check1 = prepsMap.get(temp_m.getId()-1);
						boolean pr_check1 = true;
						if(prep_check1!=null){
							if(prep_check1.equalsIgnoreCase("the")){
								prep_check1 = prepsMap.get(temp.getId()-2);
								if(prep_check1 != null){
									pr_check1 = false;
								}
							}
							else{
								pr_check1 = false;
							}
						}
						if((pronoun.contains(temp.getLabel()) || pr_1.contains(classMap.get(temp.getId())))){							
							if(pr_check && pr_check1){	
								if(!temp.getLabel().equals("यह")){	
								String pt = temp.getLabel();
								pt = proAppend(pt);
								temp.setLabel(pt);
								non_verb.set(ind, temp);	
								}
							}
							if(pr_check1 && pr_check2){
							sub_key_m.next().setLabel(temp_m.getLabel() + " से");
							}
						}
						else{
							sub_key_m.next();
						}
					}		
				}										
			}
			
			//if not any of the above
			else{				
				sub_key_m.next();
			}
			word_id = temp_m.getId();
			x = 1;
			count++;
		}
		return non_verb;
	}
	
	public static String proAppend(String word){
		if (word.equals("हम")){
			word = "हम";
		} else if (word.equals("आप")){
			word = "आपको";
		} else if (word.equals("वह")){
			word = "उसको";
		} else if (word.equals("वे")){
			word = "वे";
		} else if (word.equals("यह")){
			word = "उसको";
		} else if (word.equals("इस")){
			word = "उसको";
		} else if (word.equals("उनके")){
			word = "उनको";
		} else if (word.equals("आपका")){
			word = "आपको";
		} else if (word.equals("उसका")){
			word = "उसको";
		} else if (word.equals("उनका")){
			word = "उनको";
		} else if (word.equals("तुम्हारा")){
			word = "तुम्को";
		} else if (word.equals("मुझ")){
			word = "मुझको";
		} else if (word.equals("अपन्")){
			word = "हमको";
		} else{
			word = word + " को";
		}
		return word;
	}
	public static String proAppend_hai(String word){
		if (word.equals("हम")){
			word = "हमे";
		} else if (word.equals("आप")){
			word = "तुम्हे";
		} else if (word.equals("वह")){
			word = "उसे";
		} else if (word.equals("वे")){
			word = "वे";
		} else if (word.equals("यह")){
			word = "उसे";
		} else if (word.equals("इस")){
			word = "उसे";
		} else if (word.equals("उनके")){
			word = "उन्हे";
		} else if (word.equals("आपका")){
			word = "तुम्हे";
		} else if (word.equals("उसका")){
			word = "उसे";
		} else if (word.equals("उनका")){
			word = "उन्हे";
		} else if (word.equals("तुम्हारा")){
			word = "तुम्हे";
		} else if (word.equals("मुझ")){
			word = "मुझे";
		} else if (word.equals("अपन्")){
			word = "हमे";
		}
		return word;
	}
}


/* 
* I मैं
* we हम   

* you आप   
* she/he वह   
* they वे   
* her/him इस  

* them उनके   
* my मेरा 	मेरी (f)		mine मेरा   
* your आपका   
* his	उसका   
* our हमारा 			ours हमारा     
* their उनका 		theirs उनका
* it  यह        
* yours	तुम्हारा
*    
* me मुझ
*   * us अपन् 
*/
