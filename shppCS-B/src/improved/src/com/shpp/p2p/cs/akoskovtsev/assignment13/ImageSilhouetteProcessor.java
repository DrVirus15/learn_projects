package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImageSilhouetteProcessor {
    /**
     * The ratio to filter out noise (small silhouettes).
     * Silhouettes smaller than this ratio of the largest silhouette will be ignored.
     * For example, with a ratio of 0.05, silhouettes smaller than 5% of the largest silhouette will be considered noise.
     */
    private static final double NOISE_FILTER_RATIO = 0.05;

    /**
     * Scale factor to determine the erase radius based on the largest silhouette size.
     * Radius increases in 1 pixel for every 6000 pixels in the largest silhouette.
     */
    private static final double ERASE_RADIUS_SCALE = 1.0 / 6000;

    /**
     * Minimum erase radius to ensure some level of erasing even for small silhouettes.
     */
    private static final int MIN_ERASE_RADIUS = 3;

    private final SilhouettesFinder silhouettesFinder;
    private final BackgroundFinder backgroundFinder;
    private final Eraser eraser;

    public ImageSilhouetteProcessor() {
        this.silhouettesFinder = new SilhouettesFinder();
        this.backgroundFinder = new BackgroundFinder();
        this.eraser = new Eraser();
    }

    public int processImageAndCountSilhouettes(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] argbArray = new int[width * height];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), argbArray, 0, image.getWidth());

        int backgroundARGB = backgroundFinder.findBackground(argbArray);
        boolean[][] silhouetteMask = createSilhouetteMask(argbArray, backgroundARGB, width, height);
        List<Integer> silhouettes = silhouettesFinder.findSilhouettes(silhouetteMask);
        int maxSizeOfSilhouette = silhouettesFinder.findLargestSilhouetteSize(silhouettes);
        int radius = Math.max(MIN_ERASE_RADIUS, (int) (maxSizeOfSilhouette * ERASE_RADIUS_SCALE));
        boolean[][] separatedSilhouetteMask = eraser.separateSilhouettesMask(silhouetteMask, radius);
        List<Integer> separatedSilhouettes = silhouettesFinder.findSilhouettes(separatedSilhouetteMask);
        int minValidSize = (int) (maxSizeOfSilhouette * NOISE_FILTER_RATIO);
        return silhouettesFinder.countValidSilhouettes(separatedSilhouettes, minValidSize);
    }

    private boolean[][] createSilhouetteMask(int[] argbArray, int backgroundARGB, int width, int height) {
        boolean[][] mask = new boolean[height][width];
        for (int i = 0; i < argbArray.length; i++) {
            mask[i / width][i % width] = SimilarPixelFinder.isPixelSimilar(argbArray[i], backgroundARGB);
        }
        return mask;
    }
}
