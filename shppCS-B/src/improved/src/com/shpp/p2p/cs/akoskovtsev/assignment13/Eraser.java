package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;


/**
 * The Eraser class is responsible for separating silhouettes from the background in an image.
 * It identifies background pixels, estimates an appropriate erase radius based on silhouette sizes,
 * and creates a mask that represents the separated silhouettes.
 */
public class Eraser {

    public boolean[][] separateSilhouettesMask(boolean[][] backgroundMask, int radius) {
        int width = backgroundMask[0].length;
        int height = backgroundMask.length;

        boolean[][] erasedImageBgMask = new boolean[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (backgroundMask[row][col] && isNeedsErasing(col, row, radius, backgroundMask)) {
                    erasedImageBgMask[row][col] = true;
                } else {
                    erasedImageBgMask[row][col] = backgroundMask[row][col];
                }
            }
        }
        return erasedImageBgMask;
    }

    /**
     * Determines if a pixel at (col, row) needs to be erased based on its proximity to background pixels
     * within a specified radius.
     *
     * @param col          - the column index of the pixel
     * @param row          - the row index of the pixel
     * @param radius       - the radius to check for background pixels
     * @param isSilhouette - a 2D boolean array where true indicates a background pixel
     * @return - true if the pixel needs to be erased, false otherwise
     */
    private boolean isNeedsErasing(int col, int row, int radius, boolean[][] isSilhouette) {
        int width = isSilhouette[0].length;
        int height = isSilhouette.length;
        int yMin = Math.max(0, row - radius);
        int yMax = Math.min(height - 1, row + radius);
        int xMin = Math.max(0, col - radius);
        int xMax = Math.min(width - 1, col + radius);
        for (int y = yMin; y <= yMax; y++) {
            if (!isSilhouette[y][xMin] || !isSilhouette[y][xMax]) return true;
        }
        for (int x = xMin; x < xMax; x++) {
            if (!isSilhouette[yMin][x] || !isSilhouette[yMax][x]) return true;
        }
        return false;
    }
}
