package com.sam.webtasks.timeBasedOffloading;

import java.util.Date;

import com.google.gwt.user.client.ui.RootPanel;

public class TimeBlock {
	//timing settings
	public static int currentTime;
	public static int clockStartTime;
	public static int nextInstruction;
	public static int nextTarget;
	public static int lastTarget;
	public static int blockDuration;
	public static int tickTime;
	public static int RSI;
	
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
		
		currentTime=0;
		clockStartTime=0;
		nextInstruction=TimeDisplay.generateDelay();
		nextTarget=nextInstruction+TimeDisplay.generateDelay();
		lastTarget=nextTarget;
		blockDuration=60;
		tickTime=1000;
		RSI=300;
	}
	
	/*-----------run a block-----------*/
	
	public static void Run() {
		RootPanel.get().add(TimeDisplay.wrapper);
		TimeDisplay.waitForSpacebar = true;
		
		//set timestamp for beginning of block
		blockStart = new Date();
	}
}
