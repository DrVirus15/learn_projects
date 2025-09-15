package com.shpp.p2p.cs.akoskovtsev.assignment10;

import java.util.*;


public class Assignment10Part1 {
    private static final String OPERATORS = "^*/+-";
    private static final StringBuilder OPERATORS_LIST = new StringBuilder();

    public static void main(String[] args) {
        HashMap<String, Double> params = new HashMap<>();
        String formula = args[0].replaceAll(" ", "");
        for (int i = 1; i < args.length; i++) {
            String parse = args[i].replaceAll(" ", "");
            int index = parse.indexOf('=');
            params.put(parse.substring(0, index), Double.parseDouble(parse.substring(index + 1)));
        }
        System.out.println("expression: " + Arrays.toString(args));
        double res = makeAllCalculation(formula, params);
        System.out.println("result of calculation: " + res);
    }


    private static double makeAllCalculation(String formula, HashMap<String, Double> params) {
        double result = 0.0;
        ArrayList<Double> numbers = parse(formula, params);
        while (!OPERATORS_LIST.isEmpty()) {
            int index = findIndex();
            result = makeBaseOperation(numbers.get(index), OPERATORS_LIST.charAt(index), numbers.get(index + 1));
            OPERATORS_LIST.deleteCharAt(index);
            numbers.set(index, result);
            numbers.remove(index + 1);
        }
        return result;
    }

    private static int findIndex() {
        int index;
        for (int i = 0; i < OPERATORS.length() - 2; i++) {
            char operator = OPERATORS.charAt(i);
            if (i == 0) {
                index = OPERATORS_LIST.lastIndexOf(String.valueOf(operator));
            } else {
                index = OPERATORS_LIST.indexOf(String.valueOf(operator));
            }
            if (index != -1) {
                return index;
            }
        }
        return 0;
    }

    private static ArrayList<Double> parse(String formula, HashMap<String, Double> variables) {
        ArrayList<Double> result = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        int unaryMinus = 1;
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '-' && number.isEmpty()) {
                unaryMinus = -1;
                continue;
            }
            if (OPERATORS.indexOf(formula.charAt(i)) != -1 && !number.isEmpty() || i == formula.length() - 1) {
                if (i != formula.length() - 1) {
                    OPERATORS_LIST.append(formula.charAt(i));
                } else {
                    number.append(formula.charAt(i));
                }
                for (Map.Entry<String, Double> entry : variables.entrySet()) {
                    number = new StringBuilder(number.toString().replaceAll(entry.getKey(), entry.getValue() + ""));
                }
                result.add(Double.parseDouble(number.toString()) * unaryMinus);
                number = new StringBuilder();
                unaryMinus = 1;
            } else {
                number.append(formula.charAt(i));
            }
        }
        return result;
    }


    private static double makeBaseOperation(double a, char ch, double b) {
        return switch (ch) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '^' -> Math.pow(a, b);
            case '/' -> a / b;
            default -> throw new IllegalStateException("Unexpected value: " + ch);
        };
    }
}

