package com.shpp.p2p.cs.akoskovtsev.assignment13;

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
    private final HashMap<Integer, Integer> map = new HashMap<>();

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
        fillHashMap();
        int numberOfPixels = 0;
        int backgroundPixel = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > numberOfPixels) {
                numberOfPixels = entry.getValue();
                backgroundPixel = entry.getKey();
            }
        }
        return backgroundPixel;
    }

    private void fillHashMap() {
        for (int row = 0; row < IMAGE.getHeight(); row++) {
            for (int col = 0; col < IMAGE.getWidth(); col++) {
                handlePix(IMAGE.getRGB(col, row));
            }
        }
    }

    private void handlePix(int pixel) {
        for (Map.Entry<Integer, Integer> existing : map.entrySet()) {
            if (SimilarPixelFinder.isPixelSimilar(pixel, existing.getKey())) {
                map.put(existing.getKey(), existing.getValue() + 1);
                return;
            }
        }
        map.put(pixel, 1);
    }
}
