package com.shpp.p2p.cs.akoskovtsev.assignment1;

import com.shpp.karel.KarelTheRobot;


/*
 * Assignment 1 — Pick up a newspaper
 * A program in which Karel leaves the house, picks up a newspaper, and returns.
 *
 * Prerequisites: Karel starts in the northwest corner of his house.
 *
 * Result: Karel picks up the newspaper from the doorstep and returns to the starting point.
 */
public class Assignment1Part1 extends KarelTheRobot {
    @Override
    public void run() throws Exception {
        // move to outside the house and find a newspaper
        goOutsideTheHouseToTheNewspaper();

        // pick a newspaper and turn around
        pickBeeperAndTurnAround();

        // return inside the house to the starting point
        goInsideTheHouse();
    }


    /*
     * Takes Karel out of the house to the newspaper stand
     *
     * Prerequisites: Karel is looking at the east side of the house with the exit,
     * there may be walls of the house in front of him.
     *
     * Result: Karel goes around the walls,
     * leaves the house and stands on the square with the newspaper.
     */
    private void goOutsideTheHouseToTheNewspaper() throws Exception {
        // Until Karel reaches the newspaper, he will go east.
        while (facingEast() && noBeepersPresent()) {
            // if there is no barrier - Karel moves forward.
            if (frontIsClear()) {
                move();
            } else {  // if there is barrier - Karel takes a step to the right.
                stepToTheRight();
            }
        }
    }


    /*
     * Karel picks up the newspaper and turns towards the house.
     *
     * Prerequisites: Karel is standing on a square with a newspaper.
     *
     * Result: Karel holds the newspaper in his hands and looks towards the house.
     */
    private void pickBeeperAndTurnAround() throws Exception {
        pickBeeper();
        turnAround();
    }


    /*
     * Takes Karel into the house and returns to the starting point
     *
     * Prerequisites: Karel is standing in front of the entrance to the house,
     * looking towards the house(to the west).
     *
     * Result: Karel stands at the starting point facing east.
     */
    private void goInsideTheHouse() throws Exception {
        // while Karel facing to the west - Karel moves forward.
        while (facingWest()) {
            // if there is no barrier - Karel moves forward.
            if (frontIsClear()) {
                move();
            } else { // if there is barrier - Karel takes a step to the right.
                stepToTheRight();
            }
            // if Karel stands at the start point(in the northwest corner of his house)
            // he turns around and program is stoped
            if (frontIsBlocked() && rightIsBlocked()) {
                turnAround();     // Karel turns around as he was at the beginning
            }
        }
    }


    /*
     * Karel takes a step to the right, if free.
     *
     * Prerequisites: any position of Karel.
     *
     * Result: Karel stands one square to the right, facing the same direction as before.
     */
    private void stepToTheRight() throws Exception {

        turnRight();
        // if there is no barrier - Karel moves forward.
        if (frontIsClear()) {
            move();
        }
        turnLeft();
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
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }
}
