package com.shpp.p2p.cs.akoskovtsev.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
 * Assignment 2 — Rows of Pebbles
 * The program lays out the missing pebbles in rows
 * regardless of how many rows there are and how long they are.
 *
 * Prerequisites: Karel starts in the southwest corner
 * and looks east, and he has a backpack full of pebbles;
 * Rows with pebbles are located every 4 rows apart from each other.
 *
 * Result: each row is filled with pebbles to the end of the row.
 * If there are more than 1 row, then rows with pebbles are located every 4 empty rows.
 */
public class Assignment1Part2 extends KarelTheRobot {
    @Override
    public void run() throws Exception {
        //while Karel is facing on east he will do he`s job(build the columns).
        while (facingEast()) {
            // Fills the column with missing stones.
            buildAColumn();

            // Brings Karel to the next column, if there is no space for it,
            // then returns Karel to the south, and stops the program.
            goToNextColumn();
        }
    }


    /*
     * Fills the row with the missing pebbles and returns to the "earth" (south side).
     *
     * Prerequisites: Karel looks east and stay on the "ground".
     *
     * Result: the row is filled with pebbles, Karel stands on the ground looking east.
     */
    private void buildAColumn() throws Exception {
        turnLeft();
        //Karel builds a column.
        putBeeperWhereItNeeded();
        goBackToGround();
    }


    /*
     * Fills the row with the missing pebbles and returns to the "ground" (south side).
     *
     * Prerequisites: Karel looks east and stay on the "ground".
     *
     * Result: the row is filled with pebbles, Karel stands on the ground looking east.
     */
    private void putBeeperWhereItNeeded() throws Exception {
        //while Karel is facing the column, he will place the missing pebbles on it.
        while (facingNorth()) {
            if (noBeepersPresent()) {
                putBeeper();
            }
            // if there is no barriers - Karel goes higher up the column.
            if (frontIsClear()) {
                move();
            } else {
                // if the column is over Karel turns around.
                turnAround();
            }
        }
    }

    /*
     * Returns Karel back to the ground.
     *
     * Prerequisites: Karel stands on top of the column facing the ground.
     *
     * Result: Karel stands on the ground facing east.
     */
    private void goBackToGround() throws Exception {
        // while there is no barrier - Karel moves forward.
        while (frontIsClear()) {
            move();
        }
        turnLeft();
    }


    /*
     * Brings Karel to the next column, if there is no space for it, then returns Karel to the south.
     *
     * Prerequisites: Karel stands on the ground, facing east.
     *
     * Result: if there are 4 free rows, Karel stops on the 4th, otherwise he turns south.
     */
    private void goToNextColumn() throws Exception {
        //Karel takes 4 steps forward.
        for (int i = 0; i < 4; i++) {
            // checks for barriers before each step.
            if (frontIsClear()) {
                move();
            } else { // if there is a barrier - Karel turns south.
                while (notFacingSouth()) {
                    turnLeft();
                }
            }
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
