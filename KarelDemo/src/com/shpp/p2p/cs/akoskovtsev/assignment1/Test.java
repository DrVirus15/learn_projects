package com.shpp.p2p.cs.akoskovtsev.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Test extends KarelTheRobot {
    @Override
    public void run() throws Exception {
        __shutDown();
        //putBeepersOnTheGround();
        __shutDown();
        findTheMiddle();

    }



    private void findTheMiddle() throws Exception {
        while (beepersPresent()) {
            pickLastBeeperFromEastSide();
            pickLastBeeperFromWestSide();
        }
    }


    private void pickLastBeeperFromEastSide() throws Exception {
        checkDoubleBeepers();
        while (facingWest()) {
            chackBeepersOrEndOfLine();
        }
    }

    private void pickLastBeeperFromWestSide() throws Exception {
        checkDoubleBeepers();
        while (facingEast()) {
            chackBeepersOrEndOfLine();
        }
    }

    private void chackBeepersOrEndOfLine() throws Exception {
        if (beepersPresent()) {
            if (frontIsClear()) {
                move();
            } else {
                turnAround();
            }
        } else {
            turnAround();
            move();
        }
    }


    private void checkDoubleBeepers() throws Exception {
        if (beepersPresent()) {
            move();
            if (beepersPresent()) {
                turnAround();
                move();
                pickBeeper();
                turnAround();
                move();
            } else {
                turnToNorth();
            }
        }
    }

    private void putBeepersOnTheGround() throws Exception {
        while (facingEast()) {
            putBeeper();
            if (frontIsClear()) {
                move();
            } else {
                turnAround();
            }
        }
    }

    /*
     * Turns Karel to the North.
     *
     * Prerequisites: any position of Karel.
     *
     * Result: Karel looks north.
     */
    private void turnToNorth() throws Exception {
        while (notFacingNorth()) {
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