package com.shpp.p2p.cs.akoskovtsev.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Program raise to power.
 */

public class Assignment3Part3 extends TextProgram {
    /**
     * reads two numbers and print result of calculation a^b.
     */
    @Override
    public void run() {
        try {
            double number1 = readDouble("Enter a base number: ");
            double number2 = readDouble("Enter a exponent number: ");
            // If the base is zero and the exponent is negative, print an error message
            if(number1 == 0 && number2 < 0) {
                println("Cannot raise zero to a negative power.");
                return;
            }
            print(number1 + "^" + number2 + "=" + raiseToPower(number1, number2));
        } catch (Exception e) {
            println("Please enter a valid number.");
            readLine(); // Clear the invalid input
        }
    }

    /**
     * Calculates the power of a base number raised to an exponent.
     *
     * @param base     - the base number that needs to be powered.
     * @param exponent - the exponent number that powers a base.
     * @return - return result of calculation base^exponent.
     */
    private double raiseToPower(double base, double exponent) {
        double result = 1;
        //if exponent is negative, calculates 1/a^|b|
        if (exponent < 0) {
            exponent = -exponent;
            for (int i = 0; i < exponent; i++) {
                result *= base;
            }
            result = 1 / result;
        } else {
            for (int i = 0; i < exponent; i++) {
                result *= base;
            }
        }
        return result;
    }
}
