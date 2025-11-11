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
//        String expression = "15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))";
        String expression = args[0].replace(" ", "").toLowerCase();
        Map<String, Double> variables = new HashMap<>();
        if (args.length > 1) {
            try {
                variables = parseVariables(args);
            } catch (StringIndexOutOfBoundsException | IllegalArgumentException e) {
                System.err.println("Exception while parse variables: " + e.getMessage());
            }
        }

        double result = 0;
        try {
            result = calculate(expression, variables);
        } catch (NumberFormatException | EmptyStackException | ArithmeticException e) {
            System.err.println("Exception while evaluating expression: " + e.getMessage());
        }
        System.out.print(result);
    }

    /**
     * Parses variable assignments from command-line arguments.
     *
     * @param args - Command-line arguments
     * @return - Map of variable names to their values
     */
    private static Map<String, Double> parseVariables(String[] args) throws StringIndexOutOfBoundsException,
            IllegalArgumentException {
        Map<String, Double> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String variable = args[i].replace(" ", "");
            int index = variable.indexOf('=');
            if (index == -1) {
                throw new IllegalArgumentException("Variable must be in format name=value, got: " + variable);
            }
            variables.put(variable.substring(0, index), Double.parseDouble(variable.substring(index + 1)));
        }
        return variables;
    }

    private static double calculate(String expression, Map<String, Double> variables)
            throws NumberFormatException, EmptyStackException, ArithmeticException {
        Deque<String> rpn = parse(expression);
        rpn = checkAndPushVariable(variables, rpn);
        return calculateRPN(rpn);
    }

    /**
     * Calculates the result of an expression in Reverse Polish Notation (RPN).
     *
     * @param rpn       - the RPN expression as a stack of tokens
     * @return - the calculated result
     */
    private static double calculateRPN(Deque<String> rpn)
            throws EmptyStackException, ArithmeticException, NumberFormatException {
        Stack<Double> tokens = new Stack<>();
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
        tokens.push(OPERATOR_MAP.get(token).calculate(operand));
        if (tokens.peek().isInfinite() || tokens.peek().isNaN()) {
            throw new ArithmeticException();
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
        Deque<String> rpn = new ArrayDeque<>();
        Deque<String> opStack = new ArrayDeque<>();
        StringBuilder operand = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            boolean isUnaryMinus = handleUnaryMinus(operand, symbol, opStack);
            if (isUnaryMinus) {
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

    private static boolean handleUnaryMinus(StringBuilder operand, char symbol, Deque<String> opStack) {
        if (operand.isEmpty() && symbol == '-') {
            opStack.push("~");
            return true;
        }
        return false;
    }

    private static void popRemainingOperators(Deque<String> rpn, Deque<String> opStack) {
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
            opStack.push(token);
            return;
        }

        if (token.equals(")") && !opStack.isEmpty()) {
            handleCloseBracket(opStack, rpn);
            return;
        }
        int tokenPrecedence = OPERATOR_MAP.get(token).getPrecedence();
        boolean isLeftAssoc = OPERATOR_MAP.get(token).isLeftAssociativity();
        while (!opStack.isEmpty()) {
            String operator = opStack.peek();
            int topPrecedence = OPERATOR_MAP.get(operator).getPrecedence();
            if (operator.equals("(")) break;
            if (!(tokenPrecedence < topPrecedence || (tokenPrecedence == topPrecedence && isLeftAssoc))) break;
            rpn.add(opStack.pop());
        }
        opStack.push(token);
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