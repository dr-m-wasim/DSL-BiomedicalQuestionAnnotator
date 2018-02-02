package pk.edu.com.kics.BiomedicalQuestionAnnotator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import pk.edu.com.kics.entity.TrainingQuestion;
import pk.edu.com.kics.services.SearchSemanticType;
import pk.edu.com.kics.util.BioQATraining;



public class BiomedicalQestionAnnotator {
	static String[] questionBatches = {"resources/questions/BioASQ-task3bPhaseA-testset2" };
	
	public static void main(String[] args) throws Exception
	{
		SearchSemanticType sm = new SearchSemanticType("Cancer");
		sm.getTerm(new String[]{});;
		
		/*BioQATraining phaseTraining = new BioQATraining();
		ArrayList<TrainingQuestion> trainingResult = phaseTraining.readTestFile("resources/BioASQ-trainingDataset6b.json");
		for (Iterator iterator = trainingResult.iterator(); iterator.hasNext();) {
			TrainingQuestion trainingQuestion = (TrainingQuestion) iterator.next();
				System.out.println(trainingQuestion.getBody());
			    System.out.println(trainingQuestion.getExact_answer());
				System.out.println("--------");
			*/
			
			
			

			
		//}
	
	}

}
