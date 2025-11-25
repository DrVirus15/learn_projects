package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;


import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This class performs a breadth-first search (BFS) on a 2D boolean array to find connected components.
 * It uses a queue to explore all neighboring pixels of a given starting pixel.
 */
public class BFSSearcher {
    /**
     * The silhouette mask representing the image, where true indicates a silhouette pixel
     * and false indicates background.
     */
    private final boolean[][] silhouetteMask;
    /**
     * Directions for the 8 neighboring pixels (up, down, left, right, and diagonals)
     */
    private static final int[][] NEIGHBOR_DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };

    /**
     * Constructor to initialize the BFSSearcher with the silhouette mask.
     * @param silhouetteMask - a 2D boolean array representing the silhouette mask
     */
    public BFSSearcher(boolean[][] silhouetteMask) {
        this.silhouetteMask = silhouetteMask;
    }

    /**
     * Runs a BFS starting from the specified pixel (startRow, startCol) and counts all connected pixels
     * that are not part of the background.
     *
     * @param isVisited - a 2D boolean array to track visited pixels
     * @param startRow  - the row index of the starting pixel
     * @param startCol  - the column index of the starting pixel
     * @return - the count of connected pixels that are part of the silhouette
     */
    public int runBFS(boolean[][] isVisited, int startRow, int startCol) {
        ArrayDeque<ImagePoint> queue = new ArrayDeque<>();
        queue.offer(new ImagePoint(startRow, startCol));
        isVisited[startRow][startCol] = true;
        int size = 0;
        while (!queue.isEmpty()) {
            ImagePoint point = queue.poll();
            size++;
            addNeighborsToQueue(point.row(), point.col(), queue, isVisited);
        }
        return size;
    }

    /**
     * Adds all valid neighboring pixels to the queue for further exploration.
     *
     * @param row       - the row index of the current pixel
     * @param col       - the column index of the current pixel
     * @param queue     - the queue to add neighboring pixels to
     * @param isVisited - a 2D boolean array to track visited pixels
     */
    private void addNeighborsToQueue(int row, int col, Queue<ImagePoint> queue, boolean[][] isVisited) {
        for (int[] dir : NEIGHBOR_DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValid(newRow, newCol, isVisited)) {
                isVisited[newRow][newCol] = true;
                queue.offer(new ImagePoint(newRow, newCol));
            }
        }
    }

    /**
     * Checks if the given pixel is valid for exploration.
     * A pixel is considered valid if it is within bounds, not visited, and part of the silhouette.
     * @param row - the row index of the pixel
     * @param col - the column index of the pixel
     * @param isVisited - a 2D boolean array to track visited pixels
     * @return - true if the pixel is valid, false otherwise
     */
    private boolean isValid(int row, int col, boolean[][] isVisited) {
        return isInBounds(row, col) && !isVisited[row][col] && silhouetteMask[row][col];
    }

    /**
     * Checks if the given row and column indices are within the bounds of the background mask.
     *
     * @param row - the row index of the pixel
     * @param col - the column index of the pixel
     * @return - true if the indices are within bounds, false otherwise
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < silhouetteMask.length && col < silhouetteMask[0].length;
    }
}
