package com.shpp.p2p.cs.test;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LastNballs extends WindowProgram {
    private final int BALLS_NUMBER = 10;
    private final int BALL_SIZE = 30;
    private final int DELAY = 50;
    private final int MOVE_STEP = 5;
    private final Color BALL_COLOR = Color.GRAY;
    private final Color BALL_COLOR_VERTICAL = Color.PINK;
    private GOval[] gOvalAray = new GOval[BALLS_NUMBER];
    private GLabel[] gLabelArray = new GLabel[BALLS_NUMBER];
    private ArrayList<GPoint> mouseClics = new ArrayList<>();
    private boolean[] isHorisontal = new boolean[BALLS_NUMBER];
    private boolean[] isVertical = new boolean[BALLS_NUMBER];

    @Override
    public void run() {
        addMouseListeners();
        drawCircles();
        moveCircle();
    }


    private void drawCircles() {
        double startX = (getWidth() - BALL_SIZE * BALLS_NUMBER) / 2.0;
        double startY = (getHeight() - BALL_SIZE * BALLS_NUMBER) / 2.0;

        GRect gRect = new GRect(startX, startY, BALL_SIZE * BALLS_NUMBER, BALL_SIZE * BALLS_NUMBER);
        gRect.setFilled(false);
        gRect.setColor(Color.BLACK);
        add(gRect);
        for (int i = 0; i < BALLS_NUMBER; i++) {
            for (int j = 0; j < BALLS_NUMBER; j++) {
                if (j == i) {
                    gOvalAray[i] = drawCircle(startX + BALL_SIZE * i, startY + BALL_SIZE * i, BALL_SIZE, BALL_COLOR);
                    gLabelArray[i] = drawNumber("" + (i + 1), startX + BALL_SIZE / 2.0 + BALL_SIZE * i, startY + BALL_SIZE / 2.0 + BALL_SIZE * i);
                }
            }

        }
    }

//    @Override
//    public void mouseReleased(MouseEvent e) {
//        for (GOval circle : gOvalAray) {
//            if (circle.contains(e.getX(), e.getY())) {
//                moveCircle(circle, true);
//                System.out.println("mouse released");
//            }
//        }
//    }

    private void moveCircle() {
        double startX = (getWidth() - BALL_SIZE * BALLS_NUMBER) / 2.0;
        double startY = (getHeight() - BALL_SIZE * BALLS_NUMBER) / 2.0;
        double finishX = startX + BALL_SIZE * BALLS_NUMBER - BALL_SIZE;
        double finishY = startY + BALL_SIZE * BALLS_NUMBER - BALL_SIZE;
        int[] move = new int[BALLS_NUMBER];
        for (int i = 0; i < move.length; i++) {
            move[i] = MOVE_STEP;
        }

        while (true) {
            for (GPoint clickPoint : mouseClics) {
                for (int i = 0; i < gOvalAray.length; i++) {
                    if (gOvalAray[i].contains(clickPoint)) {
                        if(isHorisontal[i]){
                          isHorisontal[i] = false;
                          isVertical[i] = true;
                          clickPoint.setLocation(0,0);
                        } else {
                            if(gOvalAray[i].getFillColor() == BALL_COLOR) {
                                isHorisontal[i] = true;
                                clickPoint.setLocation(0, 0);
                            } else {
                                isVertical[i] = false;
                                isHorisontal[i] = true;
                                clickPoint.setLocation(0,0);
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < isHorisontal.length; i++) {
                if(isVertical[i]){
                    gOvalAray[i].move(0, move[i]);
                    gOvalAray[i].setFillColor(BALL_COLOR_VERTICAL);
                    gOvalAray[i].setColor(BALL_COLOR_VERTICAL);
                    gLabelArray[i].move(0, move[i]);
                }
                if(gOvalAray[i].getY() > finishY){
                    move[i] *= -1;
                    gOvalAray[i].move(0, move[i]);
                    gOvalAray[i].setFillColor(BALL_COLOR_VERTICAL);
                    gOvalAray[i].setColor(BALL_COLOR_VERTICAL);
                    gLabelArray[i].move(0, move[i]);
                }
                if(gOvalAray[i].getY() < startY){
                    move[i] *= -1;
                    gOvalAray[i].move(0, move[i]);
                    gOvalAray[i].setFillColor(BALL_COLOR_VERTICAL);
                    gOvalAray[i].setColor(BALL_COLOR_VERTICAL);
                    gLabelArray[i].move(0, move[i]);
                }
                if(isHorisontal[i]){
                    gOvalAray[i].setFillColor(BALL_COLOR);
                    gOvalAray[i].setColor(BALL_COLOR);
                    gOvalAray[i].move(move[i], 0);
                    gLabelArray[i].move(move[i], 0);
                }
                if (gOvalAray[i].getX() < startX) {
                    gOvalAray[i].setFillColor(BALL_COLOR);
                    gOvalAray[i].setColor(BALL_COLOR);
                    move[i] *= -1;
                    gOvalAray[i].move(move[i], 0);
                    gLabelArray[i].move(move[i], 0);
                }
                if (gOvalAray[i].getX() > finishX) {
                    gOvalAray[i].setFillColor(BALL_COLOR);
                    gOvalAray[i].setColor(BALL_COLOR);
                    move[i] *= -1;
                    gOvalAray[i].move(move[i], 0);
                    gLabelArray[i].move(move[i], 0);
                }
            }


            pause(DELAY);
        }
    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        mouseClics.add(new GPoint(e.getX(), e.getY()));
//    }

    @Override
    public void mouseReleased(MouseEvent e){
        mouseClics.add(new GPoint(e.getX(), e.getY()));
    }

    private GLabel drawNumber(String number, double x, double y) {
        GLabel gLabel = new GLabel(number, x, y);
        gLabel.setColor(Color.BLACK);
        add(gLabel, x - gLabel.getWidth() / 2.0, y + gLabel.getHeight() / 3.0);
        return gLabel;
    }

    private GOval drawCircle(double x, double y, int ballSize, Color ballColor) {
        GOval gOval = new GOval(x, y, ballSize, ballSize);
        gOval.setFilled(true);
        gOval.setFillColor(ballColor);
        gOval.setColor(ballColor);
        add(gOval);
        return gOval;
    }
}
