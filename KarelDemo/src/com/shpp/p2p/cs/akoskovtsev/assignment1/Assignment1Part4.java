package com.shpp.p2p.cs.akoskovtsev.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
 * Karel makes a chessboard in any empty space. First beeper is in west-south point.
 *
 * Prerequisites: Karel starts at west-south, looking to east,
 * maze have no walls and beepers.
 *
 * Result: First beeper there were stands Karel at start,
 * whole maze with beepers like a chessboard.
 */
public class Assignment1Part4 extends KarelTheRobot {
    @Override
    public void run() throws Exception {
        // The 1st lane starts with a west-south cell, there should be a 1st beeper.
        oddLine();
        // While Karel is not looking south, he makes chess-lanes.
        while (notFacingSouth()) {
            //Karel looks north, if there is no border he goes to next lane
            if (frontIsClear()) {
                // check before make a lane what do we need: even or odd lane.
                // if last lane starts with beeper(odd lane) Karel will make an even lane
                if (beepersPresent()) {
                    move();
                    turnRight();
                    evenLine();
                    //if there is no beeper(even lane) next lane will be odd.
                } else {
                    move();
                    turnRight();
                    oddLine();
                }
                // if Karel looks north and there is an end, he turns south and the program stops.
            } else watchToSouth();
        }
    }

    /*
     * Makes a chess-line from west to east starting from the 2nd cell (even cell)
     *
     * Prerequisites: Karel stands facing to east.
     *
     * Result: Karel stands at the eastern point. Facing east. The lane after him looks like a chess line.
     */
    private void evenLine() throws Exception {
        while (facingEast()) {

            //Karel moves forward to where he can
            if (frontIsClear()) {
                move();
                putBeeper();
                if (frontIsClear()) {
                    move();
                    //or goes back.
                } else goToNextLine();
            } else goToNextLine();
        }
    }

    /*
     * Makes a chess-line from west to east starting from the 1st cell (odd cell)
     *
     * Prerequisites: Karel stands facing to east.
     *
     * Result: Karel stands at the eastern point. Facing east. The lane after him looks like a chess line.
     */
    private void oddLine() throws Exception {
        while (facingEast()) {
            putBeeper();
            //Karel moves forward to where he can
            if (frontIsClear()) {
                move();
                if (frontIsClear()) {
                    move();
                    //or goes back.
                } else goToNextLine();
            } else goToNextLine();
        }
    }

    /*
     * Returns Karel to the beginning of the east-lane
     * (lane starts at western point and lay to the eastern point, even if it is the same points)
     *  and prepares to enter a new east-lane.
     *
     * Prerequisites: Karel looks east, he stands near border of east-lane in eastern point.
     *
     * Result: Karel stand at the same lane in western point, he looks to north.
     */
    private void goToNextLine() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
        }
        turnRight();
    }

    /*
     * Turns Karel to the South.
     *
     * Prerequisites: any position of Karel.
     *
     * Result: Karel looks south.
     */
    private void watchToSouth() throws Exception {
        while (notFacingSouth()) {
            turnLeft();
        }
    }

    /*
     * Turns Karel back.
     *
     * Prerequisites: any position of Karel.
     *
     * Result: Karel turns 180°.
     */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /*
     * Turns Karel to the right.
     *
     * Prerequisite: any Karel position
     *
     * Result: Karel turned 90° to the right
     */
    private void turnRight() throws Exception {
        turnLeft();
        turnLeft();
        turnLeft();
    }
}
