package com.sam.webtasks.perceptualTask;

import java.util.Date;

import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.Names;

public class PerceptBlock {
	//which task to run
	public static int task;
	
	//difficulty
	public static int difficulty;
	
	//adjust difficulty?
	public static boolean adjustDifficulty;
	
	//how many trials?
	public static int nTrials;
	
	//which trial number is this?
	public static int trial;
	
	//which block number is this?
	public static int block;
	
	//how many correct responses so far?
	public static int nCorrect;
	
	//how long should fixation point be shown for, before stimulus appears?
	public static int preStimFixation;
	
	//how long should the stimulus be shown for?
	public static int stimDuration;
	
	//how long to blank the screen, after a response is made
	public static int postResponseBlank;
		
	//timestamp of beginning of block
	public static Date blockStart;


	/*-----------reset all block settings-----------*/
	
	public static void Init() {
		if (PerceptDisplay.isInitialised == false) {
			PerceptDisplay.Init();
		}
		
		task = Names.PERCEPT_NUMBER;
		difficulty = 100;
		adjustDifficulty = false;
		nTrials = 1;		
		trial = 1;
		block = 1;
		nCorrect = 0;
		preStimFixation = 250;
		stimDuration = 250;
		postResponseBlank = 250;
	}
	
	/*-----------run a block-----------*/
	public static void Run() {
		RootPanel.get().add(PerceptDisplay.wrapper);
		
		//set timestamp for beginning of block
		blockStart = new Date();
		
		PerceptTrial.Run();
	}
}
