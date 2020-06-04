package com.sam.webtasks.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.iotask2.IOtask2Block;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebTasks implements EntryPoint {
	public static ProgressBar progressBar = new ProgressBar();
	public static HorizontalPanel progressBarPanel = new HorizontalPanel();
	
	//we need to include this initialiser or GWT gives an internal compiler error
	IOtask2Block IOtask2BlockInitialiser = new IOtask2Block();
		
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {	      
		      @Override
		      public void onUncaughtException(Throwable e) {
		        // TODO Auto-generated method stub
		        String msg = e.toString();
		        for (StackTraceElement elt : e.getStackTrace()) {
		          msg += "\n in " + elt.getMethodName() + "("+elt.getFileName()+":"+elt.getLineNumber()+")";
		        }
		        Window.alert("Uncaught exception. Loop: " + SequenceHandler.GetLoop() + ", Position: "
		        			 + SequenceHandler.GetPosition() + "\n" + msg);
		      }
		    });
		
		// set the sequence handler to the initialisation loop and start from the beginning
		SequenceHandler.SetLoop(1,true); 
		SequenceHandler.Next();
	}
}
