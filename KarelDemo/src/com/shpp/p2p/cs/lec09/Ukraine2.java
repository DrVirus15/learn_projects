package com.shpp.cs.a.lectures.lec09;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Ukraine2 extends WindowProgram {
    /* Make the window large so that we can see more detail. */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 300;


    /* Scaling factor for brightness. */
    private static final double BRIGHTNESS_SCALAR = 0.25;

    /* The name of the cities file. */
    private static final String CITIES_FILE = "assets/shpp-cs-a-uacities-ptypes.txt";

    public void run() {
        ArrayList<GPoint> cities = readCities();
        visualizeCities(cities);
    }

    /**
     * Given a list of all cities, draws a heatmap based on the
     * proximity of all the pixels in the window to that city.
     * This can take some time to finish.
     *
     * @param cities A list of cities .
     */
    private void visualizeCities(ArrayList<GPoint> cities) {
        /* Disable automatic repainting. This substantially
		 * speeds up the program, though no partial output is
		 * shown as the program runs.
		 */
        //getGCanvas().setAutoRepaintFlag(false);

		/* For each pixel, plot a dot at the appropriate
		 * intensity.
		 */
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                double dist = distanceToNearestCity(x, y, cities);
                plotPixel(x, y, getColorForDistance(dist));
            }

			/* Display the percentage of progress so that the
			 * program appears responsive.
			 */
            println(100.0 * x / getWidth() + "%");
        }

		/* Forcibly update the display. */
        //getGCanvas().repaint();
    }

    /**
     * Given a point on the screen, returns the distance, in pixels,
     * from that point to the nearest city.
     *
     * @param x      The X coordinate of the pixel.
     * @param y      The Y coordinate of the pixel.
     * @param cities A list of all cities.
     * @return The distance of (x, y) to the closest city.
     */
    private double distanceToNearestCity(double x, double y,
                                         ArrayList<GPoint> cities) {
		/* Initially, we guess the point is infinitely far away. */
        double minimalDistance = Double.POSITIVE_INFINITY;

		/* For each city, refine our guess of the distance. */
        for (GPoint city : cities) {
            double distance = distanceBetween(x, y, city.getX(), city.getY());
            if (distance < minimalDistance) {
                minimalDistance = distance;
            }
        }
        return minimalDistance;
    }

    /**
     * Loads a list of all cities from a file, returning an
     * ArrayList of all the cities as they would appear on the
     * screen.
     *
     * @return A list of GPoints, with one point per city.
     */
    private ArrayList<GPoint> readCities() {
        ArrayList<GPoint> result = new ArrayList<GPoint>();
        try {
			/* Open the cities file for reading. */
            BufferedReader br = new BufferedReader(new FileReader(CITIES_FILE));

            int counter = 0;

            while (true) {
				/* Read the next entry, which consists of the city, the latitude,
				 * and the longitude.
				 */
                String city = br.readLine();
                String cityType = br.readLine();
                String latitude = br.readLine();
                String longitude = br.readLine();

				/* If no more data is left, stop. */
                if (city == null || latitude == null || longitude == null) {
                    break;
                }

                counter++;
                if (counter % 2 == 0)
                    continue;

//                if (cityType != null && !cityType.equals("PPLA")) {
//                    continue;
//                }


				/* Convert the longitude and latitude to x and y coordinates on the screen.
				 * The calls to Double.parseDouble are necessary here to convert the
				 * string representations of the doubles to actual doubles.
				 */
                double x = longitudeToXCoordinate(Double.parseDouble(longitude));
                double y = latitudeToYCoordinate(Double.parseDouble(latitude));

                result.add(new GPoint(x, y));
            }

            br.close();
        } catch (IOException e) {
			/* TODO: Actual error handling? */
            println("How unpatriotic.");
        }
        return result;
    }

    /**
     * Returns the Euclidean distance between the indicated points.
     *
     * @param x0 The X coordinate of the first point.
     * @param y0 The Y coordinate of the first point.
     * @param x1 The X coordinate of the second point.
     * @param y1 The Y coordinate of the second point.
     * @return The Euclidean distance between those points.
     */
    private double distanceBetween(double x0, double y0, double x1, double y1) {
        double dx = x0 - x1;
        double dy = y0 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Plots a pixel of the given color at the specified (x, y) coordinate.
     *
     * @param x     The X coordinate.
     * @param y     The Y coordinate.
     * @param color The color of the pixel.
     */
    private void plotPixel(double x, double y, Color color) {
		/* Create a 1x1 pixel of the given color. */
        GRect pixel = new GRect(x, y, 1, 1);
        pixel.setFilled(true);
        pixel.setColor(color);
        add(pixel);
    }

    /**
     * Given a raw longitude, returns the screen x coordinate where
     * it should be displayed.
     *
     * @param longitude The longitude in question.
     * @return Where it maps to as an x coordinate.
     */
    private double longitudeToXCoordinate(double longitude) {
        return getWidth() * (longitude - MIN_LONGITUDE) / (MAX_LONGITUDE - MIN_LONGITUDE);
    }

    /**
     * Given a raw latitude, returns the screen y coordinate where
     * it should be displayed.
     *
     * @param latitude The latitude in question.
     * @return Where it maps to as a y coordinate.
     */
    private double latitudeToYCoordinate(double latitude) {
        return getHeight() * (1.0 - (latitude - MIN_LATITUDE) / (MAX_LATITUDE - MIN_LATITUDE));
    }

    /**
     * Given a distance (in degrees), returns a color based on that
     * distance that shows the relative closeness to a city.
     *
     * @param distance The distance to the nearest city.
     * @return A color encoding that intensity.
     */
    private Color getColorForDistance(double distance) {
		/* We need a function to map [0, inf) to [0, 1], so we'll
		 * use the arctangent function.
		 */
        float intensity = 1.0f
                - (float) (Math.atan(distance * BRIGHTNESS_SCALAR)
                            / (Math.PI / 2.0));
        return new Color(intensity, intensity, intensity);
    }

    /* The viewpoint coordinates - the minimum and maximum longitude
     * and latitude.
     */
    // longitude == x
    private static final double MIN_LONGITUDE = 21;
    private static final double MAX_LONGITUDE = 42;

    // latitude == y
    private static final double MAX_LATITUDE = +54;
    private static final double MIN_LATITUDE = +42;

}