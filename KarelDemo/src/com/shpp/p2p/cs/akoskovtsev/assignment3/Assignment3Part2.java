package com.shpp.p2p.cs.akoskovtsev.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Numbers-hailstones.
 */

public class Assignment3Part2 extends TextProgram {
    /**
     * reads a number from user and makes a calculation.
     * If the number is even, it divides it by 2.
     * If the number is odd, it multiplies it by 3 and adds 1.
     * The process continues until the number reaches 1
     */
    @Override
    public void run() {
        try {
            int number = readInt("Enter a positive number:");
            while (number != 1 && number > 0) {
                number = number % 2 == 0 ? getOddCalculate(number) : getEvenCalculate(number);
            }
            if (number == 1) println("The end.");
            if (number < 1) {
                println("The number must be positive.");
                println("Please enter a positive integer next time.");
            }

        } catch ( Exception e){
            println("Please enter a valid positive integer next time.");
            readLine(); // Clear the invalid input
        }
    }

    /**
     * This method is intended to be called when the input number is even.
     * It calculates `number * 3 + 1` and prints a message about the operation.
     *
     * @param number - The even integer to which the 3n + 1 formula will be applied.
     * @return - return the result of calculation.
     */
    private int getEvenCalculate(int number) {

        println(number + " is even so I make 3n + 1: " + (number = number * 3 + 1));
        return number;
    }

    /**
     * This method is intended to be called when the input number is odd.
     * It calculates `number/2` and prints a message about the operation.
     *
     * @param number - The odd integer to which the n/2 formula will be applied.
     * @return - return the result of calculation.
     */
    private int getOddCalculate(int number) {
        println(number + " is odd so I take half: " + (number /= 2));
        return number;
    }

}
