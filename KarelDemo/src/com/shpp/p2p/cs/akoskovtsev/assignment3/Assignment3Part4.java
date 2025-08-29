package com.shpp.p2p.cs.akoskovtsev.assignment3;


import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Program draws a pyramid.
 */
public class Assignment3Part4 extends WindowProgram {

    /*
     * Constants controlling the *approximately* size of the application window.
     */
    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 1000;
    // Constants of height and width of one brick.
    public static final double BRICK_HEIGHT = 70;
    public static final double BRICK_WIDTH = 95;
    // Constants number of bricks in base.
    public static final int BRICKS_IN_BASE = 4;

    /**
     * Draw a pyramid.
     */
    @Override
    public void run() {
        double yPosition = BRICK_HEIGHT;
        for (int i = BRICKS_IN_BASE; i > 0; i--) {
            // finding x position of the first brick to place the base of the pyramid in the middle.
            double xPosition = getWidth() / 2.0 - (BRICK_WIDTH * i) / 2;
            for (int j = 0; j < i; j++) {

                drawRectangle(xPosition, getHeight() - yPosition, BRICK_WIDTH, BRICK_HEIGHT);
                // shifting x position in one width of brick.
                xPosition += BRICK_WIDTH;
            }
            //  shifting y position in one height of brick.
            yPosition += BRICK_HEIGHT;
        }
    }

    /**
     * Draws a yellow rectangle with black borders.
     *
     * @param x      The x coordinate of the upper-left corner of the rectangle.
     * @param y      The y coordinate of the upper-left corner of the rectangle.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     */
    private void drawRectangle(double x, double y, double width, double height) {
        GRect gRect = new GRect(x, y, width, height);
        gRect.setColor(Color.BLACK);
        gRect.setFilled(true);
        gRect.setFillColor(Color.YELLOW);
        add(gRect);
    }
}