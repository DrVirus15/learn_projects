package com.shpp.p2p.cs.akoskovtsev.assignment4;

import acm.graphics.*;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Breakout game.
 */
public class Breakout extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 230;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 30;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 1;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

//    /**
//     * It's a bad idea to calculate brick width from APPLICATION_WIDTH
//     */
//    private static final int BRICK_WIDTH =
//            (APPLICATION_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 18;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 10;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    private static final int BALL_SPEED = 1500;

    /**
     * The paddle, ball, speed of the ball(vx, vy) and game status.
     */
    private GRect paddle;
    private GOval ball;
    private double vx, vy;
    boolean isGameInProgress = true;

    /**
     * The main method that starts the game.
     * Makes the paddle, draws the bricks,
     * and starts rolling the ball with the specified number of turns.
     */
    @Override
    public void run() {
        drawBricks();
        makePaddle();
        rollBall(NTURNS - 1);
    }

    /**
     * Creates the paddle at the bottom of the window.
     */
    private void makePaddle() {

        paddle = drawRectangle(getWidth() / 2.0 - PADDLE_WIDTH / 2.0,
                getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
        paddle.sendToFront();
        addMouseListeners();
    }

    /**
     * Moves the center of the paddle to the position of the mouse cursor.
     * The paddle does not go beyond the left and right borders of the window.
     *
     * @param event - the mouse listener event that contains the position of the cursor.
     */
    private void movePaddle(MouseEvent event) {
        double xPos = event.getX() - paddle.getWidth() / 2.0;
        // Check if the paddle is within the window bounds
        if (xPos < 0) {
            xPos = 0;
        } else if (xPos > getWidth() - paddle.getWidth()) {
            xPos = getWidth() - paddle.getWidth();
        }
        // Move the paddle to the new position
        paddle.move(xPos - paddle.getX(), 0);
    }

    /**
     * Processes mouse drag events to move the paddle.
     *
     * @param mouseEvent the mouse listener event that contains the position of the cursor.
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        movePaddle(mouseEvent);
    }

    /**
     * Processes mouse move events to move the paddle.
     *
     * @param event the mouse listener event that contains the position of the cursor.
     */
    @Override
    public void mouseMoved(MouseEvent event) {
        movePaddle(event);
    }

    /**
     * Draws the bricks in a grid pattern.
     * The bricks width is calculated based on the window width,
     * the number of bricks per row, and the separation between bricks.
     */
    private void drawBricks() {
        double brickWidth = (double) (getWidth() - (NBRICKS_PER_ROW * BRICK_SEP) - BRICK_SEP) / NBRICKS_PER_ROW;
        // The width of one brick and the separation between bricks.
        double oneBrickAndSep = brickWidth + BRICK_SEP;
        int countOfRows = 0;
        int nextColor = 0;
        // Vertical position of the first row of bricks.
        double yPosition = BRICK_Y_OFFSET;
        for (int i = 0; i < NBRICK_ROWS; i++) {
            // Horizontal position of the first brick in the row.
            double xPosition = (getWidth() - (NBRICKS_PER_ROW * oneBrickAndSep - BRICK_SEP)) / 2;
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {
                drawRectangle(xPosition, yPosition, brickWidth, BRICK_HEIGHT, setColor(nextColor));
                // x position coordinate is moved to the left by the size of one box and one separation
                xPosition += (oneBrickAndSep);
            }
            // If the row is even, the next color is changed.
            // The next color is changed only if it is not the last color.
            countOfRows++;
            if (countOfRows % 2 == 0) {
                if (nextColor != 4) {
                    nextColor++;
                }
            }
            // y position coordinate is moved to the down by the size of one box and one separation
            yPosition += BRICK_HEIGHT + BRICK_SEP;
        }
    }

    /**
     * Rolls the ball across the window.
     * Waits for the user to click the mouse to start the game.
     * The ball rolls until it goes out of the window or the game is over.
     *
     * @param nTurns the number of continues left.
     */
    private void rollBall(int nTurns) {
        ball = drawOval(getWidth() / 2.0 - BALL_RADIUS, getHeight() / 2.0 - BALL_RADIUS, BALL_RADIUS * 2.0);
        waitForStart();
        vy = 3.0;
        RandomGenerator random = RandomGenerator.getInstance();
        vx = random.nextDouble(1.0, 3.0);
        if (random.nextBoolean(0.5)) {
            vx = -vx;
        }
        // The ball rolls until it goes out of the window or the game is over.
        while (ball.getY() < getHeight() && isGameInProgress) {
            ball.move(vx, vy);
            // If the ball goes out of the window, it checks the number of continues.
            // If there are no continues left, it displays a message and ends the game.
            setBordersForBall(nTurns);
            // If the ball collides with any object, it gets the collision result.
            getCollisionResult();
            pause(BALL_SPEED);
        }
    }


    /**
     * Displays a message prompting the user to click.
     * Waits for the user to click the mouse to start the game.
     * The message is removed after the click.
     */
    private void waitForStart() {
        GLabel message = drawGLabel("Клацніть мишкою для запуску.", Color.BLACK, "Veranda-20");
        waitForClick();
        remove(message);
    }

    /**
     * Checks the borders of the ball and changes its direction if it goes out of the window.
     * If the ball goes out of the bottom border, it checks the number of continues.
     * If there are no continues left, it displays a message and ends the game.
     *
     * @param nTurns the number of continues left.
     */
    private void setBordersForBall( int nTurns) {
        if (ball.getX() < 0) {
            vx *= -1;
        }
        if (ball.getX() + BALL_RADIUS * 2.0 > getWidth()) {
            vx *= -1;
        }
        if (ball.getY() < 0) {
            vy *= -1;
        }
        if (ball.getY() + BALL_RADIUS * 2.0 > getHeight()) {
            if (nTurns != 0) {
                remove(ball);
                GLabel message = drawGLabel("Залишилось спроб: " + nTurns, Color.BLACK, "Veranda-20");
                pause(2000);
                remove(message);
                nTurns--;
                rollBall(nTurns);
            } else {
                remove(ball);
                remove(paddle);
                drawGLabel("Програш", Color.RED, "Veranda-50");
                isGameInProgress = false;
            }
        }
    }

    /**
     * Gets the collision result of the ball with the paddle or bricks.
     * If the ball collides with the paddle, it changes its direction.
     * If the ball collides with a brick, it removes the brick and changes its direction.
     * If there are only two elements left in the window, it removes all elements and sends a win message.
     */
    private void getCollisionResult() {
        // Get the colliding object with the ball, and the ball point-number of collision.
        HashMap<Integer, GObject> object = getCollidingObject();
        if (object != null) {
            // The key is the ball point-number of collision.
            int key = object.entrySet().iterator().next().getKey();
            // The value is the colliding object.
            GObject gObject = object.entrySet().iterator().next().getValue();
            // Check the key and change the direction of the ball accordingly.
            switch (key) {
                // 1 - middle top point of the ball.
                // works only with bricks.
                case 1: {
                    if (gObject != paddle) {
                        remove(gObject);
                        vy *= -1;
                        checkForWin();
                    }
                    break;
                }
                // 2, 3 - top left and top right points of the ball.
                // works only with bricks.
                // If the ball collides with the brick, it changes its direction diagonally.
                case 2:
                case 3: {
                    if (gObject != paddle) {
                        remove(gObject);
                        vy = -vy;
                        vx = -vx;
                        checkForWin();
                    }
                    break;
                }
                // 4 - bottom middle point of the ball.
                // works with the paddle and bricks.
                case 4: {
                    if (gObject == paddle) {
                        ball.setLocation(ball.getX(), paddle.getY()-ball.getHeight());
                        vy *= -1;
                    } else {
                        remove(gObject);
                        vy *= -1;
                        checkForWin();
                    }
                    break;
                }
                // 5, 6 - bottom left and bottom right points of the ball.
                // works with the paddle and bricks.
                // If the ball collides with the paddle or bricks, it changes its direction diagonally.
                case 5:
                case 6: {
                    if (gObject == paddle) {
                        ball.setLocation(ball.getX(), paddle.getY()-ball.getHeight());
                        vy = -vy;
                        vx = -vx;
                    } else {
                        remove(gObject);
                        vy = -vy;
                        vx = -vx;
                        checkForWin();
                    }
                    break;
                }
                // 7, 8 - left and right points of the ball.
                // works only with bricks.
                case 7:
                case 8: {
                    if (gObject != paddle) {
                        remove(gObject);
                        vx = -vx;
                    }
                    break;
                }
            }
        }
    }

    /**
     * Gets the colliding object with the ball.
     * Checks the points of the ball and returns the first colliding object and the number of the point.
     * The points are:
     * 1 - middle top point of the ball,
     * 2 - top left point of the ball,
     * 3 - top right point of the ball,
     * 4 - bottom middle point of the ball,
     * 5 - bottom left point of the ball,
     * 6 - bottom right point of the ball,
     * 7 - left point of the ball,
     * 8 - right point of the ball.
     *
     * @return a HashMap with the first colliding object and the number of the point.
     */
    private HashMap<Integer, GObject> getCollidingObject() {
        HashMap<Integer, GObject> gObjects = new HashMap<>();
        gObjects.put(1, getElementAt(ball.getX() + BALL_RADIUS + 1, ball.getY()));
        gObjects.put(2, getElementAt(ball.getX() + BALL_RADIUS * 0.25, ball.getY() + BALL_RADIUS * 0.25));
        gObjects.put(3, getElementAt(ball.getX() + BALL_RADIUS * 1.75, ball.getY() + BALL_RADIUS * 0.25));
        gObjects.put(4, getElementAt(ball.getX() + BALL_RADIUS + 1, ball.getY() + BALL_RADIUS * 2.0));
        gObjects.put(5, getElementAt(ball.getX() + BALL_RADIUS * 0.25, ball.getY() + BALL_RADIUS * 1.75));
        gObjects.put(6, getElementAt(ball.getX() + BALL_RADIUS * 1.75, ball.getY() + BALL_RADIUS * 1.75));
        gObjects.put(7, getElementAt(ball.getX(), ball.getY() + BALL_RADIUS + 1));
        gObjects.put(8, getElementAt(ball.getX() + BALL_RADIUS * 2.0, ball.getY() + BALL_RADIUS + 1));

        // Iterate through the map and return the first non-null GObject with the key.
        for (Map.Entry<Integer, GObject> entry : gObjects.entrySet()) {
            Integer key = entry.getKey();
            GObject value = entry.getValue();
            if (value != null) {
                HashMap<Integer, GObject> newMap = new HashMap<>();
                newMap.put(key, value);
                return newMap;
            }
        }
        return null;
    }

    /**
     * If there are only two elements left in the window,
     * It removes all elements and sends a win message.
     * Changes the game status to not in progress.
     */
    private void checkForWin() {
        if (getElementCount() == 2) {
            removeAll();
            drawGLabel("Виграш!", Color.RED, "Veranda-50");
            isGameInProgress = false;
        }
    }

    /**
     * Draws a rectangle with the specified parameters.
     *
     * @param x      the x-coordinate of the rectangle's top-left corner
     * @param y      the y-coordinate of the rectangle's top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color  the color of the rectangle
     * @return the GRect object representing the drawn rectangle
     */
    public GRect drawRectangle(double x, double y, double width, double height, Color color) {
        GRect gRect = new GRect(x, y, width, height);
        gRect.setFilled(true);
        gRect.setFillColor(color);
        gRect.setColor(color);
        add(gRect);
        return gRect;
    }

    /**
     * Draws a GLabel with the specified message, color, and font.
     *
     * @param message the text to display in the label
     * @param color   the color of the label text
     * @param font    the font of the label text
     * @return the GLabel object representing the drawn label
     */
    public GLabel drawGLabel(String message, Color color, String font) {

        GLabel gLabel = new GLabel(message, 0, 0);
        gLabel.setFont(font);
        gLabel.setColor(color);
        add(gLabel, (getWidth() - gLabel.getWidth()) / 2.0, (getHeight() - gLabel.getHeight()) / 2.0);
        return gLabel;
    }

    /**
     * Draws a filled circle with the specified parameters.
     *
     * @param x        the x-coordinate of the circle's top-left corner
     * @param y        the y-coordinate of the circle's top-left corner
     * @param diameter the diameter of the circle
     * @return the GOval object representing the drawn circle
     */
    public GOval drawOval(double x, double y, double diameter) {
        GOval gOval = new GOval(x, y, diameter, diameter);
        gOval.setFilled(true);
        gOval.setFillColor(Color.BLACK);
        add(gOval);
        return gOval;
    }

    /**
     * Sets the color of the bricks based on the color number.
     *
     * @param colorNumber the number representing the color
     * @return the Color object corresponding to the color number
     */
    public Color setColor(int colorNumber) {
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
        return colors[colorNumber];
    }
}

