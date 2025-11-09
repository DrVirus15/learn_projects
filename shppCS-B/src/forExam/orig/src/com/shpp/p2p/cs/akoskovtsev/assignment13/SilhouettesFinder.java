package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to find and count silhouettes in a given image.
 */
public class SilhouettesFinder {
    /**
     * The ratio to filter out noise (small silhouettes).
     * Silhouettes smaller than this ratio of the largest silhouette will be ignored.
     * For example, with a ratio of 0.08, silhouettes smaller than 8% of the largest silhouette will be considered noise.
     */
    private static final double NOISE_FILTER_RATIO = 0.08;
    /**
     * The image in which silhouettes are to be found.
     */
    private final BufferedImage image;

    /**
     * Constructor to initialize the SilhouettesFinder with the image.
     *
     * @param image - the image to be analyzed
     */
    public SilhouettesFinder(BufferedImage image) {
        this.image = image;
    }

    /**
     * Counts the number of valid silhouettes in the image.
     * It creates a silhouette mask, finds all silhouettes, and filters out noise.
     *
     * @return - the count of valid silhouettes
     */
    public int countSilhouettes() {
        List<Integer> silhouettes = findSilhouettes(createSilhouetteMask());
        return countValidSilhouettes(silhouettes);
    }

    /**
     * Creates a silhouette mask by separating silhouettes from the background.
     *
     * @return - a 2D boolean array where true indicates a background pixel and false indicates silhouette pixel
     */
    private boolean[][] createSilhouetteMask() {
        Eraser erase = new Eraser(image, new BackgroundFinder(image).findBackground());
        return erase.separateSilhouettesMask();
    }

    /**
     * Counts the number of valid silhouettes by filtering out small ones considered as noise.
     *
     * @param silhouettes - a list of silhouette sizes
     * @return - the count of valid silhouettes
     */
    private int countValidSilhouettes(List<Integer> silhouettes) {
        int largestSilhouetteSize = findLargestSilhouetteSize(silhouettes);
        int minValidSize = (int) (largestSilhouetteSize * NOISE_FILTER_RATIO);
        int silhouettesCount = 0;
        for (Integer silhouette : silhouettes) {
            if (silhouette > minValidSize) {
                silhouettesCount++;
            }
        }
        return silhouettesCount;
    }

    /**
     * Finds all silhouettes in the image using BFS and returns their sizes.
     *
     * @param silhouetteMask - a 2D boolean array where true indicates a background pixel and false indicates silhouette pixel
     * @return - a list of silhouette sizes
     */
    public List<Integer> findSilhouettes(boolean[][] silhouetteMask) {
        int width = image.getWidth();
        int height = image.getHeight();
        boolean[][] visited = new boolean[height][width];
        BFSSearcher bfs = new BFSSearcher(silhouetteMask);
        List<Integer> silhouetteSizes = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // silhouetteMask is true for background pixels, so we start BFS on false (silhouette) pixels
                if (!visited[row][col] && !silhouetteMask[row][col]) {
                    silhouetteSizes.add(bfs.runBFS(visited, row, col));
                }
            }
        }
        return silhouetteSizes;
    }

    /**
     * Finds the size of the largest silhouette from the list of silhouette sizes.
     * @param silhouettes - a list of silhouette sizes
     * @return - the size of the largest silhouette
     */
    public int findLargestSilhouetteSize(List<Integer> silhouettes) {
        int maxSize = 0;
        for (Integer silhouette : silhouettes) {
            maxSize = Math.max(silhouette, maxSize);
        }
        return maxSize;
    }
}
