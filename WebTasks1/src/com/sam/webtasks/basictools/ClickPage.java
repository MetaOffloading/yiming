//This code will display some text on the screen, with a button underneath.
//When the user clicks the button, the screen is cleared and we go back to the 
//SequenceHandler.
//
//Usage: ClickPage.Run(String textToDisplay, String textOnButton)
//e.g.:
//ClickPage.Run("Hello", "Click here to continue")
//
//The text to display can include HTML code e.g. <b> for bold


package com.sam.webtasks.basictools;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.client.SequenceHandler;

public class ClickPage {
	public static void Run(String htmlText, String buttonText) {
		final HTML displayText = new HTML(htmlText);
	    final Button continueButton = new Button(buttonText);
	    
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
	    final VerticalPanel verticalPanel = new VerticalPanel();

	    //set up vertical panel
	    verticalPanel.setWidth("75%");
	    verticalPanel.setHeight("75%");

	    verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

	    //add elements to panel
	    displayText.setStyleName("instructionText");
	    verticalPanel.add(displayText);
	    verticalPanel.add(continueButton);

	    //place vertical panel inside horizontal panel, so it can be centred
	    horizontalPanel.setWidth(Window.getClientWidth() + "px");
	    horizontalPanel.setHeight(Window.getClientHeight() + "px");

	    horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

	    horizontalPanel.add(verticalPanel);

	    //add panel to root
	    RootPanel.get().add(horizontalPanel);

	    //set up clickhandler  
	    continueButton.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	            RootPanel.get().remove(horizontalPanel);

	            new Timer() {
	                public void run() {
	                	SequenceHandler.Next();
	                }
	            }.schedule(500);
	        }
	    });
	}

}

