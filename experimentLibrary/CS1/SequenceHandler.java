//The SequenceHandler is the piece of code that defines the sequence of events
//that constitute the experiment.
//
//SequenceHandler.Next() will run the next step in the sequence.
//
//We can also switch between the main sequence of events and a subsequence
//using the SequenceHandler.SetLoop command. This takes two inputs:
//The first sets which loop we are in. 0 is the main loop. 1 is the first
//subloop. 2 is the second subloop, and so on.
//
//The second input is a Boolean. If this is set to true we initialise the 
//position so that the sequence will start from the beginning. If it is
//set to false, we will continue from whichever position we were currently in.
//
//So SequenceHandler.SetLoop(1,true) will switch to the first subloop,
//starting from the beginning.
//
//SequenceHandler.SetLoop(0,false) will switch to the main loop,
//continuing from where we left off.

package com.sam.webtasks.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.CheckIdExists;
import com.sam.webtasks.basictools.CheckScreenSize;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Consent;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.InfoSheet;
import com.sam.webtasks.basictools.Initialise;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.basictools.Slider;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.iotask1.IOtask1Block;
import com.sam.webtasks.iotask1.IOtask1BlockContext;
import com.sam.webtasks.iotask1.IOtask1DisplayParams;
import com.sam.webtasks.iotask1.IOtask1InitialiseTrial;
import com.sam.webtasks.iotask1.IOtask1RunTrial;
import com.sam.webtasks.iotask2.IOtask2Block;
import com.sam.webtasks.iotask2.IOtask2BlockContext;
import com.sam.webtasks.iotask2.IOtask2RunTrial;
import com.sam.webtasks.iotask2.IOtask2InitialiseTrial;
import com.sam.webtasks.iotask2.IOtask2PreTrial;

public class SequenceHandler {
	public static void Next() {	
		// move forward one step in whichever loop we are now in
		sequencePosition.set(whichLoop, sequencePosition.get(whichLoop) + 1);

		switch (whichLoop) {
		case 0: // MAIN LOOP
			switch (sequencePosition.get(0)) {
			/***********************************************************************
			 * The code here defines the main sequence of events in the experiment *
			 **********************************************************************/
			case 1:
				ClickPage.Run(Instructions.Get(0), "Next");
				break;
			case 2:
				IOtask2Block block0 = new IOtask2Block();
				block0.totalCircles = 8;
				block0.nTargets = 0;
				block0.blockNum = 0;
				block0.nTrials = 2;
				block0.Run();
				break;
			case 3:
				ClickPage.Run(Instructions.Get(1),  "Next");
				break;
			case 4:
				IOtask2Block block1 = new IOtask2Block();
				block1.totalCircles = 8;
				block1.nTargets = 1;
				block1.blockNum = 1;
				block1.nTrials = 2;
				block1.showPostTrialFeedback = false;
				block1.Run();
				break;
			case 5:
				int pracTargets = 0;
				
				if (Counterbalance.getFactorLevel("practiceDifficulty") == ExtraNames.PRACTICE_EASY) {
					pracTargets=4;
				} else if (Counterbalance.getFactorLevel("practiceDifficulty") == ExtraNames.PRACTICE_DIFFICULT) {
					pracTargets=16;
				}
				if (IOtask2BlockContext.getnHits() < 1) { //if there were fewer than 1 hits on the last trial
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
					ClickPage.Run("You didn't drag the special circle to the correct location.", "Please try again");
				} else {
					ClickPage.Run("Well done, that was correct.<br><br>Now it will get more difficult. "
	                        + "There will be a total of 25 circles, and " + pracTargets + " of them will be special ones "
	                        + "that should go to one of the coloured sides of the box.<br><br>Don't worry if you "
	                        + "do not remember all of them. That's fine - just try to remember as many as you can.", "Next");
				}
				break;	
			case 6:
			IOtask2Block block2 = new IOtask2Block();
			
			if (Counterbalance.getFactorLevel("practiceDifficulty") == ExtraNames.PRACTICE_EASY) {
				block2.nTargets=4;
			} else if (Counterbalance.getFactorLevel("practiceDifficulty") == ExtraNames.PRACTICE_DIFFICULT) {
				block2.nTargets=16;
			}
			
			block2.showPoints = false;
			block2.blockNum = 2; 
			block2.nTrials = 5;
			
			block2.Run();
			break;
			case 7:
				if (Counterbalance.getFactorLevel("practiceDifficulty") == ExtraNames.PRACTICE_EASY) {
					ClickPage.Run("Now the task will get more difficult. It will stay like this for the rest of the experiment.<br><br>"  
							                        + "Please ignore the difficulty of the practice trials you have just done and remember that the task "  
							                        + "will be like this from now on.<br><br>Click below to continue", "Next");
				} else if (Counterbalance.getFactorLevel("practiceDifficulty") == ExtraNames.PRACTICE_DIFFICULT) {
				ClickPage.Run("Now the task will get easier. It will stay like this for the rest of the experiment.<br><br>"  
							                       + "Please ignore the difficulty of the practice trials you have just done and remember that the task "  
							                       + "will be like this from now on.<br><br>Click below to continue", "Next");
				}
				break;
			case 8:
				//this runs the task with default settings: no choice at the beginning, and just one trial
				IOtask2Block block3 = new IOtask2Block();
				block3.totalCircles = 25;
				block3.nTargets = 10;
				block3.blockNum = 3;
				block3.nTrials = 1;
				block3.Run();
				break;
				
			//case 8:
			//	IOtask2Block block3 = new IOtask2Block();
			//	block3.targetValues.add(0); //forced internal condition
			//	block3.showPoints=false;    //don't display the number of points so far at the beginning. The default is to show this
			//	block3.blockNum=2;          //we always set the block number so that data from each block is kept separate
			//	
			//	if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_EASY) {
			//		block3.nTargets=4;
			//	} else if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_DIFFICULT) {
			//		block3.nTargets=16;
			//	}
			//	
			//	block3.Run();
			//	break;
			
			case 9:
				Slider.Run(Instructions.Get(2), "None of them", "All of them");
				break;
			case 10:
				//save the selected slider value to the database
				PHP.logData("sliderValue",  "" + Slider.getSliderValue(), true);
				break;
			case 11:
				ClickPage.Run(Instructions.Get(3),  "Next");
				break;
			case 12:
				IOtask2Block block4 = new IOtask2Block();
				block4.showPoints=false;
				block4.showPostTrialFeedback = true;
				block4.blockNum=4;
				block4.Run();
				break;
			case 13:
				if (IOtask2BlockContext.getnHits() < 8) { //if there were fewer than 8 hits on the last trial
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
					String msg = "You got " + IOtask2BlockContext.getnHits() + " out of 10 correct that time. You need to get at least 8 out of "
							+ "10 correct to continue to the next part.<br><br>Please keep in mind that you can set reminders to help you remember. Each "
							+ "time a special circle appears, you can immediately drag it next to the part of the box where it eventually needs to go. "
							+ "This should help reminder you what to do when you get to that circle in the sequence.";
					ClickPage.Run(msg, "Try again");		
				} else {
					SequenceHandler.Next();
				}
				break;
			case 14:
				ClickPage.Run(Instructions.Get(4), "Next");
				break;
			case 15:
				ClickPage.Run(Instructions.Get(5), "Next");
				break;
			case 16:
				IOtask2Block block5 = new IOtask2Block();
				block5.targetValues.add(0); //forced internal condition
				block5.showPoints=false;
				block5.showPostTrialFeedback = true;
				block5.blockNum=5;
				block5.Run();
				break;
			case 17: 
				ClickPage.Run(Instructions.Get(6), "Next");
				break;
			case 18:
				IOtask2Block block6 = new IOtask2Block();
				block6.targetValues.add(10); //forced internal condition
				block6.showPoints=false;
				block6.showPostTrialFeedback = true;
				block6.blockNum=6;
				block6.Run();
				break;
			case 19:
				ClickPage.Run(Instructions.Get(7), "Next");
				break;
			case 20:
				ClickPage.Run(Instructions.Get(8), "Next");
				break;
			
				//if (IOtask2BlockContext.getnHits() < 8) { //if there were fewer than 8 hits on the last trial
				//	SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
				//	ClickPage.Run("You only got " + nHits + " out of 10 correct. You need to get at least 8 out of 10 correct to continue to the next part.<br><br>"
				//			+"Please keep in mind that you can set reminders to help you remember. Each time a special circle appears, you can immediately drag it "
				//			+ "next to the part of the box where it eventually needs to go so. This should help remind you what to do when you get to that circle in the "
				//			+ "sequence.", "Try again");
				//} else {
				//	ClickPage.Run("Well done, that was correct.<br><br>Now it will get more difficult. "
	              //          + "There will be a total of 25 circles, and " + pracTargets + " of them will be special ones "
	                //        + "that should go to one of the coloured sides of the box.<br><br>Don't worry if you "
	                  //      + "do not remember all of them. That's fine - just try to remember as many as you can.", "Next");
				//	SequenceHandler.Next(); //move to the next instruction
				//}
				//break;	
			case 21: 
				//add progress bar to screen
				ProgressBar.Initialise();
				ProgressBar.Show();
				ProgressBar.SetProgress(0,  17);
				
				
				IOtask2Block block7 = new IOtask2Block();
				block7.standard17block = true; //run a standard block of 17 trials
				block7.updateProgress = true; //update the progress bar so that it represents the current trial number compared to the whole block
				block7.blockNum=7;
				block7.showPostTrialFeedback = true;
				block7.showPoints = true;
				block7.Run();
				break;
			case 22:
				//hide the progress bar
				ProgressBar.Hide();
				
				//***** log data and check that it saves
				String data = SessionInfo.rewardCode + ",";
				data = data + Counterbalance.getFactorLevel("forcedOrder") + ",";
				data = data + Counterbalance.getFactorLevel("buttonPositions") + ",";
				data = data + Counterbalance.getFactorLevel("buttonColours") + ",";
				data = data + Counterbalance.getFactorLevel("practiceDifficulty") + ",";
				data = data + Counterbalance.getFactorLevel("feedbackValence") + ",";
				data = data + SessionInfo.gender + ",";
				data = data + SessionInfo.age + ",";
				data = data + Slider.getSliderValue() + ","; //Slider.getSliderValue() returns the most recent slider
				//value. Seeing as there is only one slider in this experiment this solution works fine, but if
				//there was more than one slider response we would have to implement a more complex solution.
				data = data + TimeStamp.Now();

				PHP.logData("finish", data, true);
				break;
			case 23:
				Finish.Run();
				break;
			}
			break;

		/********************************************/
		/* no need to edit the code below this line */
		/********************************************/

		case 1: // initialisation loop
			switch (sequencePosition.get(1)) {
			case 1:
				// initialise experiment settings
				Initialise.Run();
				break;
			case 2:
				// make sure that a participant ID has been registered.
				// If not, the participant may not have accepted the HIT
				CheckIdExists.Run();
				break;
			case 3:
				// check the status of this participant ID.
				// have they already accessed or completed the experiment? if so,
				// we may want to block them, depending on the setting of
				// SessionInfo.eligibility
				PHP.CheckStatus();
				break;
			case 4:
				// clear screen, now that initial checks have been done
				RootPanel.get().clear();

				// make sure the browser window is big enough
				CheckScreenSize.Run(SessionInfo.minScreenSize, SessionInfo.minScreenSize);
				break;
			case 5:
				if (SessionInfo.runInfoConsentPages) { 
					InfoSheet.Run(Instructions.InfoText());
				} else {
					SequenceHandler.Next();
				}
				break;
			case 6:
				if (SessionInfo.runInfoConsentPages) { 
					Consent.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 7:
				SequenceHandler.SetLoop(0, true); // switch to and initialise the main loop
				SequenceHandler.Next(); // start the loop
				break;
			}
			break;
		case 2: // IOtask1 loop
			switch (sequencePosition.get(2)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 2 *
			 * This runs a single trial of IOtask1                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask1Block block = IOtask1BlockContext.getContext();

				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0, false);
				}

				SequenceHandler.Next();
				break;
			case 2:
				// now initialise trial and present instructions
				IOtask1InitialiseTrial.Run();
				break;
			case 3:
				// now run the trial
				IOtask1RunTrial.Run();
				break;
			case 4:
				// we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(2, true);
				SequenceHandler.Next();
				break;
				// TODO: mechanism to give post-trial feedback?
			}
			break;
		case 3: //IOtask2 loop
			switch (sequencePosition.get(3)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 3 *
			 * This runs a single trial of IOtask2                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask2Block block = IOtask2BlockContext.getContext();
				
				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0,  false);
				}
				
				SequenceHandler.Next();
				break;
			case 2:
				IOtask2InitialiseTrial.Run();
				break;
			case 3:
				//present the pre-trial choice if appropriate
				if (IOtask2BlockContext.currentTargetValue() > -1) {
					IOtask2PreTrial.Run();
				} else { //otherwise just skip to the start of the block
					SequenceHandler.Next();
				}
				break;
			case 4:
				//now run the trial
				IOtask2RunTrial.Run();
				break;
			case 5:
				if (IOtask2BlockContext.showPostTrialFeedback()) {
					IOtask2Feedback.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 6:
				//we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(3,  true);
				SequenceHandler.Next();
				break;
			}
		}
	}
	
	private static ArrayList<Integer> sequencePosition = new ArrayList<Integer>();
	private static int whichLoop;

	public static void SetLoop(int loop, Boolean init) {
		whichLoop = loop;

		while (whichLoop + 1 > sequencePosition.size()) { // is this a new loop?
			// if so, initialise the position in this loop to zero
			sequencePosition.add(0);
		}

		if (init) { // go the beginning of the sequence if init is true
			sequencePosition.set(whichLoop, 0);
		}
	}

	// set a new position
	public static void SetPosition(int newPosition) {
		sequencePosition.set(whichLoop, newPosition);
	}

	// get current position
	public static int GetPosition() {
		return (sequencePosition.get(whichLoop));
	}
}
