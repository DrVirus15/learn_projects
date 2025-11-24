package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.List;

public class SilhouetteCounter {
    /**
     * The ratio to filter out noise (small silhouettes).
     * Silhouettes smaller than this ratio of the largest silhouette will be ignored.
     * For example, with a ratio of 0.02, silhouettes smaller than 2% of the largest silhouette will be considered noise.
     */
    private static final double NOISE_FILTER_RATIO = 0.01;

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

    public SilhouetteCounter() {
        this.silhouettesFinder = new SilhouettesFinder();
    }

    public int processImageAndCountSilhouettes(BufferedImage image) {
        boolean[][] silhouetteMask = buildSilhouetteMask(image);

        boolean[][] separatedSilhouetteMask = separateSilhouettes(silhouetteMask);

        int minValidSize = (int) (findLargestSilhouetteSize(silhouetteMask) * NOISE_FILTER_RATIO);
        return countSilhouettes(separatedSilhouetteMask, minValidSize);
    }

    public boolean[][] separateSilhouettes(boolean[][] silhouetteMask) {
        int radius = Math.max(MIN_ERASE_RADIUS, (int)(findLargestSilhouetteSize(silhouetteMask) * ERASE_RADIUS_SCALE));
        return new Eraser().separateSilhouettesMask(silhouetteMask, radius);
    }
    public boolean[][] buildSilhouetteMask(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] argbArray = new int[width * height];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), argbArray, 0, image.getWidth());
        int backgroundARGB = new BackgroundFinder().findBackground(argbArray);
        return createSilhouetteMask(argbArray, backgroundARGB, width, height);
    }
    private boolean[][] createSilhouetteMask(int[] argbArray, int backgroundARGB, int width, int height) {
        boolean[][] silhouetteMask = new boolean[height][width];
        for (int i = 0; i < argbArray.length; i++) {
            silhouetteMask[i / width][i % width] = !SimilarPixelFinder.isPixelSimilar(argbArray[i], backgroundARGB);
        }
        return silhouetteMask;
    }


    private int findLargestSilhouetteSize(boolean[][] silhouetteMask) {
        List<Integer> silhouettes = silhouettesFinder.findSilhouettes(silhouetteMask);
        return silhouettesFinder.findLargestSilhouetteSize(silhouettes);
    }


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
