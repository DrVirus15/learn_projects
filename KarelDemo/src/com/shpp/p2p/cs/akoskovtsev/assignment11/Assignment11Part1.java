package com.shpp.p2p.cs.akoskovtsev.assignment11;

import com.shpp.p2p.cs.akoskovtsev.assignment11.operators.*;

import java.util.*;

/**
 * This program evaluates mathematical expressions provided as command-line arguments.
 * Supported operators: +, -, *, /, ^, (unary minus)
 * Supported functions: sin, cos, tan, asin, acos, atan, log, log2, log10, min, max
 * Variables can be defined in the format varName=value
 * Coma (,) is not supported in double numbers.
 */
public class Assignment11Part1 {
    // Map of operators and their corresponding implementations
    private static final Map<String, Operator> OPERATOR_MAP = new HashMap<>();
    static {
        OPERATOR_MAP.put("+", new Plus());
        OPERATOR_MAP.put("-", new Minus());
        OPERATOR_MAP.put("*", new Multiply());
        OPERATOR_MAP.put("/", new Div());
        OPERATOR_MAP.put("^", new Pow());
        OPERATOR_MAP.put("~", new UnaryMinus());
        OPERATOR_MAP.put("(", new OpenBracket());
        OPERATOR_MAP.put(")", new CloseBracket());
        OPERATOR_MAP.put(",", new Coma());
        OPERATOR_MAP.put("cos", new Cos());
        OPERATOR_MAP.put("acos", new ACos());
        OPERATOR_MAP.put("sin", new Sin());
        OPERATOR_MAP.put("asin", new ASin());
        OPERATOR_MAP.put("tan", new Tan());
        OPERATOR_MAP.put("atan", new ATan());
        OPERATOR_MAP.put("log", new Log());
        OPERATOR_MAP.put("log2", new Log_2());
        OPERATOR_MAP.put("log10", new Log_10());
        OPERATOR_MAP.put("min", new Min());
        OPERATOR_MAP.put("Min", new Min());
        OPERATOR_MAP.put("Max", new Max());
        OPERATOR_MAP.put("max", new Max());
        OPERATOR_MAP.put("sqrt", new Sqrt());
    }


    /**
     * Main method to run the program.
     * @param args Command-line arguments: the first argument is the formula, followed by variable assignments.
     */
    public static void main(String[] args) {
//        String formula = "15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))";
        String formula = args[0].replaceAll(" ", "");
        HashMap<String, Double> variables = parseVariables(args);
        System.out.println(formula);
        LinkedList<String> rpn = parse(formula); // Convert the formula to Reverse Polish Notation (RPN)
        printResult(rpn, variables);             // Calculate and print the result
    }

    /**
     * Parses variable assignments from command-line arguments.
     * @param args - Command-line arguments
     * @return     - Map of variable names to their values
     */
    private static HashMap<String, Double> parseVariables(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String parse = args[i];
            int index = parse.indexOf('=');
            try {
                variables.put(parse.substring(0, index), Double.parseDouble(parse.substring(index + 1)));
            }
            catch (NumberFormatException e){
                throw new NumberFormatException("Can`t parse a variable. Fix the errors in the expression.");
            }
        }
        return variables;
    }

    /**
     * Prints the result of the RPN calculation.
     * @param rpn       - the RPN expression as a stack of tokens
     * @param variables - map of variable names to their values
     */
    private static void printResult(LinkedList<String> rpn, HashMap<String, Double> variables) {
        try {
            double result = calculateRPN(rpn, variables);
            System.out.println("result: " + result);
        } catch (ArithmeticException e) { // Catch division by zero
            System.out.println("Zero division error. Fix the errors in the expression.");
        } catch (NumberFormatException e) { // Catch invalid number format
            System.out.println("This is not number. Fix the errors in the expression.");
        } catch (EmptyStackException e) { // Catch insufficient operands
            System.out.println("There is no operands. Fix the errors in the expression.");
        } catch (IllegalArgumentException e) { // Catch invalid operations resulting in Infinity or NaN
            System.out.println("There is Infinity or NaN. Fix the errors in the expression.");
        }
    }

    /**
     * Calculates the result of an expression in Reverse Polish Notation (RPN).
     * @param stack     - the RPN expression as a stack of tokens
     * @param variables - map of variable names to their values
     * @return          - the calculated result
     */
    private static double calculateRPN(LinkedList<String> stack, HashMap<String, Double> variables) {
        Stack<Double> tokens = new Stack<>();
        setVariables(variables, stack);     // Replace variables with their values
        while (!stack.isEmpty()) {
            String token = stack.pop();
            if (isOperator(token)) {        // If the token is an operator, perform the operation
                double[] operand = new double[OPERATOR_MAP.get(token).getOperandCount()];
                if (tokens.size() < operand.length) throw new EmptyStackException(); // Not enough operands
                for (int i = operand.length - 1; i >= 0; i--) {
                    operand[i] = tokens.pop();
                }
                tokens.push(OPERATOR_MAP.get(token).calculate(operand)); // Perform the operation
                logCalculation(tokens, operand, token);                  // Log the calculation step
            } else try {
                tokens.push(Double.parseDouble(token));                  // If it's a number, push it onto the stack
            } catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
        }
        return tokens.pop();
    }

    /**
     * Logs the calculation step.
     * @param tokens  - stack of current tokens
     * @param operand - array of operands used in the calculation
     * @param token   - the operator token
     */
    private static void logCalculation(Stack<Double> tokens, double[] operand, String token) {
        if (tokens.peek().isInfinite() || tokens.peek().isNaN()) { // Check for invalid results
            throw new IllegalArgumentException();
        }
        if (!token.equals("~") && !token.equals(",")) {            // Skip logging for unary minus and comma
            String operandsString = operand.length > 1 ? operand[0] + " " + token + " " + operand[1] : operand[0] + " " + token;
            System.out.println(operandsString + " = " + tokens.peek());
        }
    }

    /**
     * Replaces variables in the stack with their corresponding values from the variables map.
     * @param variables - map of variable names to their values
     * @param stack     - stack of tokens (operands and operators)
     */
    private static void setVariables(HashMap<String, Double> variables, LinkedList<String> stack) {
        for (int i = 0; i < stack.size(); i++) {
            if (variables.get(stack.get(i)) != null) {
                stack.set(i, variables.get(stack.get(i)) + "");
            }
        }
    }

    /**
     * Parses the formula string into Reverse Polish Notation (RPN) using the Shunting Yard algorithm.
     * @param formula - the input formula as a string
     * @return        - the RPN expression as a linked list of tokens
     */
    private static LinkedList<String> parse(String formula) {
        LinkedList<String> rpn = new LinkedList<>();// Output list for RPN
        Stack<String> opStack = new Stack<>();      // Stack for operators
        StringBuilder operand = new StringBuilder();// To build multi-character operands
        for (int i = 0; i < formula.length(); i++) {
            if (operand.isEmpty() && formula.charAt(i) == '-') { // Handle unary minus
                opStack.push("~");
                continue;
            }
            String token = String.valueOf(formula.charAt(i));
            if (isOperator(token)) {                // If the token is an operator
                addToRPN(rpn, operand, opStack);    // Add any pending operand to RPN
                handleOperator(token, opStack, rpn);// Handle the operator
            } else {
                operand.append(formula.charAt(i));  // Build the operand
            }
            if (i == formula.length() - 1 && !operand.isEmpty()) {
                addToRPN(rpn, operand, opStack);    // If it's the last character, add the operand to RPN
            }
        }
        while (!opStack.isEmpty()) {
            rpn.add(opStack.pop());                 // Pop any remaining operators to RPN
        }
        return rpn;
    }

    /**
     * Handles the operator according to the Shunting Yard algorithm.
     * @param token   - the operator token
     * @param opStack - stack of operators
     * @param rpn     - output list for RPN
     */
    private static void handleOperator(String token, Stack<String> opStack, LinkedList<String> rpn) {
        if(token.equals("(")){
            opStack.push(token);        // Push open bracket onto the stack
            return;
        }
        if(token.equals(")")){
            while (!opStack.peek().equals("(")){
                rpn.add(opStack.pop()); // Pop operators to RPN until an open bracket is found
            }
            opStack.pop();              // Remove the open bracket from the stack
            return;
        }
        int tokenPrecedence = OPERATOR_MAP.get(token).getPrecedence();      // Get the precedence of the current operator
        boolean isLeftAssoc = OPERATOR_MAP.get(token).isLeftAssociativity();// Get the associativity of the current operator
        while (!opStack.isEmpty()){
            String operator = opStack.peek();                               // Peek at the top operator on the stack
            if(operator.equals("(")) break;                                 // Stop if an open bracket is found
            int topPrecedence = OPERATOR_MAP.get(operator).getPrecedence(); // Get the precedence of the operator at the top of the stack
            if(tokenPrecedence < topPrecedence || (tokenPrecedence == topPrecedence && isLeftAssoc)){
                rpn.add(opStack.pop());                                     // Pop all higher or equal precedence operators to RPN
            } else {
                break;
            }
        }
        opStack.push(token);                                                // Push the current operator onto the stack
    }

    /**
     * Checks if the given string is a recognized operator.
     * @param strSymbol - the string to check
     * @return          - true if the string is an operator, false otherwise
     */
    private static boolean isOperator(String strSymbol) {
        return OPERATOR_MAP.get(strSymbol) != null;
    }

    /**
     * Adds the current operand to the RPN list or operator stack.
     * @param stack         - the RPN output list
     * @param operand       - the current operand being built
     * @param operatorStack - the stack of operators
     */
    private static void addToRPN(LinkedList<String> stack, StringBuilder operand, Stack<String> operatorStack) {
        if (!operand.isEmpty()) {
            if (OPERATOR_MAP.containsKey(operand.toString())) {
                operatorStack.push(operand.toString());     // If the operand is actually an operator, push it to the operator stack
            } else {
                stack.add(operand.toString());              // Otherwise, add the operand to the RPN list
            }
        }
        operand.setLength(0);                               // Clear the operand builder
    }
}