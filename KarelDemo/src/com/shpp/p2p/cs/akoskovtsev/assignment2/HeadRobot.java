package com.shpp.p2p.cs.akoskovtsev.assignment2;

import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class HeadRobot extends WindowProgram {
    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 1000;



    private static final double HEAD_WIDTH = 900;
    private static final double HEAD_HEIGHT = 700;
    private static final double EYE_RADIUS = 130;
    private static final double MOUTH_WIDTH = 313;
    private static final double MOUTH_HEIGHT = 70;

    @Override
    public void run() {
        drowHeadRobot();
//        drawLines();
    }

    private void drawLines() {
        GLine gLine = new GLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        gLine.setColor(Color.BLACK);
        add(gLine);

        GLine gLine2 = new GLine((getWidth() / 2) - (HEAD_WIDTH / 4), 0, (getWidth() / 2) - (HEAD_WIDTH / 4), getHeight());
        gLine2.setColor(Color.BLACK);
        add(gLine2);

        GLine gLine3 = new GLine((getWidth() / 2) + (HEAD_WIDTH / 4), 0, (getWidth() / 2) + (HEAD_WIDTH / 4), getHeight());
        gLine3.setColor(Color.BLACK);
        add(gLine3);

        GLine gLine4 = new GLine(0, (getHeight()/2)-HEAD_HEIGHT/4, getWidth(), (getHeight()/2)-HEAD_HEIGHT/4);
        gLine4.setColor(Color.BLACK);
        add(gLine4);

        GLine gLine5 = new GLine(0, getHeight()/2, getWidth(), getHeight()/2);
        gLine5.setColor(Color.BLACK);
        add(gLine5);

        GLine gLine6 = new GLine(0, getHeight()/2+HEAD_HEIGHT/4, getWidth(), getHeight()/2+HEAD_HEIGHT/4);
        gLine6.setColor(Color.BLACK);
        add(gLine6);
    }

    private void drowHeadRobot() {
        double xCenter = getWidth() / 2.0;
        double yCenter = getHeight() / 2.0;

        drowRect((getWidth() - HEAD_WIDTH) / 2, (getHeight() - HEAD_HEIGHT) / 2, HEAD_WIDTH, HEAD_HEIGHT, Color.GRAY);
        drowEye(xCenter - HEAD_WIDTH / 4 - EYE_RADIUS, yCenter-HEAD_HEIGHT/4-EYE_RADIUS);
        drowEye(xCenter + HEAD_WIDTH / 4 - EYE_RADIUS, yCenter-HEAD_HEIGHT/4-EYE_RADIUS);
        drowRect(xCenter-MOUTH_WIDTH/2, yCenter+HEAD_HEIGHT/4, MOUTH_WIDTH, MOUTH_HEIGHT, Color.RED);
    }

    private void drowRect(double x, double y, double width, double height, Color color) {
        GRect borderHead = new GRect(x, y, width, height);
        borderHead.setColor(color);
        borderHead.setFilled(true);
        borderHead.setFillColor(color);
        add(borderHead);
    }

    private void drowEye(double x, double y) {
        GOval gOval = new GOval(x, y, EYE_RADIUS*2, EYE_RADIUS*2);
        gOval.setColor(Color.BLUE);
        gOval.setFilled(true);
        gOval.setFillColor(Color.BLUE);
        add(gOval);
    }
}