package com.sam.webtasks.timeBasedOffloading;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TimeDisplay {
	//we can use this to check whether the display has been initialised, if not it needs to be done
	public static boolean isInitialised = false;
	
	/*------------display parameters------------*/
	
	public static String displayPanelSize="50%";
	
	/*------------GWT display objects------------*/
	
	public static final HorizontalPanel wrapper = new HorizontalPanel();
	public static final FocusPanel focusPanel = new FocusPanel();
	public static final VerticalPanel displayPanel = new VerticalPanel();
	public static final HorizontalPanel clockPanel = new HorizontalPanel();
	public static final HorizontalPanel stimulusPanel = new HorizontalPanel();
	public static final HorizontalPanel offloadPanel = new HorizontalPanel();
	
	public static final HTML clockDisplay = new HTML("0:00");
	public static final HTML stimulusDisplay = new HTML("+");
	public static final Button offloadButton = new Button("Remind me");
	
	
	/*------------set up display------------*/
	public static void Init() {
		isInitialised = true;
		
		wrapper.setWidth(Window.getClientWidth() + "px");
		wrapper.setHeight(Window.getClientHeight() + "px");
		wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		wrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		displayPanel.setWidth(displayPanelSize);
		displayPanel.setHeight(displayPanelSize);
		displayPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		//clock panel
		clockPanel.add(clockDisplay);
		
		displayPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		displayPanel.add(clockPanel);
		
		//stimulus panel (we wrap it inside a focus panel so that it can receive keypresses)
		stimulusDisplay.addStyleName("timeText");
		
		focusPanel.add(stimulusDisplay);
		
		stimulusPanel.add(focusPanel);
		
		displayPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		displayPanel.add(stimulusPanel);
		
		//offloading panel
		offloadPanel.add(offloadButton);
		
		displayPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		displayPanel.add(offloadPanel);
		
		//we wrap the display panel inside a focus panel so that it can receive keypress responses
		wrapper.add(displayPanel);
		
		//set up focus panel to receive keypress responses
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown (KeyDownEvent event) {
				TimeResponse.Run(event.getNativeKeyCode());
			}
		});
		
		focusPanel.setFocus(true);
	}

	/*------------clock functions------------*/
	public static final Timer clockTimer = new Timer() {
		public void run() {
			if (++TimeBlock.currentTime == 600) {
				TimeBlock.currentTime = 0;
			}
			
			clockDisplay.setHTML(timeString(TimeBlock.currentTime));
		}
	};
	
	public static void startClock() {
		clockTimer.scheduleRepeating(TimeBlock.tickTime);
	}
	
	public static void stopClock() {
		clockTimer.cancel();
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
}
