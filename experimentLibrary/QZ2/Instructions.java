package com.sam.webtasks.client;

import com.sam.webtasks.basictools.Counterbalance;

public class Instructions {

	public static String Get(int index) {
		String i = "";
		
		String highVal = "", lowVal = "", highValLoc = "", lowValLoc = "";

		if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
			highVal = "blue";
			lowVal = "pink";

			highValLoc = "left";
			lowValLoc = "right";
		} else {
			highVal = "pink";
			lowVal = "blue";

			highValLoc = "right";
			lowValLoc = "left";
		}

		switch (index) {
		case 0:
			i = "In this experiment you will have a simple task to do.<br><br>"
					+ "You will see several yellow circles inside a box. Inside each circle will be a number. <br><br>"
					+ "You can move them around with your finger. Your task is to drag them to the bottom of the box in sequence.<br><br>"
					+ " Please start by dragging 1 all the way to the bottom. This will make it disappear. <br><br>"
					+ "Then drag 2 to the bottom, then 3, and so on.";
			break;

		case 1:
			i = "Now you will continue the same task, but sometimes there will be something else to do.<br><br>"
					+ "As well as dragging each circle in turn to the bottom of the screen, there will sometimes be special circles that you should drag in another direction (left or right) instead of towards the bottom.<br><br>"
					+ "These special circles will initially appear in a different colour when they are first shown on the screen, instead of yellow. This is an instruction telling you where they should go.<br><br>"
					+ "For example, suppose that the circle with 7 in it was first shown in blue when it appeared on the screen. That would be an instruction that when you get to 7 in the sequence, you should drag that circle to the blue side of the box (left) instead of the bottom.<br><br>"
					+ "You will still be able to drag any circle to the bottom of the box, but you should try to remember to drag these special circles to the instructed location instead.";
			break;

		case 2:
			i = "Now it will get more difficult. There will be a total of " + Params.totalCircles + " circles, and " + Params.nTargets + " of them will be special ones that should go to one of the coloured sides of the box.<br><br>"
					+ "Don't worry if you do not remember all of them. That's fine-just try to remember as many as you can.<br><br>"
					+ "You will also see a countdown timer on the screen. Please try to complete the task before the timer runs out.";
			break;
			
		case 3:
			i = "From now on, you will score points every time you drag one of the special circles to the correct location.<br><br>"
					+ "The <b>" + highVal + "</b> circles are worth more than the <b>" + lowVal
					+ "</b> circles. Every time you drag a <b>" + highVal + "</b> circle to the " + highValLoc
					+ " you will score <b>10</b> points. But you will only score <b>1</b> point for dragging a <b>" + lowVal + "</b> circle "
					+ "to the " + lowValLoc + ".<br><br>However, any time you drag an incorrect circle to the left or right, you will lose 1 point.<br><br>"
							+ "These points are worth real money. Your payment at the end of the experiment "
					+ "will be based on how many points you score. You will be paid £1 for every " + Params.pointsPerPound + " points.<br><br>"
					+ "This means that you can earn over £9 for this experiment if you remember all the circles. <br><br>Click below to continue.";
			break;
		case 4:
			i = "Now we are going to explain a strategy that can help you remember the " + highVal + " circles.<br><br>"
					+ "As soon as you see a " + highVal + " circle, you can set a reminder by immediately dragging it next to the " + highVal + " edge of the box. "
					+ "Then, when you get to that circle in the sequence its location would remind you where it is supposed to go.<br><br>"
					+ "You will be able to do this for " + highVal + " circles, but not " + lowVal + " ones. Each time you use this strategy "
					+ " there will be a brief pause before you can continue.<br><br>Please now try the task again, using this strategy to help you.";
			break;
		case 5:
			i = "Now the task will continue as before, and you are free to set reminders by dragging the special circles to the edge of the box if you want to. "
					+ "However, every time you do this there will be a brief pause before you can continue the task.<br><br>It is completely up to you whether you decide to set reminders or not.";
			break;


		case 6:
			i = "Now the experiment will begin for real. The more points you score, the more money you will earn.<br><br>"
					+ "You will start with an initial payment of £3.00 and earn additional money in addition to this."
					+ " Click below to start."; 
			break;
			
		case 7:
			i = "From this point onwards you will not be able to set reminders any more.<br><br>You will have to do the task "
					+ "just using your own memory.";
			break;
		}

		return (i);
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
				+ "attention and memory. Full instructions will be provided before the experiment begins. "
				+ "The experiment " + "will last approximately 40 minutes. There are no anticipated risks or "
				+ "benefits associated with participation in this study.<br><br>"
				+ "It is up to you to decide whether or not to take part. If you choose "
				+ "not to participate, you won't incur any penalties or lose any "
				+ "benefits to which you might have been entitled. However, if you do "
				+ "decide to take part, you can print out this information sheet and "
				+ "you will be asked to fill out a consent form on the next page. " + "Even after agreeing to take "
				+ "part, you can still withdraw at any time and without giving a reason. "
				+ "<br><br>All data will be collected and stored in accordance with the UK Data "
				+ "Protection Act 1998.");
	}

}
