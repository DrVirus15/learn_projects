package com.shpp.p2p.cs.akoskovtsev.assignment2;


import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
/**
 * The program draws a white rectangle superimposed on four black circles
 * which are placed in four corners.
 */

public class Assignment2Part2 extends WindowProgram {

    // Constant controlling the size of the circles.
    private static final double CIRCLE_DIAMETER = 4;

    // Constants controlling the *approximately* size of the application window.

    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 1000;

    /*
     * Draws 4 circles and a white rectangle superimposed on circles.
     */
    @Override
    public void run() {

        drawCircles();
        // getting radius of circle
        double circleRadius = CIRCLE_DIAMETER/2;
        drawSquare(circleRadius, circleRadius, getWidth() - CIRCLE_DIAMETER, getHeight() - CIRCLE_DIAMETER);

    }

    /*
     * Draws each circle in each corner of window
     */
    private void drawCircles() {
        drawOneCircle(0, 0, CIRCLE_DIAMETER);
        drawOneCircle(getWidth() - CIRCLE_DIAMETER, 0, CIRCLE_DIAMETER);
        drawOneCircle(0, getHeight() - CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        drawOneCircle(getWidth() - CIRCLE_DIAMETER, getHeight() - CIRCLE_DIAMETER, CIRCLE_DIAMETER);
    }

    /*
     * Draws one circle by parameters.
     * @param x The x coordinate of the upper-left corner of the bounding box containing the circle.
     * @param y The y coordinate of the upper-left corner of the bounding box containing the circle.
     * @param diameter The diameter of the circle.
     */
    private void drawOneCircle(double x, double y, double diameter) {
        GOval oval = new GOval(x, y, diameter, diameter);
        oval.setColor(Color.BLACK);
        oval.setFilled(true);
        oval.setFillColor(Color.BLACK);
        add(oval);
    }

    /*
     * Draws rectangle by parameters.
     * @param x The x coordinate of the upper left corner of the rectangle.
     * @param y The y coordinate of the upper left corner of the rectangle.
     * @param width Rectangle width.
     * @param height Rectangle height.
     */
    private void drawSquare(double x, double y, double width, double height) {
        GRect rect = new GRect(x, y, width, height);
        rect.setColor(Color.WHITE);
        rect.setFilled(true);
        rect.setFillColor(Color.WHITE);
        add(rect);
    }
}