package com.shpp.p2p.cs.akoskovtsev.assignment13;


public class SimilarPixelFinder {
    /**
     * Percentage of accuracy
     */
    private static final int ACCURACY_PERCENTAGE = 20;

    /**
     * max brightness of pixel (0-255)
     */
    private static final int MAX_BRIGHTNESS = 255;
    private static final double SQR_DELTA;

    static {
        double delta = (double) ACCURACY_PERCENTAGE * MAX_BRIGHTNESS / 100.0;
        SQR_DELTA = delta * delta;
        System.out.println(SQR_DELTA);
    }

    public SimilarPixelFinder() {
    }

    public static boolean isPixelSimilar(int pixel, int backgroundARGB) {
        double diffA = (double) ((pixel >> 24) & 0xFF) - ((backgroundARGB >> 24) & 0xFF);
        double diffR = (double) ((pixel >> 16) & 0xFF) - ((backgroundARGB >> 16) & 0xFF);
        double diffG = (double) ((pixel >> 8) & 0xFF) - ((backgroundARGB >> 8) & 0xFF);
        double diffB = (double) (pixel & 0xFF) - (backgroundARGB & 0xFF);
        double distanceSquared = (diffA * diffA) + (diffR * diffR) + (diffG * diffG) + (diffB * diffB);
        return distanceSquared <= SQR_DELTA;
    }
}
