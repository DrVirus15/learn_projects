package com.shpp.p2p.cs.akoskovtsev.assignment3;


import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.security.GuardedObject;

/**
 * Program draws an air balloon, and moves it up.
 */
public class Assignment3Part6 extends WindowProgram {

    /*
     * Time parameters.
     */
    public static double ANIMATION_TIME = 5000;
    //    public static long CURRENT_TIME = System.currentTimeMillis();
    /*
     * Constants controlling the *approximately* size of the application window.
     */
    public static final int APPLICATION_WIDTH = 1500;
    public static final int APPLICATION_HEIGHT = 1000;
    /*
     * Balloon parameters.
     */
    public static final double BALLOON_SIZE = 100;
    public static int BALLOON_SLINGS = 10;
    public static double SLINGS_LENGTH = 1.5;
    public static double HORIZON_SIZE = 100;
    public static final Color BROWN = new Color(175, 122, 17);
    public static final Color GREEN = new Color(165, 237, 85);
    public static final Color BLUE = new Color(220, 254, 246);
    /*
     * Motion parameters.
     */
    public static double MOVE_POINT = 4;
    public static final int PAUSE = 30;

    public long time = System.currentTimeMillis();

    /**
     * Draws an air balloon, inflates it, and moves it up with the basket.
     */
    @Override
    public void run() {

        double balloonHeight = BALLOON_SIZE;
        double balloonWidth = BALLOON_SIZE / 2.0;
        double basketSize = BALLOON_SIZE / 2.0;
        // Draws a background.
        setBackground(BLUE);
        // Draws the horizon.
        GRect horizon = drawRectangle(0,
                getHeight() - HORIZON_SIZE,
                getWidth(),
                HORIZON_SIZE, Color.GREEN);
        // Draws a half-sized balloon.
        GOval balloon = setCircle((getWidth() - balloonWidth) / 2.0,
                getHeight() - BALLOON_SIZE * 2.0 - HORIZON_SIZE,
                balloonWidth,
                balloonHeight);
        // Draws a basket.
        GRect basket = drawRectangle((getWidth() - balloonWidth) / 2.0,
                balloon.getY() + BALLOON_SIZE * SLINGS_LENGTH,
                basketSize,
                basketSize, BROWN);

        // Draws a balloon slings.
        GLine[] balloonSlings = new GLine[BALLOON_SLINGS];
        for (int i = 0; i < balloonSlings.length; i++) {
            balloonSlings[i] = setLine(
                    balloon.getX(),
                    balloon.getY() + balloon.getHeight() / 2.0,
                    basket.getX() + (basket.getWidth() * ((double) i / (balloonSlings.length - 1))),
                    basket.getY());
        }

//        balloon.sendToFront();

//        time = System.currentTimeMillis();

        // The balloon inflates up.
        inflateBalloon(balloon, balloonSlings);
        // The balloon flyes up.
        makeBalloonFly(balloon, balloonSlings, basket);

        println(System.currentTimeMillis() - time);
    }

    /**
     * Inflates the balloon to full size.
     *
     * @param balloon Object GOval, that visuals the balloon.It will change the size from half to full.
     * @param slings  Object GLine array, that visuals the slings. Their position changes with the borders of balloon.
     */
    private void inflateBalloon(GOval balloon, GLine[] slings) {
        double balloonWidth = balloon.getWidth();
        double balloonXPoint = balloon.getX();

        // Resizes the balloon to full size.
        while (balloonWidth < balloon.getHeight()) {
            // Moves the position of the slings with the balloon borders.
            for (int i = 0; i < slings.length; i++) {
                slings[i].setStartPoint(balloon.getX() - i + (balloonWidth * ((double) i / (slings.length - 1))),
                        balloon.getY() + balloon.getHeight() / 2.0);
            }

            balloon.setSize(balloonWidth, balloon.getHeight());
            balloon.setLocation(balloonXPoint, balloon.getY());
            // Moves the balloon to the left to stay in the middle.
            balloonXPoint -= MOVE_POINT / 2.0;
            // Expands the balloon to the right.
            balloonWidth += MOVE_POINT;
            pause(PAUSE);
            // Checks the current animation time if it takes more than 5 seconds the program will stop.
            if (System.currentTimeMillis() > time + ANIMATION_TIME) {
                break;
            }
        }
    }

    /**
     * Raises up the balloon with the basket.
     *
     * @param balloon Object GOval, that visuals the balloon. It will raises up with the slings and basket.
     * @param slings  Object GLine array, that visuals the slings. They will raises up with the balloon and basket.
     * @param basket  Object GRect, that visuals the basket of balloon. It will raises up with the slings and balloon.
     */
    private void makeBalloonFly(GOval balloon, GLine[] slings, GRect basket) {

        // Moves up until time runs out.
        while (time + ANIMATION_TIME > System.currentTimeMillis()) {
            // Changes Y position of those elements.
            balloon.move(0, -(MOVE_POINT));
            for (GLine sling : slings) {
                sling.move(0, -(MOVE_POINT));
            }
            basket.move(0, -(MOVE_POINT));

            pause(PAUSE);
            // Checks the current animation time if it takes more than 5 seconds the program will stop.
            if (System.currentTimeMillis() > time + ANIMATION_TIME) {
                break;
            }
        }
    }

    /**
     * Draw a black line with parameters.
     *
     * @param x1 - x coordinate of line start point.
     * @param y1 - y coordinate of line start point.
     * @param x2 - x coordinate of line finish point.
     * @param y2 - y coordinate of line finish point.
     * @return - return the GLine object.
     */
    private GLine setLine(double x1, double y1, double x2, double y2) {
        GLine gLine = new GLine(x1, y1, x2, y2);
        gLine.setColor(Color.BLACK);
        add(gLine);
        return gLine;
    }

    /**
     * Draws a circle. Parameters determine placement from upper-left corner and oval's size
     *
     * @param x      - х coordinate of the upper-left corner of the bounding box containing the circle.
     * @param y      - y coordinate of the upper-left corner of the bounding box containing the circle.
     * @param width  - width of circle.
     * @param height - height of circle.
     * @return - return the GOval object.
     */
    private GOval setCircle(double x, double y, double width, double height) {
        GOval oval = new GOval(x, y, width, height);
        oval.setFilled(true);
        oval.setFillColor(GREEN);
        oval.setColor(GREEN);
        add(oval);
        return oval;
    }

    /**
     * Draws a rectangle with parameters.
     *
     * @param x      - x coordinate of the upper-left corner of rectangle.
     * @param y      - y coordinate of the upper-left corner of rectangle.
     * @param width  - width of rectangle.
     * @param height - height of rectangle.
     * @param color  - fill color of rectangle.
     * @return - return the GRect object.
     */
    private GRect drawRectangle(double x, double y, double width, double height, Color color) {
        GRect gRect = new GRect(x, y, width, height);
        gRect.setColor(Color.BLACK);
        gRect.setFilled(true);
        gRect.setFillColor(color);
        add(gRect);
        return gRect;
    }
}