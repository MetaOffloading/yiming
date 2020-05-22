package com.sam.webtasks.client;

import com.sam.webtasks.questionnaire.Questionnaire;

public class STAI {
	public static void Run() {
		//This line initialises the questionnaire object. You should replace "demoQuestinnaire" with
		//the name for your questionnaire (the Java convention is that this should start with a lower-
		//case letter. Then replace "demoQuestionnaire" in all of the lines below with your name.
		Questionnaire qSTAI = new Questionnaire();
		
		//This is the name for your questionnaire, which is outputted to the database with the
		//participant's answer
		qSTAI.name = "qSTAI";
		
		//This is the instruction given to the participant above the questionnaire. NB you
		//can include line breaks with the HTML code "<br"
		qSTAI.instructionText = "A number of statements which people have used to describe themselves "
				+ "are given on the following pages.<br><br>Read each statement and then click the "
				+ "appropriate answer to indicate how you GENERALLY feel. There are no right or "
				+ "wrong answers.<br><br>Do not spend too much time on any one statement, but give "
				+ "the answer which seems to describe your present feelings best.";   
		
		//How many response options does the questionnaire have?
		qSTAI.nOptions = 4;
		
		//How many items (i.e. questions) does the questionnaire have?
		qSTAI.nItems = 20;
		
		//What is the maximum number of items to be presented on one page? If the questionnaire
		//has more than this number, it will be presented on two or more page
		qSTAI.itemsPerPage = 5;
		
		//What proportion of the screen width should be used for the questions rather than
		//the response options. This can be adjusted by trial and error if the widths look wrong
		qSTAI.questionWidth = 0.3;
		
		//This command needs to be run before the text for the response options and items can
		//be set up below
		qSTAI.Init();
		
		//This is where you define the possible response options. Start with [0]
		//NB a line break can be included with <br> if necessary
		qSTAI.options[0] = "Almost<br>never";
		qSTAI.options[1] = "Sometimes";
		qSTAI.options[2] = "Often";
		qSTAI.options[3] = "Almost<br>always";
		
		//This is where you define the questionnaire items. As above, a line break can
		//be included with <br> if necessary
		qSTAI.items[0] = "I feel pleasant";
		qSTAI.items[1] = "I feel nervous and restless";
		qSTAI.items[2] = "I feel satisfied with myself";
		qSTAI.items[3] = "I wish I could be as happy as others seem to be";
		qSTAI.items[4] = "I feel like a failure";
		qSTAI.items[5] = "I feel rested";
		qSTAI.items[6] = "I am \"calm, cool, and collected\" ";
		qSTAI.items[7] = "I feel that difficulties are piling up so that I cannot overcome them";
		qSTAI.items[8] = "I worry too much over something that does not really matter";
		qSTAI.items[9] = "I am happy";
		qSTAI.items[10] = "I have disturbing thoughts";
		qSTAI.items[11] = "I lack self-confidence";
		qSTAI.items[12] = "I feel secure";
		qSTAI.items[13] = "I make decisions easily";
		qSTAI.items[14] = "I feel inadequate";
		qSTAI.items[15] = "I am content";
		qSTAI.items[16] = "Some unimportant thought runs through my mind and bothers me";
		qSTAI.items[17] = "I take disappointments so keenly that I cannot put them out of my mind";
		qSTAI.items[18] = "I am a steady person";
		qSTAI.items[19] = "I get in a state of tension or turmoil as I think over my recent concerns and interests";

		//This needs to be included at the end of the code so that the questionnaire
		//actually runs
		qSTAI.Run();
	}
}
