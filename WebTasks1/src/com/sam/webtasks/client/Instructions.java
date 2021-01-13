package com.sam.webtasks.client;

import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.iotask2.IOtask2BlockContext;

public class Instructions {

	public static String Get(int index) {
		String i="";
		 
		switch(index) {
		case 10:
			i="In this task you will see a sequence of letters, one by one.<br><br>If the letter "
					+ "matches the one you saw two letters ago, please press the <b>X</b> key. "
					+ "Otherwise, press the <b>Z</b> key.<br><br>For example, if you saw the sequence "
					+ "O A S A P, you would press Z Z Z X Z.<br><br>Please respond as quickly "
					+ "and as accurately as possible.";
			break;
		case 20:
			i = "At the same time as doing this test, you will also do a 'timer task'. A digital clock "
					+ "will be displayed above the letters. You will be asked to press "
					+ "the <b>spacebar</b> at a particular time.<br><br>For example you might get "
					+ "the instruction \"Hit the spacebar at 0:20\". When you see a message "
					+ "like this, first you should immediately press the spacebar to continue "
					+ "with the letter task. You should keep going with the letter task like before. Then, "
					+ "when the clock gets to the specified time (such as 0:20) you should press the "
					+ "spacebar again. Keep going with the letter task after this.<br><br>"
					+ "Please perform both the letter task and the timer task as accurately as possible. "
					+ "Both tasks are important.<br><br>"
					+ "If you press the spacebar within 2 seconds of the instructed time (for example "
					+ "0:18 to 0:22) this will be counted as correct.";
			break;
		case 30:
			i = "Now let's do some more practice of the task you have just done.<br><br>"
					+ "When you get an instruction to press the spacebar, there will always be a <b>10 "
					+ "second</b> wait until you should press it.<br><br>";
			break;
		case 40:
			i = "Now that you have had some practice, we would like you to tell us how accurately you "
					+ "think you can perform the timer task when it is exactly like the task you have just "
					+ "practiced, with a <b>10 second wait</b> until you are supposed "
					+ "to press the spacebar.<br><br>Please use the scale below to indicate what percentage "
					+ "of times you will remember to press the spacebar correctly when there is a <b>10 second wait</b>."
					+ "<br><br>100% would mean that you will always get every single one correct. 0% would mean "
					+ "that you can never get any of them correct.";
			break;
		case 50:
			i = "Now we'll do some more practice of the task, but this time there will always be a "
					+ "<b>20 second wait</b> in the timer task.";
			break;
		case 60:
			i = "Now that you have had some practice, we would like you to tell us how accurately you "
					+ "think you can perform the timer task when it is exactly like the task you have just "
					+ "practiced, with a <b>20 second wait</b> until you are supposed "
					+ "to press the spacebar.<br><br>Please use the scale below to indicate what percentage "
					+ "of times you will remember to press the spacebar correctly when there is a <b>20 second wait</b>."
					+ "<br><br>100% would mean that you will always get every single one correct. 0% would mean "
					+ "that you can never get any of them correct.";
			break;
		case 70:
			i = "Now we'll do some more practice of the task, but this time there will always be a "
					+ "<b>30 second wait</b> in the timer task.";
			break;
		case 80:
			i = "Now that you have had some practice, we would like you to tell us how accurately you "
					+ "think you can perform the timer task when it is exactly like the task you have just "
					+ "practiced, with a <b>30 second wait</b> until you are supposed "
					+ "to press the spacebar.<br><br>Please use the scale below to indicate what percentage "
					+ "of times you will remember to press the spacebar correctly when there is a <b>30 second wait</b>."
					+ "<br><br>100% would mean that you will always get every single one correct. 0% would mean "
					+ "that you can never get any of them correct.";
			break;
		case 90:
			i = "There is one last thing to explain about the task. Sometimes, there will be a button on "
					+ "the screen saying \"Remind Me\". Once you have been told to press the spacebar at "
					+ "a particular time, you can press this button to set a reminder. This means that when "
					+ "it's nearly time to press the spacebar, the clock will start flashing to remind you.<br><br>"
					+ "Please try using this button now.";
			break;
		case 100:
			i = "When the \"Remind Me\" button is on the screen, it is completely up to you whether to use it or not. "
					+ "You can set reminders if you want to, or you can just remember to press the spacebar "
					+ "with your own memory, without setting a reminder. It's your choice, so you should just do whatever you prefer.<br><br>"
					+ "That's the end of the practice. The main experiment will start now.";
			break;
		case 110:
			i = "Time for a break. Press below to continue with the experiment.";
			break;
		case 120:
			i = "You have now completed the experiment. Thank you for taking part.<br><br>"
					+ "Last of all, we would like you to complete a brief survey about the experiment. "
					+ "When you have done this, you will receive a link to receive your payment from "
					+ "the Prolific Academic system.<br><br>Click below to complete the survey:<br><br>"
					+ "<b><a href=\"https://docs.google.com/forms/d/e/1FAIpQLSeKfIkV4v2KjORr2HvXmNckd_4cK3sOj9NXfIAX6VzbJOR5vA/viewform?usp=sf_link\">"
					+ "CLICK HERE</a></b>";
			break;
		}

		return(i);	
	}
	
	public static String InfoText() {
		return ("We would like to invite you to participate in this research project. "
                + "You should only participate if you want to; choosing not to take part "
                + "will not disadvantage you in any way. Before you decide whether you "
                + "want to take part, please read the following information carefully and "
                + "discuss it with others if you wish. Ask us if there is anything that "
                + "is not clear or you would like more information.<br><br>"
                + "We are recruiting volunteers to "
                + "take part in an experiment aiming to improve our understanding of human "
                + "attention and memory. You will see various stimuli on the screen like letters of the alphabet "
                + "and you will be asked to respond to them by pressing keys. Sometimes you will be asked how "
                + "confident you are in your ability to perform the task. "
                + "The experiment "
                + "will last approximately 1 hour and you will receive a payment of £7.50 via the "
                + "Prolific Academic payment system. There are no anticipated risks or "
                + "benefits associated with participation in this study.<br><br>"
                + "It is up to you to decide whether or not to take part. If you choose "
                + "not to participate, you won't incur any penalties or lose any "
                + "benefits to which you might have been entitled. However, if you do "
                + "decide to take part, you can print out this information sheet and "
                + "you will be asked to fill out a consent form on the next page. "
                + "Even after agreeing to take "
                + "part, you can still withdraw at any time and without giving a reason. If you withdraw before the "
                + "end of the experiment, we will not retain your data and it will not be analysed."
                + "<br><br>All data will be collected and stored in accordance with the General Data Protection "
                + "Regulations 2018. Personal information is stored separately from test results, and researchers "
                + "on this project have no access to this data. Your personal information such as name and email "
                + "address is held by Prolific Academic but the researchers on this project have no acccess "
                + "to this. Data from this experiment may be made available to the research community, for example by "
                + "posting them on websites such as the Open Science Framework (<a href=\"http://osf.io\">http://osf.io</a>). "
                + "It will not be possible to identify you from these data.<br><br>"
                + "We aim to publish the results of this project in scientific journals and book chapters. Copies of the "
                + "results can either be obtained directly from the scientific journals' websites or from us.<br><br>"
                + "Should you wish to raise a complaint, please contact the Principal Investigator of this project, "
                + "Dr Sam Gilbert (<a href=\"mailto:sam.gilbert@ucl.ac.uk\">sam.gilbert@ucl.ac.uk</a>). However, "
                + "if you feel your complaint has not been handled to your satisfaction, please be aware that you can "
                + "also contact the Chair of the UCL Research Ethics Committee (<a href=\"mailto:ethics@ucl.ac.uk\">ethics@ucl.ac.uk</a>).");
    }

}
