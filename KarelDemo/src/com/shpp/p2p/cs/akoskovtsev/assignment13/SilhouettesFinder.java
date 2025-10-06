package com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;

public class SilhouettesFinder {
    private final int ACCURACY_PERCENTAGE;
    private final int width;
    private final int height;
    private final BufferedImage image;


    public SilhouettesFinder(BufferedImage image, int ACCURACY_PERCENTAGE) {
        this.image = image;
        this.ACCURACY_PERCENTAGE = ACCURACY_PERCENTAGE;
        width = image.getWidth();
        height = image.getHeight();
    }

    public int findSilhouettes(double MIN_SILHOUETTE_PERCENTAGE) {
        boolean[][] visited = new boolean[height][width];
        BackgroundFinder finder = new BackgroundFinder(image, ACCURACY_PERCENTAGE);
        SimilarPixelFinder similarPixelFinder = new SimilarPixelFinder(ACCURACY_PERCENTAGE);
        int backgroundPix = finder.findBackground();
        BFSSearcher bfs = new BFSSearcher(finder, image, backgroundPix);
        int silhouettes = 0;
        double minPixelsForSilhouette = (height * width) * MIN_SILHOUETTE_PERCENTAGE / 100.0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if(!visited[row][col] && !similarPixelFinder.isPixelSimilar(image.getRGB(col, row), backgroundPix)) {
                    int countOfPixels = bfs.startBFS(visited, row, col);
                    if(countOfPixels > minPixelsForSilhouette) {
                        silhouettes++;
                    }
                }
            }
        }
        return silhouettes;
    }
}
