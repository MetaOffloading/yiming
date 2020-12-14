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
import com.google.gwt.user.client.ui.Label;
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
import com.sam.webtasks.iotask1.IOtask1InitialiseTrial;
import com.sam.webtasks.iotask1.IOtask1RunTrial;
import com.sam.webtasks.iotask2.IOtask2Block;
import com.sam.webtasks.iotask2.IOtask2BlockContext;
import com.sam.webtasks.iotask2.IOtask2RunTrial;
import com.sam.webtasks.perceptualTask.PerceptBlock;
import com.sam.webtasks.timeBasedOffloading.TimeBlock;
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
				ClickPage.Run(Instructions.Get(10), "Next");
				break;
			case 2:
				TimeBlock.Init();
				TimeBlock.blockDuration=-10;
				TimeBlock.clockVisible=false;
				TimeBlock.offloadButtonVisible=false;
				TimeBlock.targetInstructionInterval = -1; //don't present any targets
				TimeBlock.blockNumber=-1;
				TimeBlock.Run();
				break;
			case 3:
				if ((TimeBlock.nBackNonMatchCorr==0)|(TimeBlock.nBackMatchCorr==0)) {
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2);
					
					ClickPage.Run("Your accuracy was too low", "Try again");
				} else {
					ClickPage.Run(Instructions.Get(20), "Next");
				}
				break;
			case 4:
				TimeBlock.Init();
				TimeBlock.blockDuration=25;
				TimeBlock.offloadButtonVisible=false;
				TimeBlock.defaultPMintervals=false;
				TimeBlock.PMinterval_list.add(10);
				TimeBlock.blockNumber=-2;
				TimeBlock.Run();
				break;
			case 5:
				if (TimeBlock.PMhits==0) {
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-3);
					
					//we set the nBack accuracy to greater than 0, so that the
					//practice session for this task does not get triggered again
					TimeBlock.nBackNonMatchCorr=1;
					TimeBlock.nBackMatchCorr=1;
					
					ClickPage.Run("You didn't remember to press the spacebar.", "Try again");
				} else {
					ClickPage.Run(Instructions.Get(30),  "Next");
				}
				break;
			case 6:
				TimeBlock.Init();
				TimeBlock.blockDuration=65;
				TimeBlock.offloadButtonVisible=false;
				TimeBlock.defaultPMintervals=false;
				TimeBlock.PMinterval_list.add(10);
				TimeBlock.PMinterval_list.add(10);
				TimeBlock.PMinterval_list.add(10);
				TimeBlock.blockNumber=-3;
				TimeBlock.Run();
				break;
			case 7:
				Slider.Run(Instructions.Get(40), "0%", "100%");
				break;
			case 8:
				PHP.logData("slider_10s", ""+Slider.getSliderValue(), true);
				break;
			case 9:
				ClickPage.Run(Instructions.Get(50),  "Next");
				break;
			case 10:
				TimeBlock.Init();
				TimeBlock.blockDuration=95;
				TimeBlock.offloadButtonVisible=false;
				TimeBlock.defaultPMintervals=false;
				TimeBlock.PMinterval_list.add(20);
				TimeBlock.PMinterval_list.add(20);
				TimeBlock.PMinterval_list.add(20);
				TimeBlock.blockNumber=-4;
				TimeBlock.Run();
				break;
			case 11:
				Slider.Run(Instructions.Get(60), "0%", "100%");
				break;
			case 12:
				PHP.logData("slider_20s",  ""+Slider.getSliderValue(),  true);
				break;
			case 13:
				ClickPage.Run(Instructions.Get(70),  "Next");
				break;
			case 14:
				TimeBlock.Init();
				TimeBlock.blockDuration=125;
				TimeBlock.offloadButtonVisible=false;
				TimeBlock.defaultPMintervals=false;
				TimeBlock.PMinterval_list.add(30);
				TimeBlock.PMinterval_list.add(30);
				TimeBlock.PMinterval_list.add(30);
				TimeBlock.blockNumber=-5;
				TimeBlock.Run();
				break;
			case 15:
				Slider.Run(Instructions.Get(80), "0%", "100%");
				break;
			case 16:
				PHP.logData("slider_30s",  ""+Slider.getSliderValue(),  true);
				break;
			case 17:
				ClickPage.Run(Instructions.Get(90), "Next");
				break;
			case 18:
				TimeBlock.Init();
				TimeBlock.blockDuration=35;
				TimeBlock.defaultPMintervals=false;
				TimeBlock.PMinterval_list.add(20);
				TimeBlock.blockNumber=-6;
				TimeBlock.Run();
				break;
			case 19:
				if (TimeBlock.offloadButtonOperated==false) {
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2);
					ClickPage.Run("You didn't set a reminder",  "Try again");
				} else {
					ClickPage.Run(Instructions.Get(100), "Next");		
				}
				break;
			case 20:
				TimeBlock.Init();
				TimeBlock.blockNumber=1;
				TimeBlock.Run();
				break;
			case 21:
				ClickPage.Run("This is the end of the demo.<br>At this point in the experiment we will want "
						+ "to have more experimental blocks.<br><br>We also might want to collect more "
						+ "metacognitive evaluations at the end.", "end");
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
