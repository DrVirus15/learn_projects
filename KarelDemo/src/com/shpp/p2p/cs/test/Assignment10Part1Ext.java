package com.shpp.p2p.cs.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import static java.lang.Math.exp;


public class Assignment10Part1Ext {
    private static final String OPERATORS = "^*/+-";
    private static final String TRIGON_OPERATORS = "cCsStTlLqe";
//    private static final StringBuilder OPERATORS_LIST = new StringBuilder();

    public static void main(String[] args) {
        HashMap<String, Double> params = new HashMap<>();
//        String formula = args[0].replaceAll(" ", "");
//        for (int i = 1; i < args.length; i++) {
//            String parse = args[i].replaceAll(" ", "");
//            int index = parse.indexOf('=');
//            params.put(parse.substring(0, index), Double.parseDouble(parse.substring(index + 1)));
//        }
//        System.out.println("expression: " + Arrays.toString(args));
        /// TEST
        String formula = "15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))";
        System.out.println("(" + Math.exp(0.0) + ")");
//        String formula = "-(2*(3+(4*2))*2)+1";
        params.put("a", -2.0);
        params.put("ab", -5.0);
//        String number = formula;
//        int count = 0;
        for (Map.Entry<String, Double> entry : params.entrySet()) {
//            number = number.replaceAll(entry.getKey(), entry.getValue() + "");
//            count++;
//            System.out.println(number + ", :" + count);
        }
        System.out.println(formula);
        /// END TEST
        try {
            double res = makeAllCalculation(formula.trim(), params);
            System.out.println("result of calculation: " + res);
        } catch (Exception e) {
            System.out.println("помилка при вводі. Перевірте ваш вираз");
        }
    }


    private static double makeAllCalculation(String formula, HashMap<String, Double> params) {
        double result = 0.0;

        Object[] parsedNumbers = parse(formula, params);
        @SuppressWarnings("unchecked") ArrayList<Double> numbers = (ArrayList<Double>) parsedNumbers[0];
        StringBuilder operatorsList = (StringBuilder) parsedNumbers[1];
        System.out.println("opList: " + operatorsList);
        System.out.println("numbers: " + numbers);
        if (operatorsList.isEmpty() && numbers.size() == 1 && !operatorsList.toString().contains(TRIGON_OPERATORS)) {
//            System.out.println("we are here");
            return numbers.getFirst();
        }
//        System.out.println(TRIGON_OPERATORS.contains("t"));
//        System.out.println("so, operatorsList is: " + operatorsList + " contains? " + (TRIGON_OPERATORS.contains("t")));
        for (int i = 0; i < operatorsList.length(); i++) {
            if (TRIGON_OPERATORS.contains(operatorsList.charAt(i) + "")) {
//                System.out.println("we are here...");
                int index = findIndex(operatorsList);
                System.out.println("numbers i " + numbers.get(index));
                System.out.println("operator i: " + operatorsList.charAt(index));
                numbers.set(index, makeBaseOperation(numbers.get(index), operatorsList.charAt(index), 0.0));
                result = numbers.get(index);
                operatorsList.deleteCharAt(index);
                System.out.println("after, numbers i " + numbers.get(index) + ", index: " + index);
                System.out.println("after, operatorList: " + operatorsList);
            }
        }
//        System.out.println("and");
        while (!operatorsList.isEmpty()) {
            int index = findIndex(operatorsList);
            result = makeBaseOperation(numbers.get(index), operatorsList.charAt(index), numbers.get(index + 1));
            operatorsList.deleteCharAt(index);
            numbers.set(index, result);
            numbers.remove(index + 1);
        }
//        System.out.println("try to return");
        return result;
    }

    private static int findIndex(StringBuilder operatorList) {
        int index;
        for (int i = 0; i < TRIGON_OPERATORS.length(); i++) {
            char operator = TRIGON_OPERATORS.charAt(i);
            index = operatorList.indexOf(String.valueOf(operator));
            if (index != -1) {
                return index;
            }
        }

        for (int i = 0; i < OPERATORS.length() - 2; i++) {
            char operator = OPERATORS.charAt(i);
            if (i == 0) {
                index = operatorList.lastIndexOf(String.valueOf(operator));
            } else {
                index = operatorList.indexOf(String.valueOf(operator));
            }
            if (index != -1) {
                return index;
            }
        }
        return 0;
    }

    private static Object[] parse(String formula, HashMap<String, Double> variables) {
        HashMap<String, Character> trigonoms = new HashMap<>();
        trigonoms.put("cos", 'c');
        trigonoms.put("acos", 'C');
        trigonoms.put("sin", 's');
        trigonoms.put("asin", 'S');
        trigonoms.put("tan", 't');
        trigonoms.put("atan", 'T');
        trigonoms.put("ln", 'l');
        trigonoms.put("log", 'l');
        trigonoms.put("log10", 'L');
        trigonoms.put("ln10", 'L');
        trigonoms.put("sqrt", 'q');
        trigonoms.put("exp", 'e');

        Object[] resultToReturn = new Object[2];
        ArrayList<Double> result = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        StringBuilder operators = new StringBuilder();
        int unaryMinus = 1;
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '-' && number.isEmpty()) {
                unaryMinus = -1;
                continue;
            }
            if (formula.charAt(i) == '(') {
                for (Map.Entry<String, Character> entry : trigonoms.entrySet()) {
                    if (number.toString().equals(entry.getKey())) {
                        System.out.println("numbr before change to trigonom: " + number);
                        operators.append(entry.getValue());
                        System.out.println("operators: " + operators);
                        number = new StringBuilder();
                    }
                }
//                System.out.println("we have fount (, so try to find )");
                StringBuilder newFormula = new StringBuilder();
                int endOfBrace = findEndOfBracers(formula, i);

                for (int j = i; j <= endOfBrace; j++) {
                    newFormula.append(formula.charAt(j));
                    i = j;
                }
//                System.out.println("calculate this: " + newFormula.substring(1, newFormula.length() - 1) + " =...");

                double bracersResult = makeAllCalculation(newFormula.substring(1, newFormula.length() - 1), variables);
                number.append(bracersResult);
                System.out.println("result of this: " + newFormula + " = " + number + ", i: " + i + ", formula.length: " + formula.length());
                if (i == formula.length() - 1) {
//                    System.out.println("bracers: " + bracersResult + ", unarn minus: " + unaryMinus);
                    result.add(bracersResult * unaryMinus);
                    System.out.println(result);
                }
                continue;
            }
            if (OPERATORS.indexOf(formula.charAt(i)) != -1 && !number.isEmpty() || i == formula.length() - 1) {
//                for (Map.Entry<String, Character> entry : trigonoms.entrySet()) {
//                    if (number.toString().equals(entry.getKey())) {
//                        System.out.println("numbr before change to trigonom: " + number);
//                        operators.append(entry.getValue());
//                        System.out.println("operators: " + operators);
//                        number = new StringBuilder();
//                    }
//                }
                if (i != formula.length() - 1) {
                    operators.append(formula.charAt(i));
                } else {
                    number.append(formula.charAt(i));
                }
//                System.out.println("number before parse: " + number);
                for (Map.Entry<String, Double> entry : variables.entrySet()) {
                    if (number.toString().equals(entry.getKey())) {
                        number = new StringBuilder(entry.getValue() + "");
                    }
                }

//                System.out.println("number after parse: " + number);
                if (!number.isEmpty()) {
                    result.add(Double.parseDouble(number.toString()) * unaryMinus);
                    number = new StringBuilder();
                    unaryMinus = 1;
                }
            } else {
//                System.out.println("number to append to str: " + formula.charAt(i));
                number.append(formula.charAt(i));
            }
        }
//        System.out.println("operatorList: " + operators);
//        System.out.println("return...");
        resultToReturn[0] = result;
        resultToReturn[1] = operators;
        return resultToReturn;
    }

    private static int findEndOfBracers(String formula, int i) {
        int countBracer = 0;
        for (int j = i + 1; j < formula.length(); j++) {
            if (formula.charAt(j) == '(') {
                countBracer++;
            }
            if (formula.charAt(j) == ')' && countBracer == 0) {
                return j;
            } else if (formula.charAt(j) == ')') {
                countBracer--;
            }
        }
        return 0;
    }


    private static double makeBaseOperation(double a, char ch, double b) {
        return switch (ch) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '^' -> Math.pow(a, b);
            case '/' -> a / b;
            case 'c' -> Math.cos(Math.toRadians(a));
            case 'C' -> Math.acos(a);
            case 's' -> Math.sin(Math.toRadians(a));
            case 'S' -> Math.asin(a);
            case 't' -> Math.tan(Math.toRadians(a));
            case 'T' -> Math.atan(a);
            case 'q' -> Math.sqrt(a);
            case 'l' -> Math.log(a);
            case 'L' -> Math.log10(a);
            case 'e' -> exp(a);
            default -> throw new IllegalStateException("Unexpected value: " + ch);
        };

    }
}


