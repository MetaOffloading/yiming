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
import com.sam.webtasks.basictools.Slider;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.iotask1.IOtask1Block;
import com.sam.webtasks.iotask1.IOtask1BlockContext;
import com.sam.webtasks.iotask1.IOtask1DisplayParams;
import com.sam.webtasks.iotask1.IOtask1InitialiseTrial;
import com.sam.webtasks.iotask1.IOtask1RunTrial;

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
				// update the status for this participant, saving their counterbalancing condition.
				// This means that if they come back to the experiment and eligibility is set to 
				// NEVERCOMPLETED, we will first load their previous counterbalancing settings.
				// Note that if eligibility is set to ANYONE, this stage is ignored and the counterbalancing settings
				// are always randomised.
				PHP.UpdateStatus("" + Counterbalance.getCounterbalancingCell());
				break;
			case 3:
				String text=""; 
				
				if (Counterbalance.getFactorLevel("phase1reminders")==Names.REMINDERS_NOTALLOWED) {
					text="you cannot set reminders";
				}
				
				if (Counterbalance.getFactorLevel("phase1reminders")==Names.REMINDERS_MANDATORY_ANYCIRCLE) {
					text="you must set reminders";
				}
				
				if (Counterbalance.getFactorLevel("phase1reminders")==Names.REMINDERS_MANDATORY_TARGETONLY) {
					text="you must set reminders";
				}
				
				if (Counterbalance.getFactorLevel("phase1reminders")==Names.REMINDERS_OPTIONAL) {
					text="reminders are optional";
				}
				
				ClickPage.Run(text,  "Next");
				break;
			case 4:
				//phase 1
				IOtask1Block block1 = new IOtask1Block();
				block1.blockNum = 1;
				block1.nTrials = 2;
				block1.nTargets = 3;
				block1.askArithmetic = true;
				block1.offloadCondition = Counterbalance.getFactorLevel("phase1reminders");
				
				block1.Run();
				break;
			case 5:
				ClickPage.Run("From now on you will not be able to set reminders", "Next");
				break;
			case 6:
				//phase 2
				IOtask1Block block2 = new IOtask1Block();
				block2.blockNum = 2;
				block2.nTrials = 2;
				block2.nTargets = 3;
				block2.askArithmetic = true;
				block2.offloadCondition = Names.REMINDERS_NOTALLOWED;
				block2.Run();
				break;
			case 7:
				// log data and check that it saves
				String data = SessionInfo.rewardCode + ",";
				data = data + Counterbalance.getFactorLevel("phase1reminders") + ",";
				data = data + SessionInfo.gender + ",";
				data = data + SessionInfo.age + ",";
				data = data + TimeStamp.Now();

				PHP.logData("finish", data, true);
				break;
			case 8:
				//set participant status to finished
				PHP.UpdateStatus("finished");
				break;
			case 9:
				// complete the experiment
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
				CheckScreenSize.Run(IOtask1DisplayParams.minPixels, IOtask1DisplayParams.minPixels);
				break;
			case 5:
				InfoSheet.Run(Instructions.InfoText());
				break;
			case 6:
				Consent.Run();
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
			 * The code here defines the sequence of events in subloop 1 * This runs a
			 * single trial of IOtask1 *
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
				// TODO: mechanism to give post-trial feedback?
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
