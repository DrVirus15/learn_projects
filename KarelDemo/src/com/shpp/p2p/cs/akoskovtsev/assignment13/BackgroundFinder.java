package com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BackgroundFinder {
    /**
     * Step for sampling pixels (1 = every pixel, 2 = every second pixel, etc.)
     */
    private static final int SAMPLE_STEP = 2;
    /**
     * The image to be analyzed for background color.
     */
    private final BufferedImage image;
    /**
     * A map to store the frequency of each color in the image.
     */
    private final Map<Integer, Integer> colorFrequencyMap = new HashMap<>();

    /**
     * Constructor to initialize the BackgroundFinder with the image.
     *
     * @param image - the image to be analyzed
     */
    public BackgroundFinder(BufferedImage image) {
        this.image = image;
    }

    /**
     * Finds the most frequent color in the image, which is assumed to be the background color.
     *
     * @return - the ARGB value of the most frequent color (background color)
     */
    public int findBackground() {
        calculateColorFrequencies();
        int numberOfPixels = 0;
        int backgroundPixel = 0;
        for (Map.Entry<Integer, Integer> entry : colorFrequencyMap.entrySet()) {
            if (entry.getValue() > numberOfPixels) {
                numberOfPixels = entry.getValue();
                backgroundPixel = entry.getKey();
            }
        }
        return backgroundPixel;
    }

    /**
     * Calculates the frequency of each color in the image by sampling pixels at defined intervals.
     */
    private void calculateColorFrequencies() {
        for (int row = 0; row < image.getHeight(); row += SAMPLE_STEP) {
            for (int col = 0; col < image.getWidth(); col += SAMPLE_STEP) {
                processPixel(image.getRGB(col, row));
            }
        }
    }

    /**
     * Processes a single pixel, updating the color frequency map.
     *
     * @param pixel - the ARGB value of the pixel to be processed
     */
    private void processPixel(int pixel) {
        for (Map.Entry<Integer, Integer> existing : colorFrequencyMap.entrySet()) {
            if (SimilarPixelFinder.isPixelSimilar(pixel, existing.getKey())) {
                colorFrequencyMap.put(existing.getKey(), existing.getValue() + 1);
                return;
            }
        }
        colorFrequencyMap.put(pixel, 1);
    }
}
