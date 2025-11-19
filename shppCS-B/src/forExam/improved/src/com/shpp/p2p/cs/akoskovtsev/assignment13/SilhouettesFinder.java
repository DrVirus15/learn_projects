package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

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
     * Counts the number of valid silhouettes in the given image.
     *
     * @param image - the input image
     * @return - the count of valid silhouettes
     */
    public int countSilhouettes(BufferedImage image) {
        List<Integer> silhouettes = findSilhouettes(createSilhouetteMask(image));
        return countValidSilhouettes(silhouettes);
    }

    /**
     * Creates a silhouette mask from the given image.
     *
     * @param image - the input image
     * @return - a 2D boolean array where true indicates a background pixel and false indicates silhouette pixel
     */
    private boolean[][] createSilhouetteMask(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] argbArray = new int[width * height];
        int backgroundARGB = new BackgroundFinder().findBackground(argbArray);
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), argbArray, 0, image.getWidth());
        return new Eraser().separateSilhouettesMask(argbArray, width, height, backgroundARGB);
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
        int height = silhouetteMask.length;
        int width = silhouetteMask[0].length;
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
