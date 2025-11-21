package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to find and count silhouettes in a given image.
 */
public class SilhouettesFinder {

//    /**
//     * Counts the number of valid silhouettes in the image.
//     * It creates a silhouette mask, finds all silhouettes, and filters out noise.
//     *
//     * @param image - the image to be analyzed
//     * @return - the count of valid silhouettes
//     */
//    public int countSilhouettes(BufferedImage image) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        int[] argbArray = new int[width * height];
//        image.getRGB(0, 0, image.getWidth(), image.getHeight(), argbArray, 0, image.getWidth());
////        List<Integer> silhouettes = findSilhouettes(createSeparatedSilhouetteMask(argbArray, width, height));
//        return -1;
//    }
//    /**
//     * Creates a silhouette mask by separating silhouettes from the background.
//     *
//     * @param argbArray - the array of ARGB pixel values
//     * @param width     - the width of the image
//     * @param height    - the height of the image
//     * @return - a 2D boolean array where true indicates a background pixel and false indicates silhouette pixel
//     */
//    private boolean[][] createSeparatedSilhouetteMask(int[] argbArray, int width, int height) {
////        int backgroundARGB = new BackgroundFinder().findBackground(argbArray);
////        int maxSizeOfSilhouette = findMaxSizeOfSilhouette(argbArray, width, height, backgroundARGB);
////        return new Eraser().separateSilhouettesMask(argbArray, width, height, backgroundARGB, maxSizeOfSilhouette);
//        return null;
//    }

//    /**
//     * Finds the maximum size of silhouettes in the image.
//     *
//     * @param argbArray      - the array of ARGB pixel values
//     * @param width          - the width of the image
//     * @param height         - the height of the image
//     * @param backgroundARGB - the ARGB value of the background color
//     * @return - the size of the largest silhouette
//     */
//    private int findMaxSizeOfSilhouette(int[] argbArray, int width, int height, int backgroundARGB) {
//        List<Integer> silhouettes =
//                findSilhouettes(new Eraser().findBackgroundMask(argbArray, backgroundARGB, width, height));
//        return findLargestSilhouetteSize(silhouettes);
//    }

    /**
     * Counts the number of valid silhouettes by filtering out small ones considered as noise.
     *
     * @param silhouettes - a list of silhouette sizes
     * @return - the count of valid silhouettes
     */
    public int countValidSilhouettes(List<Integer> silhouettes, int minValidSize) {
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
     *
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
