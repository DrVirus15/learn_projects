package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;


/**
 * A class responsible for erasing parts of silhouettes in a binary mask.
 */
public class Eraser {

    /**
     * Separates silhouettes in the given silhouette mask by erasing pixels near the edges.
     *
     * @param silhouetteMask the binary mask representing silhouettes
     * @param radius         the radius around each pixel to check for edge proximity
     * @return a new binary mask with separated silhouettes
     */
    public boolean[][] separateSilhouettesMask(boolean[][] silhouetteMask, int radius) {
        int width = silhouetteMask[0].length;
        int height = silhouetteMask.length;
        boolean[][] erasedImageMask = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (silhouetteMask[row][col] && isNeedsErasing(col, row, radius, silhouetteMask)) {
                    erasedImageMask[row][col] = false;
                } else {
                    erasedImageMask[row][col] = silhouetteMask[row][col];
                }
            }
        }
        return erasedImageMask;
    }

    /**
     * Determines if a pixel needs to be erased based on its proximity to the edge of a silhouette.
     *
     * @param col          the column index of the pixel
     * @param row          the row index of the pixel
     * @param radius       the radius around the pixel to check
     * @param isSilhouette the binary mask representing silhouettes
     * @return true if the pixel needs to be erased, false otherwise
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
        for (int x = xMin; x <= xMax; x++) {
            if (!isSilhouette[yMin][x] || !isSilhouette[yMax][x]) return true;
        }
        return false;
    }
}