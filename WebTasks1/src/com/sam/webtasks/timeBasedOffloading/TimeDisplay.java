package com.sam.webtasks.timeBasedOffloading;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.TimeStamp;

public class TimeDisplay {
	//we can use this to check whether the display has been initialised, if not it needs to be done
	public static boolean isInitialised = false;
	
	//use this to wait specifically for a space-bar response
	public static boolean waitForSpacebar = false;
	
	//is it time to show the instruction for the next target?
	public static boolean timeForInstruction = false;
	public static String instructionString = "";
	
	//are we waiting for a target response?
	public static boolean awaitingPMresponse = false;
	
	//should a reminder be displayed for the next target?
	public static boolean showReminder = false;
	
	/*------------display parameters------------*/
	public static String displayHeight="50%";
	public static String displayWidth="80%";
	
	/*------------stimulus------------*/
	public static int stimulus = -1, stimulus_1back = -1, stimulus_2back = -1;
	
	/*------------GWT display objects------------*/
	
	public static final HorizontalPanel wrapper = new HorizontalPanel();
	public static final FocusPanel focusPanel = new FocusPanel();
	public static final VerticalPanel displayPanel = new VerticalPanel();
	public static final HorizontalPanel clockPanel = new HorizontalPanel();
	public static final HorizontalPanel stimulusPanel = new HorizontalPanel();
	public static final HorizontalPanel offloadPanel = new HorizontalPanel();
	
	public static final HTML clockDisplay = new HTML();
	public static final HTML stimulusDisplay = new HTML();
	public static final Button offloadButton = new Button("Remind me");
	
	
	/*------------set up display------------*/
	public static void Init() {
		isInitialised = true;
		
		wrapper.setWidth(Window.getClientWidth() + "px");
		wrapper.setHeight(Window.getClientHeight() + "px");
		wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		wrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		displayPanel.setWidth(displayWidth);
		displayPanel.setHeight(displayHeight);
		displayPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		//clock panel
		clockPanel.add(clockDisplay);
		
		clockDisplay.addStyleName("clockText");
		
		displayPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		displayPanel.add(clockPanel);
		
		//stimulus panel (we wrap it inside a focus panel so that it can receive keypresses)
		stimulusDisplay.addStyleName("timeText");

		stimulusPanel.add(stimulusDisplay);
		
		displayPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		displayPanel.add(stimulusPanel);
		
		//offloading panel
		offloadButton.setEnabled(false);
		
		offloadPanel.add(offloadButton);
		
		displayPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		displayPanel.add(offloadPanel);
		
		//we wrap the wrapper / display panel inside a focus panel so that it can receive keypress responses
		wrapper.add(displayPanel);
		focusPanel.add(wrapper);
		
		//set up focus panel to receive keypress responses
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown (KeyDownEvent event) {
				TimeResponse.Run(event.getNativeKeyCode());
			}
		});
		
		//set up the offload button
		offloadButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String data = TimeBlock.blockNumber + "," + TimeBlock.currentTime + "," + TimeStamp.Now();
				
				PHP.logData("TB_offloadButtonClick", data, false);
				
				showReminder=true;
				TimeBlock.offloadButtonOperated=true;
				TimeBlock.nReminders++;
				offloadButton.setEnabled(false);
				focusPanel.setFocus(true);
			}
		});
	}

	/*------------clock functions------------*/
	public static final Timer clockTimer = new Timer() {
		public void run() {
			if (++TimeBlock.currentTime == 600) {
				TimeBlock.currentTime = 0;
			}
			
			String data = TimeBlock.blockNumber + "," + TimeBlock.currentTime + "," + TimeStamp.Now();
			PHP.logData("TB_clockTick", data, false);
			
			clockDisplay.setHTML(timeString(TimeBlock.currentTime));
			
			//start reminding for target?
			if (TimeBlock.lastTarget - TimeBlock.currentTime == TimeBlock.PMwindow) {
				if (showReminder) {
					PHP.logData("TB_reminderOn", data, false);
					TimeDisplay.showReminder = false;
					
					reminder.scheduleRepeating(200);
				}
			}
			
			//stop reminding for target?
			if (TimeBlock.currentTime - TimeBlock.lastTarget == TimeBlock.PMwindow) {
				PHP.logData("TB_reminderOff", data, false);
				
				reminder.cancel();
				TimeDisplay.offloadButton.setEnabled(false);
			}
			
			//instruction for next target?
			if (TimeBlock.currentTime == TimeBlock.nextInstruction) {
				data = TimeBlock.blockNumber + "," + TimeBlock.currentTime + ",";
				data = data + TimeBlock.nextInstruction + "," + TimeBlock.nextTarget + "," + TimeStamp.Now();
				
				PHP.logData("TB_instruction", data, false);
				
				awaitingPMresponse=true;
				
				instructionString = "Hit the spacebar at " + timeString(TimeBlock.nextTarget);

				timeForInstruction=true;
				
				TimeBlock.nextInstruction = TimeBlock.nextTarget+TimeBlock.targetInstructionInterval;
				TimeBlock.lastTarget = TimeBlock.nextTarget; //save this, to check against PM response
				TimeBlock.nextTarget = TimeBlock.nextInstruction+generateDelay();
				
				cancel();
			}
		}
	};
	
	public static final Timer spacebarToContinue = new Timer() {
		public void run() {
			Window.alert("Press the spacebar to resume the task.");
		}
	};
	
	public static final Timer reminder = new Timer() {
		public void run() {
			clockDisplay.addStyleName("red");
			
			new Timer() {
				public void run() {
					clockDisplay.removeStyleName("red");
				}
			}.schedule(100);
		}
	};
	
	public static int generateDelay() {
		int delay=0;
		
		if (TimeBlock.PMinterval_list.size()>0) {
			delay = TimeBlock.PMinterval_list.get(0);
			TimeBlock.PMinterval_list.remove(0);
		}
		return(delay);
	}
	
	public static void startClock() {
		String data = TimeBlock.blockNumber + "," + TimeBlock.currentTime + "," + TimeStamp.Now();
		PHP.logData("TB_clockStart", data, false);
		
		clockTimer.scheduleRepeating(TimeBlock.tickTime);
	}
	
	public static final String timeString(int timeInt) {
		int mins = timeInt/60;
		int secs = (timeInt % 60);
		
		String s="";
		
		if (secs<10) {
			s=mins + ":0" + secs;
		} else {
			s=mins + ":" + secs;
		}
		
		return(s);
	}

	/*------------stimulus generation------------*/
	public static String generateStimulus() {
		boolean notvalid=true;
		
		//initialise stimuli if not done already
		if (stimulus==-1) {
			while (notvalid) {
				notvalid=false;
				
				stimulus=Random.nextInt(26);
				stimulus_1back=Random.nextInt(26);
				stimulus_2back=Random.nextInt(26);
				
				if (stimulus==stimulus_1back) {
					notvalid=true;
				}
				
				if (stimulus_1back==stimulus_2back) {
					notvalid=true;
				}
			}
		}

		int newStimulus = Random.nextInt(26);
		
		notvalid=true;
		
		if (Math.random() > TimeBlock.nBackTargetProb) { //2-back nontarget
			while (notvalid) {
				notvalid=false;
				
				newStimulus = Random.nextInt(26);
				
				if (newStimulus == stimulus) {
					notvalid=true;
				}
				
				if (newStimulus == stimulus_1back) {
					notvalid=true;
				}
				
				if (newStimulus == stimulus_2back) {
					notvalid=true;
				}
			}
		} else { //2-back target
			newStimulus = stimulus_1back;
			TimeBlock.nBackTargetsPresented++;
		}
		
		//present a target if this is the last trial and there have not been any yet
		if ((TimeBlock.nBackTargetsPresented==0)&(TimeBlock.trialNumber == (-TimeBlock.blockDuration)-1)) {
			newStimulus = stimulus_1back;
			TimeBlock.nBackTargetsPresented++;
		}
		
		stimulus_2back=stimulus_1back;
		stimulus_1back=stimulus;
		stimulus=newStimulus;

		return(Character.toString((char) (stimulus + 'A')));
	}
}
