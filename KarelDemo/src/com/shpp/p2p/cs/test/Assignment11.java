package com.shpp.p2p.cs.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.lang.Math.exp;

public class Assignment11 {///  ab^a*aba
    /// 15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))
    private static final String OPERATORS = "^*/+-,()";
//    private static final HashMap<String, Character> TRIG_FUNC = new HashMap<>();
    private static final HashMap<String, Integer> OPERATORS_LIST = new HashMap<>();

    public static void main(String[] args) {
        String formula = "ab^a*aba";
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("ab", 2.0);
        variables.put("a", 3.0);
        variables.put("aba", 4.0);
//        setOperators();
        setArrayOperators();
        double result = makeCalulation(formula.trim().replaceAll(" ", ""), variables);
        System.out.println("result: " + result);
    }

//    private static void setOperators() {
//        TRIG_FUNC.put("cos", 'c');
//        TRIG_FUNC.put("acos", 'C');
//        TRIG_FUNC.put("sin", 's');
//        TRIG_FUNC.put("asin", 'S');
//        TRIG_FUNC.put("tan", 't');
//        TRIG_FUNC.put("atan", 'T');
//        TRIG_FUNC.put("sqrt", 'q');
//        TRIG_FUNC.put("log", 'l');
//        TRIG_FUNC.put("ln", 'l');
//        TRIG_FUNC.put("log10", 'L');
//        TRIG_FUNC.put("exp", 'e');
//        TRIG_FUNC.put("max", 'M');
//        TRIG_FUNC.put("min", 'm');
//    }

    private static void setArrayOperators() {
        OPERATORS_LIST.put("^", 2);
        OPERATORS_LIST.put("*", 2);
        OPERATORS_LIST.put("/", 2);
        OPERATORS_LIST.put("+", 2);
        OPERATORS_LIST.put("-", 2);
//        OPERATORS_LIST.put("c", 1);
//        OPERATORS_LIST.put("C", 1);
//        OPERATORS_LIST.put("s", 1);
//        OPERATORS_LIST.put("S", 1);
//        OPERATORS_LIST.put("t", 1);
//        OPERATORS_LIST.put("T", 1);
//        OPERATORS_LIST.put("q", 1);
//        OPERATORS_LIST.put("l", 1);
//        OPERATORS_LIST.put("L", 1);
//        OPERATORS_LIST.put("e", 1);
//        OPERATORS_LIST.put("m", 2);
//        OPERATORS_LIST.put("M", 2);
    }

    private static double makeCalulation(String formula, HashMap<String, Double> variables) {
        LinkedList<String> rpn = parse(formula);
        System.out.println("rpn: " + rpn);
        return calculatePRN(rpn, variables);
    }

    private static double calculatePRN(LinkedList<String> stack, HashMap<String, Double> variables) {
        double unarniyMinus = 1.0;
        LinkedList<Double> tokens = new LinkedList<>();
        while (!stack.isEmpty()) {
            String token = stack.pop();
            if (token.equals("~")) {
                unarniyMinus *= -1;
                continue;
            }
            for (Map.Entry<String, Double> entry : variables.entrySet()) {
                if (token.equals(entry.getKey())) {
                    tokens.push(entry.getValue() * unarniyMinus);
                    unarniyMinus = 1;
                    token = "";
                }
            }
            for (Map.Entry<String, Integer> entry : OPERATORS_LIST.entrySet()) {
                if (token.equals(entry.getKey())) {
                    double[] operand = new double[2];
                    for (int i = entry.getValue() - 1; i >= 0; i--) {
                        operand[i] = tokens.pop();
                    }
                    tokens.push(makeBaseOperation(token, operand));
//                    System.out.println("======================================");
//                    System.out.println("result of: " + operand[0] + " " + token + " " + operand[1] + " = " + tokens.peek());
//                    System.out.println("tokens stack: " + tokens);
//                    System.out.println("==========================================================");
                    token = "";
                }
            }
//            System.out.println("token: " + token);
            if (!token.isEmpty()) {
                tokens.push(Double.parseDouble(token) * unarniyMinus);
//                System.out.println("last added token: " + tokens.peek());
                unarniyMinus = 1;
            }
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!! " + tokens + " !!!!!!!!!!!!!!");
        }
        return tokens.pop();
    }

    private static LinkedList<String> parse(String formula) {

        LinkedList<String> parsedStack = new LinkedList<>();
        LinkedList<String> tempStack = new LinkedList<>();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < formula.length(); i++) {
            char symbol = formula.charAt(i);
            if (number.isEmpty() && symbol == '-') {
                symbol = '~';
//                System.out.println("the symbol is: " + symbol + ", so we push to stack: ~");
                parsedStack.add(String.valueOf(symbol));
//                System.out.println("rpn now: " + parsedStack);
//                System.out.println("tmp stck: " + tempStack);
//                System.out.println("====================================================");
                continue;
            }
            int symbolIndex = OPERATORS.indexOf(symbol);
            if (symbolIndex == -1) {
                System.out.println("the symbol is not in operators, so this is a number. ");
                number.append(symbol);
                System.out.println("number now is: " + number);
                System.out.println("rpn: " + parsedStack);
                System.out.println("tmp Stack: " + tempStack);
                System.out.println("+++++++++++++++++++++++++++++++++++++++++");
            } else {
//                if (symbol == '(') {
//                    System.out.println("we found a operator symbol: " + symbol + " and number is: " + number);
//                    number = addToPRN(parsedStack, number);
//                    System.out.println("again, number: " + number);
//                    if (!number.isEmpty()) {
//                        tempStack.push(number.toString());
//                        System.out.println("temp stack: " + tempStack);
//                        number = new StringBuilder();
//                    }
//                    tempStack.push(String.valueOf(symbol));
//                    System.out.println("number added to rpn: " + parsedStack + ", number is null: " + number + ".");
//                    System.out.println("tmp stack: " + tempStack);
//                    System.out.println("=====================================================");
//                    continue;
//                }
//                if (symbol == ')') {
//                    System.out.println("we found `)`, so we push all from ( to ) into rpn");
//                    number = addToPRN(parsedStack, number);
//                    if (!number.isEmpty()) {
//                        tempStack.push(number.toString());
//                        System.out.println("temp stack: " + tempStack);
//                        number = new StringBuilder();
//                    }
//                    while (!tempStack.isEmpty()) {
//                        String tmp = tempStack.pop();
//                        if (!tmp.equals("(")) {
//                            parsedStack.add(tmp);
//                        } else {
//                            break;
//                        }
//                    }
//                    System.out.println("rpn now: " + parsedStack);
//                    System.out.println("tmp stack: " + tempStack);
//                    System.out.println("=======================================================");
//                    continue;
//                }
//                if (symbol == ',') {
//                    System.out.println("this is '" + symbol + "' so we do NOTHING");
//                    continue;
//                }
                if (symbol == '^') {
                    number = addToPRN(parsedStack, number);
                    if (!number.isEmpty()) {
                        tempStack.push(number.toString());
                        number = new StringBuilder();
                    }
                    tempStack.push(String.valueOf(symbol));
                    System.out.println("tempStAck: " + tempStack);
                    System.out.println("PRN::" + parsedStack);
                } else {
                    System.out.println("we found a operator symbol: " + symbol + " , number is: " + number);
                    number = addToPRN(parsedStack, number);
                    if (!number.isEmpty()) {
                        tempStack.push(number.toString());
                        number = new StringBuilder();
                    }
                    System.out.println("tmpStack : " + tempStack);
                    System.out.println("Rpn : " + parsedStack);
                    if (!tempStack.isEmpty()) {
                        int stackIndex = OPERATORS.indexOf(tempStack.peek());
                        while (stackIndex == 0) {
                            System.out.println("stackIndex = 0");
                            parsedStack.add(tempStack.pop());
                            if (!tempStack.isEmpty()) {
                                stackIndex = OPERATORS.indexOf(tempStack.peek());
                            } else {
                                stackIndex = -1;
                                System.out.println("stack = -1" + stackIndex);
                            }
                        }
                        if ((stackIndex == 1 || stackIndex == 2) && symbolIndex < 3) {
                            parsedStack.add(tempStack.pop());
                        }
                        System.out.println("stack:::: " + tempStack);
                        System.out.println("RPN::: " + parsedStack);
                        System.out.println("stackIndex:: " + stackIndex);
                        System.out.println("symbolIndex::: " + symbolIndex);
                        if ((symbolIndex > 2) && (stackIndex == 1 || stackIndex == 2)) {
                            System.out.println("tempStack IS: " + tempStack);
                            while (stackIndex == 2 || stackIndex == 1) {
                                parsedStack.add(tempStack.pop());
                                if (!tempStack.isEmpty()) {
                                    stackIndex = OPERATORS.indexOf(tempStack.peek());
                                } else {
                                    stackIndex = -1;
                                }
                            }
                        }
                        while ((stackIndex == 3 || stackIndex == 4) && symbolIndex > 2) {
                            parsedStack.add(tempStack.pop());
                            if (!tempStack.isEmpty()) {
                                stackIndex = OPERATORS.indexOf(tempStack.peek());
                            } else {
                                stackIndex = -1;
                            }
                        }

                    }
//                    System.out.println("number added to rpn: " + parsedStack);
                    tempStack.push(String.valueOf(symbol));
//                    System.out.println("tmp stack: " + tempStack);
//                    System.out.println("=====================================================");
                }
            }
            if (i == formula.length() - 1 && !number.isEmpty()) {
                number = addToPRN(parsedStack, number);
                if (!number.isEmpty()) {
                    tempStack.push(number.toString());
                    number = new StringBuilder();
                }
            }
        }
        while (!tempStack.isEmpty()) {
            parsedStack.add(tempStack.pop());
        }
        return parsedStack;
    }

    private static StringBuilder addToPRN(LinkedList<String> stack, StringBuilder number) {
        if (!number.isEmpty()) {
//            for (Map.Entry<String, Character> entry : TRIG_FUNC.entrySet()) {
//                if (entry.getKey().contentEquals(number)) {
//                    System.out.println(number + " - is equal to " + entry.getKey());
//                    number = new StringBuilder();
//                    number.append(entry.getValue());
//                    return number;
//                }
//            }
            stack.add(String.valueOf(number));
        }
        return new StringBuilder();
    }

    private static double makeBaseOperation(String ch, double[] operand) {
        return switch (ch) {
            case "+" -> operand[0] + operand[1];
            case "-" -> operand[0] - operand[1];
            case "*" -> operand[0] * operand[1];
            case "/" -> operand[0] / operand[1];
            case "^" -> Math.pow(operand[0], operand[1]);
//            case "c" -> Math.cos(Math.toRadians(operand[0]));
//            case "C" -> Math.acos(operand[0]);
//            case "s" -> Math.sin(Math.toRadians(operand[0]));
//            case "S" -> Math.asin(operand[0]);
//            case "t" -> Math.tan(Math.toRadians(operand[0]));
//            case "T" -> Math.atan(operand[0]);
//            case "q" -> Math.sqrt(operand[0]);
//            case "l" -> Math.log(operand[0]);
//            case "L" -> Math.log10(operand[0]);
//            case "e" -> exp(operand[0]);
//            case "m" -> Math.min(operand[0], operand[1]);
//            case "M" -> Math.max(operand[0], operand[1]);
            default -> throw new IllegalStateException("Unexpected value: " + ch);
        };
    }
}
