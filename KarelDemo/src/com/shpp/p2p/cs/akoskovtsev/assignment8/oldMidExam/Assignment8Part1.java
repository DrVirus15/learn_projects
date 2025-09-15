package com.shpp.p2p.cs.akoskovtsev.assignment8.oldMidExam;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Assignment8Part1 extends WindowProgram {
    private final int ROW_SQUARES = 10;
    private final int COL_SQUARES = 10;
    private final double SQUARE_SIZE = 30;
    private final Color MOUSELOOP_COLOR = Color.GREEN;
    private final Color BLUE_SQUARE = Color.BLUE;
    private final Color RED_SQUARE = Color.RED;
    private final int DELAY = 100;
    private int changeBlueWay = 1;
    private int changeRedWay = 1;
    private GPoint mousePosition;

    /**
     * Runs the program.
     * Draws a mouse loop and moves a blue square around it.
     *
     */
    @Override
    public void run() {
        drawMouseloop();
        loopSquares();
    }

    /**
     * Moves the blue square around the mouse loop.
     * And moves the red square in the opposite direction
     * If the blue square collides with the red square, they change direction.
     */
    private void loopSquares() {
        int blueSquareXStep = -1;
        int blueSquareYStep = 0;
        int redSquareXStep = -1;
        int redSquareYStep = 0;
        addMouseListeners();
        double startX = (getWidth() - SQUARE_SIZE * COL_SQUARES) / 2.0;
        double startY = (getHeight() - SQUARE_SIZE * ROW_SQUARES) / 2.0;
        // Create blue square at bottom right corner
        GRect blue = drawSquare(startX + SQUARE_SIZE * (COL_SQUARES - 1), startY + SQUARE_SIZE * (ROW_SQUARES - 1), SQUARE_SIZE, BLUE_SQUARE);
        add(blue);
        // Create red square at top left corner
        GRect red = drawSquare(startX, startY, SQUARE_SIZE, RED_SQUARE);
        add(red);

        while (true) {
            // move blue square
            blue.move(SQUARE_SIZE * blueSquareXStep * changeBlueWay, SQUARE_SIZE * blueSquareYStep * changeBlueWay);
            // check for collision with left side
            if (blue.getX() < startX) {
                blue.setLocation(startX, blue.getY());
                changeBlueWay *= -1;
                blueSquareXStep = 0;
                blueSquareYStep = 1;
            }
            // check for collision with right side
            if (blue.getX() > startX + SQUARE_SIZE * (COL_SQUARES - 1)) {
                blue.setLocation(startX + SQUARE_SIZE * (COL_SQUARES - 1), blue.getY());
                changeBlueWay *= -1;
                blueSquareXStep = 0;
                blueSquareYStep = -1;
            }
            // check for collision with bottom side
            if (blue.getY() > startY + SQUARE_SIZE * (ROW_SQUARES - 1)) {
                blue.setLocation(blue.getX(), startY + SQUARE_SIZE * (ROW_SQUARES - 1));
                changeBlueWay *= -1;
                blueSquareXStep = -1;
                blueSquareYStep = 0;
            }
            // check for collision with top side
            if (blue.getY() < startY) {
                blue.setLocation(blue.getX(), startY);
                changeBlueWay *= -1;
                blueSquareXStep = 1;
                blueSquareYStep = 0;
            }
            //check for mouse collision
            if (isMouseOverPosition((int) ((blue.getY() - startY) / SQUARE_SIZE), (int) ((blue.getX() - startX) / SQUARE_SIZE))) {
                changeBlueWay *= -1;
            }
            // move red square in opposite direction
            red.move(SQUARE_SIZE * redSquareXStep * changeRedWay, SQUARE_SIZE * redSquareYStep * changeRedWay);
            // check for collision with left side
            if (red.getX() < startX) {
                red.setLocation(startX, red.getY());
                changeRedWay *= -1;
                redSquareXStep = 0;
                redSquareYStep = -1;
            }
            // check for collision with right side
            if (red.getX() > startX + SQUARE_SIZE * (COL_SQUARES - 1)) {
                red.setLocation(startX + SQUARE_SIZE * (COL_SQUARES - 1), red.getY());
                changeRedWay *= -1;
                redSquareXStep = 0;
                redSquareYStep = 1;
            }
            // check for collision with bottom side
            if (red.getY() > startY + SQUARE_SIZE * (ROW_SQUARES - 1)) {
                red.setLocation(red.getX(), startY + SQUARE_SIZE * (ROW_SQUARES - 1));
                changeRedWay *= -1;
                redSquareXStep = 1;
                redSquareYStep = 0;
            }
            // check for collision with top side
            if (red.getY() < startY) {
                red.setLocation(red.getX(), startY);
                changeRedWay *= -1;
                redSquareXStep = -1;
                redSquareYStep = 0;
            }
            // check for collision with blue square
            if (blue.getX() == red.getX() && blue.getY() == red.getY()) {
                changeBlueWay *= -1;
                changeRedWay *= -1;
            }
            pause(DELAY);
        }
    }

    /**
     * Checks if the mouse is over the given position.
     * @param row - row of the square
     * @param col - column of the square
     * @return - true if the mouse is over the square, false otherwise
     */
    private boolean isMouseOverPosition(int row, int col) {
        double startX = (getWidth() - SQUARE_SIZE * COL_SQUARES) / 2.0;
        double startY = (getHeight() - SQUARE_SIZE * ROW_SQUARES) / 2.0;
        if (mousePosition == null) return false;

        double squareX = startX + col * SQUARE_SIZE;
        double squareY = startY + row * SQUARE_SIZE;

        if(mousePosition.getX() >= squareX &&
                mousePosition.getX() <= squareX + SQUARE_SIZE &&
                mousePosition.getY() >= squareY &&
                mousePosition.getY() <= squareY + SQUARE_SIZE) {
            return true;
        }
        return false;
    }

    /**
     * This method is called whenever the mouse is moved.
     * @param e the event to be processed
     */
    public void mouseMoved(MouseEvent e) {
        mousePosition = new GPoint(e.getX(), e.getY());
    }


    /**
     * Draws the mouse loop on the screen.
     */
    private void drawMouseloop() {
        GRect square;
        double startX = (getWidth() - SQUARE_SIZE * COL_SQUARES) / 2.0;
        double startY = (getHeight() - SQUARE_SIZE * ROW_SQUARES) / 2.0;
        for (int row = 0; row < ROW_SQUARES; row++) {
            for (int col = 0; col < COL_SQUARES; col++) {
                if (row == 0 || row == ROW_SQUARES - 1 || col == 0 || col == COL_SQUARES - 1) {
                    square = drawSquare(startX + col * SQUARE_SIZE,
                            startY + row * SQUARE_SIZE,
                            SQUARE_SIZE, MOUSELOOP_COLOR);
                    add(square);
                }
            }
        }
    }

    /**
     * Draws a square of given size and color at (x, y).
     *
     * @param x - top left x coordinate
     * @param y - top left y coordinate
     * @param size - size of the square
     * @param color - fill color of the square
     * @return - the drawn square
     */
    private GRect drawSquare(double x, double y, double size, Color color) {
        GRect gRect = new GRect(x, y, size, size);
        gRect.setFilled(true);
        gRect.setFillColor(color);
        gRect.setColor(Color.BLACK);
        return gRect;
    }
}

