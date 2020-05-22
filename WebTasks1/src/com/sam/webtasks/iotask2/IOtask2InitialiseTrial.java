package com.sam.webtasks.iotask2;

import java.util.ArrayList;
import java.util.Collections;

import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.sam.webtasks.client.Params;
import com.sam.webtasks.client.SequenceHandler;

public class IOtask2InitialiseTrial {
	public static void Run() {		
		IOtask2Block block = IOtask2BlockContext.getContext();

		block.reminderFlag = -1;
		block.backupReminderFlag = -1;

		IOtask2DisplayParams.circleColours[0] = ColorName.YELLOW;
		IOtask2DisplayParams.circleColours[1] = ColorName.DEEPSKYBLUE;
		IOtask2DisplayParams.circleColours[2] = ColorName.VIOLET;
		IOtask2DisplayParams.circleColours[3] = ColorName.CORAL;

		if (block.variablePoints) {
			for (int i = 1; i < 4; i++) {
				if (block.pointValues[i] == 0) {
					// set target side to black if it has no points associated with it
					IOtask2DisplayParams.circleColours[i] = ColorName.BLACK;
				}
			}
		}
		
		// set up surprise tests if specified trial-by-trial
		if (block.surpriseTests.size() > 0) {
			block.surpriseTest = block.surpriseTests.get(block.currentTrial);
		}

		// set up target directions
		ArrayList<Integer> targetDirections = new ArrayList<Integer>();
		ArrayList<Integer> possibleTargetPositions = new ArrayList<Integer>();

		boolean redosequence; // we can set this to true if the sequence needs to be redone

		int randomisationCounter = 0;
		
		do {
			randomisationCounter++;
			
			redosequence=false;

			while (targetDirections.size() < block.nTargets) {
				if (block.variablePoints) {
					// if we have variable points, only set targets for sides of the square
					// with >0 points associated
					for (int i = 1; i < 4; i++) {
						if (block.pointValues[i] > 0) {
							targetDirections.add(i);
						}
					}
				} else {
					targetDirections.add(1);
					targetDirections.add(2);
					targetDirections.add(3);
				}
			}

			// shuffle target directions
			for (int i = 0; i < targetDirections.size(); i++) {
				Collections.swap(targetDirections, i, Random.nextInt(targetDirections.size()));
			}

			// set up where in the sequence of circles the targets appear. We try to
			// distribute them as evenly as possible

			for (int i = block.nCircles; i < block.totalCircles; i++) { // start at block.nCircles because none of the
																		// initial circles on screen can be a target
				possibleTargetPositions.add(i);
			}

			int binSize = possibleTargetPositions.size() / block.nTargets;
			int remainingItems = possibleTargetPositions.size() % block.nTargets;

			ArrayList<Integer> binSizes = new ArrayList<Integer>();

			for (int i = 0; i < remainingItems; i++) {
				binSizes.add(binSize + 1); // add a bin of minimum size + 1 for each of the remaining items
			}

			for (int i = 0; i < block.nTargets - remainingItems; i++) {
				binSizes.add(binSize); // now add the standard bin size for the other items
			}

			// now shuffle the binSizes
			for (int i = 0; i < binSizes.size(); i++) {
				Collections.swap(binSizes, i, Random.nextInt(binSizes.size()));
			}

			// put actual target positions in this variable
			ArrayList<Integer> targetPositions = new ArrayList<Integer>();

			// set up binpositions variable, collecting all positions within a single bin
			ArrayList<Integer> binPositions = new ArrayList<Integer>();

			// now loop over the targets and pick middle of corresponding bin
			for (int i = 0; i < block.nTargets; i++) {
				for (int ii = 0; ii < binSizes.get(i); ii++) {
					binPositions.add(possibleTargetPositions.get(0));
					possibleTargetPositions.remove(0);
				}

				// get middle item from binPositions
				int middle = binPositions.size() / 2;

				if ((binPositions.size() % 2) == 0) { // if it's even randomly subtract 1 half the time
					middle -= Random.nextInt(2);
				}

				targetPositions.add(binPositions.get(middle));

				// now empty binPositions variable
				binPositions.clear();
			}
			
			// now assign targets

			// first set default side to zero
			for (int i = 0; i < block.totalCircles; i++) {
				block.targetSide[i] = 0;
			}

			// now add targets
			for (int i = 0; i < block.nTargets; i++) {
				block.targetSide[targetPositions.get(0)] = targetDirections.get(0);

				targetPositions.remove(0);
				targetDirections.remove(0);
			}
			
			if (block.surpriseTest < 999) {
				//we're running a surprise test, so count the number of targets that should be memorised at the point of the test
	
				int[] nTargets = new int[4];
				nTargets[0]=0;
				nTargets[1]=0;
				nTargets[2]=0;
				nTargets[3]=0;
				
				for (int c = block.surpriseTest; c < (block.surpriseTest + block.nCircles - 1); c++) {
					nTargets[block.targetSide[c]]++;
				}
				
				for (int side = 0; side < 4; side++) {
					block.surpriseDrags[side]=nTargets[side];
				}
 					
				//now work out the expected number of targets

				double targetProb = (double) block.nTargets / (double) (block.totalCircles - block.nCircles);
				int expectedTargets = (int) (targetProb * (double) block.nCircles);
				
				//re-do the sequence if its the wrong number of targets
				if ((nTargets[1]+nTargets[2])!=expectedTargets) {
					redosequence=true;
					
					//set up a new position for the surprise test, in case that's the problem
					block.surpriseTest = Params.nCircles + Random.nextInt(Params.totalCircles - Params.nCircles);
				}
				
				//re-do the sequence if the target directions are not matched in number
				if (nTargets[1] != nTargets[2]) {
					redosequence=true;
					
					//set up a new position for the surprise test, in case that's the problem
					block.surpriseTest = Params.nCircles + Random.nextInt(Params.totalCircles - Params.nCircles);
				}
				
				if (randomisationCounter>999999) {
					redosequence=false;
				}
			}
		} while (redosequence);

		// save the block context
		IOtask2BlockContext.setContext(block);

		if (randomisationCounter>999999) {
			Window.alert("Randomisation sequence for the post-block test could not be established. nTargets = " + block.surpriseDrags + ", surprise = " + block.surpriseTest + ", trial = " + block.currentTrial);
		} else {
			SequenceHandler.Next();
		}
	}

}
