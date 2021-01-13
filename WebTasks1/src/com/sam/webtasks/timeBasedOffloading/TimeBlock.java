package com.sam.webtasks.timeBasedOffloading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.TimeStamp;

public class TimeBlock {
	//display settings
	public static boolean clockVisible;
	public static boolean offloadButtonVisible;
	
	//timing settings
	public static int currentTime;
	public static int clockStartTime;
	public static int nextInstruction;
	public static int nextTarget;
	public static int lastTarget;
	public static int blockDuration;
	public static int tickTime;
	public static int RSI;
	public static double nBackTargetProb;
	public static int targetInstructionInterval;
	public static boolean defaultPMintervals;
	public static ArrayList<Integer> PMinterval_list = new ArrayList<Integer>();
	public static boolean shufflePMintervals;
	public static int blockNumber;
	public static int trialNumber;
	public static int spaceBarKey = KeyCodes.KEY_SPACE;
	public static int nonMatchKey = KeyCodes.KEY_Z;
	public static int matchKey = KeyCodes.KEY_X;
	public static int nBackMatchCorr ; //number of correct 'match' responses
	public static int nBackNonMatchCorr; //number of correct 'nonmatch' responses
	public static int nBackTargetsPresented;
	public static int PMhits;
	public static boolean offloadButtonOperated;
	public static int nReminders;
	
	//should offloading be allowed in this block?
	public static boolean allowOffloading=true;
	
	//timestamp of beginning of block
	public static Date blockStart;
	
	//how many seconds before / after target time is ok?
	public static final int PMwindow = 2;
	
	/*-----------reset all block settings-----------*/
	
	public static void Init() {
		if (TimeDisplay.isInitialised == false) {
			TimeDisplay.Init();
		}
		 
		clockVisible=true;
		offloadButtonVisible=true;
		currentTime=0;
		clockStartTime=0;
		targetInstructionInterval=10;
		nextInstruction=targetInstructionInterval;		
		blockDuration=365;
		tickTime=1000;
		RSI=300;
		nBackTargetProb=0.2;
		PMinterval_list.clear();
		defaultPMintervals = true;
		shufflePMintervals = true;
		blockNumber = -1;
		trialNumber = 0;
		nBackMatchCorr = 0;
		nBackNonMatchCorr = 0;
		nBackTargetsPresented = 0;
		PMhits=0;
		offloadButtonOperated=false;
		nReminders=0;
		
		TimeDisplay.clockDisplay.setHTML("0:00");
		TimeDisplay.stimulusDisplay.setHTML("Press spacebar to start");
		
		TimeDisplay.focusPanel.setFocus(true);
		TimeDisplay.offloadButton.setEnabled(false);
		TimeDisplay.awaitingPMresponse=false;
		TimeDisplay.timeForInstruction=false;
	}
	
	/*-----------run a block-----------*/
	
	public static void Run() {
		if (defaultPMintervals) {
			for (int i = 0; i < 4; i++) {
				PMinterval_list.add(10);
				PMinterval_list.add(20);
				PMinterval_list.add(30);
			}
		}
		
		if (shufflePMintervals) {
			for (int i = 0; i < PMinterval_list.size(); i++) {
				Collections.swap(PMinterval_list, i, Random.nextInt(PMinterval_list.size()));
			}
		}
		
		if (targetInstructionInterval<0) { //negative interval means don't present instructions
			targetInstructionInterval = Integer.MAX_VALUE;
			nextInstruction = targetInstructionInterval;
		}
		
		nextTarget=nextInstruction+TimeDisplay.generateDelay();
		lastTarget=nextTarget;
		
		TimeDisplay.clockDisplay.setVisible(clockVisible);
		TimeDisplay.offloadButton.setVisible(offloadButtonVisible);
		RootPanel.get().add(TimeDisplay.focusPanel);
		TimeDisplay.waitForSpacebar = true;

		//set timestamp for beginning of block
		blockStart = new Date();
		TimeResponse.stimOn = new Date();
		
		PHP.logData("TB_blockstart", blockNumber + "," + TimeStamp.Now(), false);
	}
}
