package com.sam.webtasks.client;

import com.sam.webtasks.questionnaire.Questionnaire;

public class PSWQ {
	public static void Run() {
		//This line initialises the questionnaire object. You should replace "demoQuestinnaire" with
		//the name for your questionnaire (the Java convention is that this should start with a lower-
		//case letter. Then replace "demoQuestionnaire" in all of the lines below with your name.
		Questionnaire qPSWQ = new Questionnaire();
		
		//This is the name for your questionnaire, which is outputted to the database with the
		//participant's answer
		qPSWQ.name = "qPSWQ";
		
		//This is the instruction given to the participant above the questionnaire. NB you
		//can include line breaks with the HTML code "<br"
		qPSWQ.instructionText = "Please click the answer that best describes how typical or characteristic each item is of you.";
		
		//How many response options does the questionnaire have?
		qPSWQ.nOptions = 5;
		
		//How many items (i.e. questions) does the questionnaire have?
		qPSWQ.nItems = 16;
		
		//What is the maximum number of items to be presented on one page? If the questionnaire
		//has more than this number, it will be presented on two or more page
		qPSWQ.itemsPerPage = 6;
		
		//What proportion of the screen width should be used for the questions rather than
		//the response options. This can be adjusted by trial and error if the widths look wrong
		qPSWQ.questionWidth = 0.3;
		
		//This command needs to be run before the text for the response options and items can
		//be set up below
		qPSWQ.Init();
		
		//This is where you define the possible response options. Start with [0]
		//NB a line break can be included with <br> if necessary
		qPSWQ.options[0] = "Not at<br>all typical";
		qPSWQ.options[1] = "Not very<br>typical";
		qPSWQ.options[2] = "Somewhat<br>typical";
		qPSWQ.options[3] = "Fairly<br>typical";
		qPSWQ.options[4] = "Very<br>typical";
		
		//This is where you define the questionnaire items. As above, a line break can
		//be included with <br> if necessary
		qPSWQ.items[0] = "If I don’t have enough time to do everything, I don’t worry about it";
		qPSWQ.items[1] = "My worries overwhelm me";
		qPSWQ.items[2] = "I don't tend to worry about things";
		qPSWQ.items[3] = "Many situations make me worry";
		qPSWQ.items[4] = "I know I should not worry about things, but I just cannot help it";
		qPSWQ.items[5] = "When I am under pressure I worry a lot";
		qPSWQ.items[6] = "I am always worrying about something";
		qPSWQ.items[7] = "I find it easy to dismiss worrisome thoughts";
		qPSWQ.items[8] = "As soon as I finish one task, I start to worry about everything else I have to do";
		qPSWQ.items[9] = "I never worry about anything";
		qPSWQ.items[10] = "When there is nothing more I can do about a concern, I do not worry about it anymore";
		qPSWQ.items[11] = "I have been a worrier all my life";
		qPSWQ.items[12] = "I notice that I have been worrying about things";
		qPSWQ.items[13] = "Once I start worrying, I cannot stop";
		qPSWQ.items[14] = "I worry all the time";
		qPSWQ.items[15] = "I worry about projects until they are all done";
		
		//This needs to be included at the end of the code so that the questionnaire
		//actually runs
		qPSWQ.Run();
	}
}
