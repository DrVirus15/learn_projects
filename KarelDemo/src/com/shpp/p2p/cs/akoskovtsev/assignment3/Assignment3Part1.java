package com.shpp.p2p.cs.akoskovtsev.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Program counts days with aerobic and makes tips for а healthy life.
 */

public class Assignment3Part1 extends TextProgram {
    private static final int DAYS_IN_WEEK = 7;
    // Constants for minimal days with aerobic for cardio and blood pressure.
    private static final int MIN_CARDIO_TRAIN = 5;
    private static final int MIN_BLOOD_PRESSURE_TRAIN = 3;
    // Constants for minimal time of cardio and blood pressure exercises.
    private static final int BLOOD_PRESSURE_EXERCISE_MINUTES = 40;
    private static final int CARDIO_EXERCISE_MINUTES = 30;
    // initial count of days with exercises.
    private int cardioDays = 0;
    private int bloodPressureDays = 0;

    /**
     * Asks user about minutes of exercises
     * prints a tip for a cardio trainings.
     * prints a tip for a blood pressure trainings.
     */
    @Override
    public void run() {
        askAboutTraining();
        println(getCardioResult());
        println(getBloodPressureResult());
    }

    /**
     * Asks user about minutes of exercises and checks their minimal timing.
     * Every number compares with cardio trainings and blood pressure trainings.
     * If the number is less than 0 or more than 1440,
     * or if the input is not a valid integer,
     * the program asks user to enter a valid number.
     */
    private void askAboutTraining() {
        int a;
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            print("How many minutes did you do on day " + i + "?");
            try {
                a = readInt();
                checkBloodPressure(a);
                checkForCardioHealth(a);
                // If the number is less than 0 or more than 1440, ask again
                if (a < 0 || a > 1440) {
                    println("Please enter a valid number between 0 and 1440.");
                    i--; // Decrement i to repeat the same day
                }
            } catch (Exception e) {
                // If the input is not a valid integer, prompt the user again
                println("Invalid input. Enter a integer number.");
                readLine(); // Clear the invalid input
                i--; // Decrement i to repeat the same day
            }
        }
    }

    /**
     * if there has been minimal exercise time, the number of healthy days increases.
     *
     * @param a - the number that read from user.
     */
    private void checkBloodPressure(int a) {
        if (a >= BLOOD_PRESSURE_EXERCISE_MINUTES) bloodPressureDays++;
    }

    /**
     * If there has been minimal exercise time, the number of healthy days increases.
     *
     * @param a - the number that read from user.
     */
    private void checkForCardioHealth(int a) {
        if (a >= CARDIO_EXERCISE_MINUTES) cardioDays++;
    }

    /**
     * Compares number of cardio days with the minimum time of trainings.
     *
     * @return - return a message with recommendation for train cardio.
     */
    private String getCardioResult() {
        // if there enough days with trainings the message is "Great job..."
        // else prints number days to goal.
        return cardioDays >= MIN_CARDIO_TRAIN ?
                "Cardiovascular health:\n" +
                        "  Great job! You've done enough exercise for cardiovascular health."
                : "Cardiovascular health:\n" +
                "  You needed to train hard for at least " +
                (MIN_CARDIO_TRAIN - cardioDays) + " more day(s) a week!";
    }

    /**
     * Compares number of blood pressure days with minimum time of trainings.
     *
     * @return - return a message with recommendation for train blood pressure.
     */
    private String getBloodPressureResult() {
        // if there enough days with trainings the message is "Great job..."
        // else prints number days to goal.
        return bloodPressureDays >= MIN_BLOOD_PRESSURE_TRAIN ?
                "Blood pressure:\n" +
                        "  Great job! You've done enough exercise to keep a low blood pressure."
                : "Blood pressure:\n" +
                "  You needed to train hard for at least " +
                (MIN_BLOOD_PRESSURE_TRAIN - bloodPressureDays) + " more day(s) a week!";
    }
}
