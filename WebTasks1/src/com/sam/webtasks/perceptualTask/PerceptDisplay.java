package com.sam.webtasks.perceptualTask;

import java.util.ArrayList;
import java.util.List;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.Names;
import com.sam.webtasks.client.SequenceHandler;

public class PerceptDisplay {
	//we can use this to check whether the display has been initialised, if not it needs to be done
	public static boolean isInitialised = false;
	
	/*------------display parameters------------*/
	
	public static int gridSize = 20;
	public static int gridPixels = 12;
	public static final int panelSize = 3 * gridPixels * gridSize;
	public static final int referenceSquares = (gridSize * gridSize) / 2;
	
	/*------------initialise Lienzo objects------------*/
	
	public static final LienzoPanel panel = new LienzoPanel(panelSize, panelSize / 3);
	
	public static final Layer fixLayer = new Layer();
	public static final Layer grid2Layer = new Layer();
	public static final Layer stimulusLayer = new Layer();
	public static final Layer responseLayer = new Layer();
	
	public static final Text fixPoint = new Text("+", "Verdana, sans-serif", null, 32);

	//we put the lienzo panel into the lienzoWrapper, then we put the lienzoWrapper into another wrapper to center it
	public static final HorizontalPanel lienzoWrapper = new HorizontalPanel();
	public static final HorizontalPanel wrapper = new HorizontalPanel();
	

	public static Line[] leftHorizontalLines = new Line[gridSize+1];
	public static Line[] leftVerticalLines = new Line[gridSize+1];
	public static Line[] rightHorizontalLines = new Line[gridSize+1];
	public static Line[] rightVerticalLines = new Line[gridSize+1];
	
	public static final Rectangle[] leftRectangles = new Rectangle[gridSize * gridSize];
    public static final Rectangle[] rightRectangles = new Rectangle[gridSize * gridSize];
	
    public static List<Integer> filledSquares = new ArrayList<Integer>();
    
    public static Color[] palette = new Color[256];

    public static final Rectangle leftResponseRectangle = new Rectangle(gridSize * gridPixels, gridSize * gridPixels);
    public static final Rectangle rightResponseRectangle = new Rectangle(gridSize * gridPixels, gridSize * gridPixels);
    
	/*------------set up Lienzo objects------------*/

	public static void Init() {
		isInitialised = true;
		
		wrapper.setWidth(Window.getClientWidth() + "px");
		wrapper.setHeight(Window.getClientHeight() + "px");
		wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		wrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		//green lines
        for (int i = 1; i < gridSize; i++) {
            leftHorizontalLines[i] = new Line(0,i*gridPixels,gridPixels*gridSize,i*gridPixels).setStrokeColor(ColorName.LIGHTGREEN).setStrokeWidth(1);
            leftVerticalLines[i] = new Line(i*gridPixels,0,i*gridPixels,gridPixels*gridSize).setStrokeColor(ColorName.LIGHTGREEN).setStrokeWidth(1);
            
            rightHorizontalLines[i] = new Line(2*gridPixels*gridSize,i*gridPixels,3*gridPixels*gridSize,i*gridPixels).setStrokeColor(ColorName.LIGHTGREEN).setStrokeWidth(1);
            rightVerticalLines[i] = new Line((i*gridPixels)+(2*gridPixels*gridSize),0,(i*gridPixels)+(2*gridPixels*gridSize),gridPixels*gridSize).setStrokeColor(ColorName.LIGHTGREEN).setStrokeWidth(1);
            
            grid2Layer.add(leftHorizontalLines[i]);
            grid2Layer.add(leftVerticalLines[i]);
            grid2Layer.add(rightHorizontalLines[i]);
            grid2Layer.add(rightVerticalLines[i]);
        }
        
        //black lines
        for (int i = 0; i < (gridSize+1); i += gridSize) {
            leftHorizontalLines[i] = new Line(0,i*gridPixels,gridPixels*gridSize,i*gridPixels).setStrokeColor(ColorName.BLACK).setStrokeWidth(1);
            leftVerticalLines[i] = new Line(i*gridPixels,0,i*gridPixels,gridPixels*gridSize).setStrokeColor(ColorName.BLACK).setStrokeWidth(1);
            
            rightHorizontalLines[i] = new Line(2*gridPixels*gridSize,i*gridPixels,3*gridPixels*gridSize,i*gridPixels).setStrokeColor(ColorName.BLACK).setStrokeWidth(1);
            rightVerticalLines[i] = new Line((i*gridPixels)+(2*gridPixels*gridSize),0,(i*gridPixels)+(2*gridPixels*gridSize),gridPixels*gridSize).setStrokeColor(ColorName.BLACK).setStrokeWidth(1);
            
            grid2Layer.add(leftHorizontalLines[i]);
            grid2Layer.add(leftVerticalLines[i]);
            grid2Layer.add(rightHorizontalLines[i]);
            grid2Layer.add(rightVerticalLines[i]);
        }
        
        fixPoint.setTextAlign(TextAlign.CENTER);
        fixPoint.setTextBaseLine(TextBaseLine.MIDDLE);
        fixPoint.setFillColor(ColorName.BLACK);

        fixPoint.setX(panelSize / 2);
        fixPoint.setY((panelSize / 2) / 3);

        fixLayer.add(fixPoint);

        //we don't add the grid and fixation point to the panel here - instead it gets done at the beginning of each trial

        lienzoWrapper.add(panel);
        wrapper.add(lienzoWrapper);

        /*----------set up stimulus squares------------*/
        int rectangleIndex = 0;

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                int xPixel = x * gridPixels + 1;
                int yPixel = y * gridPixels + 1;

                leftRectangles[rectangleIndex] = new Rectangle(gridPixels - 2, gridPixels - 2);

                leftRectangles[rectangleIndex].setX(xPixel).setY(yPixel);
                stimulusLayer.add(leftRectangles[rectangleIndex++]);
            }
        }

        rectangleIndex = 0;

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                int xPixel = x * gridPixels + 1 + (2 * gridPixels * gridSize);
                int yPixel = y * gridPixels + 1;

                rightRectangles[rectangleIndex] = new Rectangle(gridPixels - 2, gridPixels - 2);

                rightRectangles[rectangleIndex].setX(xPixel).setY(yPixel);
                stimulusLayer.add(rightRectangles[rectangleIndex++]);
            }
        }

        for (int i = 0; i < gridSize * gridSize; i++) {
            filledSquares.add(i);
        }
        
        leftResponseRectangle.setX(1).setY(1).setAlpha(0.01);
        rightResponseRectangle.setX((2*gridPixels*gridSize)+1).setY(1).setAlpha(0.01);
        
        responseLayer.add(leftResponseRectangle);
        responseLayer.add(rightResponseRectangle);
        
        leftResponseRectangle.addNodeMouseClickHandler(new NodeMouseClickHandler() {
        	public void onNodeMouseClick(NodeMouseClickEvent event) {
        		PerceptTrial.response = Names.LEFT;
        		PerceptResponse.Run();
        	}
        });
        
        rightResponseRectangle.addNodeMouseClickHandler(new NodeMouseClickHandler() {
        	public void onNodeMouseClick(NodeMouseClickEvent event) {
        		PerceptTrial.response = Names.RIGHT;
        		PerceptResponse.Run();
        	}
        });

        /*----------set up colour palette----------*/
        for (int i = 0; i < 256; i++) {
            palette[i] = new Color(i, i, i); //greyscale palette
        }
	}
}
