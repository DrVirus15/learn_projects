package com.shpp.p2p.cs.akoskovtsev.assignment4;


import acm.graphics.*;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Program draws an air balloon, and moves it up.
 */
public class DrawTheLine extends WindowProgram {

    /*
     * Constants controlling the *approximately* size of the application window.
     */
    public static final int APPLICATION_WIDTH = 1500;
    public static final int APPLICATION_HEIGHT = 1000;

    GLine gLine;

    @Override
    public void run() {
        addMouseListeners();
    }
    public void mousePressed(MouseEvent mouseEvent) {
        gLine = new GLine(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
        add(gLine);
    }
    public void mouseDragged(MouseEvent e) {
        gLine.setEndPoint(e.getX(), e.getY());
    }
}