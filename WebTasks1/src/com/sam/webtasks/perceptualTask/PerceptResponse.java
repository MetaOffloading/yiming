package com.sam.webtasks.perceptualTask;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.client.SequenceHandler;

public class PerceptResponse {
	public static void Run() { //code to run when a response is made
		PerceptDisplay.panel.remove(PerceptDisplay.responseLayer);
		PerceptDisplay.panel.remove(PerceptDisplay.stimulusLayer);
        PerceptDisplay.panel.remove(PerceptDisplay.fixLayer);
        PerceptDisplay.panel.remove(PerceptDisplay.grid2Layer);

        if (PerceptTrial.response == PerceptTrial.correctResponse) {
        	PerceptTrial.correct = true;
            PerceptBlock.nCorrect++;
        }
        
        if (++PerceptBlock.trial > PerceptBlock.nTrials) {//end of block?
        	//return control to the sequence handler
        	RootPanel.get().remove(PerceptDisplay.lienzoWrapper);
        	SequenceHandler.Next();
        } else { //block not finished?
        	//TODO: output data
        	
        	//adjust difficulty, if applicable
        	if (PerceptBlock.trial > 0) {
        		if (PerceptTrial.correct) {
        			if (PerceptTrial.lastCorrect) {
        				if (PerceptBlock.adjustDifficulty) {
        					if (PerceptBlock.difficulty > 50) {
        						PerceptBlock.difficulty -= 20;
        					} else if (PerceptBlock.difficulty > 30) {
        						PerceptBlock.difficulty -= 10;
        					} else if (PerceptBlock.difficulty > 16) {
        						PerceptBlock.difficulty -= 5;
        					} else if (PerceptBlock.difficulty > 1) {
        						PerceptBlock.difficulty--;
        					}
        				}
        				
        				//set lastCorrect to false because difficulty has just been adjusted and you need two in a row
            			PerceptTrial.lastCorrect = false; 
        			} else {
        				PerceptTrial.lastCorrect = true;
        			}
        		} else {
        			if (PerceptBlock.adjustDifficulty) {
        				if (PerceptBlock.difficulty < 16) {
        					PerceptBlock.difficulty++;
        				} else if (PerceptBlock.difficulty < 30) {
        					PerceptBlock.difficulty += 5;
        				} else if (PerceptBlock.difficulty < 50) {
        					PerceptBlock.difficulty += 10;
        				} else if (PerceptBlock.difficulty < 100) {
        					PerceptBlock.difficulty += 20;
        				}
        			}	
        			PerceptTrial.lastCorrect = false;
        		}
        	}
        	
        	
        	//now run another trial
        	new Timer() {
        		public void run() {
        			PerceptDisplay.panel.add(PerceptDisplay.grid2Layer);
                	PerceptDisplay.panel.add(PerceptDisplay.fixLayer);
                	
                	PerceptTrial.Run();
        		}
        	}.schedule(PerceptBlock.postResponseBlank);
        }     
	}
}
