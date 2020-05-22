package com.sam.webtasks.basictools;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.client.SequenceHandler;

public class CheckScreenSize {
	public static void Run(final int xMin, final int yMin) {
		int xDim = Window.getClientWidth();
		int yDim = Window.getClientHeight();

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		final VerticalPanel verticalPanel = new VerticalPanel();
		final Button continueButton = new Button("Try Again");

		String HTMLtext = "Your browser window is too small. You will not "
				+ "be able to continue unless you make the window larger.<br><br>"
				+ "Please maximise the size of your window and click the button below " + "to try again.<br><br>";

		final HTML textHTML = new HTML(HTMLtext);

		horizontalPanel.setWidth(xDim + "px");
		horizontalPanel.setHeight(yDim + "px");

		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		verticalPanel.setWidth("75%");
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel.add(textHTML);
		verticalPanel.add(continueButton);

		horizontalPanel.add(verticalPanel);

		continueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				
				new Timer() {
					public void run() {
						Run(xMin, yMin);
					}
				}.schedule(500);
			}
		});

		if ((xDim >= xMin) & (yDim >= yMin)) {
			SequenceHandler.Next();
		} else {
			RootPanel.get().add(horizontalPanel);
		}
	}

}
