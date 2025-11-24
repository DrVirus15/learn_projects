package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to find and count silhouettes in a given image.
 */
public class SilhouettesFinder {

    public List<Integer> findSilhouettes(boolean[][] silhouetteMask) {
        int height = silhouetteMask.length;
        int width = silhouetteMask[0].length;
        boolean[][] visited = new boolean[height][width];
        BFSSearcher bfs = new BFSSearcher(silhouetteMask);
        List<Integer> silhouetteSizes = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // silhouetteMask is true for background pixels, so we start BFS on false (silhouette) pixels
                if (!visited[row][col] && silhouetteMask[row][col]) {
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
