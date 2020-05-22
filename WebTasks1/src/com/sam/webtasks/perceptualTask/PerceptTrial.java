package com.sam.webtasks.perceptualTask;

import java.util.Collections;
import java.util.Date;

import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.sam.webtasks.basictools.Names;

public class PerceptTrial {
	public static int correctResponse = Names.LEFT;
	public static int response = Names.LEFT;
	public static boolean correct, lastCorrect = false;
	public static Date stimOn;
	public static int RT;
	
	public static void Run() {
		PerceptDisplay.panel.add(PerceptDisplay.grid2Layer);
    	PerceptDisplay.panel.add(PerceptDisplay.fixLayer);
    	PerceptDisplay.panel.draw();
    	
		int leftSquares = PerceptDisplay.referenceSquares, rightSquares = PerceptDisplay.referenceSquares;
		
		double lightRef = 0.75 - ((double) PerceptBlock.difficulty / 1000);
        double darkRef = 0.25 + ((double) PerceptBlock.difficulty / 1000);
        double lightOdd = 0.75 + ((double) PerceptBlock.difficulty / 1000);
        double darkOdd = 0.25 - ((double) PerceptBlock.difficulty / 1000);
        double refDiff = lightRef - darkRef;
        double oddDiff = lightOdd - darkOdd;
        
        if (Random.nextInt(2) == 0) { //right correct
            correctResponse = Names.RIGHT;

            if (PerceptBlock.task == Names.PERCEPT_NUMBER) {
                rightSquares += PerceptBlock.difficulty;
            }
        } else { // left correct
            correctResponse = Names.LEFT;

            if (PerceptBlock.task == Names.PERCEPT_NUMBER) {
                leftSquares += PerceptBlock.difficulty;
            }
        }
        
      //shuffle filledSquares
        for (int i = 0; i < PerceptDisplay.filledSquares.size(); i++) {
            Collections.swap(PerceptDisplay.filledSquares, i, Random.nextInt(PerceptDisplay.filledSquares.size()));
        }

        //colour left squares
        for (int rectangleIndex = 0; rectangleIndex < PerceptDisplay.gridSize * PerceptDisplay.gridSize; rectangleIndex++) {
            if (PerceptBlock.task == Names.PERCEPT_NUMBER) {
                if (PerceptDisplay.filledSquares.indexOf(rectangleIndex) < leftSquares) {
                    PerceptDisplay.leftRectangles[rectangleIndex].setFillColor(ColorName.SALMON);
                } else {
                    PerceptDisplay.leftRectangles[rectangleIndex].setFillColor(ColorName.WHITE);
                }
            } else if (PerceptBlock.task == Names.PERCEPT_CONTRAST) {
                double refValue = darkRef + (PerceptDisplay.filledSquares.indexOf(rectangleIndex) * (refDiff / (PerceptDisplay.gridSize * PerceptDisplay.gridSize - 1)));
                double oddValue = darkOdd + (PerceptDisplay.filledSquares.indexOf(rectangleIndex) * (oddDiff / (PerceptDisplay.gridSize * PerceptDisplay.gridSize - 1)));

                int index = 0;

                if (correctResponse == Names.LEFT) {
                    index = (int) (oddValue * 255);
                } else if (correctResponse == Names.RIGHT) {
                    index = (int) (refValue * 255);
                }

                PerceptDisplay.leftRectangles[rectangleIndex].setFillColor(PerceptDisplay.palette[index]);
            }
        }

        //shuffle filledSquares again
        for (int i = 0; i < PerceptDisplay.filledSquares.size(); i++) {
            Collections.swap(PerceptDisplay.filledSquares, i, Random.nextInt(PerceptDisplay.filledSquares.size()));
        }

        //colour right squares
        for (int rectangleIndex = 0; rectangleIndex < PerceptDisplay.gridSize * PerceptDisplay.gridSize; rectangleIndex++) {
            if (PerceptBlock.task == Names.PERCEPT_NUMBER) {
                if (PerceptDisplay.filledSquares.indexOf(rectangleIndex) < rightSquares) {
                    PerceptDisplay.rightRectangles[rectangleIndex].setFillColor(ColorName.SALMON);
                } else {
                    PerceptDisplay.rightRectangles[rectangleIndex].setFillColor(ColorName.WHITE);
                }
            } else if (PerceptBlock.task == Names.PERCEPT_CONTRAST) {
                double refValue = darkRef + (PerceptDisplay.filledSquares.indexOf(rectangleIndex) * (refDiff / (PerceptDisplay.gridSize * PerceptDisplay.gridSize - 1)));
                double oddValue = darkOdd + (PerceptDisplay.filledSquares.indexOf(rectangleIndex) * (oddDiff / (PerceptDisplay.gridSize * PerceptDisplay.gridSize - 1)));

                int index = 0;

                if (correctResponse == Names.LEFT) {
                    index = (int) (refValue * 255);
                } else if (correctResponse == Names.RIGHT) {
                    index = (int) (oddValue * 255);
                }

                PerceptDisplay.rightRectangles[rectangleIndex].setFillColor(PerceptDisplay.palette[index]);
            }
        }
        
        new Timer() {
        	public void run() {
        		PerceptDisplay.panel.add(PerceptDisplay.stimulusLayer);
        		PerceptDisplay.stimulusLayer.draw();
        		
        		//get timestamp for stimulus presentation
        		stimOn = new Date();
        		
        		new Timer() {
        			public void run() {
        				PerceptDisplay.panel.remove(PerceptDisplay.stimulusLayer);
        				
        				//add the response layer so a click on the stimulus triggers a response
                		PerceptDisplay.panel.add(PerceptDisplay.responseLayer);
        			}
        		}.schedule(PerceptBlock.stimDuration);
        	}
        }.schedule(PerceptBlock.preStimFixation);
	}
}
