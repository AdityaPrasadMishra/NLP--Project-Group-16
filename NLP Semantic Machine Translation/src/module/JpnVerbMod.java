package module;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.atilika.kuromoji.ipadic.*; 
 

import com.ibm.icu.text.Transliterator;

public class JpnVerbMod {

	public static void main(String[] args){

		String verb = "出る";
		String mverb = modifyVerb(verb, "VBD", "negative", "");
		/*System.out.println(mverb);*/

	}

	public static String modifyVerb(String mainverb, String verbPos, String negative, String support_verb) {

		String outputVerb = mainverb;
		String verbModTable[][] = new String[14][18];
		/*System.out.println(mainverb);
		System.out.println(verbPos);
		System.out.println(negative);
		System.out.println(support_verb);*/
		
		try{
			FileReader fr = new FileReader("./Resources/JpnVerbRules/jpnVerbMod.txt");
			BufferedReader br = new BufferedReader(fr);
			String str = br.readLine();
			int count = 0;

			while(str != null){
				verbModTable[count] = str.split("\t");
				//次の行を読み込み
				str = br.readLine();
				count++;
			}


			if(mainverb.substring(mainverb.length()-1).equals("る")){

				Transliterator trH2L = Transliterator.getInstance("Katakana-Latin");
				Tokenizer tokenizer = new Tokenizer();
//				tokenizer.builder().build();
				String s2 = "";

				for (Token token : tokenizer.tokenize(mainverb)) { 
					String s = trH2L.transliterate(token.getReading());
					s2 = s.substring(0, 1).toUpperCase() + s.substring(1); 

				}
				//System.out.println(s2.substring(s2.length()-3,s2.length()-2));
				if(s2.substring(s2.length()-3,s2.length()-2).equals("i")||s2.substring(s2.length()-3,s2.length()-2).equals("e")){
					outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 11, verbModTable);
				}else if(mainverb.equals("ある")||mainverb.equals("有る")){
					outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 10, verbModTable);
				}else if(mainverb.equals("する")){
					outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 12, verbModTable);
				}else if(mainverb.equals("来る")||mainverb.equals("くる")){
					outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 13, verbModTable);
				}else{
					outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 9, verbModTable);
				}
			}else if(mainverb.equals("行く")||mainverb.equals("往く")||mainverb.equals("逝く")||mainverb.equals("いく")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 2, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("う")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 0, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("く")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 1, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("ぐ")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 3, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("す")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 4, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("つ")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 5, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("ぬ")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 6, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("ぶ")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 7, verbModTable);
			}else if(mainverb.substring(mainverb.length()-1).equals("む")){
				outputVerb = VerbModifier(mainverb, negative, support_verb, verbPos, 8, verbModTable);
			}else{
				outputVerb = mainverb + VerbModifier("する", negative, support_verb, verbPos, 12, verbModTable).substring(1,VerbModifier("する", negative, support_verb, verbPos, 12, verbModTable).length());
			}


		}catch(FileNotFoundException e){

		}catch(IOException e){

		}

		return outputVerb;
	}

	private static String VerbModifier(String mainverb, String negative,
			String support_verb, String verbPos, int i, String[][] verbModTable) {
		String outputVerb = "";
		if(verbPos.equals("VB")||verbPos.equals("VBP")||verbPos.equals("VBZ")){ //present
			if(negative.equals("")&&support_verb.equals("")){
				outputVerb = mainverb;
			}else if(negative.equals("negative")&&support_verb.equals("")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][0];
			}else if(negative.equals("negative")&&support_verb.equals("passive")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][15];
			}else if(negative.equals("negative")&&support_verb.equals("passive_past")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][17];
			}else if(negative.equals("")&&support_verb.equals("passive")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][10];
			}else if(negative.equals("")&&support_verb.equals("passive_past")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][16];
			}
		}else if(verbPos.equals("VBG")){ //Gerund or present participle
			if(negative.equals("")&&support_verb.equals("")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][13];
			}else if(negative.equals("negative")&&support_verb.equals("")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][14];
			}else if(negative.equals("negative")&&support_verb.equals("passive")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][15];
			}else if(negative.equals("negative")&&support_verb.equals("passive_past")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][17];
			}else if(negative.equals("")&&support_verb.equals("passive")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][10];
			}else if(negative.equals("")&&support_verb.equals("passive_past")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][16];
			}
		}else{	//Past
			if(negative.equals("")&&support_verb.equals("")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][1];
			}else if(negative.equals("negative")&&support_verb.equals("")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][2];
			}else if(negative.equals("negative")&&support_verb.equals("passive")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][15];
			}else if(negative.equals("negative")&&support_verb.equals("passive_past")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][17];
			}else if(negative.equals("")&&support_verb.equals("passive")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][10];
			}else if(negative.equals("")&&support_verb.equals("passive_past")){
				outputVerb = mainverb.substring(0, mainverb.length()-1) + verbModTable[i][16];
			}
		}
		return outputVerb;
	}
}
