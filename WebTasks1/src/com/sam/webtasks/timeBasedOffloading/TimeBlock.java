package com.sam.webtasks.timeBasedOffloading;

import java.util.Date;

import com.google.gwt.user.client.ui.RootPanel;

public class TimeBlock {
	//timing settings
	public static int currentTime, clockStartTime, targetTime, blockDuration, tickTime, RSI;
	
	//should offloading be allowed in this block?
	public static boolean allowOffloading;
	
	//timestamp of beginning of block
	public static Date blockStart;
	
	
	/*-----------reset all block settings-----------*/
	
	public static void Init() {
		 if (TimeDisplay.isInitialised == false) {
			TimeDisplay.Init();
		}
		
		currentTime=0;
		clockStartTime=0;
		targetTime=0;
		blockDuration=60;
		tickTime=1000;
		RSI=300;
	}
	
	/*-----------run a block-----------*/
	
	public static void Run() {
		RootPanel.get().add(TimeDisplay.wrapper);
		TimeDisplay.startClock();
		
		//set timestamp for beginning of block
		blockStart = new Date();
		
		TimeTrial.Run();
	}
}
