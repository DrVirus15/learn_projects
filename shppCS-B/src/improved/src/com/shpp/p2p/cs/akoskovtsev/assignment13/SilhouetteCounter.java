package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A class to count and separate silhouettes in a given image.
 */
public class SilhouetteCounter {
    /**
     * The ratio to filter out noise (small silhouettes).
     * Silhouettes smaller than this ratio of the largest silhouette will be ignored.
     * For example, with a ratio of 0.02, silhouettes smaller than 2% of the largest silhouette will be considered noise.
     */
    private static final double NOISE_FILTER_RATIO = 0.02;

    /**
     * Scale factor to determine the erase radius based on the largest silhouette size.
     * Radius increases in 1 pixel for every 6000 pixels in the largest silhouette.
     */
    private static final double ERASE_RADIUS_SCALE = 1.0 / 6000;

    /**
     * Minimum erase radius to ensure some level of erasing even for small silhouettes.
     */
    private static final int MIN_ERASE_RADIUS = 3;

    /**
     * Finder for silhouettes in the silhouette mask.
     */
    private final SilhouettesFinder silhouettesFinder;

    /**
     * Constructor initializing the SilhouettesFinder.
     */
    public SilhouetteCounter() {
        this.silhouettesFinder = new SilhouettesFinder();
    }

    /**
     * Processes the given image to count the number of distinct silhouettes.
     *
     * @param image - the input image to be processed
     * @return - the count of distinct silhouettes in the image
     */
    public int processImageAndCountSilhouettes(BufferedImage image) {
        boolean[][] silhouetteMask = MaskBuilder.buildSilhouetteMask(image);
        int largestSilhouetteSize = findLargestSilhouetteSize(silhouetteMask);
        boolean[][] separatedSilhouetteMask = separateSilhouettes(silhouetteMask);
        int minValidSize = (int) (largestSilhouetteSize * NOISE_FILTER_RATIO);
        return countSilhouettes(separatedSilhouetteMask, minValidSize);
    }

    /**
     * Separates silhouettes in the given silhouette mask by erasing pixels near the edges.
     *
     * @param silhouetteMask - the binary mask representing silhouettes
     * @return - a new binary mask with separated silhouettes
     */
    public boolean[][] separateSilhouettes(boolean[][] silhouetteMask) {
        int largestSilhouetteSize = findLargestSilhouetteSize(silhouetteMask);
        int radius = Math.max(MIN_ERASE_RADIUS, (int) (largestSilhouetteSize * ERASE_RADIUS_SCALE));
        return new Eraser().separateSilhouettesMask(silhouetteMask, radius);
    }

    /**
     * Finds the size of the largest silhouette in the given silhouette mask.
     *
     * @param silhouetteMask - a 2D boolean array representing the silhouette mask
     * @return - the size of the largest silhouette
     */
    private int findLargestSilhouetteSize(boolean[][] silhouetteMask) {
        List<Integer> silhouettes = silhouettesFinder.findSilhouettes(silhouetteMask);
        return silhouettesFinder.findLargestSilhouetteSize(silhouettes);
    }

    /**
     * Counts the number of silhouettes in the given silhouette mask that are larger than the specified
     * minimum valid size.
     *
     * @param silhouetteMask - a 2D boolean array representing the silhouette mask
     * @param minValidSize   - the minimum size for a silhouette to be considered valid
     * @return - the count of valid silhouettes
     */
    public int countSilhouettes(boolean[][] silhouetteMask, int minValidSize) {
        List<Integer> silhouettes = silhouettesFinder.findSilhouettes(silhouetteMask);
        int silhouettesCount = 0;
        for (Integer silhouette : silhouettes) {
            if (silhouette > minValidSize) {
                silhouettesCount++;
            }
        }
        return silhouettesCount;
    }
}