package com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;

public class Eraser {
    private final BufferedImage image;
    private BackgroundFinder backgroundFinder;
    private int backgroundColor;
    private final int dalnostPixel = 6;
    public BufferedImage erasedImage;

    public Eraser(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage eraseSlips() {
        erasedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backgroundFinder = new BackgroundFinder(image, 80);
        backgroundColor = backgroundFinder.findBackground();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if(!backgroundFinder.isPixelSimilar(image.getRGB(x, y), backgroundColor) && isPixHaveToErase(x, y)){
                    erasedImage.setRGB(x, y, backgroundColor);
                } else {
                    erasedImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return erasedImage;
    }

    private boolean isPixHaveToErase(int col, int row) {
        for (int y = -dalnostPixel; y < dalnostPixel; y++) {
            for (int x = -dalnostPixel; x < dalnostPixel; x++) {
                 int newRow = row + y;
                 int newCol = col + x;
                 if (newRow < 0 || newRow >= image.getHeight() || newCol < 0 || newCol >= image.getWidth()) {
                     continue; // Skip out-of-bounds pixels
                 }
                 // Check if the pixel is similar to the background color
                 if(backgroundFinder.isPixelSimilar(image.getRGB(newCol, newRow), backgroundColor)){
                        return true;
                 }
            }
        }
        return false;
    }
}
