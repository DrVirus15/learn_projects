package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11;

import java.util.*;

/**
 * This program evaluates mathematical expressions provided as command-line arguments.
 * Supported operators: +, -, *, /, ^, (unary minus)
 * Supported functions: sin, cos, tan, asin, acos, atan, log, log2, log10
 * Variables can be defined in the format varName=value
 * Coma (,) is not supported in double numbers.
 */
public class Assignment11Part1 {

    /**
     * Map of operator symbols to their corresponding Operator enum instances.
     */
    private static final Map<String, Operator> OPERATOR_MAP = new HashMap<>();

    // Static block to initialize the operator map
    static {
        for (Operator op : Operator.values()) {
            OPERATOR_MAP.put(op.getOperator(), op);
        }
    }

    /**
     * Main method to run the program.
     *
     * @param args Command-line arguments: the first argument is the formula, followed by variable assignments.
     */
    public static void main(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) return;
        String expression = args[0].replace(" ", "").toLowerCase();
        Map<String, Double> variables = new HashMap<>();
        if (args.length > 1) {
            try {
                variables = parseVariables(args);
            } catch (StringIndexOutOfBoundsException | NullPointerException | IllegalArgumentException e) {
                System.out.println("Exception while parse variables: " + e.getMessage());
            }
        }
        try {
            System.out.println(calculate(expression, variables));
        } catch (NumberFormatException | EmptyStackException | ArithmeticException e) {
            System.out.println("Exception while evaluating expression: " + e.getMessage());
        }
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
            String variable = args[i].replace(" ", "");
            int index = variable.indexOf('=');
            if (index == -1) {
                throw new IllegalArgumentException("Variable must be in format name=value, got: " + variable);
            }
            variables.put(variable.substring(0, index), Double.parseDouble(variable.substring(index + 1)));
        }
        return variables;
    }

    /**
     * Calculates the result of the given expression with the provided variables.
     *
     * @param expression - the mathematical expression as a string
     * @param variables  - a map of variable names to their values
     * @return - the calculated result
     * @throws NumberFormatException - if a token cannot be parsed as a number
     * @throws EmptyStackException   - if there are not enough operands for an operation or mismatched brackets
     * @throws ArithmeticException   - if an arithmetic error occurs (division by zero)
     */
    private static double calculate(String expression, Map<String, Double> variables)
            throws NumberFormatException, EmptyStackException, ArithmeticException {
        Deque<String> rpn = parse(expression);
        if (!variables.isEmpty()) {
            rpn = checkAndPushVariable(variables, rpn);
        }
        return calculateRPN(rpn);
    }

    /**
     * Replaces variable tokens in the RPN stack with their corresponding values from the variables map.
     *
     * @param variables - a map of variable names to their values
     * @param RPN       - the RPN expression as a stack of tokens
     * @return - a new stack with variables replaced by their values
     */
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
     * Calculates the result of the expression in Reverse Polish Notation (RPN).
     *
     * @param rpn - the expression in RPN as a stack of tokens
     * @return - the calculated result
     * @throws NumberFormatException - if a token cannot be parsed as a number
     * @throws EmptyStackException   - if there are not enough operands for an operation
     * @throws ArithmeticException   - if an arithmetic error occurs (division by zero)
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

    /**
     * Performs the operation represented by the token on the operands in the stack.
     *
     * @param token  - the operator token
     * @param tokens - the stack of operands
     * @throws ArithmeticException - if an arithmetic error occurs (division by zero)
     */
    private static void performTheOperation(String token, Stack<Double> tokens) throws ArithmeticException {
        double[] operands = new double[OPERATOR_MAP.get(token).getOperandsCount()];
        if (tokens.size() < operands.length) throw new EmptyStackException();
        for (int i = 0; i < operands.length; i++) {
            operands[operands.length - 1 - i] = tokens.pop();
        }
        tokens.push(OPERATOR_MAP.get(token).calculate(operands));
        if (tokens.peek().isInfinite() || tokens.peek().isNaN()) {
            throw new ArithmeticException("The result of the operation is undefined (infinity or NaN).");
        }
    }

    /**
     * Parses the expression string into Reverse Polish Notation (RPN) using the Shunting Yard algorithm.
     *
     * @param expression - the input expression as a string
     * @return - the RPN expression as a linked list of tokens
     * @throws EmptyStackException - if there are mismatched brackets
     */
    private static Deque<String> parse(String expression) throws EmptyStackException {
        Deque<String> rpn = new ArrayDeque<>();
        Deque<String> opStack = new ArrayDeque<>();
        StringBuilder operand = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (operand.isEmpty() && symbol == '-') {
                opStack.push("~");
                continue;
            }
            String token = String.valueOf(symbol);
            if (isOperator(token)) {
                addToRPN(rpn, operand, opStack);
                handleOperator(token, opStack, rpn);
            } else {
                operand.append(token);
            }
            if (i == expression.length() - 1) {
                handleEndOfExpression(rpn, operand, opStack);
            }
        }
        popRemainingOperators(rpn, opStack);
        return rpn;
    }

    /**
     * Handles the end of the expression by adding any remaining operand to the RPN list.
     *
     * @param rpn     - the RPN stack
     * @param operand - the current operand being built
     * @param opStack - stack of operators
     */
    private static void handleEndOfExpression(Deque<String> rpn, StringBuilder operand, Deque<String> opStack) {
        if (!operand.isEmpty()) {
            addToRPN(rpn, operand, opStack);
        }
    }

    /**
     * Pops any remaining operators from the operator stack to the RPN output list.
     *
     * @param rpn     - output list for RPN
     * @param opStack - stack of operators
     */
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
     * @throws EmptyStackException - if there are mismatched brackets
     */
    private static void handleOperator(String token, Deque<String> opStack, Deque<String> rpn) throws EmptyStackException {
        if (token.equals("(")) {
            opStack.push(token);
            return;
        }
        if (token.equals(")") && !opStack.isEmpty()) {
            handleCloseBracket(opStack, rpn);
            return;
        }
        popOperatorsByPrecedence(token, opStack, rpn);
        opStack.push(token);
    }

    /**
     * Pops operators from the operator stack to the RPN output based on precedence and associativity.
     *
     * @param token   - the current operator token
     * @param opStack - stack of operators
     * @param rpn     - output list for RPN
     */
    private static void popOperatorsByPrecedence(String token, Deque<String> opStack, Deque<String> rpn) {
        int tokenPrecedence = OPERATOR_MAP.get(token).getPrecedence();
        boolean isLeftAssoc = OPERATOR_MAP.get(token).isLeftAssociativity();
        while (!opStack.isEmpty()) {
            String operator = opStack.peek();
            int topPrecedence = OPERATOR_MAP.get(operator).getPrecedence();
            if (operator.equals("(")) break;
            if (!(tokenPrecedence < topPrecedence || (tokenPrecedence == topPrecedence && isLeftAssoc))) break;
            rpn.add(opStack.pop());
        }
    }

    /**
     * Handles the closing bracket by popping operators from the stack to the RPN output
     * until the corresponding opening bracket is found.
     * If no opening bracket is found, an EmptyStackException is thrown.
     *
     * @param opStack - stack of operators
     * @param rpn     - output list for RPN
     */
    private static void handleCloseBracket(Deque<String> opStack, Deque<String> rpn) {
        String lastToken = opStack.peek();
        while (!Objects.equals(lastToken, "(")) {
            rpn.add(opStack.pop());
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
     * @param symbol - the string to check
     * @return - true if the string is an operator, false otherwise
     */
    private static boolean isOperator(String symbol) {
        return OPERATOR_MAP.get(symbol) != null;
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
                operatorStack.push(operand.toString());
            } else {
                stack.add(operand.toString());
            }
        }
        operand.setLength(0);
    }
}