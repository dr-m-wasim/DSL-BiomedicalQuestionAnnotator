package pk.edu.com.kics.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pk.edu.com.kics.entity.Snippet;
import pk.edu.com.kics.entity.TrainingQuestion;
import pk.edu.com.kics.entity.Triples;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class BioQATraining 
{
	int idealLength;
	int exactLength;
	int bodyLength;
	ArrayList<TrainingQuestion> document=new ArrayList<>();

	public ArrayList<TrainingQuestion> readTestFile(String path) throws IOException, ParseException
	{		
		String id=null, body = null, type = null;

		FileReader reader = new FileReader(path);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

		// check the file for illegle charachters
		String s = jsonObject.toString();
		if(s.contentEquals("\\\"")& s.contentEquals("]\""))
		{
			String s2= s.replace("\"[", "[" ).replace("]\"", "]").replace("\\\"", "\"");
			jsonObject = (JSONObject) jsonParser.parse(s2);
		}

		JSONArray questionsArray = (JSONArray) jsonObject.get("questions");

		//Parse each questions file ..

		for(int i=0;i<questionsArray.size();i++)
		{
			JSONObject questionObject=(JSONObject) questionsArray.get(i);
			TrainingQuestion item = new TrainingQuestion();
			if((String) questionObject.get("id") != null) id=questionObject.get("id").toString();
			if((String) questionObject.get("body") != null) body=(String) questionObject.get("body");
			bodyLength += body.length();
			if((String) questionObject.get("type") != null) type=(String) questionObject.get("type");

			
			if(type.equals("list") ||  type.equals("factoid"))
			{
				item.setBody(body);
				item.setId(id);
				item.setType(type);
				item.setConcepts(getConcepts(questionObject.get("concepts")));
				item.setDocuments(getDocuments(questionObject.get("documents")));
				item.setSnippets(getSnippets(questionObject.get("snippets")));
				item.setTriples(getTriples(questionObject.get("triples")));
				item.setIdeal_answer(getIdealAnswer(questionObject.get("ideal_answer")));
				item.setExact_answer(getExactAnswer(questionObject.get("exact_answer")));
			
				document.add(item);
			}
			
		}
		return document;
	}


	private ArrayList<String> getConcepts(Object conceptsObject) throws ParseException {

		ArrayList<String> conceptsList = new ArrayList<String>();
		if(conceptsObject != null) {
			JSONParser parser = new JSONParser();
			JSONArray concepts = (JSONArray) parser.parse(conceptsObject.toString());
			for (int j = 0; j < concepts.size(); j++) {
				Object concept = concepts.get(j);
				if(concept != null) {
					conceptsList.add( (String) concept);
				}
			}
		}

		return conceptsList;
	}


	private ArrayList<String> getDocuments(Object documentsObject) throws ParseException {

		ArrayList<String> documentsList = new ArrayList<String>();
		if(documentsObject != null) {
			JSONParser parser = new JSONParser();
			JSONArray documents = (JSONArray) parser.parse(documentsObject.toString());
			for (int j = 0; j < documents.size(); j++) {
				Object concept = documents.get(j);
				if(concept != null) {
					documentsList.add( (String) concept);
				}
			}
		}

		return documentsList;
	}

	private ArrayList<Snippet> getSnippets(Object snippetsObject) throws ParseException {

		ArrayList<Snippet> snippetList = new ArrayList<Snippet>();

		if(snippetsObject != null) {
			JSONParser parser = new JSONParser();
			JSONArray snippets = (JSONArray) parser.parse(snippetsObject.toString());
			for (int j = 0; j < snippets.size(); j++) {
				JSONObject snippet = (JSONObject) snippets.get(j);
				if(snippet != null) {
					Snippet snippy = new Snippet();

					snippy.beginSection = snippet.get("beginSection").toString();
					snippy.document = snippet.get("document").toString();
					snippy.endSection = snippet.get("endSection").toString();
					snippy.offsetInBeginSection = Integer.parseInt(snippet.get("offsetInBeginSection").toString());
					snippy.offsetInEndSection = Integer.parseInt(snippet.get("offsetInEndSection").toString());
					snippy.text = snippet.get("text").toString();

					snippetList.add(snippy);
				}
			}
		}

		return snippetList;
	}

	private ArrayList<Triples> getTriples(Object triplesObject) throws ParseException {

		ArrayList<Triples> triplesList = new ArrayList<Triples>();
		if(triplesObject != null) {
			JSONParser parser = new JSONParser();
			JSONArray triples = (JSONArray) parser.parse(triplesObject.toString());
			for (int j = 0; j < triples.size(); j++) {
				JSONObject triple = (JSONObject) triples.get(j);
				if(triple != null) {
					Triples trip=new Triples();
					trip.obj = triple.get("o").toString();
					trip.sub = triple.get("s").toString();
					trip.pred = triple.get("p").toString();
					triplesList.add(trip);
				}
			}
		}

		return triplesList;
	}

	private ArrayList<String> getIdealAnswer(Object idealObject) throws ParseException {

		ArrayList<String> ideallist = new ArrayList<String>();
		if(idealObject != null) {
			JSONParser parser = new JSONParser();
			try
			{
			if((idealObject.toString().startsWith("[")&(idealObject.toString().endsWith("]"))))
			{
				JSONArray idealArray = (JSONArray) parser.parse(idealObject.toString());
				for (int j = 0; j < idealArray.size(); j++) {
					Object ideal = idealArray.get(j);
					if(ideal != null) 
					{
						ideallist.add( (String) ideal);
						idealLength += ((String) ideal).length();
					}
				}
			}
		else
		{
			//ideallist.add((String) idealObject);
			//idealLength += ((StNLPHelper.extractNounPhrases(sen,noun);
			//int miring) idealObject).length();
		}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
		return ideallist;
		}

	private ArrayList<String> getExactAnswer(Object exactObject) throws ParseException {

		ArrayList<String> exactlist = new ArrayList<String>();
		if(exactObject != null) {
			JSONParser parser = new JSONParser();
		
			try
			{
			if((exactObject.toString().startsWith("[")&(exactObject.toString().endsWith("]"))))
			{
				JSONArray exactArray = (JSONArray) parser.parse(exactObject.toString());
				for (int j = 0; j < exactArray.size(); j++) {
					Object exact = exactArray.get(j).toString();
					if(exact != null) 
					{
						exactlist.add( (String) exact);
						exactLength += ((String) exact).length();
					}
				}
			}
		else
		{
			//ideallist.add((String) idealObject);
			//idealLength += ((StNLPHelper.extractNounPhrases(sen,noun);
			//int miring) idealObject).length();
		}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
		return exactlist;
		}
	
	public static void main(String[] args) {

		//BioQATraining phaseTraining = new BioQATraining();
		/*ArrayList<TrainingQuestion> trainingResult = phaseTraining.readTestFile("resources\\Training\\BioASQ-trainingDataset5b.json");
		for (TrainingQuestion trainingQuestion : trainingResult) {
			if(trainingQuestion.getType().equals("factoid"))
			{
				ArrayList<String> noun = new ArrayList<String>();
				//System.out.println("The Output are :" + noun.toString());
				if(trainingQuestion.getBody().startsWith("Which") | trainingQuestion.getBody().startsWith("What"))
				{
					String sen = trainingQuestion.getBody();
					System.out.println(sen);
					//NLPHelper.chunkSentence(sen);
					NLPHelper.extractNounPhrases(sen,noun);
					int minIndex=1000;
					String nounPhrase="";
					for (int i = 0; i < noun.size(); i++) 
					{
						String keywords = noun.get(i).toLowerCase();
						int startIndex = sen.toLowerCase().indexOf(keywords);
						if(minIndex > startIndex)

						{
							minIndex = startIndex;
							nounPhrase = keywords;

						}
						keywords.split(" ");
					}
					System.out.println(nounPhrase);
					//System.out.println(noun.toString());
					//System.out.println(counter++ + ":" + trainingQuestion.getBody());
				}
				//System.out.println("The Output are :" + noun.toString());
			}


		}*/
	}
}
