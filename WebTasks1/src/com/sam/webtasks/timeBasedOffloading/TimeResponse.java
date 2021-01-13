package com.sam.webtasks.timeBasedOffloading;

import java.util.Date;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.client.SequenceHandler;

public class TimeResponse {
	public static Date stimOn;
	
	public static void Run(int response) {
		int RT = (int) (new Date().getTime() - stimOn.getTime());
	
		//end of block? if so return control to the sequencehandler
		//we specify blockDuration in seconds if it is positive, or trials if it is negative
		boolean blockOver = false;
		
		if (TimeBlock.blockDuration > 0) { //specified in seconds
			if (TimeBlock.currentTime >= TimeBlock.blockDuration) {
				blockOver = true;
			}
		}
		
		if (TimeBlock.blockDuration < 0) { //specified in trials
			if (TimeBlock.trialNumber >= -TimeBlock.blockDuration) {
				blockOver = true;
			}
		}
		
		if (blockOver) {
			RootPanel.get().remove(TimeDisplay.focusPanel);
			
			TimeDisplay.clockTimer.cancel();
			
			new Timer() {
				public void run() {
					String data = TimeBlock.blockNumber + "," + TimeBlock.trialNumber + ",";
					data = data + TimeBlock.offloadButtonVisible + ",";
					data = data + TimeBlock.nBackNonMatchCorr + "," + TimeBlock.nBackMatchCorr + ",";
					data = data + TimeBlock.PMhits + "," + TimeBlock.nReminders;
					
					PHP.logData("blockEnd", data, true);
				}
			}.schedule(1000);
		} else if (TimeDisplay.timeForInstruction) {
			TimeDisplay.timeForInstruction=false;
			
			TimeDisplay.focusPanel.setFocus(false);
			
			TimeDisplay.stimulusDisplay.setHTML("");
			
			new Timer() {
				public void run() {
					if (!TimeDisplay.waitForSpacebar) { 
						TimeDisplay.stimulusDisplay.setHTML(TimeDisplay.instructionString);
						TimeDisplay.waitForSpacebar=true;
						TimeDisplay.focusPanel.setFocus(true);
						
						TimeDisplay.spacebarToContinue.schedule(7000); //remind them to press the spacebar to continue
					}
				}
			}.schedule(TimeBlock.RSI);
		}
		
		if (TimeDisplay.waitForSpacebar) {
			if (response==TimeBlock.spaceBarKey) {
				if (TimeDisplay.awaitingPMresponse) {
					if (TimeBlock.allowOffloading) {
						TimeDisplay.offloadButton.setEnabled(true);
					}
				}
				
				TimeDisplay.spacebarToContinue.cancel(); // no need to remind participant they need to press spacebar
				TimeDisplay.waitForSpacebar = false;
				TimeDisplay.focusPanel.setFocus(false);
				TimeDisplay.stimulusDisplay.setHTML("");
				TimeDisplay.startClock();
				
				new Timer() {
					public void run() {
						TimeDisplay.stimulusDisplay.setHTML(TimeDisplay.generateStimulus());
						TimeDisplay.focusPanel.setFocus(true);
					}
				}.schedule(TimeBlock.RSI);
			} else {
				Window.alert("Press the spacebar to continue");
			}
		} else {
			if (response != TimeBlock.spaceBarKey) { //increment trial number if a key other than spacebar was pressed
				TimeBlock.trialNumber++;
			}
			
			boolean nBackCorrect = false;
			
			if (TimeDisplay.stimulus == TimeDisplay.stimulus_2back) {
				if (response == TimeBlock.matchKey) {
					nBackCorrect = true;
					TimeBlock.nBackMatchCorr++;
				}
			} else {
				if (response == TimeBlock.nonMatchKey) {
					nBackCorrect = true;
					TimeBlock.nBackNonMatchCorr++;
				}
			}
			
			String data = TimeBlock.blockNumber + "," + TimeBlock.offloadButtonVisible + ",";
			data = data + TimeBlock.trialNumber + "," + TimeDisplay.stimulus + ",";
			data = data + response + "," + RT + ",";
			data = data + TimeDisplay.awaitingPMresponse + "," + (TimeDisplay.stimulus == TimeDisplay.stimulus_2back) + ",";
			data = data + nBackCorrect + "," + TimeBlock.nextTarget + "," + TimeBlock.currentTime + ",";
			data = data + TimeStamp.Now();
			
			PHP.logData("TB_response", data, false);
			
			if (response==TimeBlock.spaceBarKey) {
				if (TimeDisplay.awaitingPMresponse) {
					if (Math.abs(TimeBlock.currentTime-TimeBlock.lastTarget) <= TimeBlock.PMwindow) {
						TimeBlock.PMhits++;
						
						TimeDisplay.awaitingPMresponse=false;
						
						TimeDisplay.reminder.cancel();
						TimeDisplay.showReminder = false;
						TimeDisplay.offloadButton.setEnabled(false);
						
						TimeDisplay.clockDisplay.addStyleName("greenyellow");
						
						new Timer() {
							public void run() {
								TimeDisplay.clockDisplay.removeStyleName("greenyellow");
							}
						}.schedule(200);
					}
				}
			} else {
				TimeDisplay.focusPanel.setFocus(false);
		
				TimeDisplay.stimulusDisplay.setHTML("");
		
				new Timer() {
					public void run() {
						if (!TimeDisplay.waitForSpacebar) { 
							TimeDisplay.stimulusDisplay.setHTML(TimeDisplay.generateStimulus());
							TimeDisplay.focusPanel.setFocus(true);
							TimeResponse.stimOn = new Date();
							
							String data = TimeBlock.blockNumber + "," + TimeDisplay.stimulus + ",";
							data = data + TimeStamp.Now();
							
							PHP.logData("TB_stimOn", data, false);
						}
					}
				}.schedule(TimeBlock.RSI);
			}
		}
	}
}
