package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment12;

import java.awt.image.BufferedImage;

/**
 * This class performs a Depth-First Search (DFS) on a given image to identify pixels
 * that are part of a silhouette, based on a specified background pixel.
 * It uses recursion to explore all connected pixels that are not part of the background.
 */
public class DFSSearcher {
    private final int backgroundPixel; // The ARGB value of the background pixel
    private final BufferedImage image; // The image to be processed
    private final BackgroundFinder finder; // Utility to compare pixel colors

    /**
     * Constructor to initialize the DFSSearcher with the necessary components.
     *
     * @param finder - an instance of BackgroundFinder to help with pixel comparison
     * @param image  - the image to be analyzed
     * @param pixel  - the ARGB value of the background pixel
     */
    public DFSSearcher(BackgroundFinder finder, BufferedImage image, int pixel) {
        this.finder = finder;
        this.image = image;
        this.backgroundPixel = pixel;
    }

    /**
     * Starts the DFS from a given pixel (row, col) and counts all connected pixels
     * that are not part of the background.
     *
     * @param isWatched - a 2D boolean array to track visited pixels
     * @param row       - the row index of the starting pixel
     * @param col       - the column index of the starting pixel
     * @return - the count of connected pixels that are part of the silhouette
     */
    public int startDFS(boolean[][] isWatched, int row, int col) {
        if (row < 0 || col < 0 || // Check for out-of-bounds
                row >= image.getHeight() || col >= image.getWidth() || // Check for out-of-bounds
                isWatched[row][col] || // Check if already visited
                finder.isPixelSimilar(image.getRGB(col, row), backgroundPixel)) { // Check if it's a background pixel
            return 0;
        }
        isWatched[row][col] = true; // Mark the pixel as visited
        int countOfPixels = 1; // Count the current pixel

        // Recursively visit all 8 neighboring pixels
        countOfPixels += startDFS(isWatched, row, col - 1);
        countOfPixels += startDFS(isWatched, row, col + 1);
        countOfPixels += startDFS(isWatched, row + 1, col);
        countOfPixels += startDFS(isWatched, row - 1, col);
        countOfPixels += startDFS(isWatched, row + 1, col + 1);
        countOfPixels += startDFS(isWatched, row - 1, col - 1);
        countOfPixels += startDFS(isWatched, row + 1, col - 1);
        countOfPixels += startDFS(isWatched, row - 1, col + 1);

        return countOfPixels;
    }
}
