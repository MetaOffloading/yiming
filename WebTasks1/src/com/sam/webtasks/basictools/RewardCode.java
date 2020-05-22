package com.sam.webtasks.basictools;

import com.google.gwt.user.client.Random;
import com.sam.webtasks.client.SessionInfo;

public class RewardCode {
	private static int codeLength = 7; //number of digits in the reward code
	
	public static void Generate() {
		SessionInfo.rewardCode="";
		
		for (int i = 0; i < codeLength; i++) {
			SessionInfo.rewardCode=SessionInfo.rewardCode + (Random.nextInt(9)+1);
		}
		
		//log the reward code to the database.
		PHP.logData("rewardCode", SessionInfo.rewardCode, false);
	}

}
