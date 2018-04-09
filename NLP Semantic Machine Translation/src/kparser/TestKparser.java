package kparser;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.jws.soap.SOAPBinding;

import module.Convert;
import module.Convert2;
import module.HindiGenerator;
import module.HindiHelper;
import module.HindiTranslater;
import module.MultiwordExpressionFinder;
import module.Transliteration;
import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;
import module.graph.helper.JAWSutility;
import module.graph.helper.NEObject;
import module.graph.resources.DependencyParserResource;
import module.graph.resources.InputDependencies;
import module.graph.resources.NamedEntityTagger;
import edu.mit.jmwe.data.IMWE;
import edu.mit.jmwe.data.IToken;
import edu.mit.jmwe.data.Token;
import edu.mit.jmwe.detect.Consecutive;
import edu.mit.jmwe.detect.IMWEDetector;
import edu.mit.jmwe.index.IMWEIndex;
import edu.mit.jmwe.index.MWEIndex;

public class TestKparser {

	public static void main(String[] args) throws Exception {
		String sentences = "";
		try {
			String fileName = args[0] + "hinout";
			PrintWriter outputStream = new PrintWriter(fileName);
			Scanner inputStream = new Scanner(new File(args[0]));
			while (inputStream.hasNextLine()) {
				sentences = inputStream.nextLine();
				NEObject neo = NamedEntityTagger.tagNamedEntities(sentences);
				HashMap<String, String> sMap = neo.getNamedEntityMap();
				String translation1 = HindiTranslater.hinTranslate(sentences, sMap);
				byte[] strToBytes = translation1.getBytes();
			    outputStream.println(translation1);
				System.out.println(translation1);
				Convert.wordMap.clear();
			}
			inputStream.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file ");
			System.exit(0);
		}
	}

}
