package com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.util.Random;

public class KMeansFinder {
    /**
     * Minimal threshold for centroids changes
     */
    final double THRESHOLD = 0.001;
    /**
     * The number of clusters for K-means++ algorithm.
     */
    final int NUMBER_OF_CLUSTERS = 5;

    private final BufferedImage image;
    private final Centroid[] centroids = new Centroid[NUMBER_OF_CLUSTERS];
    private final int imageSize;

    public KMeansFinder(BufferedImage image) {
        this.image = image;
        imageSize = image.getHeight() * image.getWidth();
    }

    public int[] setLabels() {
        findCentroids();
        int[] labels = new int[imageSize];

        boolean converged = false;
        int maxIterations = 100;
        int currentIteration = 0;


        while (!converged && currentIteration < maxIterations) {
            currentIteration++;
            Centroid[] oldCentroids = saveOldCentroids();

            int index = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {

                    double minDistance = Double.MAX_VALUE;
                    int closestCentroidIndex = -1;
                    double Dx;
                    int argbValue = image.getRGB(x, y);
                    int A = (argbValue >> 24) & 0xFF;
                    int R = (argbValue >> 16) & 0xFF;
                    int G = (argbValue >> 8) & 0xFF;
                    int B = argbValue & 0xFF;
                    for (int j = 0; j < centroids.length; j++) {
                        Dx = Math.pow(A - centroids[j].getA(), 2) +
                                Math.pow(R - centroids[j].getR(), 2) +
                                Math.pow(G - centroids[j].getG(), 2) +
                                Math.pow(B - centroids[j].getB(), 2);
                        if(Dx < minDistance){
                            minDistance = Dx;
                            closestCentroidIndex = j;
                        }
                    }
                    labels[index] = closestCentroidIndex;
                    index++;
                }
            }
            updateAllCentroids(labels);
            double totalShift = calculateTotalShift(oldCentroids);
            if (totalShift < THRESHOLD) {
                converged = true;
            }
        }
        return labels;
    }

    private double calculateTotalShift(Centroid[] oldCentroids) {
        double totalShift = 0.0;

        for (int i = 0; i < centroids.length; i++) {
            Centroid oldC = oldCentroids[i];
            Centroid newC = centroids[i];

            // Використовуємо квадрат відстані, але тут краще брати корінь,
            // щоб відстань була в одиницях простору кольорів (0-255).
            double distanceSq = Math.pow(newC.getA() - oldC.getA(), 2) +
                    Math.pow(newC.getR() - oldC.getR(), 2) +
                    Math.pow(newC.getG() - oldC.getG(), 2) +
                    Math.pow(newC.getB() - oldC.getB(), 2);

            totalShift += Math.sqrt(distanceSq); // Додаємо справжню (неквадратну) відстань
        }
        return totalShift;
    }


    private Centroid[] saveOldCentroids() {
        Centroid[] oldCentroids = new Centroid[centroids.length];
        for (int i = 0; i < centroids.length; i++) {
            // Ми припускаємо, що у Centroid є конструктор копіювання
            // Або ми просто беремо старі значення
            oldCentroids[i] = new Centroid(
                    centroids[i].getA(), centroids[i].getR(),
                    centroids[i].getG(), centroids[i].getB()
            );
        }
        return oldCentroids;
    }

    public void updateAllCentroids(int[] labels) {
        double[][] sums = new double[NUMBER_OF_CLUSTERS][4];
        int[] counts = new int[NUMBER_OF_CLUSTERS];
        int currentPixelIndex = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int clusterIndex = labels[currentPixelIndex];

                int argbValue = image.getRGB(x, y);
                int A = (argbValue >> 24) & 0xFF;
                int R = (argbValue >> 16) & 0xFF;
                int G = (argbValue >> 8) & 0xFF;
                int B = argbValue & 0xFF;

                sums[clusterIndex][0] += A; // [0] = A
                sums[clusterIndex][1] += R; // [1] = R
                sums[clusterIndex][2] += G; // [2] = G
                sums[clusterIndex][3] += B; // [3] = B
                counts[clusterIndex]++;

                currentPixelIndex++;
            }
        }


        for (int i = 0; i < centroids.length; i++) {
            centroids[i].update(
                    sums[i][0], // sumA
                    sums[i][1], // sumR
                    sums[i][2], // sumG
                    sums[i][3], // sumB
                    counts[i]   // count
            );
        }
    }



    public void findCentroids() {
        centroids[0] = setCentroid(0, 0);
        double[] minimalDistance = new double[imageSize];
        int centroidsCount = 1;
        for (int i = 1; i < centroids.length; i++) {
            int index = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    double Dx = Double.MAX_VALUE;
                    int argbValue = image.getRGB(x, y);
                    int A = (argbValue >> 24) & 0xFF;
                    int R = (argbValue >> 16) & 0xFF;
                    int G = (argbValue >> 8) & 0xFF;
                    int B = argbValue & 0xFF;
                    for (int j = 0; j < centroidsCount; j++) {
                        Dx = Math.min(Math.pow(A - centroids[j].getA(), 2) +
                                Math.pow(R - centroids[j].getR(), 2) +
                                Math.pow(G - centroids[j].getG(), 2) +
                                Math.pow(B - centroids[j].getB(), 2), Dx);
                    }
                    minimalDistance[index] = Dx;
                    index++;
                }
            }

            double sumDx = 0.0;

            for (double dx : minimalDistance) {
                sumDx += dx;
            }
            Random random = new Random();
            double RND = random.nextDouble() * sumDx;
            double cumulativeSum = 0;

            for (int j = 0; j < minimalDistance.length; j++) {
                cumulativeSum += minimalDistance[j];
                if (cumulativeSum >= RND) {
                    int x = j % image.getWidth();
                    int y = j / image.getWidth();
                    centroids[i] = setCentroid(x, y);
                    break;
                }
            }
            centroidsCount++;
        }
    }

    private Centroid setCentroid(int x, int y) {
        int argbValue = image.getRGB(x, y);
        int A = (argbValue >> 24) & 0xFF;
        int R = (argbValue >> 16) & 0xFF;
        int G = (argbValue >> 8) & 0xFF;
        int B = argbValue & 0xFF;
        return new Centroid(A, R, G, B);
    }
}
