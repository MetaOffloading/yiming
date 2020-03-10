package com.sam.webtasks.client;

import com.sam.webtasks.questionnaire.Questionnaire;

public class DemoQuestionnaire {
	public static void Run() {
		//This line initialises the questionnaire object. You should replace "demoQuestinnaire" with
		//the name for your questionnaire (the Java convention is that this should start with a lower-
		//case letter. Then replace "demoQuestionnaire" in all of the lines below with your name.
		Questionnaire demoQuestionnaire = new Questionnaire();
		
		//This is the name for your questionnaire, which is outputted to the database with the
		//participant's answer
		demoQuestionnaire.name = "demoQuestionnaire";
		
		//This it the instruction given to the participant above the questionnaire. NB you
		//can include line breaks with the HTML code "<br"
		demoQuestionnaire.instructionText = "Here is a demonstration questionnaire.<br><br>Please answer the questions below.";
		
		//How many response options does the questionnaire have?
		demoQuestionnaire.nOptions = 4;
		
		//How many items (i.e. questions) does the questionnaire have?
		demoQuestionnaire.nItems = 3;
		
		//What is the maximum number of items to be presented on one page? If the questionnaire
		//has more than this number, it will be presented on two or more page
		demoQuestionnaire.itemsPerPage = 2;
		
		//What proportion of the screen width should be used for the questions rather than
		//the response options. This can be adjusted by trial and error if the widths look wrong
		demoQuestionnaire.questionWidth = 0.1;
		
		//This command needs to be run before the text for the response options and items can
		//be set up below
		demoQuestionnaire.Init();
		
		//This is where you define the possible response options. Start with [0]
		//NB a line break can be included with <br> if necessary
		demoQuestionnaire.options[0] = "Option 1";
		demoQuestionnaire.options[1] = "Option 2";
		demoQuestionnaire.options[2] = "Option 3";
		demoQuestionnaire.options[3] = "Option 4";
		
		//This is where you define the questionnaire items. As above, a line break can
		//be included with <br> if necessary
		demoQuestionnaire.items[0] = "Question 1";
		demoQuestionnaire.items[1] = "Question 2";
		demoQuestionnaire.items[2] = "Question 3";
		
		//This needs to be included at the end of the code so that the questionnaire
		//actually runs
		demoQuestionnaire.Run();
	}
}
