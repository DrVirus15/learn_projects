package com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * This clas identifies the most frequent (dominant) color group in an image,treating it as the background.
 * The **accuracyPercentage** defines the color tolerance (delta) for grouping similar pixels.
 * The returned value is the representative color of the largest group.
 */
public class BackgroundFinder {
    private final BufferedImage IMAGE; // The image to be processed




    public BackgroundFinder(BufferedImage image) {
        this.IMAGE = image;
    }


    /**
     * Finds the most frequent color in the image, treating it as the background color.
     * It groups similar colors based on the accuracy percentage.
     *
     * @return - the ARGB value of the identified background color
     */
    public int findBackground() {
        HashMap<Integer, Integer> colorMap = new HashMap<>(); // Map to store color frequencies
        for (int row = 0; row < IMAGE.getHeight(); row++) {
            outer:                              // TODO remove it
            for (int col = 0; col < IMAGE.getWidth(); col++) {
                int pixel = IMAGE.getRGB(col, row);
                for (Integer existingColor : colorMap.keySet()) {
                    if (SimilarPixelFinder.isPixelSimilar(pixel, existingColor)) {
                        colorMap.put(existingColor, colorMap.get(existingColor) + 1);
                        continue outer;
                    }
                }
                colorMap.put(pixel, 1);
            }
        }
        int numberOfPixels = 0;
        int backgroundPixel = 0;

        for (Map.Entry<Integer, Integer> entry : colorMap.entrySet()) {
            if (entry.getValue() > numberOfPixels) {
                numberOfPixels = entry.getValue();
                backgroundPixel = entry.getKey();
            }
        }

        return backgroundPixel;
    }
}
