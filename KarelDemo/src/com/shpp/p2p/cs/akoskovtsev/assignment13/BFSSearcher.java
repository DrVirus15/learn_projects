package com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class performs a Depth-First Search (DFS) on a given image to identify pixels
 * that are part of a silhouette, based on a specified background pixel.
 * It uses recursion to explore all connected pixels that are not part of the background.
 */
public class BFSSearcher {
    private final int BACKGROUND_PIXEL; // The ARGB value of the background pixel
    private final BufferedImage IMAGE; // The image to be processed


    public BFSSearcher(BufferedImage image, int pixel) {
        this.IMAGE = image;
        this.BACKGROUND_PIXEL = pixel;
    }

    /**
     * Starts the DFS from a given pixel (row, col) and counts all connected pixels
     * that are not part of the background.
     *
     * @param isVisited - a 2D boolean array to track visited pixels
     * @param row       - the row index of the starting pixel
     * @param col       - the column index of the starting pixel
     * @return - the count of connected pixels that are part of the silhouette
     */
    public int startBFS(boolean[][] isVisited, int row, int col) {
        ImagePoint point = new ImagePoint(row, col);
        Queue<ImagePoint> queue = new LinkedList<>();
        isVisited[row][col] = true;
        queue.offer(point);
        int countOfPixels = 0;
        while (!queue.isEmpty()) {
            point = queue.poll();
            row = point.getX();
            col = point.getY();
            countOfPixels++;
            findBreads(row, col, queue, isVisited);
        }

        return countOfPixels;
    }

    private void findBreads(int row, int col, Queue<ImagePoint> queue, boolean[][] isVisited) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                int newRow = row + x;
                int newCol = col + y;
                if (isValid(newRow, newCol, isVisited)) {
                    isVisited[newRow][newCol] = true;
                    queue.offer(new ImagePoint(newRow, newCol));
                }
            }
        }
    }

    private boolean isValid(int row, int col, boolean[][] isVisited) {
        return  isInBounds(row, col)&& // Check for out-of-bounds
                !isVisited[row][col] && // Check if already visited
                !SimilarPixelFinder.isPixelSimilar(IMAGE.getRGB(col, row), BACKGROUND_PIXEL); // TODO передавать колір а не піксель (або наоборот)
    }
    private boolean isInBounds(int row, int col){
        return row >= 0 && col >= 0 && row < IMAGE.getHeight() && col < IMAGE.getWidth();
    }

}
