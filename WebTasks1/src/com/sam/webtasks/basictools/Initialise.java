package com.sam.webtasks.basictools;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.sam.webtasks.client.SequenceHandler;
import com.sam.webtasks.client.SessionInfo;

public class Initialise {
	public static void Run() {
		if (SessionInfo.localTesting) {
			Window.alert("Set to local testing mode. Data will not be stored on server.");
		}
		
		if (SessionInfo.experimentType == Names.EXPERIMENT_MTURK) {
			RootPanel.get().add(new Label("initalising..."));
		}
		
		//set timestamp for the beginning of the experiment
		TimeStamp.Start();
		
		//generate a random session key so that we have an identifier for this session
	    SessionInfo.sessionKey=SessionKey.Get();

		//get participant ID from query line
		SessionInfo.participantID = Window.Location.getParameter("workerId");
		
		if (SessionInfo.participantID == null) {
			SessionInfo.participantID = "null";
		}
		
		//generate a reward code, which can be used to claim payment at end
		RewardCode.Generate();

		//set up counterbalancing
		for (int i = 0; i < SessionInfo.counterbalanceFactors.length; i++) {
			if (SessionInfo.specifiedLevels[i]==-1) {
				Counterbalance.addFactor(SessionInfo.counterbalanceFactors[i], SessionInfo.counterbalanceLevels[i]);
			} else {
				Counterbalance.addFactor(SessionInfo.counterbalanceFactors[i], SessionInfo.counterbalanceLevels[i], SessionInfo.specifiedLevels[i]);
			}
		}
		
		if (SessionInfo.experimentType == Names.EXPERIMENT_STANDALONE) {
			HTML participantHTML = new HTML("Experiment: " + SessionInfo.experimentCode + ", Version: " + SessionInfo.experimentVersion + 
					"<br>Participant code:");
			final TextBox textBox = new TextBox();
			Button continueButton = new Button("Continue");
			
			RootPanel.get().add(participantHTML);
			RootPanel.get().add(textBox);
			RootPanel.get().add(continueButton);
			
			continueButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (textBox.getText().length() > 0) {
						SessionInfo.participantID = textBox.getText();
						
						int ID = Integer.parseInt(SessionInfo.participantID);
						Counterbalance.setCounterbalancingFactors(ID % 4);
								
						String data = Counterbalance.getFactorLevel("colourMeaning") + ",";
						data = data + Counterbalance.getFactorLevel("conditionOrder") + ",";
						data = data + TimeStamp.Now();
						
						RootPanel.get().clear();
						
						PHP.logData("start", data, true);
					}
					
				}
			});
		} else {
			SequenceHandler.Next();
		}
	}
}
