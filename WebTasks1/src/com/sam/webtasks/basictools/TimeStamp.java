package com.sam.webtasks.basictools;

import java.util.Date;

public class TimeStamp {
	private static Date start;
	
	//set new timestamp
	public static void Start() {
		start = new Date();
	}
	
	//return time in ms since start
	public static int Now() {
		Date now = new Date();
		
		return ((int) (now.getTime() - start.getTime()));
	}
}
