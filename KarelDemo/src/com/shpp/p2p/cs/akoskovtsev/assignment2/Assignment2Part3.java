package com.shpp.p2p.cs.akoskovtsev.assignment2;


import acm.graphics.GOval;

import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
/**
 * Draws a pair of pawprint
 */
public class Assignment2Part3 extends WindowProgram {
    /* Constants controlling the relative positions of the
     * three toes to the upper-left corner of the pawprint.
     */
    private static final double FIRST_TOE_OFFSET_X = 0;
    private static final double FIRST_TOE_OFFSET_Y = 20;
    private static final double SECOND_TOE_OFFSET_X = 30;
    private static final double SECOND_TOE_OFFSET_Y = 0;
    private static final double THIRD_TOE_OFFSET_X = 60;
    private static final double THIRD_TOE_OFFSET_Y = 20;

    /* The position of the heel relative to the upper-left
     * corner of the pawprint.
     */
    private static final double HEEL_OFFSET_X = 20;
    private static final double HEEL_OFFSET_Y = 40;

    /* Each toe is an oval with this width and height. */
    private static final double TOE_WIDTH = 20;
    private static final double TOE_HEIGHT = 30;

    /* The heel is an oval with this width and height. */
    private static final double HEEL_WIDTH = 40;
    private static final double HEEL_HEIGHT = 60;

    /*
     * Constants controlling the *approximately* size of the application window.
     */
    public static final int APPLICATION_WIDTH = 370;
    public static final int APPLICATION_HEIGHT = 320;

    /*
     * Draws two pawprints in location by parameters.
     */
    @Override
    public void run() {
        drawPawprint(20, 20);
        drawPawprint(180, 70);
    }

    /*
     * Draws a pawprint. The parameters specify the upper-left corner of the
     * bounding box containing that pawprint.
     * @param x The x coordinate of the upper-left corner of the bounding box for the pawprint.
     * @param y The y coordinate of the upper-left corner of the bounding box for the pawprint.
     */
    private void drawPawprint(double x, double y) {
        drawToe(FIRST_TOE_OFFSET_X + x, FIRST_TOE_OFFSET_Y + y);
        drawToe(SECOND_TOE_OFFSET_X + x, SECOND_TOE_OFFSET_Y + y);
        drawToe(THIRD_TOE_OFFSET_X + x, THIRD_TOE_OFFSET_Y + y);
        drawHeel(HEEL_OFFSET_X + x, HEEL_OFFSET_Y + y);
    }

    /*
     * Draws a heel of pawprint. The parameters specify the upper-left corner of the
     * bounding box containing that heel.
     * @param x The x coordinate of the upper-left corner of the bounding box for the heel.
     * @param y The y coordinate of the upper-left corner of the bounding box for the heel.
     */
    private void drawHeel(double x, double y) {
        GOval heel = new GOval(x, y, HEEL_WIDTH, HEEL_HEIGHT);
        heel.setFilled(true);
        heel.setFillColor(Color.BLACK);
        heel.setColor(Color.BLACK);
        add(heel);
    }
    /*
     * Draws a toe of pawprint. The parameters specify the upper-left corner of the
     * bounding box containing that toe.
     * @param x The x coordinate of the upper-left corner of the bounding box for the toe.
     * @param y The y coordinate of the upper-left corner of the bounding box for the toe.
     */
    private void drawToe(double x, double y) {
        GOval toe = new GOval(x, y, TOE_WIDTH, TOE_HEIGHT);
        toe.setFilled(true);
        toe.setFillColor(Color.BLACK);
        toe.setColor(Color.BLACK);
        add(toe);
    }

}