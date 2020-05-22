package com.sam.webtasks.client;

import com.google.gwt.user.client.Window;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.Names;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.iotask2.IOtask2Block;
import com.sam.webtasks.iotask2.IOtask2BlockContext;

public class IOtask2PostTrial {

	public static void Run() {
		//here we run the code that gives the post-trial feedback, or anything else that needs to be done after a trial
		//0: counterbalancing cell, 1: resumablePosition, 2: progressBarPosition, 3: resumablePoints, 4: actualPoints, 5: earnedPoints
		
		int unearnedPoints = 0;
		
		if (IOtask2BlockContext.getRewardFrame() == Names.LOSS_FRAME) {
			int remainingTrials = 13 - IOtask2BlockContext.getTrialNum();
			
			unearnedPoints += 100*remainingTrials;
		}
		
		String statusString = Counterbalance.getCounterbalancingCell() + ","
							+ SessionInfo.resumePosition + ","
	                        + SessionInfo.resumeProgress + ","
							+ SessionInfo.resumePoints + ","
							+ IOtask2BlockContext.getTotalPoints() + ","
							+ (IOtask2BlockContext.getTotalPoints() - unearnedPoints);
		
		PHP.UpdateStatus(statusString);							
	}

}
