package module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import module.GenNode;
import module.graph.helper.GraphPassingNode;

public class HindiGenerator {

	static String temp = "";
	static String sentence = "";
	static String check = "";
	static boolean emotion_flag = false;
	static ArrayList<Integer> same_word_count = new ArrayList<Integer>();
	static ArrayList<Integer> same_word_count_g = new ArrayList<Integer>();
	static ArrayList<GenNode> verb_g = new ArrayList<GenNode>();
	static ArrayList<GenNode> non_verb_g = new ArrayList<GenNode>();
	static ArrayList<GenNode> verb_g_output = new ArrayList<GenNode>();
	static ArrayList<GenNode> non_verb_g_output = new ArrayList<GenNode>();
	static ArrayList<GenNode> non_verb_g_output_final = new ArrayList<GenNode>();
	static boolean pronoun_flag = false;

	public static String hinGenerator(GraphPassingNode gpn, HashMap<Integer, String> classMap,
			HashMap<Integer, String> prepsMap, HashMap<String, String> sMap, int question) {
		String translation_g = "";

		try {
			String translation = new String();
			ArrayList<String> pr_1 = new ArrayList<String>(
					Arrays.asList("male", "female", "animal", "artifact", "organization"));
			ArrayList<String> pronoun_gen = new ArrayList<String>(Arrays.asList("मई", "उन", "तुम्", "मुझ", "उस", "हम",
					"आप", "वह", "वे", "इस", "उनके", "मेरा", "आपका", "उसका", "हमारा", "उनका", "यह", "तुम्हारा", "अपन्"));
			GenNode hin = GenNodeConverter.genNodeConverter(gpn);

			if (hin == null) {
				return "Error in parsing.";
			}

			// printGen(hin);
			translation = GenerateHinSent(hin, classMap, sMap);

			non_verb_g_output = Pronoun_Handler.hinPronoun(non_verb_g, classMap, prepsMap, pronoun_flag, emotion_flag);

			non_verb_g_output_final = Preposition_Handler.hinPrepositions(non_verb_g_output, classMap, prepsMap);

			verb_g_output = Preposition_Handler.hinPrepositions(verb_g, classMap, prepsMap);

			int pro_flag = 0;
			if (!non_verb_g_output_final.isEmpty()) {
				GenNode prevkey = non_verb_g_output_final.get(0);
				for (GenNode key : non_verb_g_output_final) {
					if (sMap.containsKey(key.getLabel())) {
						pro_flag = 1;
					}
					if (pro_flag != 1) {
						if (key.getSemanticRole() != null) {

							if (key.getSemanticRole().equals("preposition")
									&& pronoun_gen.contains(prevkey.getLabel())) {
								int ind = translation_g.lastIndexOf(" ");
								translation_g = new StringBuilder(translation_g).replace(ind, ind + 1, "").toString();

							}
						}
					}
					pro_flag = 0;
					translation_g = translation_g + key.getLabel() + " ";
					prevkey = key;
				}
			}
			String translatioVerb = "";
			String negativetranslation ="";
			String verb = "";
			int main_flag1 = 0;
			for (GenNode key : verb_g_output) {
				if (key.getLabel() != null) {
					
					if ((key.getLabel().equals("होता है") || key.getLabel().equals("होना"))) {
						key.setLabel("है");
					} else if (key.getLabel().equals("खाया")) {
						main_flag1 = 1;

					} else if (key.getLabel().equals("हुआ था") && main_flag1 == 1) {
						key.setLabel("था");
					}
					if(key.getSemanticRole() == null) {
						verb = key.getLabel();
					}
					else if ((key.getSemanticRole()!=null)&&(key.getSemanticRole().equals("negative")))
						negativetranslation  =key.getLabel() ;
					else {
						translatioVerb = translatioVerb + " " + key.getLabel() ;
					}
				}

			}
			translatioVerb = translatioVerb + " " +negativetranslation+" " +verb;
			
			
			translation_g += " "+translatioVerb;
			if (question == 1) {
				translation_g = translation_g + "?";
			}
			translation_g = translation_g.replace("~", " ");
		} catch (Exception ex) {
			if (translation_g != null) {
				return translation_g;
			} else
				return "Error in parsing";
		}

		return translation_g;
	}

	
	
	
	
	private static String Sub_Child(GenNode subhpn) {
		String trail = "";
		String lead = "";
		String ret_sentence = "";
		String conjunction = "";
		if (!same_word_count.contains(subhpn.getId())) {
			same_word_count.add(subhpn.getId());
			HashMap<Integer, String> sub_child_trail = new HashMap<Integer, String>();
			HashMap<Integer, String> sub_child_lead = new HashMap<Integer, String>();
			HashMap<Integer, String> conj_words = new HashMap<Integer, String>();
			for (GenNode subhpn2 : subhpn.getChildren()) {
				if (subhpn2.getSemanticRole() == null) {
					break;
				}
				if (subhpn2.getId() == subhpn.getId()) {
					// save the tense -> books - has a child BOOK
					// (NNS) -> for further use
				} else if (subhpn2.getSemanticRole().equals("identifier") || subhpn2.getSemanticRole().equals("trait")
						|| subhpn2.getSemanticRole().equals("complement_word")
						|| subhpn2.getSemanticRole().equals("is_possessed_by")
						|| subhpn2.getSemanticRole().equals("is_related_to")
						|| subhpn2.getSemanticRole().equals("destination")
						|| subhpn2.getSemanticRole().equals("size")) {
					same_word_count.add(subhpn2.getId());
					if (subhpn2.getId() > subhpn.getId()) {
						sub_child_trail.put(subhpn2.getId(), subhpn2.getLabel());
					} else {
						sub_child_lead.put(subhpn2.getId(), subhpn2.getLabel());
					}
				} else if (subhpn2.getSemanticRole().equals("in_conjunction_with")) {
					same_word_count.add(subhpn2.getId());
					conj_words.put(subhpn2.getId(), subhpn2.getLabel());
					// System.out.println(subhpn2.getLabel());
				}
				int length = conj_words.size();
				// System.out.println(length);
				int fl = 0;
				if (length > 0) {
					if (length == 1) {
						fl = 1;
					}
					for (String key : conj_words.values()) {
						if (fl == 1) {
							conjunction = " " + "और" + " " + key + " ";
							fl = 0;
						} else {
							if (length == 1) {
								conjunction = conjunction + "और" + " " + key + " ";
							} else {
								conjunction = conjunction + ", " + key + " ";
							}
						}
						length--;
					}
				}
			}

			for (String key : sub_child_lead.values()) {
				lead = lead + key + " ";
			}
			for (String key : sub_child_trail.values()) {
				trail = trail + key + " ";
			}
		}

		if (trail.equals("")) {
			if (conjunction.equals("")) {
				ret_sentence = lead + subhpn.getLabel() + " ";
			} else {
				ret_sentence = lead + subhpn.getLabel() + conjunction;
			}
		} else {
			if (conjunction.equals("")) {
				ret_sentence = lead + subhpn.getLabel() + " " + trail;
			} else {
				ret_sentence = lead + subhpn.getLabel() + conjunction + trail;
			}
		}
		return ret_sentence;
	}

	private static void Agent_Recipient_gen(GenNode subhpn, GenNode inputhpn) {
		if (!same_word_count_g.contains(subhpn.getId())) {
			same_word_count.add(subhpn.getId());
			same_word_count_g.add(subhpn.getId());
			/*
			 * System.out.println("inside function call");
			 * System.out.println(subhpn.getLabel());
			 * System.out.println(subhpn.getSemanticRole());
			 */
			String trait = "";
			String temp_agent = "";
			String conjunction = "";
			String negative = "";
			String destination = "";
			ArrayList<GenNode> negative_g = new ArrayList<GenNode>();
			ArrayList<GenNode> temp_agent_g = new ArrayList<GenNode>();
			ArrayList<GenNode> conjunction_g = new ArrayList<GenNode>();
			ArrayList<GenNode> destination_g = new ArrayList<GenNode>();
			ArrayList<GenNode> trait_g = new ArrayList<GenNode>();
			HashMap<Integer, String> conj_words = new HashMap<Integer, String>();
			HashMap<Integer, ArrayList<GenNode>> conj_words_g = new HashMap<Integer, ArrayList<GenNode>>();
			HashMap<Integer, GenNode> ag_word = new HashMap<Integer, GenNode>();
			int flag = 0;
			/* System.out.println("came here too"); */
			for (GenNode subhpn2 : subhpn.getChildren()) {
				/*
				 * System.out.println("inside loop"); System.out.println(subhpn.getLabel());
				 * System.out.println(subhpn2.getLabel()); System.out.println(subhpn2.getId());
				 * System.out.println(subhpn2.getSemanticRole());
				 */
				if (!same_word_count_g.contains(subhpn2.getId())) {
					/* System.out.println("came inside"); */
					if (subhpn2.getSemanticRole() == null) {
						break;
					}
					if (subhpn2.getId() == subhpn.getId()) {
						// save the tense -> books - has a child BOOK
						// (NNS) -> for further use
					} else if (subhpn2.getSemanticRole().equals("identifier")
							|| subhpn2.getSemanticRole().equals("trait")
							|| subhpn2.getSemanticRole().equals("complement_word")
							|| subhpn2.getSemanticRole().equals("is_possessed_by")
							|| subhpn2.getSemanticRole().equals("is_related_to")
							|| subhpn2.getSemanticRole().equals("size")) {

						ag_word.put(subhpn2.getId(), subhpn2);
						// same_word_count.add(subhpn2.getId());
					} else if (subhpn2.getSemanticRole().equals("negative")) {
						same_word_count.add(subhpn2.getId());
						same_word_count_g.add(subhpn2.getId());
						if (flag == 0) {
							negative = negative + subhpn2.getLabel() + " " + inputhpn.getLabel() + " ";
							negative_g.add(subhpn2);
							negative_g.add(inputhpn);
						} else {
							negative = negative + subhpn2.getLabel() + " ";
							negative_g.add(subhpn2);
						}
						flag = 1;
					} else if (subhpn2.getSemanticRole().equals("destination")) {
						if (flag == 0) {
							if (!subhpn2.getChildren().isEmpty()
									&& ((subhpn2.getChildren().get(0).getId() != subhpn2.getId())
											|| (subhpn2.getChildren().size() > 1))) {
								destination = inputhpn.getLabel() + " " + Sub_Child(subhpn2);
								destination_g.add(inputhpn);
								ArrayList<GenNode> temp = new ArrayList<GenNode>();
								temp = Sub_Child_G(subhpn2);
								if (temp.size() != 0) {
									destination_g.addAll(temp);
								}
							} else {
								destination = inputhpn.getLabel() + " " + subhpn2.getLabel() + " ";
								destination_g.add(inputhpn);
								destination_g.add(subhpn2);
							}
						} else {
							if (!subhpn2.getChildren().isEmpty()
									&& ((subhpn2.getChildren().get(0).getId() != subhpn2.getId())
											|| (subhpn2.getChildren().size() > 1))) {
								destination = Sub_Child(subhpn2);
								ArrayList<GenNode> temp = new ArrayList<GenNode>();
								temp = Sub_Child_G(subhpn2);
								if (temp.size() != 0) {
									destination_g.addAll(temp);
								}
							} else {
								destination = subhpn2.getLabel() + " ";
								destination_g.add(subhpn2);
							}
						}
						flag = 1;
						same_word_count.add(subhpn2.getId());
						same_word_count_g.add(subhpn2.getId());
					} else if (subhpn2.getSemanticRole().equals("is_participant_in")) {
						same_word_count.add(subhpn2.getId());
						same_word_count_g.add(subhpn2.getId());
						temp_agent = subhpn2.getLabel() + " ";
						temp_agent_g.add(subhpn2);
					} else if (subhpn2.getSemanticRole().equals("in_conjunction_with")) {
						/*
						 * System.out.println(subhpn.getLabel()); System.out.println("conjunction");
						 * System.out.println(subhpn2.getLabel());
						 */
						if (!subhpn2.getChildren().isEmpty()
								&& ((subhpn2.getChildren().get(0).getId() != subhpn2.getId())
										|| (subhpn2.getChildren().size() > 1))) {
							/* System.out.println("came here"); */
							conj_words.put(subhpn2.getId(), Sub_Child(subhpn2));
							ArrayList<GenNode> temp = new ArrayList<GenNode>();
							temp = Sub_Child_G(subhpn2);
							conj_words_g.put(subhpn2.getId(), temp);
						} else {
							/* System.out.println("came here 1"); */
							conj_words.put(subhpn2.getId(), subhpn2.getLabel());
							ArrayList<GenNode> temp = new ArrayList<GenNode>();
							temp.add(subhpn2);
							conj_words_g.put(subhpn2.getId(), temp);
						}
						same_word_count.add(subhpn2.getId());
						same_word_count_g.add(subhpn2.getId());
						// System.out.println(subhpn2.getLabel());
					}
				}

			}
			for (GenNode key : ag_word.values()) {
				if (!same_word_count.contains(key.getId())) {
					if (key.getId() > subhpn.getId()) {
						if (!key.getChildren().isEmpty() && ((key.getChildren().get(0).getId() != key.getId())
								|| (key.getChildren().size() > 1))) {
							trait = trait + Sub_Child(key);
							trait_g.addAll(Sub_Child_G(key));
						} else {
							trait = trait + key.getLabel() + " ";
							trait_g.add(key);
						}
					} else {
						if (!key.getChildren().isEmpty() && ((key.getChildren().get(0).getId() != key.getId())
								|| (key.getChildren().size() > 1))) {
							sentence = sentence + Sub_Child(key);
							non_verb_g.addAll(Sub_Child_G(key));
						} else {
							sentence = sentence + key.getLabel() + " ";
							non_verb_g.add(key);
						}
					}
				}
				same_word_count.add(key.getId());
				same_word_count_g.add(key.getId());
			}
			int length = conj_words.size();
			// System.out.println(length);
			int fl = 0;
			if (length > 0) {
				if (length == 1) {
					fl = 1;
				}
				for (String key : conj_words.values()) {
					if (fl == 1) {
						conjunction = " " + "और" + " " + key + " ";
						fl = 0;
					} else {
						if (length == 1) {
							conjunction = conjunction + "और" + " " + key + " ";
						} else {
							conjunction = conjunction + ", " + key + " ";
						}
					}
					length--;
				}
			}
			int fl_g = 0;
			length = conj_words_g.size();
			/*
			 * System.out.println("conjunction length"); System.out.println(length);
			 */
			if (length > 0) {
				/* System.out.println("came insie length"); */
				GenNode aur_g = new GenNode();
				aur_g.setLabel("और");
				aur_g.setId(0);
				aur_g.setChildren(null);
				aur_g.setSemanticRole("hindi_conjunction");
				GenNode aur_comma_g = new GenNode();
				aur_comma_g.setLabel(",");
				aur_comma_g.setId(0);
				aur_comma_g.setChildren(null);
				aur_comma_g.setSemanticRole("hindi_conjunction_comma");
				if (length == 1) {
					/* System.out.println("hi"); */
					fl_g = 1;
				}
				for (ArrayList<GenNode> key : conj_words_g.values()) {
					if (fl_g == 1) {
						conjunction_g.add(aur_g);
						conjunction_g.addAll(key);
						fl_g = 0;
					} else {
						if (length == 1) {
							conjunction_g.add(aur_g);
							conjunction_g.addAll(key);
						} else {
							conjunction_g.add(aur_comma_g);
							conjunction_g.addAll(key);
						}
					}
					length--;
				}
			}

			// conjunction = "और " + conjunction + " ";
			if (conjunction.equals("")) {
				sentence = temp_agent + sentence + subhpn.getLabel() + " " + negative + destination + conjunction
						+ trait;
			} else {
				sentence = temp_agent + sentence + subhpn.getLabel() + negative + destination + conjunction + trait;
			}
			if (temp_agent_g.size() != 0) {
				non_verb_g.addAll(temp_agent_g);
			}
			non_verb_g.add(subhpn);
			if (negative_g.size() != 0) {
				non_verb_g.addAll(negative_g);
			}
			if (destination_g.size() != 0) {
				non_verb_g.addAll(destination_g);
			}
			if (conjunction_g.size() != 0) {
				non_verb_g.addAll(conjunction_g);
			}
			if (trait_g.size() != 0) {
				non_verb_g.addAll(trait_g);
			}

			// subhpn check if objective then add differently
		}
	}

	private static String Verb_Modifier(HashMap<Integer, GenNode> subhpn) {
		String modifier = "";
		String time = "";
		// change here based on time not explicit word
		for (GenNode key : subhpn.values()) {
			if (key.getLabel().equals("When") || key.getLabel().equals("when")) {
				time = key.getLabel() + " ";
			} else {
				modifier = key.getLabel() + " " + modifier;
			}
		}
		modifier = modifier + time;
		return modifier;
	}

	private static ArrayList<GenNode> Verb_Modifier_G(HashMap<Integer, GenNode> subhpn) {
		ArrayList<GenNode> verb_modif_g = new ArrayList<GenNode>();
		GenNode time_g = null;
		// change here based on time not explicit word
		for (GenNode key : subhpn.values()) {
			if (key.getLabel().equals("When") || key.getLabel().equals("when")) {
				time_g = key;
			} else {
				int index_modif = 0;
				verb_modif_g.add(index_modif, key);
			}
		}
		if (time_g != null) {
			verb_modif_g.add(time_g);
		}
		return verb_modif_g;
	}

	private static ArrayList<GenNode> Sub_Child_G(GenNode subhpn) {
		ArrayList<GenNode> conjunction_g = new ArrayList<GenNode>();
		ArrayList<GenNode> lead_g = new ArrayList<GenNode>();
		ArrayList<GenNode> trail_g = new ArrayList<GenNode>();
		ArrayList<GenNode> ret_sentence_g = new ArrayList<GenNode>();
		if (!same_word_count_g.contains(subhpn.getId())) {
			same_word_count_g.add(subhpn.getId());
			HashMap<Integer, GenNode> sub_child_trail = new HashMap<Integer, GenNode>();
			HashMap<Integer, GenNode> sub_child_lead = new HashMap<Integer, GenNode>();
			HashMap<Integer, GenNode> conj_words = new HashMap<Integer, GenNode>();
			for (GenNode subhpn2 : subhpn.getChildren()) {
				if (subhpn2.getSemanticRole() == null) {
					break;
				}
				if (subhpn2.getId() == subhpn.getId()) {
					// save the tense -> books - has a child BOOK
					// (NNS) -> for further use
				} else if (subhpn2.getSemanticRole().equals("identifier") || subhpn2.getSemanticRole().equals("trait")
						|| subhpn2.getSemanticRole().equals("complement_word")
						|| subhpn2.getSemanticRole().equals("is_possessed_by")
						|| subhpn2.getSemanticRole().equals("is_related_to")
						|| subhpn2.getSemanticRole().equals("destination")
						|| subhpn2.getSemanticRole().equals("size")) {
					same_word_count_g.add(subhpn2.getId());
					if (subhpn2.getId() > subhpn.getId()) {
						sub_child_trail.put(subhpn2.getId(), subhpn2);
					} else {
						sub_child_lead.put(subhpn2.getId(), subhpn2);
					}
				} else if (subhpn2.getSemanticRole().equals("in_conjunction_with")) {
					same_word_count_g.add(subhpn2.getId());
					conj_words.put(subhpn2.getId(), subhpn2);
					// System.out.println(subhpn2.getLabel());
				}
				int length = conj_words.size();
				// System.out.println(length);
				int fl = 0;
				if (length > 0) {
					GenNode aur_g = new GenNode();
					aur_g.setLabel("और");
					aur_g.setId(0);
					aur_g.setChildren(null);
					aur_g.setSemanticRole("hindi_conjunction");
					GenNode aur_comma_g = new GenNode();
					aur_g.setLabel(",");
					aur_g.setId(0);
					aur_g.setChildren(null);
					aur_g.setSemanticRole("hindi_conjunction_comma");
					if (length == 1) {
						fl = 1;
					}
					for (GenNode key : conj_words.values()) {
						if (fl == 1) {
							// conjunction = " " + "और" + " " + key + " ";
							conjunction_g.add(aur_g);
							conjunction_g.add(key);
							fl = 0;
						} else {
							if (length == 1) {
								// conjunction = conjunction + "और" + " " + key + " ";
								conjunction_g.add(aur_g);
								conjunction_g.add(key);
							} else {
								// conjunction = conjunction + ", " + key + " ";
								conjunction_g.add(aur_comma_g);
								conjunction_g.add(key);
							}
						}
						length--;
					}
				}
			}

			for (GenNode key : sub_child_lead.values()) {
				// lead = lead + key + " ";
				lead_g.add(key);
			}
			for (GenNode key : sub_child_trail.values()) {
				// trail = trail + key + " ";
				trail_g.add(key);
			}
		}
		if (lead_g.size() != 0) {
			ret_sentence_g.addAll(lead_g);
		}
		ret_sentence_g.add(subhpn);
		if (conjunction_g.size() != 0) {
			ret_sentence_g.addAll(conjunction_g);
		}
		if (trail_g.size() != 0) {
			ret_sentence_g.addAll(trail_g);
		}
		return ret_sentence_g;
	}

	private static String Verb_Modal(HashMap<Integer, GenNode> subhpn, int question) {
		String modifier = "";
		for (GenNode key : subhpn.values()) {
			// if (key.getLabel().equals("इच्छा")){ // will
			//
			// }
			// else if (key.getLabel().equals("सकना")){ //can
			//
			// }
			// else if (key.getLabel().equals("{प्रश्नवाचक}क्या")){ //could
			//
			// }
			// else if (key.getLabel().equals("सकना")){ may
			// kya mein apki maadad kar sakta hun?
			// kya mein apki maadad kar sakta hun?
			//
			// }
			// else if (key.getLabel().equals("सकना")){
			//
			// }
			// else if (key.getLabel().equals("सकना")){
			//
			// }
			// else if (key.getLabel().equals("सकना")){
			//
			// }
			// else if (key.getLabel().equals("सकना")){
			//
			// }
			// else if (key.getLabel().equals("सकना")){
			//
			// }
			// else if (key.getLabel().equals("सकना")){
			//
			// }
			modifier = key.getLabel() + " " + modifier;
		}
		return modifier;
	}

	private static String GenerateHinSent(GenNode inputhpn, HashMap<Integer, String> classMap,
			HashMap<String, String> sMap) {
		// Working on it
		// handling is -> है
		/*
		 * if(inputhpn.getLabel().equals("होना")){ inputhpn.setLabel("है"); }
		 */

		try {
			int id = inputhpn.getId();
			int flag = 0;
			int mod = 0;
			int modal = 0;
			int question = 0;
			String negative = "";
			GenNode negative_g = new GenNode();
			negative_g.setLabel("hindi_negative_flag");
			ArrayList<GenNode> verb_modifier_g = new ArrayList<GenNode>();
			ArrayList<GenNode> verb_modal_g = new ArrayList<GenNode>();
			check = inputhpn.getSemanticRole();
			//System.out.println("loop starting");
			if (check == null) {
				
				for (GenNode subhpn : inputhpn.getChildren()) {
					//System.out.println(subhpn.getLabel());
					//System.out.println(subhpn.getSemanticRole());
					if (subhpn.getSemanticRole().equals("modal_verb")) {
						pronoun_flag = true;
					}
					if (subhpn.getSemanticRole().equals("VBD") || subhpn.getSemanticRole().equals("VBG")
							|| subhpn.getSemanticRole().equals("VBN") || subhpn.getSemanticRole().equals("VBP")
							|| subhpn.getSemanticRole().equals("VBZ")) {
						String pt = subhpn.getLabel();
						// modify this
						if (pt.contains("ing")) {
							// || pt.contains("ed")
							pronoun_flag = true;
						}
					}
					//System.out.println("Changed");
					//System.out.println(subhpn.getLabel());
					//System.out.println(subhpn.getSemanticRole());
				}
				System.out.println("loop starting");
				String check = classMap.get(inputhpn.getId());
				if (check != null) {
					if (check.equals("emotion")) {
						emotion_flag = true;
					}
				}
			}

			int passive_flag = 0;
			GenNode passive = null;
			HashMap<Integer, GenNode> verb_modifier = new HashMap<Integer, GenNode>();
			HashMap<Integer, GenNode> verb_modal = new HashMap<Integer, GenNode>();
			if (check == null
					|| (!check.equals("agent") && !check.equals("recipient") && !check.equals("attachment"))) {
				if (!same_word_count_g.contains(inputhpn.getId())) {
					for (GenNode subhpn : inputhpn.getChildren()) {
						String a = subhpn.getSemanticRole();
						if (a.equals("passive_supporting_verb")) {
							passive_flag = 1;
							passive = subhpn;
							same_word_count.add(subhpn.getId());
							same_word_count_g.add(subhpn.getId());
						}
						if (subhpn.getLabel().equals("होना")) {
							subhpn.setLabel("है");
						}
						if (subhpn.getLabel().equals("आई")) {
							subhpn.setLabel("मई");
						}
						if (a.equals("attachment") && (!subhpn.getLabel().equals("इस")
								&& !subhpn.getLabel().equals("उनके") && !subhpn.getLabel().equals("आप")
								&& !subhpn.getLabel().equals("अपन्") && !subhpn.getLabel().equals("मुझ")
								&& !subhpn.getLabel().equals("यह") && !sMap.containsKey(subhpn.getLabel()))) {
							flag = 1;
						}
						if (a.equals("modifier")) {
							verb_modifier.put(subhpn.getId(), subhpn);
							mod = 1;
						}
						if (a.equals("modal_verb")) {
							verb_modal.put(subhpn.getId(), subhpn);
							modal = 1;
						}
						if (a.equals("question") || subhpn.getLabel().equals("क्या")) {
							question = 1;
						}
						if (a.equals("negative")) {
							negative = subhpn.getLabel() + " ";
							negative_g = subhpn;
							same_word_count.add(subhpn.getId());
							same_word_count_g.add(subhpn.getId());
						}
						// i was here ##############################
						for (GenNode subhpn2 : subhpn.getChildren()) {
							if (subhpn2.getSemanticRole().equals("destination")
									|| subhpn2.getSemanticRole().equals("negative")) {
								flag = 1;
								same_word_count.add(inputhpn.getId());
								same_word_count_g.add(inputhpn.getId());
							}
						}

					}
					if (flag == 0 && mod == 0) {
						same_word_count.add(inputhpn.getId());
						same_word_count_g.add(inputhpn.getId());
						temp =  negative + inputhpn.getLabel() + " "  + Verb_Modal(verb_modal, question) + temp;
						int ind = 0;
						verb_g.add(ind, inputhpn);
						ind++;
						if (!negative_g.getLabel().equals("hindi_negative_flag")) {
							verb_g.add(ind, negative_g);
							ind++;
						}
						if (passive_flag == 1) {
							verb_g.add(ind, passive);
						}
						// modal verbs need to handle #######################################3
					}
					// temp = inputhpn.getLabel() + " " + temp;
					else if (flag == 0 && mod == 1) {
						same_word_count.add(inputhpn.getId());
						same_word_count.add(inputhpn.getId());
						temp = Verb_Modifier(verb_modifier) + inputhpn.getLabel() + " " + negative
								+ Verb_Modal(verb_modal, question) + temp;
						verb_modifier_g = Verb_Modifier_G(verb_modifier);
						int ind = 0;
						verb_g.addAll(ind, verb_modifier_g);
						ind = verb_modifier_g.size();
						verb_g.add(ind, inputhpn);
						ind++;
						if (!negative_g.getLabel().equals("hindi_negative_flag")) {
							verb_g.add(ind, negative_g);
							ind++;
						}
						if (passive_flag == 1) {
							verb_g.add(ind, passive);
						}
						// modal verb need to handle #############################################
					}
				}
			}

			if (!inputhpn.getChildren().isEmpty()) {
				HashMap<Integer, GenNode> agn_position = new HashMap<Integer, GenNode>();
				for (GenNode subhpn : inputhpn.getChildren()) {
					if (subhpn.getSemanticRole() != null) {
						if (subhpn.getSemanticRole().equals("agent")) {
							if (subhpn.getLabel().equals("क्या") || subhpn.getLabel().equals("कहाँ")
									|| subhpn.getLabel().equals("कौन") || subhpn.getLabel().equals("कैसे")
									|| subhpn.getLabel().equals("कब") || subhpn.getLabel().equals("कितना")
									|| subhpn.getLabel().equals("कितने") || subhpn.getLabel().equals("किसका")
									|| subhpn.getLabel().equals("क्यों")) {
								int ind = 0;
								verb_g.add(ind, subhpn);
								same_word_count.add(subhpn.getId());
							} else {
								/*
								 * System.out.println(subhpn.getLabel());
								 * System.out.println(subhpn.getSemanticRole());
								 */
								agn_position.put(subhpn.getId(), subhpn);
							}
							// Agent_Recipient_gen(subhpn);
						}
					}
				}

				for (GenNode key : agn_position.values()) {
					/* System.out.println("checking " +key.getLabel()); */
					int cot = 0;
					for (GenNode dupkey : agn_position.values()) {
						for (GenNode subchildkey : dupkey.getChildren()) {
							if (subchildkey.getId() == key.getId()) {
								if (subchildkey.getSemanticRole().equals("in_conjunction_with")) {
									cot = 1;
									// if(subchildkey.getId() < dupkey.getId()){
									// int tem = dupkey.getId();
									// dupkey.setId(subchildkey.getId());
									// subchildkey.setId(tem);
									// }
								}
							}
						}
					}
					/*
					 * System.out.println("checked " +key.getLabel()); System.out.println(cot);
					 */
					/* System.out.println(key.getLabel()); */
					if (cot == 0) {
						/* System.out.println(key.getLabel()); */
						Agent_Recipient_gen(key, inputhpn);
						cot = 0;
					}
				}

				HashMap<Integer, GenNode> rec_position = new HashMap<Integer, GenNode>();

				/*
				 * for (GenNode subhpn : inputhpn.getChildren()) { if (subhpn.getSemanticRole()
				 * != null) { rec_position.put(subhpn.getId(), subhpn); //change here } }
				 */

				for (GenNode subhpn : inputhpn.getChildren()) {
					if (subhpn.getSemanticRole() != null) {
						if (subhpn.getSemanticRole().equals("recipient")
								|| subhpn.getSemanticRole().equals("quality")) {
							if (subhpn.getLabel().equals("क्या") || subhpn.getLabel().equals("कहाँ")
									|| subhpn.getLabel().equals("कौन") || subhpn.getLabel().equals("कैसे")
									|| subhpn.getLabel().equals("कब") || subhpn.getLabel().equals("कितना")
									|| subhpn.getLabel().equals("कितने") || subhpn.getLabel().equals("किसका")
									|| subhpn.getLabel().equals("क्यों")) {
								int ind = 0;
								verb_g.add(ind, subhpn);
								same_word_count.add(subhpn.getId());
							} else {
								rec_position.put(subhpn.getId(), subhpn);
							}
							// Agent_Recipient_gen(subhpn);
						}
					}
				}

				HashMap<Integer, GenNode> rec_pro_position = new HashMap<Integer, GenNode>();

				for (GenNode subhpn : rec_position.values()) {
					if (subhpn.getSemanticRole().equals("attachment") && (subhpn.getLabel().equals("इस")
							|| subhpn.getLabel().equals("उनके") || subhpn.getLabel().equals("आप")
							|| subhpn.getLabel().equals("अपन्") || subhpn.getLabel().equals("मुझ")
							|| subhpn.getLabel().equals("यह") || sMap.containsKey(subhpn.getLabel()))) {
						Agent_Recipient_gen(subhpn, inputhpn);
					} else if (subhpn.getSemanticRole().equals("attachment")) {
						rec_pro_position.put(subhpn.getId(), subhpn);
					} else if (subhpn.getSemanticRole().equals("recipient") && (subhpn.getLabel().equals("इस")
							|| subhpn.getLabel().equals("उनके") || subhpn.getLabel().equals("आप")
							|| subhpn.getLabel().equals("अपन्") || subhpn.getLabel().equals("मुझ")
							|| subhpn.getLabel().equals("यह") || sMap.containsKey(subhpn.getLabel()))) {
						Agent_Recipient_gen(subhpn, inputhpn);
					} else if (subhpn.getSemanticRole().equals("recipient")) {
						rec_pro_position.put(subhpn.getId(), subhpn);
					}
				}

				for (GenNode subhpn : rec_pro_position.values()) {
					if (subhpn.getSemanticRole().equals("attachment")) {
						Agent_Recipient_gen(subhpn, inputhpn);
						if (mod == 1 || modal == 1) {
							if (!same_word_count.contains(inputhpn.getId())) {
								same_word_count.add(inputhpn.getId());
								same_word_count_g.add(inputhpn.getId());
								sentence = sentence + Verb_Modifier(verb_modifier) + inputhpn.getLabel() + " "
										+ negative + Verb_Modal(verb_modal, question);
								int ind = non_verb_g.size();
								if (verb_modifier_g.size() != 0) {
									non_verb_g.addAll(ind, verb_modifier_g);
									ind = non_verb_g.size();
								}
								non_verb_g.add(inputhpn);
								ind++;
								if (!negative_g.getLabel().equals("hindi_negative_flag")) {
									non_verb_g.add(negative_g);
									ind++;
								}
								// modal verb need to handle #################################################
							}
						} else {
							if (!same_word_count.contains(inputhpn.getId())) {
								same_word_count.add(inputhpn.getId());
								same_word_count_g.add(inputhpn.getId());
								sentence = sentence + inputhpn.getLabel() + " " + negative;
								int ind = non_verb_g.size();
								non_verb_g.add(inputhpn);
								ind++;
								if (!negative_g.getLabel().equals("hindi_negative_flag")) {
									non_verb_g.add(negative_g);
									ind++;
								}
							}
						}
					}
				}

				// to handle raw materials issue -> not sure (kparser output)correct or not

				for (GenNode subhpn : inputhpn.getChildren()) {
					if (!same_word_count.contains(subhpn.getId())) {
						if (subhpn.getSemanticRole() != null) {
							if (!subhpn.getSemanticRole().equals("agent")
									&& !subhpn.getSemanticRole().equals("recipient")
									&& !subhpn.getSemanticRole().equals("attachment")
									&& !subhpn.getSemanticRole().equals("modifier")
									&& !subhpn.getSemanticRole().equals("modal_verb")
									&& !subhpn.getSemanticRole().equals("supporting_verb")
									&& !subhpn.getSemanticRole().equals("quality")) {
								GenerateHinSent(subhpn, classMap, sMap);
							}
						}
					}
				}
				for (GenNode subhpn : inputhpn.getChildren()) {
					if (subhpn.getSemanticRole() != null) {
						if (subhpn.getSemanticRole().equals("recipient")) {
							Agent_Recipient_gen(subhpn, inputhpn);
						}
					}
				}
				// if (!same_word_count.contains(inputhpn.getId())){
				// same_word_count.add(inputhpn.getId());
				// System.out.println(inputhpn.getLabel());
				// sentence = sentence + inputhpn.getLabel() + " ";
				// }
			}
		} catch (Exception ex) {
			String result = sentence + temp;
			return result;
		}
		String result = sentence + temp;
		return result;
	}

	public static void printGen(GenNode inputhpn) {
		System.out.println(inputhpn.getLabel());
		System.out.println(inputhpn.getSemanticRole());
		if (!inputhpn.getChildren().isEmpty()) {
			System.out.println("call");
			for (GenNode subhpn : inputhpn.getChildren()) {
				printGen(subhpn);
			}
			System.out.println("call back");
		}
	}
}