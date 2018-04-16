package module;

import java.util.ArrayList;

import module.graph.helper.GraphPassingNode;

public class GenNodeConverter {
	public static GenNode genNodeConverter(GraphPassingNode gpn){
		GenNode jpn = new GenNode();				
		try{
			ArrayList<GenNode> preList = PreProcessASP(gpn);
			jpn = preList.get(0);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
		return jpn;
	}

	private static ArrayList<GenNode> PreProcessASP(GraphPassingNode gpn) {
		ArrayList<GenNode> preList = new ArrayList<GenNode>();
		//Get All relations
		try{
		for(String k:gpn.getAspGraph()){
			String processStr = k.replace("has(", "");
			processStr = processStr.replace(").","");
			String rules[] = processStr.split(",");
			if(rules[1].equals("instance_of")||rules[1].equals("is_subclass_of")||rules[1].equals("semantic_role")||rules[1].equals("prototype_of")){
				
			}else if(rules[1].equals("in_conjunction_with")){
				try{
				GenNode newnode = new GenNode();
				GenNode newchildnode = new GenNode();
				String roots[] = rules[0].split("-");
				String roles[] = rules[2].split("-");
				newnode.setLabel(roles[0]);
				newnode.setId(Integer.parseInt(roles[1]));
				newchildnode.setLabel(roots[0]);
				newchildnode.setId(Integer.parseInt(roots[1]));
				newchildnode.setSemanticRole(rules[1]);
				newnode.getChildren().add(newchildnode);
				preList.add(newnode);
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
			}else{
				try{
				GenNode newnode = new GenNode();
				GenNode newchildnode = new GenNode();
				String roots[] = rules[0].split("-");
				newnode.setLabel(roots[0]);
				newnode.setId(Integer.parseInt(roots[1]));
				String roles[] = rules[2].split("-");
				newchildnode.setLabel(roles[0]);
				newchildnode.setId(Integer.parseInt(roles[1]));
				newchildnode.setSemanticRole(rules[1]);
				newnode.getChildren().add(newchildnode);
				preList.add(newnode);
				}
				catch(Exception ex){
					ex.printStackTrace();
					return null;
				}
			}
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
		//Merge the nodes
		for(int i=0; i<preList.size()-1;i++){
			try{
			GenNode jgn1 = preList.get(i);
			for(int j=i+1; j<preList.size();j++){
				GenNode jgn2 = preList.get(j);
				if(jgn1.getLabel().equals(jgn2.getLabel())&&jgn1.getId()==jgn2.getId()){
					jgn1.setSemanticRole(jgn2.getSemanticRole());
					jgn1.getChildren().addAll(jgn2.getChildren());
					preList.set(i,jgn1);
					preList.remove(j);
					j--;
				}
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
		}
		
		//Merge the node to the other child node
		for(int i=0; i<preList.size()-1;i++){
			try{
			GenNode jgn1 = preList.get(i);
			int prelistSize1 = preList.size();
			for(int j=i+1; j<preList.size();j++){
				int prelistSize = preList.size();
				GenNode jgn2 = preList.get(j);
				VisitChildren(jgn1,jgn2,preList,j);
				if(prelistSize != preList.size()){
					j=i;
					prelistSize = preList.size();
				}
			}
			
			if(prelistSize1 != preList.size()){
				i=0;
				prelistSize1 = preList.size();
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
		}
		for(int i=preList.size()-1;i>0;i--){
			try{
			GenNode jgn1 = preList.get(i);
			int prelistSize = preList.size();
			if(prelistSize==1){
				break;
			}else{
				for(int j=i-1; j>=0;j--){
					GenNode jgn2 = preList.get(j);
					VisitChildrenRev(jgn1,jgn2,preList,j);
					if(prelistSize != preList.size()){
						if(preList.size()==1){
							break;
						}
					}
				}
				if(preList.size()==1){
					break;
				}
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
		}
		//Flip case
		
		return preList;
	}

	private static void VisitChildrenRev(GenNode jgn1, GenNode jgn2,
			ArrayList<GenNode> preList, int k) {
		for(int j=0; j<jgn1.getChildren().size();j++){
			if(jgn1.getChildren().get(j).getLabel().equals(jgn2.getLabel())&&jgn1.getChildren().get(j).getId()==jgn2.getId()){
				//jgn1.getChildren().get(j).setSemanticRole(jgn2.getSemanticRole());
				jgn1.getChildren().get(j).getChildren().addAll(jgn2.getChildren());
				preList.remove(k);
				//k--;
				return;
			}else if(jgn1.getChildren().get(j).getChildren()!=null){
				VisitChildren(jgn1.getChildren().get(j), jgn2, preList,k);
			}
		}
		
	}

	private static void VisitChildren(GenNode jgn1, GenNode jgn2, ArrayList<GenNode> preList, int k) {
		for(int j=0; j<jgn1.getChildren().size();j++){
			if(jgn1.getChildren().get(j).getLabel().equals(jgn2.getLabel())&&jgn1.getChildren().get(j).getId()==jgn2.getId()){
				//jgn1.getChildren().get(j).setSemanticRole(jgn2.getSemanticRole());
				jgn1.getChildren().get(j).getChildren().addAll(jgn2.getChildren());
				preList.remove(k);
				//k++;
				return;
			}else if(jgn1.getChildren().get(j).getChildren()!=null){
				VisitChildren(jgn1.getChildren().get(j), jgn2, preList,k);
			}
		}
	}
}
