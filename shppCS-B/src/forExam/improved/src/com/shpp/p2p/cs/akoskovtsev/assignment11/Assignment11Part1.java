package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11;

import forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators.*;

import java.util.*;

/**
 * This program evaluates mathematical expressions provided as command-line arguments.
 * Supported operators: +, -, *, /, ^, (unary minus)
 * Supported functions: sin, cos, tan, asin, acos, atan, log, log2, log10
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
//        OPERATOR_MAP.put(",", new Coma());
        OPERATOR_MAP.put("cos", new Cos());
        OPERATOR_MAP.put("acos", new ACos());
        OPERATOR_MAP.put("sin", new Sin());
        OPERATOR_MAP.put("asin", new ASin());
        OPERATOR_MAP.put("tan", new Tan());
        OPERATOR_MAP.put("atan", new ATan());
        OPERATOR_MAP.put("log", new Log());
        OPERATOR_MAP.put("log2", new Log_2());
        OPERATOR_MAP.put("log10", new Log_10());
//        OPERATOR_MAP.put("min", new Min());
//        OPERATOR_MAP.put("Min", new Min());
//        OPERATOR_MAP.put("Max", new Max());
//        OPERATOR_MAP.put("max", new Max());
        OPERATOR_MAP.put("sqrt", new Sqrt());
        OPERATOR_MAP.put("exp", new Exp());
    }


    /**
     * Main method to run the program.
     *
     * @param args Command-line arguments: the first argument is the formula, followed by variable assignments.
     */
    public static void main(String[] args) {
//        String formula = "15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))";
        String formula = args[0].replace(" ", "").toLowerCase(); //TODO handle args... null 0 etc.
        HashMap<String, Double> variables = parseVariables(args);
//        System.out.println(formula + " " + variables);
        LinkedList<String> rpn = parse(formula); // Convert the formula to Reverse Polish Notation (RPN)
        printResult(rpn, variables);             // Calculate and print the result
    }

    /**
     * Parses variable assignments from command-line arguments.
     *
     * @param args - Command-line arguments
     * @return - Map of variable names to their values
     */
    private static HashMap<String, Double> parseVariables(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String parse = args[i];
            int index = parse.indexOf('=');
            try {
                variables.put(parse.substring(0, index), Double.parseDouble(parse.substring(index + 1)));
            } catch (NumberFormatException e) {
                System.out.println("Can`t parse a variable " + parse + ".");
            }
        }
        return variables;
    }

    /**
     * Prints the result of the RPN calculation.
     *
     * @param rpn       - the RPN expression as a stack of tokens
     * @param variables - map of variable names to their values
     */
    private static void printResult(LinkedList<String> rpn, HashMap<String, Double> variables) {
        try {
            double result = calculateRPN(rpn, variables); //TODO перевірка на неправильну кількість дужок
            System.out.println(result);
        } catch (ArithmeticException e) { // Catch division by zero
            System.out.println("Zero division error. Fix the errors in the expression.");
        } catch (NumberFormatException e) { // Catch invalid number format
            System.out.println("Fix the errors in the expression.");
        } catch (EmptyStackException e) { // Catch invalid number of operands
            System.out.println("There is no operands. Fix the errors in the expression.");
        } catch (IllegalArgumentException e) { // Catch invalid operations resulting in Infinity or NaN
            System.out.println("There is Infinity or NaN. Fix the errors in the expression.");
        }
    }

    /**
     * Calculates the result of an expression in Reverse Polish Notation (RPN).
     *
     * @param rpn       - the RPN expression as a stack of tokens
     * @param variables - map of variable names to their values
     * @return - the calculated result
     */
    private static double calculateRPN(LinkedList<String> rpn, HashMap<String, Double> variables)
            throws NumberFormatException {
        Stack<Double> tokens = new Stack<>();
        setVariables(variables, rpn);
        while (!rpn.isEmpty()) {
            String token = rpn.pop();
            if (isOperator(token)) {        // If the token is an operator, perform the operation
                double[] operand = new double[OPERATOR_MAP.get(token).getOperandCount()];
                if (tokens.size() < operand.length) throw new EmptyStackException(); // Not enough operands
                for (int i = operand.length - 1; i >= 0; i--) {
                    operand[i] = tokens.pop();
                }
                tokens.push(OPERATOR_MAP.get(token).calculate(operand)); // Perform the operation
                if (tokens.peek().isInfinite() || tokens.peek().isNaN()) {
                    throw new IllegalArgumentException();
                }
            } else {
                tokens.push(Double.parseDouble(token));
            }
        }
        return tokens.pop();
    }

    /**
     * Replaces variables in the stack with their corresponding values from the variables map.
     *
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
     *
     * @param formula - the input formula as a string
     * @return - the RPN expression as a linked list of tokens
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
            if (isOperator(token)) {
                addToRPN(rpn, operand, opStack);
                handleOperator(token, opStack, rpn);
            } else {
                operand.append(token);
            }
            if (i == formula.length() - 1 && !operand.isEmpty()) {
                addToRPN(rpn, operand, opStack);
            }
        }
        while (!opStack.isEmpty()) {
            rpn.add(opStack.pop());
        }
        return rpn;
    }

    /**
     * Handles the operator according to the Shunting Yard algorithm.
     *
     * @param token   - the operator token
     * @param opStack - stack of operators
     * @param rpn     - output list for RPN
     */
    private static void handleOperator(String token, Stack<String> opStack, LinkedList<String> rpn) {
        if (token.equals("(")) {
            opStack.push(token);        // Push open bracket onto the stack
            return;
        }
        if(opStack.isEmpty()){
            System.out.println("opStack is empty");
        }
        if (token.equals(")") && !opStack.isEmpty()) {
            String lastToken = opStack.peek();
            while (!lastToken.equals("(")) {
                rpn.add(opStack.pop()); // Pop operators to RPN until an open bracket is found
                if(!opStack.isEmpty()){
                    lastToken = opStack.peek();
                }else {
                    throw new EmptyStackException();
                }
            }
            opStack.pop();              // Remove the open bracket from the stack
            return;
        }
        int tokenPrecedence = OPERATOR_MAP.get(token).getPrecedence();      // Get the precedence of the current operator
        boolean isLeftAssoc = OPERATOR_MAP.get(token).isLeftAssociativity();// Get the associativity of the current operator
        while (!opStack.isEmpty()) {
            String operator = opStack.peek();                               // Peek at the top operator on the stack
            int topPrecedence = OPERATOR_MAP.get(operator).getPrecedence(); // Get the precedence of the operator at the top of the stack
            if (operator.equals("(")) break;                                 // Stop if an open bracket is found
            if (tokenPrecedence < topPrecedence || (tokenPrecedence == topPrecedence && isLeftAssoc)) {
                rpn.add(opStack.pop());                                     // Pop all higher or equal precedence operators to RPN
            } else {
                break;
            }
        }
        opStack.push(token);                                                // Push the current operator onto the stack
    }

    /**
     * Checks if the given string is a recognized operator.
     *
     * @param strSymbol - the string to check
     * @return - true if the string is an operator, false otherwise
     */
    private static boolean isOperator(String strSymbol) {
        return OPERATOR_MAP.get(strSymbol) != null;
    }

    /**
     * Adds the current operand to the RPN list or operator stack.
     *
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