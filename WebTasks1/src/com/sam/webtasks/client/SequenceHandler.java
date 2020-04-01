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

//TODO:
//scroll
//data output
//resume where you left off

package com.sam.webtasks.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.CheckIdExists;
import com.sam.webtasks.basictools.CheckScreenSize;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Consent;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.InfoSheet;
import com.sam.webtasks.basictools.Initialise;
import com.sam.webtasks.basictools.Names;
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
		
		int rewardFrame = Counterbalance.getFactorLevel("conditionOrder");
		
		switch (whichLoop) {
		case 0: // MAIN LOOP
			switch (sequencePosition.get(0)) {
			/***********************************************************************
			 * The code here defines the main sequence of events in the experiment *
			 **********************************************************************/
			case 1:		
				PHP.logData("start", "" + TimeStamp.Now(), false);
				
				//add progress bar to screen
				ProgressBar.Initialise();
				ProgressBar.Show();
				ProgressBar.SetProgress(0,  34);
				
				if (SessionInfo.resume) {
					//get info about where to start from
					//this experiment stores status as:
					//0: counterbalancing cell, 1: resumablePosition, 2: progressBarPosition, 3: resumablePoints, 4: actualPoints
					String[] parsedPhpOutput = SessionInfo.status.split(",");
					
					//which position in the sequence handler should we go from?
					SessionInfo.resumePosition = Integer.parseInt(parsedPhpOutput[1]);
					sequencePosition.set(0,  SessionInfo.resumePosition); //NB this will be incremented by one when the Next command is run
					
					//what should we set the progress bar to?
					SessionInfo.resumeProgress = Integer.parseInt(parsedPhpOutput[2]);	
					ProgressBar.SetProgress(SessionInfo.resumeProgress,  34);
								
					//how many points should we start from?
					IOtask2Block initBlock = new IOtask2Block(); 
					initBlock.totalPoints = Integer.parseInt(parsedPhpOutput[3]);
					SessionInfo.resumePoints = initBlock.totalPoints;
					
					//configure block
					initBlock.pointDisplay = Names.POINT_GAINLOSS;
					IOtask2BlockContext.setContext(initBlock);	
				}
				
				SequenceHandler.Next();
				break;
			case 2:
				ClickPage.Run(Instructions.Get(0), "Next");
				break;
			case 3:
				IOtask2Block block0 = new IOtask2Block();
				block0.totalCircles = 8;
				block0.nTargets = 0;
				block0.blockNum = 0;
				block0.blockNum = 0;
				block0.Run();
				break;
			case 4:
				ClickPage.Run(Instructions.Get(1),  "Next");
				break;
			case 5:
				IOtask2Block block1 = new IOtask2Block();
				block1.totalCircles = 8;
				block1.nTargets = 1;
				block1.blockNum = 1;
				block1.blockNum = 1;
				block1.Run();
				break;
			case 6:
				if (IOtask2BlockContext.getnHits() < 1) { //if there were fewer than 1 hits on the last trial
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
					ProgressBar.Decrement();
					ProgressBar.Decrement();
					ClickPage.Run("You didn't drag the special circle to the correct location.", "Please try again");	
				} else {
					ClickPage.Run("Well done, that was correct.<br><br>Now it will get more difficult. "
							+ "There will be a total of 25 circles, and many of them will be special ones "
							+ "that should go to one of the coloured sides of the box.<br><br>Don't worry if you "
							+ "do not remember all of them. That's fine - just try to remember as many as you can.<br><br>"
							+ "Click below to practice the task twice.", "Next");
				}
				break;
			case 7:
				IOtask2Block block2 = new IOtask2Block();
				block2.blockNum = 2;
				block2.nTrials = 2;
				block2.Run();
				break;
			case 8:
				//get internal metacognitive judgement
				Slider.Run(Instructions.Get(2), "0%", "100%");
				break;
			case 9:
				//save response to database
				PHP.logData("intConfidence", "" + Slider.getSliderValue(), true);
				break;
			case 10:
				//offloading instructions
				ClickPage.Run(Instructions.Get(3),  "Next");
				break;
			case 11:
				IOtask2Block block3 = new IOtask2Block();
				block3.blockNum = 3;
				block3.offloadCondition = Names.REMINDERS_MANDATORY_TARGETONLY;
				block3.Run();
				break;
			case 12:	
				if (IOtask2BlockContext.getnHits() < 8) { //if there were fewer than 8 hits on the last trial
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
					ProgressBar.Decrement();
					ProgressBar.Decrement();
					
					String msg = "You got " + IOtask2BlockContext.getnHits() + " correct that time. You need to get at least 8 "
							+ "correct to continue to the next part.<br><br>Please keep in mind that you can set reminders to help you remember. Each "
							+ "time a special circle appears, you can immediately drag it next to the part of the box where it eventually needs to go. "
							+ "This should help reminder you what to do when you get to that circle in the sequence.";
					ClickPage.Run(msg, "Try again");		
				} else {
					SequenceHandler.Next();
				}
				break;
			case 13:
				IOtask2Block block4 = new IOtask2Block();
				block4.blockNum = 4;
				block4.offloadCondition = Names.REMINDERS_MANDATORY_TARGETONLY;
				block4.Run();
				break;
			case 14:
				//get external metacognitive judgement
				Slider.Run(Instructions.Get(4), "0%", "100%");
				break;
			case 15:
				//save response to database
				PHP.logData("extConfidence",  "" + Slider.getSliderValue(), true);
				break;
			case 16:
				//use this to initialise points
				IOtask2Block initBlock = new IOtask2Block(); 
				initBlock.totalPoints = Params.initialPoints;
				initBlock.pointDisplay = Names.POINT_GAINLOSS;
				
				if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_SECOND) {
					initBlock.totalPoints = initBlock.totalPoints + (Params.maxPoints / 2);
				}
				
				IOtask2BlockContext.setContext(initBlock);
				
				SequenceHandler.Next();
				break;
			case 17:
				//payment instructions
				ClickPage.Run(Instructions.Get(5), "Next");
				break;
			case 18:
				//forced internal practice
				ClickPage.Run(Instructions.Get(6),  "Next");
				break;
			case 19:
				IOtask2Block block5 = new IOtask2Block();
				block5.blockNum = 5;
				block5.targetValues.add(0);
				block5.nTrials = -1;
				block5.totalPoints = IOtask2BlockContext.getTotalPoints();
				block5.pointDisplay = Names.POINT_GAINLOSS;
				block5.showLivePoints = true;
				
				if (rewardFrame == ExtraNames.GAIN_SECOND) {
					block5.rewardFrame = Names.LOSS_FRAME;
				}
				
				block5.Run();
				break;
			case 20:
				//forced external practice
				ClickPage.Run(Instructions.Get(7),  "Next");
				break;
			case 21:
				IOtask2Block block6 = new IOtask2Block();
				block6.blockNum = 6;
				block6.targetValues.add(10);
				block6.nTrials = -1;
				block6.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block6.pointDisplay = Names.POINT_GAINLOSS;
				block6.showLivePoints = true;
				
				if (rewardFrame == ExtraNames.GAIN_SECOND) {
					block6.rewardFrame = Names.LOSS_FRAME;
				}
				
				block6.Run();
				break;
			case 22:
				//instructions about choice trials
				ClickPage.Run(Instructions.Get(8),  "Next");
				break;
			case 23:
				//up to you, here's an example
				ClickPage.Run(Instructions.Get(9),  "Next");;
				break;
			case 24:
				IOtask2Block block7 = new IOtask2Block();
				block7.targetValues.add(6);
				block7.nTrials = -1;
				block7.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block7.pointDisplay = Names.POINT_GAINLOSS;
				block7.showLivePoints = true;
				
				if (rewardFrame == ExtraNames.GAIN_SECOND) {
					block7.rewardFrame = Names.LOSS_FRAME;
				}
				
				block7.Run();
				break;
			case 25:
			    //resume point	
				SavePosition.Save();
				break;
			case 26:
				ClickPage.Run("Now the task will begin for real.<br><br>"
						+ "You will see a countdown timer at the top. Please try to complete the task before "
						+ "this runs out.<br><br>Click below to start.", "Next");
				break;
			case 27:
				//first half of experimental trials
				IOtask2Block block8 = new IOtask2Block();
				block8.blockNum = 8;
				block8.standard13block = true;
				block8.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block8.pointDisplay = Names.POINT_GAINLOSS;
				block8.showLivePoints = true;
				block8.gainLossExp = true; //adjust points according to gain / loss
				block8.countdownTimer = true;
				
				if (rewardFrame == ExtraNames.GAIN_SECOND) {
					block8.rewardFrame = Names.LOSS_FRAME;
				}
				
				block8.Run();
				break;
			case 28:
				//resume point	
				SavePosition.Save();
				break;
			case 29:
				//change of condition
				if (Counterbalance.getFactorLevel("conditionOrder") == ExtraNames.GAIN_FIRST) {
					IOtask2BlockContext.incrementPoints(Params.maxPoints / 2);
				}

				
				ClickPage.Run(Instructions.Get(10),  "Next");
				break;
			case 30:
				ClickPage.Run(Instructions.Get(11),  "Next");
				break;
			case 31:
				ClickPage.Run(Instructions.Get(12),  "Next");
				break;
			case 32:
				//second half of experimental trials
				IOtask2Block block9 = new IOtask2Block();
				block9.blockNum = 9;
				block9.standard13block = true;
				block9.totalPoints = IOtask2BlockContext.getTotalPoints(); //carry over points from previous block
				block9.reverseForcedOrder = true; //switch the ordering of forced int / ext
				
				block9.pointDisplay = Names.POINT_GAINLOSS;
				block9.showLivePoints = true;
				block9.gainLossExp = true; //adjust points according to gain / loss
				
				if (rewardFrame == ExtraNames.GAIN_FIRST) {
					block9.rewardFrame = Names.LOSS_FRAME;
				}
				
				block9.Run();
				break;
			case 33:
				//resume point	
				SavePosition.Save();
				break;
			case 34:
				ClickPage.Run(Instructions.Get(13),  "Next");
				break;
			case 35:
				ProgressBar.Increment();
				STAI.Run();
				break;
			case 36:
				//resume point	
				SavePosition.Save();
				break;
			case 37:
				ProgressBar.Increment();
				PSWQ.Run();
				break;
			case 38:
				PHP.UpdateStatus("finished");
				break;
			case 39:
				String data = TimeStamp.Now() + ","
				            + Counterbalance.getFactorLevel("conditionOrder") + ","
				            + Counterbalance.getFactorLevel("buttonColours") + ","
				            + Counterbalance.getFactorLevel("forcedOrder") + ","
				            + SessionInfo.rewardCode + ","
				            + IOtask2BlockContext.getTotalPoints() + ","
				            + IOtask2BlockContext.getMoneyString();
				
				PHP.logData("finish",  data, true);
				break;
			case 40:
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
				//record the participant's counterbalancing condition in the status table				
				if (!SessionInfo.resume) {
					PHP.UpdateStatus("" + Counterbalance.getCounterbalancingCell() + ",1,0,0,0,0");
				} else {
					SequenceHandler.Next();
				}
				break;
			case 9:
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
			case 3:;
				//present the pre-trial choice if appropriate
				if (IOtask2BlockContext.currentTargetValue() > -1) {
					IOtask2PreTrial.Run();
				} else { //otherwise just skip to the start of the block
					if ((IOtask2BlockContext.getTrialNum() > 0)&&(IOtask2BlockContext.countdownTimer())) {
						//if we're past the first trial and there's a timer, click to begin
						ClickPage.Run("Ready?", "Continue");
					} else {
						SequenceHandler.Next();
					}
				}
				break;
			case 4:
				if (IOtask2BlockContext.getNTrials() == -1) { //if nTrials has been set to -1, we quit before running
					SequenceHandler.SetLoop(0,  false);
					SequenceHandler.Next();
				} else {
					//otherwise, run the trial
					IOtask2RunTrial.Run();
				}
				break;
			case 5:
				IOtask2PostTrial.Run();
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
	
	// get current loop
	public static int GetLoop() {
		return (whichLoop);
	}

	// set a new position
	public static void SetPosition(int newPosition) {
		sequencePosition.set(whichLoop, newPosition);
	}

	// get current position
	public static int GetPosition() {
		return (sequencePosition.get(whichLoop));
	}
	
	// get current position from particular loop
	public static int GetPosition(int nLoop) {
		return (sequencePosition.get(nLoop));
	}
}
