package module;

import java.util.ArrayList;

import module.graph.helper.GraphPassingNode;

public class JapaneseGenerator {
	public static String jpnGenerator(GraphPassingNode gpn){
		String translation = new String();
		
		/*for(String k:gpn.getAspGraph()){
			System.out.println(k);
		}*/
		GenNode jgn = GenNodeConverter.genNodeConverter(gpn);
		translation = GenerateJpnSent(jgn);
		return translation;
	}

	private static String GenerateJpnSent(GenNode jgn) {
		String mainverb = jgn.getLabel();
		String verbPos="";
		String negative="";
		String support_verb="";
		String subject="";
		String time="";
		String site="";
		String location="";
		String attachment="";
		String verb="";
		String recipient="";
		String originVal="";
		String verbMod="";
		String object="";
		String modelVerb="";
		
		for(GenNode childnode: jgn.getChildren()){
			if(childnode.getSemanticRole().equals("VBD")||childnode.getSemanticRole().equals("VBG")||childnode.getSemanticRole().equals("VBN")||childnode.getSemanticRole().equals("VBP")||childnode.getSemanticRole().equals("VBZ")){
				verbPos=childnode.getSemanticRole();
			}else if(childnode.getSemanticRole().equals("negative")){
				negative = childnode.getSemanticRole();
			}else{
				if(childnode.getSemanticRole().equals("agent")){
					subject = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("attachment")){
					attachment = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("time")){
					time = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("recipient")){
					recipient = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("objective")){
					object = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("site")){
					site = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("is_participant_in")){
					location = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("origin_value")){
					originVal = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("modifier")){
					verbMod = VisitNode(childnode);
				}else if(childnode.getSemanticRole().equals("passive_supporting_verb")){
					support_verb = VisitNodePassive(childnode);
				}else if(childnode.getSemanticRole().equals("model_verb")){
					modelVerb = VisitNode(childnode);
				}
				
				
			}
		}
		
		//Build the sentence
		String outputSent ="";
		
		//Add subject
		if(!subject.equals("")&&support_verb.equals(""))
			outputSent = outputSent + subject +"は";
			
		//Add time 
		if(!time.equals(""))
			outputSent = outputSent + time +"、";
		
		if(!verbMod.equals(""))
			outputSent = outputSent + verbMod+"、";
		
		//Add
		if(!attachment.equals(""))
			outputSent = outputSent + attachment +"へ";
		
		if(!originVal.equals(""))
			outputSent = outputSent + originVal+ "から";
		
		if(!recipient.equals("")&&support_verb.equals("")&&!object.equals(""))
			outputSent = outputSent + recipient+ "に";
		else if(!recipient.equals("")&&support_verb.equals("")&&object.equals(""))
			outputSent = outputSent + recipient+ "を";
		else
			outputSent = outputSent + recipient+ "は";
		
		if(!subject.equals("")&&!support_verb.equals(""))
			outputSent = outputSent + subject+ "に";
			
		if(!object.equals(""))
			outputSent = outputSent + object+ "を";
		
		if(!site.equals(""))
			outputSent = outputSent + site+ "を";
		
		if(!location.equals(""))
			outputSent = outputSent + location+ "で";
		
		
		
		//Modify Verb
		verb = JpnVerbMod.modifyVerb(mainverb, verbPos, negative, support_verb);
		outputSent = outputSent + verb;
		
		return outputSent;
	}

	private static String VisitNodePassive(GenNode childnode) {
		String output = "passive";
		if(childnode.getChildren()!=null){
			for(GenNode child: childnode.getChildren()){
				if(child.getSemanticRole().equals("VBD")||child.getSemanticRole().equals("VBN")){
					output = "passive_past";
				}
			}
		}
		return output;
	}

	private static String VisitNode(GenNode childnode) {
		String output = "";
		String part = childnode.getLabel();
		//System.out.println(childnode.getLabel());
		//System.out.println(childnode.getSemanticRole());
		//Need to modify more deeply
		if(childnode.getChildren()!=null){
			for(GenNode child: childnode.getChildren()){
				if(child.getSemanticRole().equals("trait")){
					part = VisitNode(child) + "の"+part; 
				}else if(child.getSemanticRole().equals("size")){
					part = VisitNode(child) + "の"+part;
				}else if(child.getSemanticRole().equals("is_possessed_by")){
					part = VisitNode(child) + "の"+part;
					//System.out.println(part);
				}else if(child.getSemanticRole().equals("complement_word")){
					part = VisitNode(child) + "の"+part;
					//System.out.println(part);
				}else if(child.getSemanticRole().equals("identifier")){
					part = VisitNode(child) + "の"+part;
					//System.out.println(part);
				}else if(child.getSemanticRole().equals("dependent")){
					part = VisitNode(child) + "に"+part;
					//System.out.println(part);
				}else if(child.getSemanticRole().equals("site")){
					part = VisitNode(child) + "に"+part;
				}else if(child.getSemanticRole().equals("in_conjunction_with")){
					part = VisitNode(child) + "と"+ part ;
				}else{
				}
			}
			output = part;
		}else{
			output = part;
		}
		
		return output;
	}

}