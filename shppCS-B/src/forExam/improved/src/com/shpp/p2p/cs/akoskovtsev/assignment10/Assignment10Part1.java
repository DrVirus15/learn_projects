package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment10;

import java.util.*;


/**
 * A calculator that evaluates mathematical expressions with support for variables.
 * It supports the operators +, -, *, /, ^ and parentheses.
 * Variables can be passed as command line arguments in the format var=value.
 * Example usage: java Assignment10Part1 2^3*2+5^2^-2 a=3 b=4
 */
public class Assignment10Part1 {
    /**
     * Operators supported by the calculator.
     */
    private static final String OPERATORS = "^*/+-";

    /**
     * Main method to run the program.
     *
     * @param args Command-line arguments: the first argument is the expression, then variables in the format var=value.
     */
    public static void main(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) return;
        String expression = args[0].replace(" ", "");
        Map<String, Double> variables = new HashMap<>();
        if (args.length > 1) {
            try {
                variables = parseVariables(args);
            } catch (StringIndexOutOfBoundsException | NullPointerException | IllegalArgumentException e) {
                System.out.println("Exception while parse variables: " + e.getMessage());
            }
        }
        double result = 0;
        try {
            result = calculate(expression, variables);
        } catch (NumberFormatException | EmptyStackException | ArithmeticException e) {
            System.out.println("Exception while evaluating expression: " + e.getMessage());
        }
        System.out.print(result);
    }

    /**
     * Parses variables from command-line arguments.
     *
     * @param args - command-line arguments
     * @return - a map of variable names to their double values
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
     * Calculates the result of the expression with given variables.
     *
     * @param expression - the mathematical expression to evaluate
     * @param variables  - a map of variable names to their double values
     * @return - the result of the evaluation
     * @throws NumberFormatException - if a token cannot be parsed as a number
     * @throws EmptyStackException   - if there are not enough operands for an operation
     * @throws ArithmeticException   - if a mathematical error occurs (division by zero)
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
     * Calculates the result of the expression in Reverse Polish Notation (RPN).
     *
     * @param rpn - a stack representing the RPN of the expression
     * @return - the result of the evaluation
     * @throws EmptyStackException - if there are not enough operands for an operation
     * @throws ArithmeticException - if a mathematical error occurs (division by zero)
     */
    private static double calculateRPN(Deque<String> rpn) throws EmptyStackException, ArithmeticException {
        double unaryMinus = 1.0;
        Deque<Double> tokens = new ArrayDeque<>();
        while (!rpn.isEmpty()) {
            String token = rpn.pop();
            if (token.equals("~")) {
                unaryMinus *= -1.0;
                continue;
            }
            if (isOperator(token)) {
                performTheOperation(token, tokens);
            } else {
                tokens.push(Double.parseDouble(token) * unaryMinus);
                unaryMinus = 1;
            }
        }
        return tokens.pop();
    }

    /**
     * Performs the operation represented by the token on the top two elements of the tokens stack.
     *
     * @param token  - the operator token
     * @param tokens - the stack of operands
     * @throws ArithmeticException - if a mathematical error occurs (division by zero)
     */
    private static void performTheOperation(String token, Deque<Double> tokens) throws ArithmeticException {
        if (tokens.size() < 2) throw new EmptyStackException();
        tokens.push(makeBaseOperation(token, tokens.pop(), tokens.pop()));
        if (Objects.requireNonNull(tokens.peek()).isNaN() || tokens.peek().isInfinite()) {
            throw new ArithmeticException("Mathematical error: The operation resulted in NaN or Infinity.");
        }
    }

    /**
     * Replaces variables in the RPN with their corresponding values from the variables map.
     *
     * @param variables - a map of variable names to their double values
     * @param RPN       - a deque representing the RPN of the expression
     * @return - a deque with variables replaced by their values
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
     * Parses the expression string into Reverse Polish Notation (RPN) using the Shunting Yard algorithm.
     *
     * @param expression - the mathematical expression to parse
     * @return - a stack representing the RPN of the expression
     */
    private static Deque<String> parse(String expression) {
        Deque<String> rpn = new ArrayDeque<>();
        Deque<String> operatorStack = new ArrayDeque<>();
        StringBuilder operand = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (operand.isEmpty() && symbol == '-') {
                symbol = '~';
                rpn.add(String.valueOf(symbol));
                continue;
            }
            if (isOperator(String.valueOf(symbol))) {
                addToRPN(rpn, operand);
                handleOperator(operatorStack, rpn, symbol);
            } else {
                operand.append(symbol);
            }
            if (i == expression.length() - 1) {
                handleEndOfExpression(operand, rpn);
            }
        }
        popRemainingOperators(operatorStack, rpn);
        return rpn;
    }

    /**
     * Handles the end of the expression by adding any remaining operand to the RPN stack.
     *
     * @param operand - the built operand
     * @param rpn     - the RPN stack
     */
    private static void handleEndOfExpression(StringBuilder operand, Deque<String> rpn) {
        if (!operand.isEmpty()) {
            addToRPN(rpn, operand);
        }
    }

    /**
     * Pops all remaining operators from the operator stack to the RPN stack.
     *
     * @param operatorStack - the stack of operators
     * @param rpn           - the RPN stack
     */
    private static void popRemainingOperators(Deque<String> operatorStack, Deque<String> rpn) {
        while (!operatorStack.isEmpty()) {
            rpn.add(operatorStack.pop());
        }
    }

    /**
     * Handles the operator according to the Shunting Yard algorithm.
     *
     * @param operatorStack - stack of operators
     * @param rpn           - output stack for RPN
     * @param symbol        - the operator symbol
     */
    private static void handleOperator(Deque<String> operatorStack, Deque<String> rpn, char symbol) {
        String operator = String.valueOf(symbol);
        if (!operatorStack.isEmpty() && symbol != '^') {
            handleStackPrecedence(operatorStack, rpn, operator);
        }
        operatorStack.push(operator);
    }

    /**
     * Handles operator precedence when adding operators to the RPN stack.
     *
     * @param operatorStack - stack of operators
     * @param rpn           - output stack for RPN
     * @param operator      - the current operator
     */
    private static void handleStackPrecedence(Deque<String> operatorStack, Deque<String> rpn, String operator) {
        handlePowPrecedence(operatorStack, rpn);
        handleDivMultiPrecedence(operatorStack, rpn, operator);
        handlePlusMinusPrecedence(operatorStack, rpn, operator);
    }

    /**
     * Handles the addition of '^' operators to the RPN stack.
     *
     * @param operatorStack       - stack of operators
     * @param rpn                 - output stack for RPN
     */
    private static void handlePowPrecedence(Deque<String> operatorStack, Deque<String> rpn) {
        String lastOperatorInStack = operatorStack.peek();
        while (isPow(lastOperatorInStack)) {
            lastOperatorInStack = addOperatorToRPN(rpn, operatorStack);
        }
    }

    /**
     * Handles the addition of '*' and '/' operators to the RPN stack.
     *
     * @param operatorStack       - stack of operators
     * @param rpn                 - output stack for RPN
     * @param operator            - the current operator
     */
    private static void handleDivMultiPrecedence(Deque<String> operatorStack, Deque<String> rpn, String operator) {
        String lastOperatorInStack = operatorStack.peek();
        if (isDivOrMulti(lastOperatorInStack) && isDivOrMulti(operator)) {
            rpn.add(operatorStack.pop());
        }
        lastOperatorInStack = operatorStack.peek();
        if (isDivOrMulti(lastOperatorInStack) && isPlusOrMinus(operator)) {
            while (isDivOrMulti(lastOperatorInStack)) {
                lastOperatorInStack = addOperatorToRPN(rpn, operatorStack);
            }
        }
    }

    /**
     * Handles the addition of '+' and '-' operators to the RPN stack.
     *
     * @param operatorStack       - stack of operators
     * @param rpn                 - output stack for RPN
     * @param operator            - the current operator
     */
    private static void handlePlusMinusPrecedence(Deque<String> operatorStack, Deque<String> rpn, String operator) {
        String lastOperatorInStack = operatorStack.peek();
        while (isPlusOrMinus(lastOperatorInStack) && isPlusOrMinus(operator)) {
            lastOperatorInStack = addOperatorToRPN(rpn, operatorStack);
        }
    }

    /**
     * Pops the top operator from the operator stack and adds it to the RPN stack.
     *
     * @param rpn           - the RPN stack
     * @param operatorStack - the stack of operators
     * @return - the new top operator of the operator stack, or an empty string if the stack is empty
     */
    private static String addOperatorToRPN(Deque<String> rpn, Deque<String> operatorStack) {
        rpn.add(operatorStack.pop());
        return !operatorStack.isEmpty() ? operatorStack.peek() : "";
    }

    /**
     * Checks if the symbol is a power operator.
     *
     * @param symbol - the operator symbol
     * @return - true if the symbol is '^', false otherwise
     */
    private static boolean isPow(String symbol) {
        return symbol != null && symbol.equals("^");
    }

    /**
     * Checks if the symbol is a division or multiplication operator.
     *
     * @param symbol - the operator symbol
     * @return - true if the symbol is '*' or '/', false otherwise
     */
    private static boolean isDivOrMulti(String symbol) {
        return symbol != null && (symbol.equals("*") || symbol.equals("/"));
    }

    /**
     * Checks if the symbol is a plus or minus operator.
     *
     * @param symbol - the operator symbol
     * @return - true if the symbol is '+' or '-', false otherwise
     */
    private static boolean isPlusOrMinus(String symbol) {
        return symbol != null && (symbol.equals("+") || symbol.equals("-"));
    }

    /**
     * Checks if the given string is a recognized operator.
     *
     * @param strSymbol - the string to check
     * @return - true if the OPERATORS contains the string as an operator, false otherwise
     */
    private static boolean isOperator(String strSymbol) {
        return OPERATORS.contains(strSymbol);
    }

    /**
     * Adds the built operand to the RPN stack if it's not empty.
     *
     * @param rpn     - the RPN stack
     * @param operand - the built operand
     */
    private static void addToRPN(Deque<String> rpn, StringBuilder operand) {
        if (!operand.isEmpty()) {
            rpn.add(operand.toString());
        }
        operand.setLength(0);
    }

    /**
     * Performs a basic mathematical operation between two operands.
     *
     * @param operator     - the operator symbol
     * @param rightOperand - the right operand
     * @param leftOperand  - the left operand
     * @return - the result of the operation
     * @throws ArithmeticException - if a mathematical error occurs (division by zero)
     */
    private static double makeBaseOperation(String operator, double rightOperand, double leftOperand) {
        if (operator.equals("/") && rightOperand == 0) {
            throw new ArithmeticException("Mathematical error: Division by zero.");
        }
        return switch (operator) {
            case "+" -> leftOperand + rightOperand;
            case "-" -> leftOperand - rightOperand;
            case "*" -> leftOperand * rightOperand;
            case "/" -> leftOperand / rightOperand;
            case "^" -> Math.pow(leftOperand, rightOperand);
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }
}