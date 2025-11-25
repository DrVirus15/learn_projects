package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;

/**
 * A utility class to build a silhouette mask from a given image.
 */
public class MaskBuilder {

    /**
     * Builds a silhouette mask from the provided image.
     *
     * @param image - the input BufferedImage
     * @return - a 2D boolean array representing the silhouette mask
     */
    public static boolean[][] buildSilhouetteMask(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] argbArray = extractArgbArray(image);
        int backgroundARGB = new BackgroundFinder().findBackground(argbArray);
        boolean[][] silhouetteMask = new boolean[height][width];
        for (int i = 0; i < argbArray.length; i++) {
            silhouetteMask[i / width][i % width] = !SimilarPixelFinder.isPixelSimilar(argbArray[i], backgroundARGB);
        }
        return silhouetteMask;
    }

    /**
     * Extracts the ARGB values from the given BufferedImage into a one-dimensional array.
     *
     * @param image - the input BufferedImage
     * @return - an array of ARGB values
     */
    private static int[] extractArgbArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] argbArray = new int[width * height];
        image.getRGB(0, 0, width, height, argbArray, 0, width);
        return argbArray;
    }
}