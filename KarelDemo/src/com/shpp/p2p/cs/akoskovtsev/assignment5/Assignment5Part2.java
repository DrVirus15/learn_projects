package com.shpp.p2p.cs.akoskovtsev.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * Adds two numbers.
 * The number must be non-negative integers.
 * They can be as big as a line.
 */
public class Assignment5Part2 extends TextProgram {
    @Override
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first non-negative integer number:  ");
            String n2 = readLine("Enter second non-negative integer number: ");
            // checks there are empty strings.
            if (n1.isEmpty() || n2.isEmpty()) {
                println("Invalid input.");
            } else {
                if (isNumeric(n1) && isNumeric(n2)) {
                    println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
                    println();
                } else {
                    println("Invalid input.");
                }
            }
        }
    }

    /**
     * Given two string representations of non-negative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        // searching the max length of numbers.
        int stringLength = Math.max(n1.length(), n2.length());
        stringLength++;
        // makes the same length of our numbers by add 0 before the numbers.
        String str1 = makeSameLength(n1, stringLength);
        String str2 = makeSameLength(n2, stringLength);


        StringBuilder sb = new StringBuilder();
        int res = 0;
        for (int i = stringLength - 1; i >= 0; i--) {
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(i);
            // adds two digits.
            res = res + (ch1 - '0') + (ch2 - '0');
            // if result more than 9, we add 1 to next adding.
            if (res >= 10) {
                sb.append(res - 10);
                res = 1;
            } else {
                sb.append(res);
                res = 0;
            }
        }
        sb.reverse();
        // removes all '0' at start of string, if we have it.
        while (sb.charAt(0) == '0') {
            if (sb.length() == 1) {
                break;
            }
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Checks if there is a numeric number given.
     *
     * @param number - a number that we have to check.
     * @return - returned true if there is only numeric in the number.
     * - returned false if there is some non-digit symbol.
     */
    private boolean isNumeric(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes the string length the same as nullNuber is, filling with '0' before the string.
     *
     * @param string     - the string that we want to make required length.
     * @param nullNumber - the parameter of length for our string.
     * @return - returns the string with required length with filled '0'.
     */
    private String makeSameLength(String string, int nullNumber) {
        String result = "";
        for (int i = 0; i < nullNumber; i++) {
            try {
                result = result + string.charAt(i);
            } catch (Exception e) {
                result = "0" + result;
            }
        }
        return result;
    }
}