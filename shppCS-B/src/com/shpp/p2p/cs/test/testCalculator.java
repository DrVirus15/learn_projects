package com.shpp.p2p.cs.test;

import forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment10.Assignment10Part1;
import forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Assignment11Part1;

public class testCalculator {
    public static void main(String[] args) {
        System.out.print("test1: ");
        String[] CalcArgs = {"2^3*2+5^2^-2", "a=3", "b=4s"};
        Assignment10Part1.main(CalcArgs);
        System.out.println();
        Assignment11Part1.main(CalcArgs);
        System.out.println();
        System.out.println("==============================");
        System.out.print(" test2: ");
        CalcArgs = new String[]{"a^3^a", "a=2", null};
        Assignment10Part1.main(CalcArgs);
        System.out.println();
        Assignment11Part1.main(CalcArgs);
        System.out.println();
        System.out.println("==============================");
        System.out.print(" test3: ");
        CalcArgs = new String[]{"2+3*5-2/0"};
        Assignment10Part1.main(CalcArgs);
        System.out.println();
        Assignment11Part1.main(CalcArgs);
        System.out.println();
        System.out.println("==============================");
        System.out.print(" test4: ");
        CalcArgs = new String[]{"3+5*2-8/4^2+6*1+2-10+2^3^2"};
        Assignment10Part1.main(CalcArgs);
        System.out.println();
        Assignment11Part1.main(CalcArgs);
        System.out.println();
        System.out.println("==============================");
        System.out.print(" test5: ");
        CalcArgs = new String[]{"15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))"};
        Assignment10Part1.main(CalcArgs);
        System.out.println();
        Assignment11Part1.main(CalcArgs);
        System.out.println();
        System.out.println("==============================");
        System.out.print(" test6: ");
        CalcArgs = new String[]{"a^b^a", "a=2", "b=3"};
        Assignment10Part1.main(CalcArgs);
        System.out.println();
        Assignment11Part1.main(CalcArgs);
        System.out.println();
        System.out.println("==============================");
        System.out.print(" test7: ");
        CalcArgs = new String[]{"2+3**5-2/2"};
        Assignment10Part1.main(CalcArgs);
        System.out.println();
        Assignment11Part1.main(CalcArgs);
    }
}
