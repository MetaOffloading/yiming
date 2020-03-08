package com.sam.webtasks.client;

import com.sam.webtasks.questionnaire.Questionnaire;

public class DemoQuestionnaire {
	public static void Run() {
		Questionnaire demoQuestionnaire = new Questionnaire();
		
		demoQuestionnaire.name = "demoQuestionnaire";
		
		demoQuestionnaire.instructionText = "Here is a demonstration questionnaire.<br><br>Please answer the questions below.";
		
		demoQuestionnaire.nOptions = 4;
		demoQuestionnaire.nItems = 3;
		demoQuestionnaire.itemsPerPage = 2;
		demoQuestionnaire.questionWidth = 0.1;
		
		demoQuestionnaire.Init();
		
		demoQuestionnaire.options[0] = "Option 1";
		demoQuestionnaire.options[1] = "Option 2";
		demoQuestionnaire.options[2] = "Option 3";
		demoQuestionnaire.options[3] = "Option 4";
		
		demoQuestionnaire.items[0] = "Question 1";
		demoQuestionnaire.items[1] = "Question 2";
		demoQuestionnaire.items[2] = "Question 3";
		
		demoQuestionnaire.Run();
	}
}
