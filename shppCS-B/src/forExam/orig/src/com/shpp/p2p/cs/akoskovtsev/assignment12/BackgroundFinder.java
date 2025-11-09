package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment12;

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
    private static final int MAX_BRIGHTNESS = 255; // Maximum value for a color channel (0-255)
    private final BufferedImage image; // The image to be processed
    private final int delta; // The tolerance for color comparison

    /**
     * Constructor to initialize the BackgroundFinder with an image and accuracy percentage.
     *
     * @param image              - the image to be analyzed
     * @param accuracyPercentage - the percentage defining color tolerance (0-100)
     */
    public BackgroundFinder(BufferedImage image, int accuracyPercentage) {
        this.image = image;
        delta = accuracyPercentage * MAX_BRIGHTNESS / 100;
    }

    /**
     * Compares two pixels to determine if they are similar within the defined delta
     * witch is based on **accuracyPercentage**.
     *
     * @param pixel         - the ARGB value of the pixel to compare
     * @param backgroundRGB - the ARGB value of the background pixel
     * @return - true if the pixels are similar, false otherwise
     */
    public boolean isPixelSimilar(int pixel, int backgroundRGB) {
        Color pixelColor = new Color(pixel, true);
        Color backgroundColor = new Color(backgroundRGB, true);

        // Compare each color channel (red, green, blue, alpha) with background color within the delta
        boolean isRed = Math.abs(pixelColor.getRed() - backgroundColor.getRed()) <= delta;
        boolean isGreen = Math.abs(pixelColor.getGreen() - backgroundColor.getGreen()) <= delta;
        boolean isBlue = Math.abs(pixelColor.getBlue() - backgroundColor.getBlue()) <= delta;
        boolean isAlpha = Math.abs(pixelColor.getAlpha() - backgroundColor.getAlpha()) <= delta;

        return isRed && isGreen && isBlue && isAlpha;
    }


    /**
     * Finds the most frequent color in the image, treating it as the background color.
     * It groups similar colors based on the accuracy percentage.
     *
     * @return - the ARGB value of the identified background color
     */
    public int findBackground() {
        HashMap<Integer, Integer> colorMap = new HashMap<>(); // Map to store color frequencies
        for (int row = 0; row < image.getHeight(); row++) {
            outer:
            for (int col = 0; col < image.getWidth(); col++) {
                int pixel = image.getRGB(col, row);
                // Check if the pixel is similar to any existing color in the map
                for (Integer existingColor : colorMap.keySet()) {
                    if (isPixelSimilar(pixel, existingColor)) {
                        // If similar, increment the count for that color group and continue to next pixel
                        colorMap.put(existingColor, colorMap.get(existingColor) + 1);
                        continue outer;
                    }
                }
                // If no similar color found, add the new color to the map with a count of 1
                colorMap.put(pixel, 1);
            }
        }
        int numberOfPixels = 0;
        int backgroundPixel = 0;
        // Find the color with the maximum count in the map
        for (Map.Entry<Integer, Integer> entry : colorMap.entrySet()) {
            if (entry.getValue() > numberOfPixels) {
                numberOfPixels = entry.getValue();
                backgroundPixel = entry.getKey();
            }
        }

        return backgroundPixel;
    }
}
