package com.shpp.p2p.cs.test;

import acm.graphics.GOval;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.event.MouseEvent;
import java.awt.*;


public class Snow extends WindowProgram {
    //    private final Color[] color = {Color.BLACK, Color.GREEN, Color.RED, Color.BLUE, Color.CYAN, Color.GRAY,
//            Color.YELLOW, Color.PINK, Color.ORANGE, Color.MAGENTA, Color.LIGHT_GRAY};
    private final int NUMBERS_OF_SNOW_IN_LINE = 40;
    private final int MAX_SIZE_OF_SNOW = 30;
    private final int MIN_SIZE_OF_SNOW = 5;
    private boolean isWind = false;
    private boolean westWind = false;

    @Override
    public void run() {
        GOval[] snow = drawSnow();
        makeSnowFly(snow);
    }

    private GOval[] drawSnow() {
        int snowNumber = NUMBERS_OF_SNOW_IN_LINE * (getHeight() / (getWidth() / (NUMBERS_OF_SNOW_IN_LINE + 2)));

        double startX = -getWidth();
        double startY = -getWidth() * 2.0;
        double finishX = getWidth() * 5.0;
        double finishY = getHeight() * 3.0;
        GOval[] snowBalls = new GOval[snowNumber];
        RandomGenerator randomGenerator = new RandomGenerator();
        for (int i = 0; i < snowBalls.length; i++) {
            GOval gOval = drawSnow(randomGenerator.nextDouble(startX, finishX),
                    randomGenerator.nextDouble(startY, finishY),
                    randomGenerator.nextDouble(MIN_SIZE_OF_SNOW, MAX_SIZE_OF_SNOW),
                    randomGenerator.nextColor());
            snowBalls[i] = gOval;
        }
        return snowBalls;
    }

    private void makeSnowFly(GOval[] snowBalls) {
        addMouseListeners();
        RandomGenerator rg = new RandomGenerator();
        // begining X position of each snow
        double[] snowXpos = new double[snowBalls.length];
        // altitude of each snow
        double[] altitude = new double[snowBalls.length];
        // left-right speed
        int[] xStep = new int[snowBalls.length];
        // falling speed
        int[] yStep = new int[snowBalls.length];
        int[] rotationSpeed = new int[snowBalls.length];
        // feel all arrays
        double[] snowSize = new double [snowBalls.length];
        for (int i = 0; i < snowBalls.length; i++) {
            snowXpos[i] = (int) snowBalls[i].getX();
            altitude[i] = rg.nextDouble(snowBalls[i].getWidth() / 2.0, snowBalls[i].getWidth() * 2.0);
            xStep[i] = rg.nextBoolean() ? rg.nextInt(1, 3) : -rg.nextInt(1, 3);
            yStep[i] = rg.nextInt(1, 3);
            snowBalls[i].setSize(rg.nextDouble(0, snowBalls[i].getHeight()), snowBalls[i].getHeight());
            snowSize[i] = snowBalls[i].getWidth();
            rotationSpeed[i] = rg.nextInt(1, 3);
        }
        while (true) {
            for (int i = 0; i < snowBalls.length; i++) {
                if (isWind) {
                    if (westWind) {
                        snowBalls[i].move(Math.abs(xStep[i]), yStep[i]);
                        snowXpos[i] = snowBalls[i].getX();
                    } else {
                        snowBalls[i].move(-Math.abs(xStep[i]), yStep[i]);
                        snowXpos[i] = snowBalls[i].getX();
                    }
                } else {
                    if (snowBalls[i].getX() > altitude[i] + snowXpos[i]) {
                        xStep[i] *= -1;
                    }
                    if (snowBalls[i].getX() < snowXpos[i] - altitude[i]) {
                        xStep[i] *= -1;
                    }
                    snowBalls[i].move(xStep[i], yStep[i]);
                }


                if (snowBalls[i].getWidth() < 1) {
                    rotationSpeed[i] *= -1;
                }
                if (snowBalls[i].getWidth() > snowBalls[i].getHeight() + 1) {
                    rotationSpeed[i] *= -1;
                }
                snowBalls[i].setSize(snowBalls[i].getWidth() - rotationSpeed[i], snowBalls[i].getHeight());
            }
            pause(100);
        }
    }

    private void makeRotation(GOval snowBall, int size) {
        RandomGenerator rg = new RandomGenerator();
        //int rotationSpeed = rg.nextInt(1, 3);
        int rotationSpeed = 2;
        if (snowBall.getWidth() < 1) {
            rotationSpeed *= -1;
        }
        if (snowBall.getWidth() > size + 1) {
            rotationSpeed *= -1;
        }
        snowBall.setSize(snowBall.getWidth() - rotationSpeed, snowBall.getHeight());
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (isWind) {
            isWind = false;
        } else {
            RandomGenerator rg = new RandomGenerator();
            westWind = rg.nextBoolean();
            if (westWind) {
                System.out.println("wind from West");
            } else {
                System.out.println("wind from East");
            }
            isWind = true;
        }
    }

    private GOval drawSnow(double x, double y, double size, Color color) {
        GOval gOval = new GOval(x, y, size, size);
        gOval.setFilled(true);
        gOval.setFillColor(color);
        gOval.setColor(color);
        add(gOval);
        return gOval;
    }
}
