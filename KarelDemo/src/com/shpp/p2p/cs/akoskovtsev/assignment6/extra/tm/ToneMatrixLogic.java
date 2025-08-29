package com.shpp.p2p.cs.akoskovtsev.assignment6.extra.tm;

import java.util.ArrayList;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(int[][] toneMatrix, int column, double[][] samples, double[][] bases, double[][] guitar) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        // make an array to store activated samples
        ArrayList<double[]> activatedSamples = new ArrayList<>();
        // Check if the column have any activated rows
        for (int row = 0; row < toneMatrix[0].length; row++) {

            if (toneMatrix[row][column] != 0) {
                if(toneMatrix[row][column] == 1){
                    activatedSamples.add(samples[row]);
                } else if(toneMatrix[row][column] == 2){
                    activatedSamples.add(bases[row]);
                } else {
                    activatedSamples.add(guitar[row]);
                }
            }
        }
        // If there is just 1 activated row, return an array with samples
//        if (activatedSamples.size() == 1) {
//            return fillResultArray(activatedSamples.getFirst(), 1);
//        }
        // If activated rows are more than 1, combine them
        // and normalize the resulting sound sample
        if (!activatedSamples.isEmpty()) {
            result = correctSamples(activatedSamples);
        }
        return result;
    }

    /**
     * Combines the sound samples from all activated rows in the tone matrix,
     * normalizes the resulting sound sample, and returns it.
     * @param activatedSamples The list of sound samples from activated rows in the tone matrix.
     * @return A normalized sound samples that represents the combined sound of all activated rows.
     */
    private static double[] correctSamples(ArrayList<double[]> activatedSamples) {
        double[] amplitudeOverflowSamples = new double[ToneMatrixConstants.sampleSize()];
        // Sum the samples from all activated rows
        for (int i = 0; i < ToneMatrixConstants.sampleSize(); i++) {
            double sample = 0.0;
            for (double[] activatedSample : activatedSamples) {
                sample += activatedSample[i];
            }
            amplitudeOverflowSamples[i] = sample;
        }
        // Find the maximum amplitude in the combined samples
        double maxAmplitude = getMaxOfAmplitude(amplitudeOverflowSamples);
        // return the normalized samples
        return fillResultArray(amplitudeOverflowSamples, maxAmplitude);
    }

    /**
     * Returns the maximum value of amplitude from the given array of sound samples.
     * @param unnormalizedSamples The array of sound samples to analyze.
     * @return The maximum amplitude value found in the array.
     */
    private static double getMaxOfAmplitude(double[] unnormalizedSamples) {
        double max = 0.0;
        double min = 0.0;
        for (int i = 1; i < unnormalizedSamples.length; i++) {
            max = Math.max(max, unnormalizedSamples[i]);
            min = Math.min(min, unnormalizedSamples[i]);
        }
        double minAbs = -min;
        if (minAbs > max) {
            max = min;
        }
        return max == 0 ? 1 : max;
    }

    /**
     * Fills the result array with normalized sound samples.
     * @param doubles The array of sound samples to normalize.
     * @param normalize The value of maximum amplitude to normalize samples.
     * @return A new array containing normalized sound samples.
     */
    private static double[] fillResultArray(double[] doubles, double normalize) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        for (int i = 0; i < ToneMatrixConstants.sampleSize(); i++) {
            result[i] = doubles[i] / normalize;
        }
        return result;
    }
}
