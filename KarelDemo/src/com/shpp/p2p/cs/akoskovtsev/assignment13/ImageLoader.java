package com.shpp.p2p.cs.akoskovtsev.assignment13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A utility class for loading images from the file system.
 */
public class ImageLoader {
    /**
     * The path to the image file.
     */
    private final String filePath;

    /**
     * Constructor to initialize the ImageLoader with the specified file path.
     * @param filePath - the path to the image file
     */
    public ImageLoader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the image from the specified file path.
     * @return - the loaded BufferedImage
     * @throws IOException - if the file cannot be read or is not a valid image
     */
    public BufferedImage load() throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        if (image == null) {
            throw new IOException("Unsupported image format or corrupted file: " + filePath);
        }
        return image;
    }
}
