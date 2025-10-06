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
    private static final double MIN_SILHOUETTE_PERCENTAGE = 0.55;

    // Default image path if none is provided
    private static final String DEFAULT_IMAGE_PATH = "assets/separate/test1.jpg";


    /**
     * The main method reads an image file and counts the number of silhouettes present.
     * If no file path is provided as a command-line argument, it defaults to "test.jpg".
     *
     * @param args - command-line arguments, where args[0] can be the image file path.
     */
    public static void main(String[] args) {
        String filePath = (args.length > 0 && !args[0].isEmpty()) ? args[0] : DEFAULT_IMAGE_PATH;
        ImageReader imageReader = new ImageReader(filePath);
        try {
            BufferedImage image = imageReader.readImage();
            Eraser erase = new Eraser(image);

            BufferedImage processedImage = erase.eraseSlips();


            System.out.println(new SilhouettesFinder(processedImage).findSilhouettes(MIN_SILHOUETTE_PERCENTAGE));


            String filePathForNewFile = "assets/zzz.png";
            String format = "png";
            int size = image.getHeight()*image.getWidth();
            BufferedImage dummyImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int[][] imageDouble = new int[image.getHeight()][image.getWidth()];
            for (int row = 0; row < image.getHeight(); row++) {
                for (int col = 0; col < image.getWidth(); col++) {
                    imageDouble[row][col] = processedImage.getRGB(col, row);
                }
            }
            int[] odnovumirniyMassive = new int[size];
            int index = 0;
            for (int i = 0; i < imageDouble.length; i++) {
                int[] ints = imageDouble[i];
                for (int col = 0; col < imageDouble[0].length; col++) {
                    odnovumirniyMassive[index] = ints[col];
                    index++;
                }
            }
            dummyImage.setRGB(0, 0, image.getWidth(), image.getHeight(),
                    odnovumirniyMassive, 0, image.getWidth());
            saveImage(dummyImage, filePathForNewFile, format);



        } catch (RuntimeException e) {
            System.err.println("FATAL ERROR: " + e.getMessage());
        }
    }

    private static void saveImage(BufferedImage image, String filePath, String format) {
        try {
            File outputfile = new File(filePath);
            ImageIO.write(image, format, outputfile);
            System.out.println("Зображення успішно збережено за шляхом: " + filePath);
        } catch (IOException e) {
            System.err.println("Помилка при збереженні зображення: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
