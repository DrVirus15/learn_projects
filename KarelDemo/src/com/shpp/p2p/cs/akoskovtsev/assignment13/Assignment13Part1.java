package com.shpp.p2p.cs.akoskovtsev.assignment13;

import com.shpp.p2p.cs.akoskovtsev.assignment12.DFSSearcher;

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
public class Assignment13Part1 {
    /**
     * TODO delete this shit
     */


    private static final String DEFAULT_IMAGE_PATH = "assets/separate/test6.png";


    /**
     * The main method reads an image file and counts the number of silhouettes present.
     * If no file path is provided as a command-line argument, it defaults to "test.jpg".
     *
     * @param args - command-line arguments, where args[0] can be the image file path.
     */
    public static void main(String[] args) {
        String filePath = (args.length > 0 && !args[0].isEmpty()) ? args[0] : DEFAULT_IMAGE_PATH;
        ImageReader imageReader = new ImageReader(filePath);
        BufferedImage image = null;
        try {
            image = imageReader.readImage();
        } catch (RuntimeException e) {
            System.err.println("FATAL ERROR: " + e.getMessage());
        }
        if (image != null) {
            System.out.println(new SilhouettesFinder(image).countSilhouettes());
        }
    }


}
