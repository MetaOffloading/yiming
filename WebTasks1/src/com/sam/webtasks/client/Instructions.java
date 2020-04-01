package com.sam.webtasks.client;

import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.iotask2.IOtask2BlockContext;

public class Instructions {

	public static String Get(int index) {
		String conditionInstruct="";
		String i="";
		
		String reminderColour="";
		String noReminderColour="";
		
		if (Counterbalance.getFactorLevel("buttonColours") == 0) {
			reminderColour="red";
			noReminderColour="green";
		} else {
			reminderColour="green";
			noReminderColour="red";
		}
		
		 
		switch(index) {
		case 0:
			i="In this experiment you will have a simple task to do.<br><br>"
                    + "You will see several yellow circles inside a box. "
                    + "Inside each circle will be a number. <br><br>"
                    + "You can move them around using your mouse. Your task is to drag them to the bottom "
                    + "of the box in sequence. "
                    + "Please start by dragging 1 all the way to the bottom. "
                    + "This will make it disappear. Then drag 2 to the bottom, then 3, "
                    + "and so on.<br><br>";
			break;
		case 1:
			i="Now you will continue the same task, but sometimes there will be something else to "
                    + "do.<br><br>As well as dragging each circle in turn to the "
                    + "bottom of the screen, there will sometimes be special "
                    + "circles that you should drag in another direction (left, top, or right) instead of towards the bottom.<br><br>"
                    + "These special circles will initially appear in a different colour "
                    + "when they are first shown on the screen, instead of yellow. This is an "
                    + "instruction telling you where they should go.<br><br>"
                    + "For example, suppose that the circle with 7 in it was first shown in blue "
                    + "when it appeared on the screen. That would be an instruction that "
                    + "when you get to 7 in the sequence, you should drag that circle "
                    + "to the blue side of the box (left) instead of the bottom.<br><br>"
                    + "You will still be able to drag any "
                    + "circle to the bottom of the box, but you should try to "
                    + "remember to drag these special circles to the instructed "
                    + "location instead.";
			break;
		case 2:
			i="Now that you have had some practice with the experiment, we would like you to tell us "
	                        + "how accurately you can perform the task.<br><br>"
	                        + "Please use the scale below to indicate what percentage of "
	                        + "the special circles you can correctly drag to the instructed side of the square, on average. 100% "
	                        + "would mean that you always get every single one correct. 0% would mean that you can never "
	                        + "get any of them correct.";
			break;
		case 3:
			i="Now we are going to explain a strategy that can make the task easier.<br><br>"
                    + "When you see a special circle, you can set a reminder by immediately dragging it to a "
                    + "different part of the box. For example, if a circle initially appeared in blue, you "
                    + "could immediately drag it next to the blue (left) side of the box. Then, when "
                    + "you get to that circle in the sequence its location would remind you where it is supposed "
                    + "to go.<br><br>Click below to practice the task twice, using this strategy to help you.";
			break;
		case 4:
			i="Now that you have practiced doing the task using reminders, we would like you to tell us "
                    + "how accurately you can perform the task when you use this strategy.<br><br>"
                    + "Please use the scale below to indicate what percentage of "
                    + "the special circles you can correctly drag to the instructed side of the square, on average, "
                    + "when you use reminders. 100% "
                    + "would mean that you always get every single one correct. 0% would mean that you can never "
                    + "get any of them correct.";
			break;
		case 5:
			i="Now we will explain how you will earn your payment for performing this task.<br><br>"
					+ "In this half of the experiment, ";
			
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_FIRST) {
				i = i + "you are trying to score points. You will earn money by "
						+ "scoring these points.<br><br>You can score up to a maximum of " 
					+ (Params.maxPoints / 2) + " points, and you will earn $1 for every "
					+ Params.pointsPerDollar + " points you score.<br><br>This means that you "
			        + "can earn over $" + (Params.maxPoints / (2 * Params.pointsPerDollar))
			        + " in this half of the experiment. In addition, you will start with "
			        + Params.basePayment + " as a base payment for taking part.<br><br>";
			} else {
				i = i + "we will start by giving you " + (Params.maxPoints / 2) + " points. "
						+ "You will earn money by holding on to these points.<br><br> You "
						+ "will receive $1 for every " + Params.pointsPerDollar + " that "
						+ "you retain.<br><br>This means that you can receive over $"
						+ (Params.maxPoints / (2 * Params.pointsPerDollar)) + " if you hold on "
						+ "to these points.<br><br>We have also given you a base payment of "
						+ "600 points for taking part.<br><br>";
			}			
			
			i = i + "You currently have " + IOtask2BlockContext.getTotalPoints()
    			+ " points (" + IOtask2BlockContext.getMoneyString() + ").";
			break;
		case 6:
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_FIRST) {
				conditionInstruct = "score 10 points for every special circle you remember";
			} else {
				conditionInstruct = "lose 10 points for every special circle you forget";
			}
			
			i="Sometimes when you do the task, you will have to do it without setting any reminders.<br><br>"
                    + "When this happens, you will " + conditionInstruct +  ".<br><br>"
                    + "You will always be given clear instructions what you should do. In this case you will be "
                    + "told \"This time you must do the task without setting any reminders\" and see a " + noReminderColour + " button. "
                    + "When this happens, "
                    + "the computer will not let you set any reminders.<br><br>Click below to see an example.";
			break;
		case 7:
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_FIRST) {
				conditionInstruct = "score 10 points every time you remember a special circle";
			} else {
				conditionInstruct = "lose 10 points every time you forget a special circle";
			}
			
			i="Other times, you will have to set reminders for all the special circles.<br><br>When "
	                + "this happens, you will also " + conditionInstruct + ".<br><br>"
	                + "In this case, you will be told \"This time you <b>must</b> set a reminder for every special circle\" "
	                + "and you will see a " + reminderColour + " button.<br><br>"
	                + "When this happens, the computer will make sure that you always set a reminder for every "
	                + "circle and it will not let you continue if you do not.<br><br>Click below to see an example.";
			
			break;
		case 8:
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_FIRST) {
				i="Sometimes, you will have a choice between two options when you do the task. One option will be to do the task "
		                + "without being able to set any reminders. If you choose this option, you will always score "
		                + "10 points for every special circle you remember.<br><br>The other option will be to "
		                + "do the task with reminders, but in this case each special circle will be worth "
		                + "fewer points. For example, you might be told that if you want to use reminders, "
		                + "each special circle will be worth only 6 points.<br><br>You should choose whichever "
		                + "option you think will score you the most points. <br><br>So if, for example, you "
		                + "thought you would earn more points by setting reminders and scoring 6 points for "
		                + "each special circle, you should choose this option. But if you thought you would "
		                + "score more points by just using your own memory and earning 10 points for each special "
		                + "circle, you should choose this option instead.";
			} else {
				i="Sometimes, you will have a choice between two options when you do the task. One option will be to do the task "
		                + "without being able to set any reminders. If you choose this option, you can potentially "
		                + "keep all of your points.<br><br>The other option will allow you to use reminders. "
		                + "If you use reminders, you will lose some points, even when you remember a special circle. "
		                + "For example, you might be told that if you choose this option "
		                + "you will lose 4 points each time you remember a special circle.<br><br>"
		                + "Regardless of what you choose, you will always lose 10 points each time "
		                + "you forget a special circle.";         
			}
			break;
		case 9:
			i="";
			
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_FIRST) {
				conditionInstruct = "score";
			} else {
				conditionInstruct = "keep";
				
				i += "You should choose whichever "
		                + "option you think will allow you to keep the most points. <br><br>So if, for example, you "
		                + "thought you would keep most points by using your own memory and keeping all your points when "
		                + "you remember special circles, you should choose this option. But if you thought you would "
		                + "keep most points by using reminders and losing 4 points every time you remember a special "
		                + "circle, you should choose this option instead.<br><br>";
			}
			
			i=i+"When you are presented with a choice like this, it is completely up to you. "
                    + "You should do whatever you think will allow you to " + conditionInstruct 
                    + " the most points.<br><br>Click below to see an example.";
			break;	
		case 10:
			i="You have now completed more than half the experiment. For the rest of the experiment, the "
					+ "way that you earn your payment will change. ";
			
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_SECOND) {
				i = i + "From now on, you are trying to score additional points. You will earn money by "
						+ "scoring these points.<br><br>You can score up to a maximum of " 
					+ (Params.maxPoints / 2) + " extra points, and you will earn $1 for every "
					+ Params.pointsPerDollar + " points you score.<br><br>This means that you "
			        + "can earn over $" + (Params.maxPoints / (2 * Params.pointsPerDollar))
			        + " in the remainder of the experiment, in addition to the money you already have."; 
			} else {
				i = i + "We are giving you " + (Params.maxPoints / 2) + " extra points right now. "
						+ "You will earn money by holding on to these points.<br><br> You "
						+ "will receive $1 for every " + Params.pointsPerDollar + " that "
						+ "you retain.<br><br>This means that you can receive over $"
						+ (Params.maxPoints / (2 * Params.pointsPerDollar)) + " based on "
						+ "how many points you retain, in addition to "
						+ "the money you already have.";
			}	
			
			i = i + "<br><br>You currently have " + IOtask2BlockContext.getTotalPoints()
			+ " points (" + IOtask2BlockContext.getMoneyString() + ").";
			break;
		case 11:
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_SECOND) {
				conditionInstruct = "score 10 points every time you remember a special circle";
			} else {
				conditionInstruct = "lose 10 points every time you forget a special circle";
			}
			
			i="You will continue the same task as before. As before, sometimes you will have to "
					+ "do the task using your own memory, and sometimes you will have to do the "
					+ "task with reminders.<br><br>When you do this, you will " + conditionInstruct;
			break;
		case 12:
			if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_SECOND) {
				i="Also as before, you will sometimes have a choice between two options when you do the task. One option will be to do the task "
		                + "without being able to set any reminders. If you choose this option, you will always score "
		                + "10 points for every special circle you remember.<br><br>The other option will be to "
		                + "do the task with reminders, but in this case each special circle will be worth "
		                + "fewer points. For example, you might be told that if you want to use reminders, "
		                + "each special circle will be worth only 6 points.<br><br>You should choose whichever "
		                + "option you think will score you the most points. <br><br>So if, for example, you "
		                + "thought you would earn more points by setting reminders and scoring 6 points for "
		                + "each special circle, you should choose this option. But if you thought you would "
		                + "score more points by just using your own memory and earning 10 points for each special "
		                + "circle, you should choose this option instead.";
			} else {
				i="Also as before, you will sometimes have a choice between two options when you do the task. One option will be to do the task "
		                + "without being able to set any reminders. If you choose this option, you can potentially "
		                + "keep all of your points.<br><br>The other option will allow you to use reminders. "
		                + "If you use reminders, you will lose some points, even when you remember a special circle. "
		                + "For example, you might be told that if you choose this option "
		                + "you will lose 4 points each time you remember a special circle.<br><br>"
		                + "Regardless of what you choose, you will always lose 10 points each time "
		                + "you forget a special circle.";         
			}
			break;

		case 13:
			i = "Thank you. The experiment is nearly finished now.<br><br>Finally, we would like you to complete two "
					+ "brief questionnaires on the following pages.";
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
                + "We are recruiting volunteers from the Amazon Mechanical Turk website to "
                + "take part in an experiment aiming to improve our understanding of human "
                + "attention and memory. You will see various objects on the screen like coloured, numbered circles, "
                + "and you will be asked to move them with your computer mouse. Sometimes you will be asked to remember "
                + "particular numbers and move the corresponding circle in a particular direction. You will be asked how "
                + "confident you are in your ability to solve the task. "
                + "The experiment "
                + "will last approximately 45 minutes and you will receive a payment of $2 plus an additional bonus via the "
                + "Amazon Mechanical Turk payment system. There are no anticipated risks or "
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
                + "address is held by Amazon Mechanical Turk but the researchers on this project have no acccess "
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
