//this code presents a draggable slider on the screen
//the first input is the instruction text, the next two inputs specify the text
//on the left and right of the slider
//when the continue button is clicked, control is passed back to the sequence handler
//and the selected slider value is stored in the sliderValue variable,
//which can subsquently be retrieved using the method getSliderValue()

package com.sam.webtasks.basictools;

import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveEvent;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.DragBounds;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.client.widget.DragContext;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragConstraint;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.client.SequenceHandler;

public class Slider {
	private static int sliderValue;
	
	public static void Run(String instruct, String leftLabelText, String rightLabelText) {
        final Button continueButton = new Button("Next");
        final int sliderWidth = 400;
        final int sliderHeight = 20;
        final int width = sliderWidth + 2 * sliderHeight;
        final int height = 5 * sliderHeight;

        final int sliderRange = sliderWidth - (2 * sliderHeight);

        LinearGradient gradient = new LinearGradient(0, -50, 0, 50);
        gradient.addColorStop(0.5, "#4DA4F3");
        gradient.addColorStop(0.8, "#ADD9FF");
        gradient.addColorStop(1, "#9ED1FF");

        LienzoPanel lienzoPanel = new LienzoPanel(width, height);

        Rectangle slider = new Rectangle(sliderWidth, sliderHeight, sliderHeight / 2);

        slider.setX(sliderHeight).setY(2 * sliderHeight).setFillGradient(gradient).setStrokeColor(ColorName.GRAY.getValue()).setStrokeWidth(1);

        final Circle thumbCircle = new Circle(2 * sliderHeight);
        final Text thumbText = new Text("50%", "Verdana, sans-serif", null, 12);
        final Circle thumbOverlay = new Circle(2 * sliderHeight);
        
        final Group thumb = new Group();

        thumbText.setTextAlign(TextAlign.CENTER);
        thumbText.setTextBaseLine(TextBaseLine.MIDDLE);
        thumbText.setFillColor(ColorName.BLACK);
        
        thumbOverlay.setFillColor(ColorName.BLACK);
        thumbOverlay.setAlpha(0.000000001);

        thumb.add(thumbCircle);
        thumb.add(thumbText);
        thumb.add(thumbOverlay);

        thumb.setX(slider.getX() + sliderHeight + (sliderRange / 2));
        thumb.setY(slider.getY() + sliderHeight / 2);
        thumbCircle.setStrokeColor(ColorName.GRAY.getValue());
        thumbCircle.setStrokeWidth(1);
        thumb.setDraggable(true);
        thumb.setDragConstraint(DragConstraint.HORIZONTAL);
        thumb.setDragBounds(new DragBounds().setX1(slider.getX() + sliderHeight).setX2(slider.getX() + sliderWidth - sliderHeight));
        thumbCircle.setFillColor(ColorName.LIGHTGRAY.getValue());

        thumb.addNodeDragStartHandler(new NodeDragStartHandler() {
            public void onNodeDragStart(NodeDragStartEvent event) {
                thumbCircle.setFillColor(ColorName.LIGHTPINK.getValue());
                thumbCircle.setShadow(new Shadow("gray", 5, 1, 1));
                thumb.getLayer().draw();
                continueButton.setEnabled(true);
            }
        });

        thumb.addNodeDragEndHandler(new NodeDragEndHandler() {
            public void onNodeDragEnd(NodeDragEndEvent event) {
                thumbCircle.setShadow(null);
                thumb.getLayer().draw();
            }
        });

        thumb.addNodeDragMoveHandler(new NodeDragMoveHandler() {
            public void onNodeDragMove(NodeDragMoveEvent event) {
                DragContext dC = event.getDragContext();

                int offset = dC.getDragStartX() + dC.getDx();

                if (offset > sliderWidth) {
                    offset = sliderWidth;
                }

                if (offset < 2 * sliderHeight) {
                    offset = 2 * sliderHeight;
                }

                sliderValue = (int) ((100 * (offset - 2 * sliderHeight)) / sliderRange);
                thumbText.setText(sliderValue + "%");
            }
        });

        Layer canvas = new Layer();
        canvas.add(slider);
        canvas.add(thumb);

        lienzoPanel.add(canvas);

        final HorizontalPanel sliderPanel = new HorizontalPanel();

        final Label leftValue = new Label(leftLabelText);
        final Label rightValue = new Label(rightLabelText);

        sliderPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        sliderPanel.add(leftValue);
        sliderPanel.add(lienzoPanel);
        sliderPanel.add(rightValue);

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final VerticalPanel verticalPanel = new VerticalPanel();

        //set up vertical panel
        verticalPanel.setWidth("75%");
        //verticalPanel.setHeight(Window.getClientHeight() + "px");
        verticalPanel.setHeight("75%");

        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        //add elements to panel
        final HTML displayText = new HTML(instruct);
        displayText.setStyleName("instructionText");
        verticalPanel.add(displayText);
        verticalPanel.add(sliderPanel);
        verticalPanel.add(continueButton);

        continueButton.setEnabled(false);

        //place vertical panel inside horizontal panel, so it can be centred
        horizontalPanel.setWidth(Window.getClientWidth() + "px");
        horizontalPanel.setHeight(Window.getClientHeight() + "px");

        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        horizontalPanel.add(verticalPanel);

        //add panel to root
        RootPanel.get().add(horizontalPanel);

        //set up clickhandler  
        continueButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(horizontalPanel);

                new Timer() {
                    public void run() {
                        SequenceHandler.Next();
                    }
                }.schedule(500);
            }
        });
    }
	
	public static int getSliderValue() {
		return(sliderValue);
	}
}
