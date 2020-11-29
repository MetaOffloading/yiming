package com.sam.webtasks.timeBasedOffloading;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.client.SequenceHandler;

public class TimeResponse {
	public static void Run(int response) {
		//end of block? if so return control to the sequencehandler
		if (TimeBlock.currentTime >= TimeBlock.blockDuration) {
			RootPanel.get().remove(TimeDisplay.wrapper);
			
			TimeDisplay.clockTimer.cancel();
			
			new Timer() {
				public void run() {
					SequenceHandler.Next();
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
					}
				}
			}.schedule(TimeBlock.RSI);
		}
		
		if (TimeDisplay.waitForSpacebar) {
			if (response==32) {
				if (TimeDisplay.awaitingPMresponse) {
					if (TimeBlock.allowOffloading) {
						TimeDisplay.offloadButton.setEnabled(true);
					}
				}
				
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
				Window.alert("Press the spacebar to start the clock");
			}
		} else {
			if (response==32) {
				if (TimeDisplay.awaitingPMresponse) {
					if (Math.abs(TimeBlock.currentTime-TimeBlock.lastTarget) <= TimeBlock.PMwindow) {
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
						}
					}
				}.schedule(TimeBlock.RSI);
			}
		}
	}
}
