package com.shpp.p2p.cs.akoskovtsev.assignment8;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class mouseLoopExtra extends WindowProgram {
    private final int ROWS_NUMBER = 9;
    private final int COLS_NUMBER = 9;
    private final int SQUARE_SIZE = 30;
    private final Color FIELD_COLOR = Color.GREEN;
    private final Color BLUE_MOUSE_COLOR = Color.BLUE;
    private final Color RED_MOUSE_COLOR = Color.RED;
    private final int DELAY = 200;

    private GRect[][] field = new GRect[ROWS_NUMBER][COLS_NUMBER];
    private GPoint mousePoint = new GPoint(0, 0);

    /**
     * Draw a grid of squares and move two "mice" (represented by colored squares)
     */
    @Override
    public void run() {
        drawLoop();
        runRedBlueMice();
    }

    private void runRedBlueMice() {
        addMouseListeners();
        // Initial position of the blue mouse
        int blueRow = 0;
        int blueCol = 0;
        // Initial position of the red mouse
        int redRow = ROWS_NUMBER - 1;
        int redCol = COLS_NUMBER - 1;
        // Direction change factors
        int changeDirectionBlue = 1;
        int changeDirectionRed = -1;
        // Set initial colors for the mice
        field[blueRow][blueCol].setFillColor(BLUE_MOUSE_COLOR);
        field[redRow][redCol].setFillColor(RED_MOUSE_COLOR);

        while (true) {
            // Check for current cursor position and update blue mouse direction if necessary
            if (field[blueRow][blueCol].getBounds().contains(mousePoint)) {
                changeDirectionBlue *= -1;
            }
            // Check for current cursor position and update red mouse direction if necessary
//            if (field[redRow][redCol].getBounds().contains(mousePoint)) {
//                changeDirectionRed *= -1;
//            }
            // Check for collision between the two mice and reverse their directions if they collide
            if (blueRow == redRow && blueCol == redCol) {
                changeDirectionBlue *= -1;
                changeDirectionRed *= -1;
            }

            // Move the blue mouse
            field[blueRow][blueCol].setFillColor(FIELD_COLOR);
            // Get the result of collision with walls and update position
            int[] resultOfCollision = checkWalls(blueRow, blueCol, changeDirectionBlue);
            blueCol += resultOfCollision[0];
            blueRow += resultOfCollision[1];
            field[blueRow][blueCol].setFillColor(BLUE_MOUSE_COLOR);

            // Move the red mouse
            field[redRow][redCol].setFillColor(FIELD_COLOR);
            // Get the result of collision with walls and update position
            resultOfCollision = checkWalls(redRow, redCol, changeDirectionRed);
            redCol += resultOfCollision[0];
            redRow += resultOfCollision[1];
            field[redRow][redCol].setFillColor(RED_MOUSE_COLOR);
            pause(DELAY);
        }
    }

    /**
     *  Check for collisions with the walls and return the new direction of movement
     * @param Row - current row position
     * @param Col - current column position
     * @param changeDirection - current direction change factor
     * @return - returns an array with the new row and column direction changes
     */
    private int[] checkWalls(int Row, int Col, int changeDirection) {
        int[] result = new int[2];
        // If the mouse hits the top wall he moves right
        if (Row == 0 && Col < field[Row].length - 1) {
            result[0] = changeDirection;
            // If mouse moves in reverse direction and hits the left wall, change direction to down
            if (Col == 0 && changeDirection < 0) {
                result[0] = 0;
                result[1] = 1;
            }
        }
        // If the mouse hits the right wall he moves down
        if (Col == field[Row].length - 1 && Row < field.length - 1) {
            result[0] = 0;
            result[1] = changeDirection;
            // If mouse moves in reverse direction and hits the top wall, change direction to left
            if (Row == 0 && changeDirection < 0) {
                result[1] = 0;
                result[0] = -1;
            }
        }
        // If the mouse hits the bottom wall he moves left
        if (Row == field.length - 1 && Col > 0) {
            result[0] = -1 * changeDirection;
            result[1] = 0;
            // If mouse moves in reverse direction and hits the right wall, change direction to up
            if (Col == field[Row].length - 1 && changeDirection < 0) {
                result[0] = 0;
                result[1] = -1;
            }
        }
        // If the mouse hits the left wall he moves up
        if (Col == 0 && Row > 0) {
            result[1] = -1 * changeDirection;
            // If mouse moves in reverse direction and hits the bottom wall, change direction to right
            if (Row == field.length - 1 && changeDirection < 0) {
                result[0] = 1;
                result[1] = 0;
            }
        }
        return result;
    }

    /**
     * Handle mouse movement events to update the current mouse point position
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // update the mouse point position
        mousePoint.setLocation(e.getX(), e.getY());
    }

    /**
     * Draw the loop of squares
     * The loop looks like a field with walls around it
     * The walls are represented by green squares
     */
    private void drawLoop() {
        double startX = (getWidth() - ROWS_NUMBER * SQUARE_SIZE) / 2.0;
        double startY = (getHeight() - COLS_NUMBER * SQUARE_SIZE) / 2.0;
        for (int row = 0; row < ROWS_NUMBER; row++) {
            for (int col = 0; col < COLS_NUMBER; col++) {
                if (row == 0 || row == (ROWS_NUMBER - 1) || col == 0 || col == (COLS_NUMBER - 1)) {
                    field[row][col] = drawSquare(startX + col * SQUARE_SIZE, startY + row * SQUARE_SIZE, FIELD_COLOR);
                }
            }
        }
    }

    /**
     * Draw a single square at the specified position with the specified color
     * @param x - x position of the square
     * @param y - y position of the square
     * @param color - color of the square
     * @return - returns the drawn square
     */
    private GRect drawSquare(double x, double y, Color color) {
        GRect gRect = new GRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
        gRect.setFilled(true);
        gRect.setFillColor(color);
        add(gRect);
        return gRect;
    }
}
