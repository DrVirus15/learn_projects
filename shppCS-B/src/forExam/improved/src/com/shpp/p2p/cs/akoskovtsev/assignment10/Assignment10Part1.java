package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment10;

import java.util.*;


/**
 * A calculator that evaluates mathematical expressions with support for variables.
 * It supports the operators +, -, *, /, ^ and parentheses.
 * Variables can be passed as command line arguments in the format var=value.
 * Example usage: java Assignment10Part1 2^3*2+5^2^-2 a=3 b=4
 */
public class Assignment10Part1 {

    private static final String OPERATORS = "^*/+-";

    public static void main(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) return;
        String expression = args[0].replace(" ", "");
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
        Deque<String> rpnWithoutVariables = checkAndPushVariable(variables, rpn);
        return calculateRPN(rpnWithoutVariables);
    }


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

    private static void performTheOperation(String token, Deque<Double> tokens) throws EmptyStackException,
            ArithmeticException {
        if (tokens.size() < 2) throw new EmptyStackException();
        tokens.push(makeBaseOperation(token, tokens.pop(), tokens.pop()));
        if (Objects.requireNonNull(tokens.peek()).isNaN() || tokens.peek().isInfinite()) {
            throw new ArithmeticException("Mathematical error: The operation resulted in NaN or Infinity.");
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
     * Parses the input expression into Reverse Polish Notation (RPN) using the Shunting Yard algorithm.
     *
     * @param expression - the mathematical expression to parse
     * @return - a LinkedList representing the RPN of the expression
     */
    private static Deque<String> parse(String expression) {
        Deque<String> rpn = new ArrayDeque<>();
        Deque<String> operatorStack = new ArrayDeque<>();
        StringBuilder operand = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            boolean isUnaryMinus = handleUnaryMinus(operand, symbol, rpn);
            if (isUnaryMinus) {
                continue;
            }
            if (isOperator(String.valueOf(symbol))) {
                addToRPN(rpn, operand);
                handleOperator(operatorStack, rpn, symbol);
            } else {
                operand.append(symbol);
            }
            handleEndOfExpression(expression, operand, rpn, i);
        }
        popRemainingOperators(operatorStack, rpn);
        return rpn;
    }

    private static void handleEndOfExpression(String formula, StringBuilder operand, Deque<String> rpn, int index) {
        if (index == formula.length() - 1 && !operand.isEmpty()) {
            addToRPN(rpn, operand);
        }
    }

    private static boolean handleUnaryMinus(StringBuilder operand, char symbol, Deque<String> rpn) {
        if (operand.isEmpty() && symbol == '-') {
            symbol = '~';
            rpn.add(String.valueOf(symbol));
            return true;
        }
        return false;
    }

    private static void popRemainingOperators(Deque<String> operatorStack, Deque<String> rpn) {
        while (!operatorStack.isEmpty()) {
            rpn.add(operatorStack.pop());
        }
    }

    private static void handleOperator(Deque<String> operatorStack, Deque<String> rpn, char symbol) {
        String operator = String.valueOf(symbol);
        if (!operatorStack.isEmpty() && symbol != '^') {
            handleStackPrecedence(operatorStack, rpn, operator);
        }
        operatorStack.push(String.valueOf(symbol));
    }

    private static void handleStackPrecedence(Deque<String> operatorStack, Deque<String> rpn, String operator) {
        String lastOperatorInStack = operatorStack.peek();

        while (isPow(lastOperatorInStack)) {
            lastOperatorInStack = addAllOperatorsToRPN(rpn, operatorStack);
        }
        if (isDivOrMulti(lastOperatorInStack) && isDivOrMulti(operator)) {
            rpn.add(operatorStack.pop());
        }
        if (isDivOrMulti(lastOperatorInStack) && isPlusOrMinus(operator)) {
            while (isDivOrMulti(lastOperatorInStack)) {
                lastOperatorInStack = addAllOperatorsToRPN(rpn, operatorStack);
            }
        }
        while (isPlusOrMinus(lastOperatorInStack) && isPlusOrMinus(operator)) {
            lastOperatorInStack = addAllOperatorsToRPN(rpn, operatorStack);
        }
    }

    private static String addAllOperatorsToRPN(Deque<String> rpn, Deque<String> operatorStack) {
        rpn.add(operatorStack.pop());
        return !operatorStack.isEmpty() ? operatorStack.peek() : "";
    }

    private static boolean isPlusOrMinus(String symbol) {
        return symbol != null && (symbol.equals("+") || symbol.equals("-"));
    }

    private static boolean isDivOrMulti(String symbol) {
        return symbol != null && (symbol.equals("*") || symbol.equals("/"));
    }

    private static boolean isPow(String symbol) {
        return symbol != null && symbol.equals("^");
    }

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

    private static double makeBaseOperation(String operator, double rightOperand, double leftOperand)
            throws ArithmeticException {
        // handle division by zero
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