package com.shpp.p2p.cs.akoskovtsev.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
 * Karel finds a middle of southern line and puts a one beeper there.
 *
 * Prerequisites: Karel is in the southwest corner and looks east.
 *
 * Result: a one beeper in the middle of line
 *
 */
public class Assignment1Part3 extends KarelTheRobot {
    @Override
    public void run() throws Exception {
        //Fills the entire southern line with beepers
        putBeepersOnTheSouthernLine();
        // If there is a one cell the program will end.
        // If not Karel goes to find the middle of the line.
        if (frontIsClear()) {
            findTheMiddle();
        }
    }


    /*
     * Finds the middle of the line on which Karel is located
     * and puts a beeper there
     *
     * Prerequisites: Karel stands at the edge of a line filled with beepers.
     *
     * Result: a one beeper in the middle of line
     *
     */
    private void findTheMiddle() throws Exception {
        // while Karel looks on the line of beepers, he finds a middle of this line
        while (facingEast() || facingWest()) {
            //Checks if there is a pair of beepers and remove one of them.
            checkPairBeepersAndPickOne();
            //go to the end of beepers line
            goToTheEndOfBeepersInLine();
        }
    }

    /*
     * Karel goes to the end of the beeper line,
     * if there is a beeper, he removes it and steps on the near beeper.
     *
     * Prerequisites: Karel stands on the beeper and looks on the beepers line
     *
     * Result: Karel moves to the other side of the beeper line
     * if there is a beeper, he removes it and steps on the near beeper,
     * Karel looks at the beeper line.
     *
     */
    private void goToTheEndOfBeepersInLine() throws Exception {
        // while there is a beepers, Karel moves forward, if it possible
        // If is not Karel removes beeper if there is a pair of beepers
        while (beepersPresent()) {
            if (frontIsClear()) {
                move();
            } else {
                // Karel stands near the border, turned around and
                // removes beeper if there is a pair of beepers
                // than goes back on the cell without the beepers
                turnAround();
                checkPairBeepersAndPickOne();
                turnAround();
                move();
            }
        }
        //Karel moves to first beeper on the lane of beepers
        turnAround();
        makePossibleStep();
    }

    /*
     * Checks if there is a pair of beepers and remove one of them.
     *
     * Prerequisites: Karel looks on the line of beepers
     *
     * Result: remove one beeper from the pair if it's possible,
     * if there is one beeper left - turns Karel to the south.
     */
    private void checkPairBeepersAndPickOne() throws Exception {
        makePossibleStep();
        //if Karel stands on the beeper, he makes one step back
        //and pick beeper from there, then turns to the starting point
        if (beepersPresent()) {
            turnAround();
            move();
            pickBeeper();
            turnAround();
            move();
            //if Karel not standing on the beeper he turns to the south.
        } else {
            watchToSouth();
        }
    }

    /*
     * Fills the entire southern line with beepers
     *
     * Prerequisites: Karel is in the southwest corner and looks east.
     *
     * Result: Karel is in the southeast corner and looks west,
     * the entire southern line is filled with beepers
     */
    private void putBeepersOnTheSouthernLine() throws Exception {
        //puts beepers, to the obstacle
        while (facingEast()) {
            putBeeper();
            //if there is an obstacle - Karel turns around
            if (frontIsClear()) {
                move();
            } else {
                turnAround();
            }
        }
    }


    /*
     * Karel makes a step only if it`s possible.
     *
     * Prerequisites: any position of Karel.
     *
     * Result: Karel takes a one step forward.
     */
    private void makePossibleStep() throws Exception {
        if (frontIsClear()) {
            move();
        }
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
}
