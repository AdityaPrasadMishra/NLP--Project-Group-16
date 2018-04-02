package module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class Preposition_Handler {
	static HashMap<String,String> prep_translate = new HashMap<String,String>()
	{{
		put("in","में");
		put("inside","अंदर");
		put("into","के अंदर");
		put("like","की तरह");
		put("of","का");
		put("off","पर से");
		put("on","पर");
		put("onto","पर");
		put("over","ऊपर");
		put("through","के ज़रिए");
		put("towards","तरफ़");
		put("to","को");
		put("from","से");
		put("about","के बारे में");
		put("above","ऊपर");
		put("after","बाद");
		put("against","के खिलाफ");
		put("along","साथ");
		put("at","पर");
		put("before","पहले");
		put("behind","पीछे");
		put("beneath","नीचे");
		put("between","बीच");
		put("by","तक");
		put("down","नीचे");
		put("for","लिए");
		put("with","साथ");
	}};
	
	public static ArrayList<GenNode> hinPrepositions(ArrayList<GenNode> word, HashMap<Integer, String> classMap, HashMap<Integer, String> prepsMap){
		ArrayList<String> pronoun = new ArrayList<String>(Arrays.asList("मई","हम","आप", "वह", "वे", "इस", "उनके", "मेरा", "आपका", "उसका", "हमारा", "उनका", "यह", "तुम्हारा", "मुझ", "अपन्"));
		ArrayList<String> pr_1 = new ArrayList<String>(Arrays.asList("male","female","animal","artifact"));
		ArrayList<String> pr_2 = new ArrayList<String>(Arrays.asList("male","female","animal"));
		for (ListIterator<GenNode> sub_key = word.listIterator(); sub_key.hasNext();) {
			ListIterator<GenNode> sub_key_conj = word.listIterator();
			ListIterator<GenNode> sub_key_traits = word.listIterator();
			int ind = sub_key.nextIndex();			
			if(sub_key.hasNext()){
				GenNode temp = sub_key.next();
				GenNode temp_conj = sub_key_conj.next();				
				if(sub_key_conj.hasNext()){
					temp_conj = sub_key_conj.next();
					if(temp_conj.getSemanticRole()!=null){
						while(temp_conj.getSemanticRole().equals("hindi_conjunction") || temp_conj.getSemanticRole().equals("hindi_conjunction_comma")){
							temp = sub_key.next();
							if(temp_conj.getSemanticRole() != null){
								break;
							}
							if (temp_conj.getSemanticRole().equals("hindi_conjunction_comma")){
//								temp = sub_key.next();
								temp_conj = sub_key_conj.next();	
							}
							temp = sub_key.next();
							temp_conj = sub_key_conj.next();
							if(temp_conj.getSemanticRole() != null){
								break;
							}
						}		
					}						
				}										
				int id = 0;
				id = temp.getId()-1;
				String prep_check = prepsMap.get(id);			
				boolean pr_check = false;
				if(prep_check!=null){		
					if(temp.getSemanticRole() != null){
					while(temp.getSemanticRole().equals("identifier") || temp.getSemanticRole().equals("trait")
					|| temp.getSemanticRole().equals("complement_word") || temp.getSemanticRole().equals("is_possessed_by")
					|| temp.getSemanticRole().equals("is_related_to") || temp.getSemanticRole().equals("size")){
						if(sub_key.hasNext()){							
							temp = sub_key.next();
							if(temp.getSemanticRole() == null){
								break;
							}
						}						
					}
					}
					if(prep_check.equalsIgnoreCase("the")){
						id = temp.getId()-2;
						prep_check = prepsMap.get(id);
						if(prep_check != null){
							pr_check = true;
						}
					}
					else{
						pr_check = true;
					}
				}			
				if(pr_check){
					GenNode prep = new GenNode();
					prep.setId(id);
					String prep_label;
					if (pronoun.contains(temp.getLabel())){
						prep_label = prep_Label(prep_check,true);
						temp.setLabel(pro_Label(temp.getLabel()));
						word.set(ind, temp);
					}
					else if(pr_2.contains(classMap.get(temp.getId()))){
						prep_label = prep_Label(prep_check,true);
					}
					else{
						prep_label = prep_Label(prep_check,false);
					}
					prep.setLabel(prep_label);
					prep.setSemanticRole("preposition");
					prep.setChildren(null);
					sub_key.add(prep);
//					word.add(ind, prep);
				}
		
			}
		}
		ArrayList<GenNode> word2 = new ArrayList<GenNode>();
		for(GenNode key: word){			
			if(key == null || key.getLabel() == null){
				//do nothing
			}
			else{
				word2.add(key);
			}
		}
		return word2;
	}
	public static String prep_Label(String prep_word,boolean flag){
		String translate = prep_translate.get(prep_word.toLowerCase());
		if(prep_word.equalsIgnoreCase("by") && flag == true){
			return "ने";
		}
		return translate;
	}
	public static String pro_Label(String pro_word){
		if (pro_word.equals("हम")){
			pro_word = "हम";
		} else if (pro_word.equals("आप")){
			pro_word = "आप";
		} else if (pro_word.equals("वह")){
			pro_word = "उस";
		} else if (pro_word.equals("वे")){
			pro_word = "वे";
		} else if (pro_word.equals("यह")){
			pro_word = "उस";
		} else if (pro_word.equals("इस")){
			pro_word = "उस";
		} else if (pro_word.equals("उनके")){
			pro_word = "उन";
		} else if (pro_word.equals("आपका")){
			pro_word = "आप";
		} else if (pro_word.equals("उसका")){
			pro_word = "उस";
		} else if (pro_word.equals("उनका")){
			pro_word = "उन";
		} else if (pro_word.equals("तुम्हारा")){
			pro_word = "तुम्";
		} else if (pro_word.equals("मुझ")){
			pro_word = "मुझ";
		} else if (pro_word.equals("अपन्")){
			pro_word = "हम";
		}
		return pro_word;
	}
}
