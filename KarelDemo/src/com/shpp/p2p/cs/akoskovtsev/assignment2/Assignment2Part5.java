package com.shpp.p2p.cs.akoskovtsev.assignment2;


import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
/**
 * Program draws an optical illusion with black boxes.
 */
public class Assignment2Part5 extends WindowProgram {


    //Constants controlling the *approximately* size of the application window.
    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 500;

    //The number of rows and columns in the grid, respectively.
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    //The width and height of each box.
    private static final double BOX_SIZE = 40;

    //The horizontal and vertical spacing between the boxes.
    private static final double BOX_SPACING = 10;

    /*
     * Draws an optical illusion with black boxes.
     */
    @Override
    public void run() {
        // The size of one black box and one spacing between boxes.
        double onePartIllusion = BOX_SIZE+BOX_SPACING;
        // Vertical position of the optical illusion in the middle of the window.
        double yPosition = (getHeight() - (NUM_ROWS * onePartIllusion - BOX_SPACING)) / 2;

        for (int i = 0; i < NUM_ROWS; i++) {
            // Horizontal position of the optical illusion in the middle of the window.
            double xPosition = (getWidth() - (NUM_COLS * onePartIllusion - BOX_SPACING)) / 2;

            for (int j = 0; j < NUM_COLS; j++) {
                drawOneBox(xPosition, yPosition);
                // x position coordinate is moved to the left by the size of one box
                // and one spacing
                xPosition += (onePartIllusion);
            }
            // y position coordinate is moved to the down by the size of one box
            // and one spacing
            yPosition += (onePartIllusion);
        }
    }

    /*
     * Draws a black box
     * @param x The x coordinate of the upper-left corner of the box.
     * @param y The y coordinate of the upper-left corner of the box.
     */
    private void drawOneBox(double x, double y) {
        GRect gRect = new GRect(x, y, BOX_SIZE, BOX_SIZE);
        gRect.setFilled(true);
        gRect.setFillColor(Color.BLACK);
        gRect.setColor(Color.BLACK);
        add(gRect);
    }
}