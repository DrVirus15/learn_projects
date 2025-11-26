package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to find silhouettes in a silhouette mask.
 */
public class SilhouettesFinder {

    /**
     * Finds all silhouettes in the given silhouette mask using BFS and returns their sizes.
     * @param silhouetteMask - a 2D boolean array representing the silhouette mask
     * @return - a list of sizes of all found silhouettes
     */
    public static List<Integer> findSilhouettes(boolean[][] silhouetteMask) {
        int height = silhouetteMask.length;
        int width = silhouetteMask[0].length;
        boolean[][] visited = new boolean[height][width];
        BFSSearcher bfs = new BFSSearcher(silhouetteMask);
        List<Integer> silhouetteSizes = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // If the pixel is part of a silhouette and hasn't been visited yet, perform BFS
                if (!visited[row][col] && silhouetteMask[row][col]) {
                    silhouetteSizes.add(bfs.runBFS(visited, row, col));
                }
            }
        }
        return silhouetteSizes;
    }
}
