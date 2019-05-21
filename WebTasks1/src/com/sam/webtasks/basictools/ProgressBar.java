package com.sam.webtasks.basictools;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.client.WebTasks;

public class ProgressBar extends HTML 
{
    public ProgressBar()
    {
        super("<progress style='width: 98%;'></progress>");
    }
 
    /**
     * Set the progress indicator the the specified values
     * @param value Current progress value
     * @param max Target/complete value
     */
    public void setProgress(int value, int max)
    {
        Element progressElement = getElement().getFirstChildElement();
        progressElement.setAttribute("value", String.valueOf(value));
        progressElement.setAttribute("max", String.valueOf(max));
    }
 
    /**
     * Remove the progress indicator values.  On firefox, this causes the
     * progress bar to sweep back and forth.
     */
    public void clearProgress()
    {
        Element progressElement = getElement().getFirstChildElement();
        progressElement.removeAttribute("value");
        progressElement.removeAttribute("max");
    }
    
    public static void Initialise() {
    	WebTasks.progressBarPanel.setWidth("100%");
    	WebTasks.progressBarPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    	WebTasks.progressBar.setWidth("200px");
    	WebTasks.progressBarPanel.add(WebTasks.progressBar);
    }
    
    public static void Show() {
    	RootPanel.get().add(WebTasks.progressBarPanel);
    }
    
    public static void Hide() {
    	RootPanel.get().remove(WebTasks.progressBarPanel);
    }
    
    public static void SetProgress(int value, int max) {
    	WebTasks.progressBar.setProgress(value,  max);
    }
}