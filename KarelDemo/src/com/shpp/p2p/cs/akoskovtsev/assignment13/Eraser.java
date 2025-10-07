package com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Eraser {
    private final BufferedImage image;
    private final int backgroundPix;
    public BufferedImage erasedImage;

    public Eraser(BufferedImage image, int backgroundARGB) {
        this.image = image;
        this.backgroundPix = backgroundARGB;
    }


    public BufferedImage eraseSlips() {

        int dalnostPixel = searchMaxSilhouetteSize();

        boolean[][] isBackground = new boolean[image.getHeight()][image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                isBackground[y][x] = SimilarPixelFinder.isPixelSimilar(image.getRGB(x, y), backgroundPix);
            }
        }

        erasedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (!isBackground[y][x] && isPixHaveToErase(x, y, dalnostPixel, isBackground)) {
                    erasedImage.setRGB(x, y, backgroundPix);
                } else {
                    erasedImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return erasedImage;
    }

    private int searchMaxSilhouetteSize() {
        SilhouettesFinder silhouettesFinder = new SilhouettesFinder(image);
        ArrayList<Integer> silhouettes = silhouettesFinder.findSilhouettes(image, backgroundPix);
        int max = silhouettesFinder.findBigSilhouette(silhouettes);
        int result = Math.max(3, max / 6000);
        System.out.println("max siluete count pix: " + max + ", result: " + result);
        return result;
    }

    private boolean isPixHaveToErase(int col, int row, int dalnostPixel, boolean[][] isBackground) {
        int yMin = Math.max(0, row - dalnostPixel);
        int yMax = Math.min(image.getHeight() - 1, row + dalnostPixel);
        int xMin = Math.max(0, col - dalnostPixel);
        int xMax = Math.min(image.getWidth() -1, col + dalnostPixel);


        for (int y = yMin; y <= yMax; y++) {
            if (isBackground[y][xMin] || isBackground[y][xMax]) {
                return true;
            }
        }
        for (int x = xMin; x < xMax; x++) {
            if(isBackground[yMin][x] || isBackground[yMax][x]) {
                return true;
            }
        }
        return false;
    }
}
