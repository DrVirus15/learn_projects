package com.shpp.cs.a.lectures.lec06;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Sunset extends WindowProgram {

    /* The radius of the sun. */
    private static final double SUN_RADIUS = 75;

    /* The height of the horizon. */
    private static final double HORIZON_HEIGHT = 100;

    /* The sun's setting velocity. */
    private static final double SUNSET_VELOCITY = -1.0;

    /* How much time to pause between frames. */
    private static final double PAUSE_TIME = 40;

    public void run() {
        /* Color the window cyan to simulate the sky. */
        setBackground(Color.CYAN);

        /* Create the sun and horizon. */
        GOval sun = makeSun();
        GRect horizon = makeHorizon();

        /* Add the sun, then the horizon, so that the sun can
         * set behind it.
         */
        add(sun);
        add(horizon);

        performSunset(sun);
    }

    /**
     * Creates and returns an oval representing the sun.
     *
     * @return A GOval representing the sun.
     */
    private GOval makeSun() {
        /* Center the GOval in the window. */
        GOval result = new GOval((getWidth() - 2 * SUN_RADIUS) / 2.0,
                (getHeight() - 2 * SUN_RADIUS) / 2.0,
                2 * SUN_RADIUS, 2 * SUN_RADIUS);

        result.setFilled(true);
        result.setColor(Color.YELLOW);
        return result;
    }

    /**
     * Creates and returns a rectangle representing the horizon.
     *
     * @return A GRect representing the horizon.
     */
    private GRect makeHorizon() {
        /* The horizon should horizontally fill the window and
         * should have height HORIZON_HEIGHT. It will be
         * aligned to the bottom of the window.
         */
        GRect result = new GRect(0, getHeight() - HORIZON_HEIGHT,
                getWidth(), HORIZON_HEIGHT);

        result.setColor(Color.GREEN);
        result.setFilled(true);
        return result;
    }

    /**
     * Simulates a sunset.
     *
     * @param sun The object representing the sun.
     */
    private void performSunset(GOval sun) {
        /* Keep moving the sun downward until it has set. */
        while (!hasSunSet(sun)) {
            sun.move(0, SUNSET_VELOCITY);
            pause(PAUSE_TIME);

            /* TODO: Change the sun color, the sky color, or the
             * horizon color if you'd like!
             */
        }
    }

    /**
     * Given the sun, determine whether or not it has set.
     *
     * @param sun The object representing the sun.
     * @return Whether the sun has set.
     */
    private boolean hasSunSet(GOval sun) {
        /* The sun has set as soon as its top is below the
         * horizon.
         */
        return sun.getY() > getHeight() - HORIZON_HEIGHT;
    }
}
