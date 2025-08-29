package com.shpp.p2p.cs.akoskovtsev.assignment2;


import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.util.ArrayList;

/**
 * Program draws the flag of Romania and writes its name at the bottom
 */
public class Assignment2Part4 extends WindowProgram {

    /*
     * Constants controlling the *approximately* size of the application window.
     */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 700;

    /*
     * The flag is a rectangle with this height and width
     */
    public static final double HEIGHT_OF_FLAG = 400;
    public static final double WIDTH_OF_FLAG = 600;

    /*
     * The flag has the specified colours
     */
    private static final Color CARDINAL_GREEN = new Color(20, 181, 58);
    private static final Color CARDINAL_YELLOW = new Color(252, 209, 22);
    private static final Color CARDINAL_RED = new Color(206, 17, 38);

    /*
     * Draws the flag of Romania and writes its name at the bottom
     */
    @Override
    public void run() {
        drawFlagOfRomania();
        printFlagName("Flag of Romania");
    }

    /*
     * Draws the flag of Romania
     */
    private void drawFlagOfRomania() {
        // position of the x and y coordinates of the flag to be placed in the middle position of the window
        double xPosition = (getWidth() - WIDTH_OF_FLAG) / 2;
        double yPosition = (getHeight() - HEIGHT_OF_FLAG) / 2;
        // width of one strip of the flag
        double stripWidth = WIDTH_OF_FLAG/3;

        //draws strips of the flag
        drawVerticalStrip(xPosition, yPosition, stripWidth, HEIGHT_OF_FLAG, CARDINAL_GREEN);
        drawVerticalStrip(xPosition + stripWidth, yPosition, stripWidth, HEIGHT_OF_FLAG, CARDINAL_YELLOW);
        drawVerticalStrip(xPosition + stripWidth * 2, yPosition, stripWidth, HEIGHT_OF_FLAG, CARDINAL_RED);
    }

    /*
     * Draws one strip of the flag
     * @param x The x coordinate of the upper-left corner of the flag strip.
     * @param y The y coordinate of the upper-left corner of the flag strip.
     * @param width The width of the flag strip.
     * @param height The height of the flag strip.
     * @param color The color of the flag strip.
     */
    private void drawVerticalStrip(double x, double y, double width, double height, Color color) {
        GRect gRect = new GRect(x, y, width, height);
        gRect.setFilled(true);
        gRect.setFillColor(color);
        gRect.setColor(color);
        add(gRect);
    }
    /*
     * Writes a name of the flag at the right-bottom of the window.
     * @param flagName The name of the flag.
     */
    private void printFlagName(String flagName) {
        GLabel label = new GLabel(flagName);
        label.setFont("Verdana-20");
        label.setLocation(getWidth()- label.getWidth(), getHeight() - label.getDescent());
        add(label);
    }
}