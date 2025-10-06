package com.shpp.p2p.cs.akoskovtsev.assignment13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {
    private final String filePath;
    BufferedImage image;

    public ImageReader(String filePath) {
        this.filePath = filePath;
    }
    public BufferedImage readImage() {
        try {
            image = ImageIO.read(new File(filePath));
            if (image == null) {
                throw new RuntimeException("The file is not a valid image format at path: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the image file: " + e.getMessage(), e);
        }
        return image;
    }
}
