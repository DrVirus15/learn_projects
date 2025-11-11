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
        OPERATOR_MAP.put("cos", new Cos());
        OPERATOR_MAP.put("acos", new ACos());
        OPERATOR_MAP.put("sin", new Sin());
        OPERATOR_MAP.put("asin", new ASin());
        OPERATOR_MAP.put("tan", new Tan());
        OPERATOR_MAP.put("atan", new ATan());
        OPERATOR_MAP.put("log", new Log());
        OPERATOR_MAP.put("log2", new Log_2());
        OPERATOR_MAP.put("log10", new Log_10());
        OPERATOR_MAP.put("sqrt", new Sqrt());
        OPERATOR_MAP.put("exp", new Exp());
    }

    /**
     * Main method to run the program.
     *
     * @param args Command-line arguments: the first argument is the formula, followed by variable assignments.
     */
    public static void main(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) return;

//        String formula = "15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))";
        String formula = args[0].trim().replace(" ", "").toLowerCase(); //TODO handle args... null 0 etc.
        Map<String, Double> variables = parseVariables(args);
//        System.out.println(formula + " " + variables);
        Deque<String> rpn = parse(formula); // Convert the formula to Reverse Polish Notation (RPN)
        printResult(rpn, variables);             // Calculate and print the result
    }

    /**
     * Parses variable assignments from command-line arguments.
     *
     * @param args - Command-line arguments
     * @return - Map of variable names to their values
     */
    private static Map<String, Double> parseVariables(String[] args) {
        Map<String, Double> variables = new HashMap<>();
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
    private static void printResult(Deque<String> rpn, Map<String, Double> variables) {
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
    private static double calculateRPN(Deque<String> rpn, Map<String, Double> variables)
            throws NumberFormatException {
        Stack<Double> tokens = new Stack<>();
        rpn = checkAndPushVariable(variables, rpn);
        while (!rpn.isEmpty()) {
            String token = rpn.pop();
            if (isOperator(token)) {
                performTheOperation(token, tokens);
            } else {
                tokens.push(Double.parseDouble(token));
            }
        }
        return tokens.pop();
    }

    private static void performTheOperation(String token, Stack<Double> tokens) {
        double[] operand = new double[OPERATOR_MAP.get(token).getOperandCount()];
        if (tokens.size() < operand.length) throw new EmptyStackException(); // Not enough operands
        for (int i = 0; i < operand.length; i++) {
            operand[operand.length - 1 - i] = tokens.pop();
        }
        tokens.push(OPERATOR_MAP.get(token).calculate(operand)); // Perform the operation
        if (tokens.peek().isInfinite() || tokens.peek().isNaN()) {
            throw new IllegalArgumentException();
        }
    }

    private static Deque<String> checkAndPushVariable(Map<String, Double> variables, Deque<String> RPN) {
        Deque<String> stackWithoutVariables = new ArrayDeque<>();
        while (!RPN.isEmpty()) {
            String token = RPN.pop();
            boolean tokenIsVariable = variables.get(token) != null;
            stackWithoutVariables.offer(tokenIsVariable ? String.valueOf(variables.get(token)) : token);
        }
        return stackWithoutVariables;
    }

    /**
     * Parses the expression string into Reverse Polish Notation (RPN) using the Shunting Yard algorithm.
     *
     * @param expression - the input expression as a string
     * @return - the RPN expression as a linked list of tokens
     */
    private static Deque<String> parse(String expression) {
        Deque<String> rpn = new ArrayDeque<>();// Output list for RPN
        Deque<String> opStack = new ArrayDeque<>();      // Stack for operators
        StringBuilder operand = new StringBuilder();// To build multi-character operands
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            boolean isUnaryMinus = handleUnaryMinus(operand, symbol, opStack);
            if(isUnaryMinus){
                continue;
            }
            String token = String.valueOf(symbol);
            if (isOperator(token)) {
                addToRPN(rpn, operand, opStack);
                handleOperator(token, opStack, rpn);
            } else {
                operand.append(token);
            }
            if (i == expression.length() - 1 && !operand.isEmpty()) {
                addToRPN(rpn, operand, opStack);
            }
        }
        popRemainingOperators(rpn, opStack);
        return rpn;
    }

    private static boolean handleUnaryMinus(StringBuilder operand, char symbol, Deque<String> opStack){
        if (operand.isEmpty() && symbol == '-') { // Handle unary minus
            opStack.push("~");
            return true;
        }
        return false;
    }

    private static void popRemainingOperators(Deque<String> rpn, Deque<String> opStack){
        while (!opStack.isEmpty()) {
            rpn.add(opStack.pop());
        }
    }
    /**
     * Handles the operator according to the Shunting Yard algorithm.
     *
     * @param token   - the operator token
     * @param opStack - stack of operators
     * @param rpn     - output list for RPN
     */
    private static void handleOperator(String token, Deque<String> opStack, Deque<String> rpn) {
        if (token.equals("(")) {
            opStack.push(token);        // Push open bracket onto the stack
            return;
        }

        if (token.equals(")") && !opStack.isEmpty()) {
            handleCloseBracket(opStack, rpn);
            return;
        }
        int tokenPrecedence = OPERATOR_MAP.get(token).getPrecedence();      // Get the precedence of the current operator
        boolean isLeftAssoc = OPERATOR_MAP.get(token).isLeftAssociativity();// Get the associativity of the current operator
        while (!opStack.isEmpty()) {
            String operator = opStack.peek();                               // Peek at the top operator on the stack
            int topPrecedence = OPERATOR_MAP.get(operator).getPrecedence(); // Get the precedence of the operator at the top of the stack
            if (operator.equals("(")) break;                                 // Stop if an open bracket is found
            if (!(tokenPrecedence < topPrecedence || (tokenPrecedence == topPrecedence && isLeftAssoc))) break;
            rpn.add(opStack.pop());                                     // Pop all higher or equal precedence operators to RPN
        }
        opStack.push(token);                                                // Push the current operator onto the stack
    }

    private static void handleCloseBracket(Deque<String> opStack, Deque<String> rpn) {
        String lastToken = opStack.peek();
        while (!Objects.equals(lastToken, "(")) {
            rpn.add(opStack.pop()); // Pop operators to RPN until an open bracket is found
            if (!opStack.isEmpty()) {
                lastToken = opStack.peek();
            } else {
                throw new EmptyStackException();
            }
        }
        opStack.pop();
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
    private static void addToRPN(Deque<String> stack, StringBuilder operand, Deque<String> operatorStack) {
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