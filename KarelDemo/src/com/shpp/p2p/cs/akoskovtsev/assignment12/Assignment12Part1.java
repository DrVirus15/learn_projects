package com.shpp.p2p.cs.akoskovtsev.assignment12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This program analyzes an image to identify and count silhouettes against a background.
 * It uses a specified accuracy percentage to determine color similarity and a minimum silhouette size
 * to filter out noise. The main components include reading the image, finding the background color,
 * and performing a depth-first search (DFS) to count connected components that differ from the background.
 */
public class Assignment12Part1 {

    // Percentage defining color tolerance for background detection (0-100)
    // Higher values mean more tolerance.
    // Lower values mean stricter color matching.
    private static final int ACCURACY_PERCENTAGE = 20;
    // Minimum percentage of total pixels for a component to be considered a silhouette (0-100)
    // 0.5 means 0.5% of the total image pixels
    // all smaller components will be ignored as noise
    private static final double MIN_SILHOUETTE_PERCENTAGE = 0.5;

    /**
     * The main method reads an image file and counts the number of silhouettes present.
     * If no file path is provided as a command-line argument, it defaults to "test.jpg".
     *
     * @param args - command-line arguments, where args[0] can be the image file path.
     */
    public static void main(String[] args) {
        String filePath = (args.length > 0 && !args[0].isEmpty()) ? args[0] : "test.jpg";
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            if (image == null) {
                System.err.println("The file is not a valid image.");
                return;
            }
            System.out.println(findSilhouettes(image));
        } catch (IOException e) {
            System.err.println("Error reading the image file: " + e.getMessage());
        }
    }

    /**
     * Finds and counts the number of silhouettes in the given image.
     *
     * @param image - the image to be analyzed
     * @return - the count of identified silhouettes
     */
    private static int findSilhouettes(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        // 2D array to track visited pixels during DFS
        boolean[][] visited = new boolean[height][width];
        // Initialize the BackgroundFinder with the image and accuracy percentage
        BackgroundFinder finder = new BackgroundFinder(image, ACCURACY_PERCENTAGE);
        int backgroundPix = finder.findBackground();
        int silhouettes = 0;
        DFSSearcher dfs = new DFSSearcher(finder, image, backgroundPix);
        // Calculate the minimum number of pixels required for a component to be considered a silhouette
        double minPixelsForSilhouette = (height * width) * MIN_SILHOUETTE_PERCENTAGE / 100.0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!visited[row][col] && !finder.isPixelSimilar(image.getRGB(col, row), backgroundPix)) {
                    int countOfPixels = dfs.startDFS(visited, row, col);
                    // If the connected component has enough pixels, count it as a silhouette
                    if (countOfPixels > minPixelsForSilhouette) {
                        silhouettes++;
                    }
                }
            }
        }
        return silhouettes;
    }
}
