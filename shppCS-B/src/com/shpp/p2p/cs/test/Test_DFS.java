package com.shpp.p2p.cs.test;

import improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13.Assignment13Part1;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Test_DFS {
    public String filePaths = "assets/1_4.png" +
            "\nassets/4.jpg" +
            "\nassets/4.png" +
            "\nassets/8.png" +
            "\nassets/18.png" +
            "\nassets/21.jpg" +
            "\nassets/binarysuperbeerman.jpg" +
            "\nassets/butterfly.png" +
            "\nassets/butterr.jpg" +
            "\nassets/football.jpg" +
            "\nassets/image.png" +
            "\nassets/image2.png" +
            "\nassets/image3.png" +
            "\nassets/image4.png" +
            "\nassets/image5.png" +
            "\nassets/my_image.png" +
            "\nassets/photo.jpg" +
            "\nassets/s.jpg" +
            "\nassets/s_2.png" +
            "\nassets/Silhouettes.png" +
            "\nassets/SPOILER2.png" +
            "\nassets/SPOILER_test2.jpg" +
            "\nassets/stanga.jpg" +
            "\nassets/test.png" +
            "\nassets/test12.jpeg" +
            "\nassets/test12.png" +
            "\nassets/test_4.jpg" +
            "\nassets/test_5.jpg" +
            "\nassets/testReference.png" +
            "\nassets/triangle.png" +
            "\nassets/tx21_1.jpg" +
            "\nassets/tx25_7.jpg" +
            "\nassets/unknown.png" +
            "\nassets/white.jpg" +
            "\nassets/separate/test1.jpg" +
            "\nassets/separate/test2.png" +
            "\nassets/separate/test3.png" +
            "\nassets/separate/test4.png" +
            "\nassets/separate/test5.png" +
            "\nassets/separate/test7.png" +
            "\nassets/separate/test8.png" +
            "\nassets/separate/test9.png";


    public int[] results = {4, 4, 5, 8, 10,11,1, 1, 1, 7, 2,
                            2, 4, 4, 3, 4, 4, 4, 4, 4, 2, 2, 1,
                            4, 2, 6, 4, 1, 2, 3, 1, 7, 8, 0,
                            8, 30,2, 2, 2, 2, 2, 2};


    public static void main(String[] args) {
        Test_DFS test_dfs = new Test_DFS();
        PrintStream originalOut = System.out;
        String[] paths = test_dfs.filePaths.split("\n");
        for (int i = 0; i < paths.length; i++) {
            String[] path = new String[]{paths[i]};
//            String[] filePath = null;

            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);
            String output;
            try {
                Assignment13Part1.main(path);
                ps.flush();
                output = baos.toString().trim();
            } finally {
                System.setOut(originalOut);
            }
            int result = Integer.parseInt(output);
            int expected = test_dfs.results[i];
            if (result != expected) {
                System.err.println("Test failed for " + path[0] + ". Expected: " + expected + ", but got: " + result);
            } else {
                System.out.println("Test passed for " + path[0] + ". Result: " + result);
            }
        }


        //        String filePathForNewFile = "assets/zzz.png";
//        String format = "png";
//        int size = image.getHeight() * image.getWidth();
//        BufferedImage dummyImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
//        int[][] imageDouble = new int[image.getHeight()][image.getWidth()];
//        for (int row = 0; row < image.getHeight(); row++) {
//            for (int col = 0; col < image.getWidth(); col++) {
//                if(processedImage[row][col]){
//                    imageDouble[row][col] = -1;
//                } else {
//                    imageDouble[row][col] = 0;
//                }
//            }
//        }
//        int[] odnovumirniyMassive = new int[size];
//        int index = 0;
//        for (int[] ints : imageDouble) {
//            for (int col = 0; col < imageDouble[0].length; col++) {
//                odnovumirniyMassive[index] = ints[col];
//                index++;
//            }
//        }
//        dummyImage.setRGB(0, 0, image.getWidth(), image.getHeight(),
//                odnovumirniyMassive, 0, image.getWidth());
//        saveImage(dummyImage, filePathForNewFile, format);


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
