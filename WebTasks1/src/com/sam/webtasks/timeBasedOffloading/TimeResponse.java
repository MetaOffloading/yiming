package com.sam.webtasks.timeBasedOffloading;

import com.google.gwt.user.client.Timer;

public class TimeResponse {
	public static void Run(int response) {
		TimeDisplay.focusPanel.setFocus(false);
		
		TimeDisplay.stimulusDisplay.setHTML("");
		
		new Timer() {
			public void run() {
				TimeDisplay.stimulusDisplay.setHTML("A");
			}
		}.schedule(TimeBlock.RSI);
	}
}
