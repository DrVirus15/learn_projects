package com.shpp.p2p.cs.akoskovtsev.assignment2;

import com.shpp.cs.a.console.TextProgram;
/**
 * Program takes 3 numbers and calculate Quadratic Equation
 *
 * Prerequisites: reads 3 double numbers form user where can be "," symbol
 * example: 4,1 or 0,8 (numbers with "." symbol is not accepted).
 *
 * Result: displays the calculation  of the quadratic equation and shows the result with one root,
 * two roots or no real roots of quadratic equation.
 */

public class Assignment2Part1 extends TextProgram {

    /*
     * Program takes 3 numbers and calculate Quadratic Equation
     *
     * @param a Coefficient of a quadratic equation
     * @param b Coefficient of a quadratic equation
     * @param c Coefficient of a quadratic equation
     * @param d Discriminant of a quadratic equation
     */
    private double a;
    private double b;
    private double c;
    private double d;

    @Override
    public void run() {

        // reads 3 double numbers form user
        print("Please enter a: ");
        a = readDouble();
        print("Please enter b: ");
        b = readDouble();
        print("Please enter c: ");
        c = readDouble();

        //calculation the quadratic equation
        calculation();
    }

    /*
     * Calculates discriminant of quadratic equation by formula:
     * b² - 4ac. And if D < 0, the square root of a negative number is involved, where is no roots.
     * Otherwise: if D = 0, there is only one possible root
     *            if D > 0, there are two roots.
     */
    private void calculation() {
        d = Math.pow(b, 2) - 4 * a * c;
        // displays the result of calculation.
        println(d < 0 ? noRoots() : // no roots.
                (d == 0 ? oneRoot() : twoRoots()));// one root if d==0, two roots if d>0.
    }

    // Calculates formula x = -b/2a to find one root, and returns result.
    private String oneRoot() {
        double x = (-b / 2 * a);
        return "There is one root: " + x;
    }

    /*
     * Calculates formula x1 = (-b + √d) / 2a and x1 = (-b - √d) / 2a
     * to find two roots and returns result.
     */
    private String twoRoots() {
        double x1 = (-b + Math.sqrt(d)) / (2 * a);
        double x2 = (-b - Math.sqrt(d)) / (2 * a);
        return "There are two roots: " + x1 + " and " + x2;
    }


    //Returns the massage no roots.
    private String noRoots() {
        return "There are no real roots";
    }
}

