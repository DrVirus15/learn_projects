package com.shpp.p2p.cs.test;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.util.ArrayList;

public class Snake extends WindowProgram {
    public static final int APPLICATION_WIDTH = 810;
    public static final int APPLICATION_HEIGHT = 810;
    public static final int CELL_SIZE = 50;
    public static final int SPEED = 500; // milliseconds between moves
    public static final int INITIAL_SNAKE_LENGTH = 10;


    public static ArrayList<GRect> snake = new ArrayList<>();
    public static GRect food = new GRect(0, 0, CELL_SIZE, CELL_SIZE);
    public static int moveX = 0;
    public static int moveY = -CELL_SIZE;

    @Override
    public void run() {
        setBackground(Color.GRAY);
        drawTable();
        drawSnake();
        drawFood();
        startTheGame();
    }

    private void startTheGame() {
        addActionListeners();
        addKeyListeners();
        addMouseListeners();
        int numberOfCells = Math.min(getHeight(), getWidth()) / CELL_SIZE;
        double x = (getWidth() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        double y = (getHeight() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        // Initialize the movement arrays
        ArrayList<Double> xPointArray = new ArrayList<Double>();
        for (int i = 0; i < snake.size(); i++) {
            xPointArray.add(snake.get(i).getX());
        }
        ArrayList<Double> yPointArray = new ArrayList<Double>();
        for (int i = 0; i < snake.size(); i++) {
            yPointArray.add(snake.get(i).getY());
        }
        int gameInProgress = 0;
        GOval headEyeLeft = new GOval(snake.get(0).getX() + CELL_SIZE/2.0 - 10, snake.get(0).getY() + CELL_SIZE/2.0 + 2, 5, 5);
        headEyeLeft.setFilled(true);
        headEyeLeft.setFillColor(Color.RED);
        add(headEyeLeft);
        GOval headEyeRight  = new GOval(headEyeLeft.getX() + 10, headEyeLeft.getY(), 5, 5);
        headEyeRight.setFilled(true);
        headEyeRight.setFillColor(Color.RED);
        add(headEyeRight);
        while (gameInProgress == 0) {
            for (int i = 0; i < snake.size(); i++) {
                snake.get(i).setLocation(xPointArray.get(i), yPointArray.get(i));
            }

            // Check if the snake head has eaten the food
            if (xPointArray.get(0).equals(food.getX()) && yPointArray.get(0).equals(food.getY())) {
                // Increase the snake length
                GRect newSegment = new GRect(xPointArray.get(snake.size() - 1), yPointArray.get(snake.size() - 1), CELL_SIZE, CELL_SIZE);
                newSegment.setColor(Color.BLACK);
                newSegment.setFilled(true);
                newSegment.setFillColor(Color.WHITE);
                add(newSegment);
                snake.add(newSegment);
                xPointArray.add(xPointArray.get(snake.size() - 2));
                yPointArray.add(yPointArray.get(snake.size() - 2));
                remove(food);
                drawFood();
            }
            for (int i = snake.size() - 1; i >= 0; i--) {
                if (i == 0) {
                    xPointArray.set(i, xPointArray.get(i) + moveX);
                    yPointArray.set(i, yPointArray.get(i) + moveY);
                } else {
                    xPointArray.set(i, xPointArray.get(i - 1));
                    yPointArray.set(i, yPointArray.get(i - 1));
                }
            }

            // if snake has collided with wall, change direction of snake movement to right
            if (snake.get(0).getX() < x || snake.get(0).getX() > getWidth() - CELL_SIZE || snake.get(0).getY() < y || snake.get(0).getY() > getHeight() - CELL_SIZE) {
               snake.get(0).setLocation(x + (numberOfCells / 2) * CELL_SIZE, y + (numberOfCells / 2) * CELL_SIZE);
                moveX = 0;
                moveY = -CELL_SIZE;
                //if snake collides with bottom wall, change direction to left
                if(snake.get(0).getY() > getHeight() - CELL_SIZE) {
                    moveX = CELL_SIZE;
                    moveY = 0;
                }
                //if snake collides with top wall, change direction to right
                if(snake.get(0).getY() < y) {
                    moveX = CELL_SIZE;
                    moveY = 0;
                }
                //if snake collides with left wall, change direction to up
                if(snake.get(0).getX() < x) {
                    moveX = 0;
                    moveY = -CELL_SIZE;
                }
                //if snake collides with right wall, change direction to down
                if(snake.get(0).getX() > getWidth() - CELL_SIZE) {
                    moveX = 0;
                    moveY = CELL_SIZE;
                }


            }
            // Check for collisions with itself
            headEyeLeft.setLocation(snake.get(0).getX() + 1, snake.get(0).getY() + 1);
            headEyeRight.setLocation(headEyeLeft.getX() + 10, headEyeLeft.getY());
            for (GRect segment : snake) {
                if (segment != snake.get(0)) {
                    if (headEyeLeft.getBounds().intersects(segment.getBounds())) {
                        // If the snake head collides with any of its segments, end the game
                        drawGameOver();
                        snake.clear();
                        xPointArray.clear();
                        yPointArray.clear();
                        remove(headEyeLeft);
                        gameInProgress = 1;
                        break;
                    }
                }
            }
            pause(SPEED);
        }
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        switch (e.getKeyCode()) {
            case java.awt.event.KeyEvent.VK_UP:
                // Check if the snake is not moving down
                if (moveY != CELL_SIZE) {
                    // Move snake up
                    moveY = -CELL_SIZE;
                    moveX = 0;
                }
                break;
            case java.awt.event.KeyEvent.VK_DOWN:
                // Move snake down
                if (moveY != -CELL_SIZE) {
                    // Check if the snake is not moving up
                    moveY = CELL_SIZE;
                    moveX = 0;
                }
                break;
            case java.awt.event.KeyEvent.VK_LEFT:
                // Move snake left
                if (moveX != CELL_SIZE) {
                    // Check if the snake is not moving right
                    moveX = -CELL_SIZE;
                    moveY = 0;
                }
                break;
            case java.awt.event.KeyEvent.VK_RIGHT:
                // Check if the snake is not moving left
                if (moveX != -CELL_SIZE) {
                    moveX = CELL_SIZE;
                    moveY = 0;
                }
                break;
        }
    }

    private void drawFood() {
        int numberOfCells = Math.min(getHeight(), getWidth()) / CELL_SIZE;
        double x = (getWidth() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        double y = (getHeight() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        int foodRow;
        int foodCol;
        // Ensure food is not placed on the snake
        boolean isFoodOnSnake;
        do {
            isFoodOnSnake = false;
            foodRow = (int) (Math.random() * numberOfCells);
            foodCol = (int) (Math.random() * numberOfCells);
            for (GRect segment : snake) {
                if (segment.getX() == x + foodCol * CELL_SIZE && segment.getY() == y + foodRow * CELL_SIZE) {
                    isFoodOnSnake = true;
                    break;
                }
            }
        } while (isFoodOnSnake);
        food = new GRect(x + foodCol * CELL_SIZE, y + foodRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        food.setFilled(true);
        food.setFillColor(Color.RED);
        add(food);
    }

    private void drawTable() {
        int numberOfCells = Math.min(getHeight(), getWidth()) / CELL_SIZE;
        double x = (getWidth() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        double y = (getHeight() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++) {
                GRect cell = new GRect(x + j * CELL_SIZE, y + i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                cell.setFilled(true);
                cell.setFillColor(Color.LIGHT_GRAY);
                add(cell);
            }
        }
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++) {
                GLine horizontalLine = new GLine(x, y + i * CELL_SIZE, x + numberOfCells * CELL_SIZE, y + i * CELL_SIZE);
                GLine verticalLine = new GLine(x + j * CELL_SIZE, y, x + j * CELL_SIZE, y + numberOfCells * CELL_SIZE);
                horizontalLine.setColor(Color.BLACK);
                verticalLine.setColor(Color.BLACK);
                add(horizontalLine);
                add(verticalLine);
            }
        }
    }


    private void drawSnake() {
//        GRect[] snakeSegments = new GRect[INITIAL_SNAKE_LENGTH];
        int numberOfCells = Math.min(getHeight(), getWidth()) / CELL_SIZE;
        double x = (getWidth() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        double y = (getHeight() - (numberOfCells * CELL_SIZE)) / 2.0; // Centering the table
        int startSnakeCell = numberOfCells / 2; // Starting in the middle of the table
        for (int i = 0; i < INITIAL_SNAKE_LENGTH; i++) {
            GRect snakeSegment = new GRect(
                    x + startSnakeCell * CELL_SIZE,
                    y + (startSnakeCell + i) * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE);
            snakeSegment.setColor(Color.BLACK);
            snakeSegment.setFilled(true);
            snakeSegment.setFillColor(Color.WHITE);
            add(snakeSegment);
            snake.add(snakeSegment);
        }
    }


    private void drawGameOver() {
        GRect gameOverRect = new GRect(getWidth() / 4.0, getHeight() / 4.0, getWidth() / 2.0, getHeight() / 2.0);
        gameOverRect.setFilled(true);
        gameOverRect.setFillColor(Color.RED);
        add(gameOverRect);
        GLabel gameOverLabel = new GLabel("Game Over");
        gameOverLabel.setFont("Arial-36");
        gameOverLabel.setColor(Color.WHITE);
        add(gameOverLabel, getWidth() / 2.0 - gameOverLabel.getWidth() / 2.0, getHeight() / 2.0 + gameOverLabel.getAscent() / 2.0);
    }
}