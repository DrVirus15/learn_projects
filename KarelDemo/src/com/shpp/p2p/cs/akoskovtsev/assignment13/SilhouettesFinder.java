package com.shpp.p2p.cs.akoskovtsev.assignment13;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SilhouettesFinder {

    private final double MIN_SIZE_RATIO = 0.08;

    private final int width;
    private final int height;
    private final BufferedImage image;


    public SilhouettesFinder(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }


    public int countSilhouettes(){
        BackgroundFinder finder = new BackgroundFinder(image);
        int backgroundPix = finder.findBackground();
        Eraser erase = new Eraser(image, backgroundPix);
//        boolean[][] imageWithSilhouettes = erase.eraseSlips();
        BufferedImage processedImage = erase.eraseSlips();
        System.out.println("erised. Start to find silyettes.");
        ArrayList<Integer> silhouettes = findSilhouettes(processedImage, backgroundPix);
        int max = findBigSilhouette(silhouettes);
        int noiseThreshold = (int) Math.round(max * MIN_SIZE_RATIO);
        int silhouettesCount = 0;
        for (Integer silhouette : silhouettes) {
            if (silhouette > noiseThreshold) {
                silhouettesCount++;
            }
        }



        String filePathForNewFile = "assets/zzz.png";
        String format = "png";
        int size = image.getHeight() * image.getWidth();
        BufferedImage dummyImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int[][] imageDouble = new int[image.getHeight()][image.getWidth()];
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                imageDouble[row][col] = processedImage.getRGB(col, row);
            }
        }
        int[] odnovumirniyMassive = new int[size];
        int index = 0;
        for (int[] ints : imageDouble) {
            for (int col = 0; col < imageDouble[0].length; col++) {
                odnovumirniyMassive[index] = ints[col];
                index++;
            }
        }
        dummyImage.setRGB(0, 0, image.getWidth(), image.getHeight(),
                odnovumirniyMassive, 0, image.getWidth());
        saveImage(dummyImage, filePathForNewFile, format);

        return silhouettesCount;
    }

    public ArrayList<Integer> findSilhouettes(BufferedImage image, int backgroundPix) {
        boolean[][] visited = new boolean[height][width];
        BFSSearcher bfs = new BFSSearcher(image, backgroundPix);
        ArrayList<Integer> silhouettes = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!visited[row][col] && !SimilarPixelFinder.isPixelSimilar(image.getRGB(col, row), backgroundPix)) {
                    silhouettes.add(bfs.startBFS(visited, row, col));
                }
            }
        }
        return silhouettes;
    }

    public int findBigSilhouette(ArrayList<Integer> silhouettes) {
        if (silhouettes.isEmpty()) return 0;
        int maxPixForOneSilhouettes = 0;
        for (Integer silhouette : silhouettes) {
            maxPixForOneSilhouettes = Math.max(silhouette, maxPixForOneSilhouettes);
        }
        return maxPixForOneSilhouettes;
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
