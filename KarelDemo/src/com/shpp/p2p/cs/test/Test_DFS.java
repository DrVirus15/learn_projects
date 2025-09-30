package com.shpp.p2p.cs.test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test_DFS {
    public String filePaths = "assets/1_4.png" +
            "\nassets/4.jpg" +
            "\nassets/4.png" +
            "\nassets/8.png" +
            "\nassets/17.jpg" +
            "\nassets/18.png" +
            "\nassets/21.jpg" +
            "\nassets/29.jpg" +
            "\nassets/3333.png" +
            "\nassets/4444.png" +
            "\nassets/binarysuperbeerman.jpg" +
            "\nassets/butterfly.png" +
            "\nassets/butterr.jpg" +
            "\nassets/football.jpg" +
            "\nassets/image.png" +
            "\nassets/image2.png" +
            "\nassets/image3.png" +
            "\nassets/image4.png" +
            "\nassets/image5.png" +
            "\nassets/image6.png" +
            "\nassets/line.png" +
            "\nassets/my_image.png" +
            "\nassets/photo.jpg" +
            "\nassets/rainbow-dash.png" +
            "\nassets/s.jpg" +
            "\nassets/s_2.png" +
            "\nassets/setka2.jpg" +
            "\nassets/Silhouettes.png" +
            "\nassets/SPOILER2.png" +
            "\nassets/SPOILER_test2.jpg" +
            "\nassets/stanga.jpg" +
            "\nassets/test.png" +
            "\nassets/test3.png" +
            "\nassets/test12.jpeg" +
            "\nassets/test12.png" +
            "\nassets/test_4.jpg" +
            "\nassets/test_5.jpg" +
            "\nassets/testReference.png" +
            "\nassets/triangle.png" +
            "\nassets/tx21_1.jpg" +
            "\nassets/tx25_7.jpg" +
            "\nassets/unknown.png";


   public int[] results = {4, 4, 5, 8, 1, 10,9,15,5,2,1,1,1,12,2,2,4,2,7,4,3,4,4,1,4,4,1,4,2,2,1,4,1,5,6,4,1,2,3,1,6,8};


    public static void main(String[] args) {
        Test_DFS testDfs = new Test_DFS();
        System.out.println(testDfs.filePaths);
        testDfs.results[0] = 2;
        testDfs.results[1] = 4;
        testDfs.results[2] = 4;
        testDfs.results[3] = 3;
    }




}
