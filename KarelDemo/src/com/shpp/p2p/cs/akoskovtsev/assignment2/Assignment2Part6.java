package com.shpp.p2p.cs.akoskovtsev.assignment2;


import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
/**
 * Program draws a green Caterpillar
 */
public class Assignment2Part6 extends WindowProgram {

    //Constants controlling the *approximately* size of the application window.
    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 1000;

    // The diameter of one caterpillar's part
    public static final double CATERPILLARS_THICKNESS = 100;
    // The numbers of parts of the  caterpillar
    public static final int CATERPILLARS_LENGTH = 7;
    // The factor of shifting parts of the caterpillar.
    public static final double X_SHIFT_FACTOR = 1.66;
    public static final double Y_SHIFT_FACTOR = 2.5;

    /*
     * Draws a green Caterpillar
     */
    @Override
    public void run() {
        // The distance by which the next caterpillar part is shifted horizontally.
        double segmentXOffset = CATERPILLARS_THICKNESS / X_SHIFT_FACTOR;
        // The distance by which the next caterpillar part is shifted vertically.
        double segmentYOffset =  CATERPILLARS_THICKNESS / Y_SHIFT_FACTOR;
        // First x position for edge case position of first caterpillar`s circle.
        double xPosition = 0 - segmentXOffset;
        for (int i = 0; i < CATERPILLARS_LENGTH; i++) {
            // Draws the lower part of the caterpillar if it is odd.
            if(i % 2 == 0) {
                drawCircle(xPosition += segmentXOffset, segmentYOffset, CATERPILLARS_THICKNESS, Color.GREEN, Color.RED);
            } else // Draws the upper part of the caterpillar if it is even.
            {
                drawCircle(xPosition += segmentXOffset, 0, CATERPILLARS_THICKNESS, Color.GREEN,Color.RED);
            }
        }
    }

    /*
     * Draws a circle.
     * @param x The x coordinate of the upper-left corner of the bounding box containing the circle.
     * @param y The y coordinate of the upper-left corner of the bounding box containing the circle.
     */
     private void drawCircle(double x, double y, double radius, Color fillColour, Color borderColour) {
        GOval gOval = new GOval(x, y, radius, radius);
        gOval.setFilled(true);
        gOval.setFillColor(fillColour);
        gOval.setColor(borderColour);
        add(gOval);
    }
}