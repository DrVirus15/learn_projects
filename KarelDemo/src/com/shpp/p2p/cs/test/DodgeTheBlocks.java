package com.shpp.p2p.cs.test;

import acm.graphics.GImage;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class DodgeTheBlocks extends WindowProgram {
    private static GImage paddle;
   // private static final double PADDLE_HEIGHT = 10;
//    private static final double PADDLE_WIDTH = 50;
    private static final double PADDLE_Y_OFFSET = 20;
    private static final String WOOD_BLOCK_FILE = "assets/images/WoodBlock.png";
    private static final String STEEL_BLOCK_FILE = "assets/images/SteelBlock.png";
    private static final int NUMBERS_OF_STEEL_BLOCKS = 3;

    @Override
    public void run() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                removeAll();
                drawPaddle();
            }
        });
        drawPaddle();
        drawBricks();
//        startTheGame();

    }

    private void drawBricks() {
        for (int i = 0; i < NUMBERS_OF_STEEL_BLOCKS; i++) {

        }
    }

    private void update(){
        removeAll();
        drawPaddle();
    }
    private void drawPaddle() {
        double paddleWidth = getWidth() /5.0;
        double paddleHeight = getHeight() / 20.0;
           paddle = drawRectangle(WOOD_BLOCK_FILE, getWidth() / 2.0 - paddleWidth / 2.0,
                getHeight() - paddleHeight - PADDLE_Y_OFFSET, paddleWidth, paddleHeight);
        paddle.sendToFront();
        addMouseListeners();
    }

    private GImage drawRectangle(String fileName, double x, double y, double paddleWidth, double paddleHeight) {
        GImage gImage = new GImage(fileName);
        gImage.setSize(paddleWidth, paddleHeight);
        add(gImage, x, y);
        return gImage;
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
}
