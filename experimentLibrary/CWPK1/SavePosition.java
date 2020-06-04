package com.sam.webtasks.client;

import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.iotask2.IOtask2BlockContext;

public class SavePosition {
	public static void Save() {
		ProgressBar.GetProgress();
		SessionInfo.resumePoints=IOtask2BlockContext.getTotalPoints();
		SessionInfo.resumePosition=SequenceHandler.GetPosition(0);
		SessionInfo.resumeProgress=ProgressBar.progressValue;				
		
		String statusString = Counterbalance.getCounterbalancingCell() + ","
				+ SequenceHandler.GetPosition(0) + ","
				+ ProgressBar.progressValue + ","
				+ IOtask2BlockContext.getTotalPoints() + ","
				+ IOtask2BlockContext.getTotalPoints() + ","
				+ IOtask2BlockContext.getTotalPoints();

		PHP.UpdateStatus(statusString);	
	}

}
