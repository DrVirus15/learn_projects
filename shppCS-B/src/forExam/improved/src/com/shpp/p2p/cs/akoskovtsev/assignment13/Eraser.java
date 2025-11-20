package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.util.List;

/**
 * The Eraser class is responsible for separating silhouettes from the background in an image.
 * It identifies background pixels, estimates an appropriate erase radius based on silhouette sizes,
 * and creates a mask that represents the separated silhouettes.
 */
public class Eraser {
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
     * Separates silhouettes from the background in the given ARGB array of the image.
     *
     * @param argbArray     - the array of ARGB pixel values
     * @param width         - the width of the image
     * @param height        - the height of the image
     * @param backgroundARGB - the ARGB value of the background color
     * @return - a 2D boolean array where true indicates a background pixel after erasing near silhouettes
     */
    public boolean[][] separateSilhouettesMask(int[] argbArray, int width, int height, int backgroundARGB) {
        boolean[][] backgroundMask = findBackgroundMask(argbArray, backgroundARGB, width, height);
        int radius = estimateEraseRadius(backgroundMask);
        boolean[][] erasedImageBgMask = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!backgroundMask[row][col] && isNeedsErasing(col, row, radius, backgroundMask)) {
                    erasedImageBgMask[row][col] = true;
                } else {
                    erasedImageBgMask[row][col] = backgroundMask[row][col];
                }
            }
        }
        return erasedImageBgMask;
    }

    /**
     * Creates a background mask from the ARGB array of the image.
     *
     * @param argbArray     - the array of ARGB pixel values
     * @param backgroundARGB - the ARGB value of the background color
     * @param width         - the width of the image
     * @param height        - the height of the image
     * @return - a 2D boolean array where true indicates a background pixel
     */
    private boolean[][] findBackgroundMask(int[] argbArray, int backgroundARGB, int width, int height) {
        boolean[][] backgroundMask = new boolean[height][width];
        for (int i = 0; i < argbArray.length; i++) {
            backgroundMask[i / width][i % width] = SimilarPixelFinder.isPixelSimilar(argbArray[i], backgroundARGB);
        }
        return backgroundMask;
    }

    /**
     * Estimates the erase radius based on the size of the largest silhouette found in the image.
     * The radius is calculated as a fraction of the largest silhouette size, with a minimum threshold
     *
     * @param backgroundMask - a 2D boolean array where true indicates a background pixel
     * @return - the estimated erase radius
     */
    private int estimateEraseRadius(boolean[][] backgroundMask) {
        SilhouettesFinder finder = new SilhouettesFinder();
        List<Integer> silhouettes = finder.findSilhouettes(backgroundMask);
        int maxSize = finder.findLargestSilhouetteSize(silhouettes);
        // Calculate radius based on the largest silhouette size.
        return Math.max(MIN_ERASE_RADIUS, (int) (maxSize * ERASE_RADIUS_SCALE));
    }

    /**
     * Determines if a pixel at (col, row) needs to be erased based on its proximity to background pixels
     * within a specified radius.
     *
     * @param col          - the column index of the pixel
     * @param row          - the row index of the pixel
     * @param radius       - the radius to check for background pixels
     * @param isBackground - a 2D boolean array where true indicates a background pixel
     * @return - true if the pixel needs to be erased, false otherwise
     */
    private boolean isNeedsErasing(int col, int row, int radius, boolean[][] isBackground) {
        int width = isBackground[0].length;
        int height = isBackground.length;
        int yMin = Math.max(0, row - radius);
        int yMax = Math.min(height - 1, row + radius);
        int xMin = Math.max(0, col - radius);
        int xMax = Math.min(width - 1, col + radius);
        for (int y = yMin; y <= yMax; y++) {
            if (isBackground[y][xMin] || isBackground[y][xMax]) return true;
        }
        for (int x = xMin; x < xMax; x++) {
            if (isBackground[yMin][x] || isBackground[yMax][x]) return true;
        }
        return false;
    }
}
