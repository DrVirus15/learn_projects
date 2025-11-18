package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;


/**
 * A utility class to determine if two pixels are similar based on a defined accuracy percentage.
 */
public class SimilarPixelFinder {
    /**
     * The percentage accuracy to determine if two pixels are similar (0-100).
     */
    private static final int ACCURACY_PERCENTAGE = 40;

    /**
     * The maximum brightness value for a color channel (0-255).
     */
    private static final int MAX_BRIGHTNESS = 255;

    /**
     * The squared delta value used for comparison, calculated based on the accuracy percentage.
     */
    private static final int SQUARED_DELTA;

    static {
        int delta = (int) (ACCURACY_PERCENTAGE * MAX_BRIGHTNESS / 100.0);
        SQUARED_DELTA = delta * delta;
    }

    /**
     * Determines if two pixels are similar based on the defined accuracy percentage.
     *
     * @param pixel          - the ARGB value of the pixel to compare
     * @param backgroundARGB - the ARGB value of the background pixel
     * @return - true if the pixels are similar, false otherwise
     */
    public static boolean isPixelSimilar(int pixel, int backgroundARGB) {
        int diffA = (pixel >> 24) & 0xFF - ((backgroundARGB >> 24) & 0xFF);
        int diffR = (pixel >> 16) & 0xFF - ((backgroundARGB >> 16) & 0xFF);
        int diffG = (pixel >> 8) & 0xFF - ((backgroundARGB >> 8) & 0xFF);
        int diffB = pixel & 0xFF - (backgroundARGB & 0xFF);
        int distanceSquared = (diffA * diffA) + (diffR * diffR) + (diffG * diffG) + (diffB * diffB);

        return distanceSquared <= SQUARED_DELTA;
    }
}
