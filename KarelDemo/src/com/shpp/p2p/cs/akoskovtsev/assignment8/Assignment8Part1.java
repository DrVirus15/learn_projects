package com.shpp.p2p.cs.akoskovtsev.assignment8;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This program allows the user to create balls by clicking and holding the mouse button.
 * The longer the button is held, the larger and darker the ball becomes.
 * When released, the ball falls under the influence of gravity,
 * bouncing off the floor and ceiling with elasticity. If a ball is clicked, it reverses
 * its gravity direction.
 */
public class Assignment8Part1 extends WindowProgram {
    /*
     * Constants controlling the size of the application window.
     */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 700;
    private final double SHADE_TIME = 1000; // Time in milliseconds to reach the darkest shade
    private static final double GROW_TIME = 10.0; // Time in milliseconds to grow the ball by 1 pixel in radius
    private static final int COLOR_BRIGHTNESS = 255; // Maximum brightness for the ball color
    private static final double GRAVITY = 1.5; // Gravitational acceleration
    private static final double ELASTICITY = 0.75; // Gravitational acceleration
    private static final double ELASTICITY_DECAY = 0.05; // Gravitational acceleration
    private static final int DELAY = 50;
    private final GPoint mousePoint = new GPoint(-SHADE_TIME, -SHADE_TIME);
    private long timePressed;
    private final ArrayList<GOval> balls = new ArrayList<>();

    /**
     * Starts the program, adding mouse listeners and initiating the ball falling process.
     */
    @Override
    public void run() {
        addMouseListeners();
        fallBalls();
    }

    // Elasticity coefficient for bounces

    /**
     * Handles the falling and bouncing of balls under gravity.
     * If a ball is clicked, it reverses its gravity direction.
     */
    private void fallBalls() {
        // Lists of vertical velocities for each ball
        ArrayList<Double> dy = new ArrayList<>();

        ArrayList<Double> elasticityForEachBall = new ArrayList<>();
        // List to track gravity direction for each ball (1 for down, -1 for up)
        ArrayList<Integer> reversGravity = new ArrayList<>();
        while (true) {

            for (int i = balls.size() - 1; i >= 0; i--) {
                // Ensure dy and reversGravity lists are in sync with balls list
                if (balls.size() > dy.size()) {
                    dy.add(0.0);
                    reversGravity.add(1);
                    elasticityForEachBall.add(ELASTICITY);
                }
                // Check if the ball is clicked to reverse gravity
                if (balls.get(i).getBounds().contains(mousePoint)) {
                    mousePoint.setLocation(-SHADE_TIME, -SHADE_TIME);
                    reversGravity.set(i, reversGravity.get(i) * -1);
                    elasticityForEachBall.set(i, ELASTICITY);
                }
                // Move the ball and update its vertical velocity
                balls.get(i).move(0, dy.get(i));
                dy.set(i, dy.get(i) + GRAVITY * reversGravity.get(i));
                // Handle bouncing off the floor
                if (isBallBelowFloor(balls.get(i)) && dy.get(i) > 0) {
                    dy.set(i, dy.get(i) * -elasticityForEachBall.get(i));
                    if(elasticityForEachBall.get(i) > ELASTICITY_DECAY) {
                        elasticityForEachBall.set(i, elasticityForEachBall.get(i) - ELASTICITY_DECAY);
                    } else {
                        elasticityForEachBall.set(i, 0.0);
                    }
                }
                // Handle bouncing off the top
                if (isBallUnderTop(balls.get(i)) && dy.get(i) < 0) {
                    dy.set(i, dy.get(i) * -elasticityForEachBall.get(i));
                    if(elasticityForEachBall.get(i) > ELASTICITY_DECAY) {
                        elasticityForEachBall.set(i, elasticityForEachBall.get(i) - ELASTICITY_DECAY);
                    } else {
                        elasticityForEachBall.set(i, 0.0);
                    }
                }
            }
            pause(DELAY);
        }
    }

    /**
     * Checks if the ball has fallen below the floor.
     *
     * @param ball - The ball to check.
     * @return - true if the ball is below the floor, false otherwise.
     */
    private boolean isBallBelowFloor(GOval ball) {
        return ball.getY() + ball.getHeight() >= getHeight();
    }

    /**
     * Checks if the ball has gone above the top of the window.
     *
     * @param ball - The ball to check.
     * @return - true if the ball is above the top, false otherwise.
     */
    private boolean isBallUnderTop(GOval ball) {
        return ball.getY() <= 0;
    }

    /**
     * Creates a ball at the specified coordinates with size and color based on the duration of the mouse press.
     *
     * @param x - The x-coordinate where the ball is created.
     * @param y - The y-coordinate where the ball is created.
     */
    private void makeBall(double x, double y) {

        long timeFinish = System.currentTimeMillis();
        long timeDuration = timeFinish - timePressed;
        // Calculate the shade of gray based on the time the mouse was pressed
        double ratio = Math.min(1, (double) timeDuration / SHADE_TIME);
        System.out.println("timy Duration: " + timeDuration);
        int grayColor = (int) (COLOR_BRIGHTNESS * (1.0 - ratio));
        // Ensure the gray color is within valid bounds
        grayColor = Math.max(0, Math.min(COLOR_BRIGHTNESS, grayColor));
        Color shadeOfGray = new Color(grayColor, grayColor, grayColor);
        // Calculate the radius of the ball based on the time the mouse was pressed
        double radius = timeDuration / GROW_TIME;
        // Create and add the ball to the canvas and the list
        GOval ball = drawOval(x - radius, y - radius, radius, shadeOfGray);
        add(ball);
        balls.add(ball);
    }

    /**
     * Draws an oval (ball) with the specified parameters.
     *
     * @param x     - x coordinate of the upper-left corner of the bounding box containing the oval.
     * @param y     - y coordinate of the upper-left corner of the bounding box containing the oval.
     * @param size  - radius of the oval.
     * @param color - fill color of the oval.
     * @return - the GOval object representing the oval.
     */
    private GOval drawOval(double x, double y, double size, Color color) {
        GOval gOval = new GOval(x, y, size * 2.0, size * 2.0);
        gOval.setFilled(true);
        gOval.setFillColor(color);
        gOval.setColor(color);
        return gOval;
    }

    /**
     * Handles mouse press events to record the time when the mouse button is pressed.
     *
     * @param e -  the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        timePressed = System.currentTimeMillis();
    }

    /**
     * Handles mouse release events to create a ball or set the mouse point for gravity reversal.
     *
     * @param e - the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Check if the mouse was released over an existing ball
        if (getElementAt(e.getX(), e.getY()) instanceof GOval) {
            // Set the mouse point to trigger gravity reversal
            mousePoint.setLocation(e.getX(), e.getY());
        } else {
            // Create a new ball at the release point
            makeBall(e.getX(), e.getY());
        }
    }
}
