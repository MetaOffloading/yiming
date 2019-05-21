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
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.CheckIdExists;
import com.sam.webtasks.basictools.CheckScreenSize;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Consent;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.Finish;
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
import com.sam.webtasks.iotask2.IOtask2Feedback;
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
			 ********************************************************************/
			case 1:
				ClickPage.Run(Instructions.Get(0),  "Next");
				break;		
			case 2:	
				IOtask2Block block1 = new IOtask2Block();
				block1.logDragData=true; //log trial-by-trial data to the database
				block1.blockNum=1;
				block1.totalCircles=8;
				block1.nTargets=0;
				block1.nTrials=1;		
				//different coloured circles can be worth different numbers of points
				block1.variablePoints = true;

				//points associated with bottom, left, right, and top corners of the box
				//if the left, right, or top is set to zero points it will be shown in black
				//and no targets will be assigned to that side
				block1.pointValues = new int[] {0,1,1,0};

				block1.Run();
				break;
			case 3:
				ClickPage.Run(Instructions.Get(1),  "Next");
			    break;	
			case 4:
				IOtask2Block block2 = new IOtask2Block();
				block2.logDragData=true; //log trial-by-trial data to the database
				block2.blockNum=2;
				block2.totalCircles=8;
				block2.nTargets=1;
				block2.nTrials=1;
				block2.variablePoints = true;
				block2.pointValues = new int[] {0,1,1,0};
		
				block2.Run();
				break;
			  case 5:
				  if (IOtask2BlockContext.getnHits() == 0) {
					  SequenceHandler.SetPosition(SequenceHandler.GetPosition() - 2);
					  ClickPage.Run("You did not drag the special circle to the instructed location. Please try again.", "Continue");
				  } else {
					  SequenceHandler.Next();
				  }
				break;		
			case 6:
				ClickPage.Run(Instructions.Get(2),  "Next");
				break;			
			case 7:// need add feedback 
				IOtask2Block block3 = new IOtask2Block();
				block3.logDragData=true; //log trial-by-trial data to the database
				block3.blockNum=3;
				block3.totalCircles=Params.totalCircles;
				block3.nTargets=Params.nTargets;	
				block3.nTrials=1;
				block3.variablePoints = true;
				block3.pointValues = new int[] {0,1,1,0};
				block3.countdownTimer = true;
				
				block3.Run();
			    break;			
			case 8:
				ClickPage.Run(Instructions.Get(3),  "Next");
				break;
			case 9:
				IOtask2Block block4 = new IOtask2Block();
				block4.logDragData=true; //log trial-by-trial data to the database
				block4.blockNum=4;
				block4.showLivePoints=true;
				block4.showPointLabels = true;
				block4.totalCircles=Params.totalCircles;
				block4.nTargets=Params.nTargets;
				block4.nTrials=1;
				block4.variablePoints = true;
				block4.countdownTimer = true;
				
				if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
					block4.pointValues = new int[] {0,10,1,0};
				} else {
					block4.pointValues = new int[] {0,1,10,0}; 
				}
				
				block4.Run();
				break;
			case 10:
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_FIRST) {
					ClickPage.Run(Instructions.Get(4), "Next");
				} else {
					SequenceHandler.Next();
				}
				break;
			case 11:
				IOtask2Block block5 = new IOtask2Block();
				block5.logDragData=true; //log trial-by-trial data to the database
				block5.blockNum = 5;
				block5.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block5.showLivePoints=true;
				block5.showPointLabels = true;
				block5.totalCircles=Params.totalCircles;
				block5.nTargets=Params.nTargets;
				block5.nTrials=1;
				block5.variablePoints = true;
				block5.countdownTimer = true;
				
				if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
					block5.pointValues = new int[] {0,10,1,0};
				} else {
					block5.pointValues = new int[] {0,1,10,0}; 
				}
				
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_FIRST) {
					block5.offloadCondition = Names.REMINDERS_OPTIONAL;
					block5.reminderLockout = true;
					block5.reminderLockoutTime = Params.lockOutTime;
				} else {
					block5.offloadCondition = Names.REMINDERS_NOTALLOWED;
				}
				
				block5.Run();
				break;
			case 12:
				SequenceHandler.Next();
				
				/*if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_FIRST) {
					ClickPage.Run(Instructions.Get(5), "Next");
				} else {
					SequenceHandler.Next();
				}*/
				break;
			case 13:
				SequenceHandler.Next();
				/*
				IOtask2Block block6 = new IOtask2Block();
				block6.logDragData=true; //log trial-by-trial data to the database
				block6.blockNum = 6;
				block6.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block6.showLivePoints=true;
				block6.showPointLabels = true;
				block6.totalCircles=Params.totalCircles;
				block6.nTargets=Params.nTargets;
				block6.nTrials=1;
				block6.variablePoints = true;
				
				if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
					block6.pointValues = new int[] {0,10,1,0};
				} else {
					block6.pointValues = new int[] {0,1,10,0}; 
				}
				
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_FIRST) {
					block6.offloadCondition = Names.REMINDERS_OPTIONAL;
					block6.reminderLockout = true;
					block6.reminderLockoutTime = Params.lockOutTime;
				} else {
					block6.offloadCondition = Names.REMINDERS_NOTALLOWED;
				}
				
				block6.Run(); */
				break;
			case 14:			
				ClickPage.Run(Instructions.Get(6), "Next");
				break;
			case 15:
				//add progress bar to screen
				ProgressBar.Initialise();
				ProgressBar.Show();
				ProgressBar.SetProgress(0,  (2*Params.nTrials)-1);
				Params.progress=0;
				
				IOtask2Block block7 = new IOtask2Block();
				block7.logDragData=true; //log trial-by-trial data to the database
				block7.blockNum = 7;
				block7.totalPoints = 3 * Params.pointsPerPound; //start with £3
				block7.showLivePoints=true;
				block7.showPointLabels = true;
				block7.totalCircles=Params.totalCircles;
				block7.nTargets=Params.nTargets;
				block7.nTrials=Params.nTrials;
				block7.variablePoints = true;
				block7.countdownTimer = true;
				
				if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
					block7.pointValues = new int[] {0,10,1,0};
				} else {
					block7.pointValues = new int[] {0,1,10,0}; 
				}
				
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_FIRST) {
					block7.offloadCondition = Names.REMINDERS_OPTIONAL;
					block7.reminderLockout = true;
					block7.reminderLockoutTime = Params.lockOutTime;
				} else {
					block7.offloadCondition = Names.REMINDERS_NOTALLOWED;
				}
				
				block7.Run();
				break;
			case 16:
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_FIRST) {
					ClickPage.Run(Instructions.Get(7), "Next");
				} else {
					ClickPage.Run(Instructions.Get(4), "Next");
				}
				break;
			case 17:
				IOtask2Block block8 = new IOtask2Block();
				block8.logDragData=true; //log trial-by-trial data to the database
				block8.blockNum = 8;
				block8.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block8.showLivePoints=true;
				block8.showPointLabels = true;
				block8.totalCircles=Params.totalCircles;
				block8.nTargets=Params.nTargets;
				block8.nTrials=1;
				block8.variablePoints = true;
				block8.countdownTimer = true;
				
				if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
					block8.pointValues = new int[] {0,10,1,0};
				} else {
					block8.pointValues = new int[] {0,1,10,0}; 
				}
				
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_SECOND) {
					block8.offloadCondition = Names.REMINDERS_OPTIONAL;
					block8.reminderLockout = true;
					block8.reminderLockoutTime = Params.lockOutTime;
				} else {
					block8.offloadCondition = Names.REMINDERS_NOTALLOWED;
				}
				
				block8.Run();
				break;
			case 18:
				SequenceHandler.Next();
				/*
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_SECOND) {
					ClickPage.Run(Instructions.Get(5), "Next");
				} else {
					SequenceHandler.Next();
				}
				*/
				break;
			case 19:
				SequenceHandler.Next();
				/*
				IOtask2Block block9 = new IOtask2Block();
				block9.logDragData=true; //log trial-by-trial data to the database
				block9.blockNum = 9;
				block9.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block9.showLivePoints=true;
				block9.showPointLabels = true;
				block9.totalCircles=Params.totalCircles;
				block9.nTargets=Params.nTargets;
				block9.nTrials=1;
				block9.variablePoints = true;
				
				if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
					block9.pointValues = new int[] {0,10,1,0};
				} else {
					block9.pointValues = new int[] {0,1,10,0}; 
				}
				
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_SECOND) {
					block9.offloadCondition = Names.REMINDERS_OPTIONAL;
					block9.reminderLockout = true;
					block9.reminderLockoutTime = Params.lockOutTime;
				} else {
					block9.offloadCondition = Names.REMINDERS_NOTALLOWED;
				}
				
				block9.Run();
				*/
				break;
			case 20:
				IOtask2Block block10 = new IOtask2Block();
				block10.logDragData=true; //log trial-by-trial data to the database
				block10.blockNum = 10;
				block10.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block10.showLivePoints=true;
				block10.showPointLabels = true;
				block10.totalCircles=Params.totalCircles;
				block10.nTargets=Params.nTargets;
				block10.nTrials=Params.nTrials;
				block10.variablePoints = true;
				block10.countdownTimer = true;
				
				if (Counterbalance.getFactorLevel("colourMeaning") == Names.BLUE_HIGHVAL) {
					block10.pointValues = new int[] {0,10,1,0};
				} else {
					block10.pointValues = new int[] {0,1,10,0}; 
				}
				
				if (Counterbalance.getFactorLevel("conditionOrder") == Names.OFFLOAD_SECOND) {
					block10.offloadCondition = Names.REMINDERS_OPTIONAL;
					block10.reminderLockout = true;
					block10.reminderLockoutTime = Params.lockOutTime;
				} else {
					block10.offloadCondition = Names.REMINDERS_NOTALLOWED;
				}
				
				block10.Run();
				break;
			case 21:
				String data = Counterbalance.getFactorLevel("colourMeaning") + ",";
				data = data + Counterbalance.getFactorLevel("conditionOrder") + ",";
				data = data + IOtask2BlockContext.getMoneyString() + ",";
				data = data + TimeStamp.Now();

				PHP.logData("finish", data, true);
				break;
			case 22:
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
				// check whether this participant ID has been used to access a previous experiment
				PHP.CheckStatusPrevExp();
				break;
			case 5:
				// clear screen, now that initial checks have been done
				RootPanel.get().clear();

				// make sure the browser window is big enough
				CheckScreenSize.Run(SessionInfo.minScreenSize, SessionInfo.minScreenSize);
				break;
			case 6:
				if (SessionInfo.runInfoConsentPages) { 
					InfoSheet.Run(Instructions.InfoText());
				} else {
					SequenceHandler.Next();
				}
				break;
			case 7:
				if (SessionInfo.runInfoConsentPages) { 
					Consent.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 8:
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
					if (IOtask2BlockContext.getTrialNum() > 0) {
						ClickPage.Run("Ready?", "Continue");
					} else {
						SequenceHandler.Next();
					}
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
