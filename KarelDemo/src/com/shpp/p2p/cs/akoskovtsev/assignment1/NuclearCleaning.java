package com.shpp.p2p.cs.akoskovtsev.assignment1;

import com.shpp.karel.KarelTheRobot;

public class NuclearCleaning extends KarelTheRobot {
    @Override
    public void run() throws Exception {
        while (frontIsClear()) {
            if(beepersPresent()){
                move();
                pause();
            }else {
            checkForWorkingCells();}

        }
        checkForWorkingCells();

    }

    private void goToEndOfReactor() throws Exception {
        makePossibleStep();
        while (rightIsBlocked() && leftIsBlocked() && facingEast()){
            if(frontIsClear()){
                move();
            }
            else watchToSouth();
        }
        watchToEast();
    }



    private void cleanReactorCell() throws Exception {
        turnLeft();
        cleanOneSideOfReactor();
        cleanOneSideOfReactor();
        turnRight();
    }

    private void cleanOneSideOfReactor() throws Exception {
        makePossibleStep();
        while (beepersPresent()) {
            pickBeeper();
        }
        turnAround();
        makePossibleStep();
    }

    private void checkForWorkingCells() throws Exception {
        while (noBeepersPresent()){
            cleanReactorCell();
            goToEndOfReactor();
        }
    }

    private void watchToEast() throws Exception {
        while (notFacingEast()){
            turnLeft();
        }
    }

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

    private void makePossibleStep() throws Exception {
        if (frontIsClear()) {
            move();
        }
    }
}
