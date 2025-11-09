package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment10;

import java.util.*;


/**
 * A calculator that evaluates mathematical expressions with support for variables.
 * It supports the operators +, -, *, /, ^ and parentheses.
 * Variables can be passed as command line arguments in the format var=value.
 * Example usage: java Assignment10Part1 2^3*2+5^2^-2 a=3 b=4
 * Trigonometric functions, other advanced functions and '(' or ')' or ',' are not supported in this version.
 * All commented code is for future implementation Assignment11.
 */
public class Assignment10Part1 {
    //    private static final HashMap<String, Character> TRIG_FUNC = new HashMap<>();

    // Operators supported by the calculator, operators '(' and ')' - are not used in this version
    private static final String OPERATORS = "^*/+-,()";
    // The number of operands for each operator, made for future implementation Assignment11
    private static final HashMap<String, Integer> OPERATORS_LIST = new HashMap<>();

    public static void main(String[] args) {
//        String formula = "-2^3^2+5^2-2";
        String formula = args[0].replaceAll(" ", "");
        HashMap<String, Double> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String parse = args[i].replaceAll(" ", "");
            int index = parse.indexOf('=');
            variables.put(parse.substring(0, index), Double.parseDouble(parse.substring(index + 1)));
        }
//        variables.put("ab", 2.0);
//        variables.put("a", 3.0);
//        variables.put("aba", 4.0);
//        setOperators();
        setOperandsCount(); // set the number of operands for each operator
        double result = makeCalculation(formula.trim().replaceAll(" ", ""), variables);
        System.out.println("result: " + result);
    }

    /**
     * Performs the calculation of the given expression using the provided variables.
     *
     * @param formula   - expression to calculate
     * @param variables - map of variable names to their values
     * @return - the result of the calculation
     */
    private static double makeCalculation(String formula, HashMap<String, Double> variables) {
        LinkedList<String> rpn = parse(formula);// convert to Reverse Polish Notation
//        System.out.println("rpn: " + rpn);
        return calculateRPN(rpn, variables); // calculate the result from RPN
    }

    /**
     * Calculates the result of an expression in Reverse Polish Notation (RPN).
     *
     * @param stack     - the RPN expression as a stack of tokens
     * @param variables - map of variable names to their values
     * @return - the result of the calculation
     */
    private static double calculateRPN(LinkedList<String> stack, HashMap<String, Double> variables) {
        double unaryMinus = 1.0; // to handle unary minus
        LinkedList<Double> tokens = new LinkedList<>(); // stack for operands
        while (!stack.isEmpty()) {
            String token = stack.pop();
            // Handle unary minus
            if (token.equals("~")) {
                unaryMinus *= -1.0;
                continue;
            }
            // Check if the token is a variable
            for (Map.Entry<String, Double> entry : variables.entrySet()) {
                if (token.equals(entry.getKey())) {
                    // if variable found, push its value onto the stack and apply unary minus if needed
                    tokens.push(entry.getValue() * unaryMinus);
                    // change unaryMinus back to 1.0 for next token
                    unaryMinus = 1.0;
                    // clear the token to avoid further processing
                    token = "";
                }
            }
            // Check if the token is an operator
            for (Map.Entry<String, Integer> entry : OPERATORS_LIST.entrySet()) {
                // if operator found, pop the required number of operands and perform the operation
                if (token.equals(entry.getKey())) {
                    double[] operand = new double[2];
                    // pop operands in reverse order
                    for (int i = entry.getValue() - 1; i >= 0; i--) {
                        // if not enough operands, print error and return 0.0
                        if (tokens.isEmpty()) {
                            System.out.println("For operator " + token + " not enough operands.");
                            System.out.println("Fix the errors in the expression.");
                            return 0.0;
                        } else { // pop operand from stack
                            operand[i] = tokens.pop();
                        }
                    }
                    // perform the operation and push the result onto the stack
                    tokens.push(makeBaseOperation(token, operand));
                    System.out.println(operand[0] + " " + token + " " + operand[1] + " = " + tokens.peek());
                    //noinspection DataFlowIssue
                    if (tokens.peek().isNaN() || tokens.peek().isInfinite()) {
                        System.out.println("The result of the operation is not a number or infinity. Fix the errors in the expression.");
                        return 0.0;
                    }
                    token = "";
                }
            }
            // If the token is not a variable or operator, it should be a number
            if (!token.isEmpty()) {
                try {
                    // parse the number and push it onto the stack, applying unary minus if needed
                    tokens.push(Double.parseDouble(token) * unaryMinus);
                    unaryMinus = 1;
                } catch (NumberFormatException e) {
                    System.out.println("The token " + token + " cannot be parsed as a number.");
                    System.out.println("Fix the errors in the expression.");
                    return 0.0;
                }
            }
        }
        // if there are some exceptions in the calculation, print error and return 0.0
        if (tokens.isEmpty()) {
            System.out.println("There is no numbers. Fix the errors in the expression");
            return 0.0;
        }
        return tokens.pop();
    }

    /**
     * Parses the input formula into Reverse Polish Notation (RPN) using the Shunting Yard algorithm.
     *
     * @param formula - the mathematical expression to parse
     * @return - a LinkedList representing the RPN of the expression
     */
    private static LinkedList<String> parse(String formula) {
        LinkedList<String> rpn = new LinkedList<>(); // output stack
        LinkedList<String> operatorStack = new LinkedList<>(); // operator stack
        StringBuilder operand = new StringBuilder(); // to build multi-character operands
        for (int i = 0; i < formula.length(); i++) {
            char symbol = formula.charAt(i);
            // Handle unary minus
            if (operand.isEmpty() && symbol == '-') { // if '-' is at the start of operand or variable
                symbol = '~';                         // use '~' to represent unary minus
                rpn.add(String.valueOf(symbol));
                continue;
            }
            // Check if the symbol is an operator
            int symbolIndex = OPERATORS.indexOf(symbol);
            if (symbolIndex == -1) { // if not an operator, build the operand
                operand.append(symbol);
            } else {
//                if (symbol == '(') {
//                    operand = addToPRN(rpn, operand);
//                    if (!operand.isEmpty()) {
//                        operatorStack.push(operand.toString());
//                        operand = new StringBuilder();
//                    }
//                    operatorStack.push(String.valueOf(symbol));
//                    continue;
//                }
//                if (symbol == ')') {
//                    operand = addToPRN(rpn, operand);
//                    if (!operand.isEmpty()) {
//                        operatorStack.push(operand.toString());
//                        operand = new StringBuilder();
//                    }
//                    while (!operatorStack.isEmpty()) {
//                        String tmp = operatorStack.pop();
//                        if (!tmp.equals("(")) {
//                            rpn.add(tmp);
//                        } else {
//                            break;
//                        }
//                    }
//                    continue;
//                }
//                if (symbol == ',') {
//                    continue;
//                }
                operand = addToPRN(rpn, operand); // add the built operand to RPN
//                if (!operand.isEmpty()) {
//                    operatorStack.push(operand.toString());
//                    operand = new StringBuilder();
//                }
                // Handle operator precedence and associativity
                if (!operatorStack.isEmpty() && symbol != '^') {
                    // check operator at the top of the stack
                    int stackIndex = OPERATORS.indexOf(operatorStack.peek());
                    // while the top operator is ^, pop all to RPN (first right associativity)
                    while (stackIndex == 0) {
                        rpn.add(operatorStack.pop());
                        // check the next operator at the top of the stack
                        if (!operatorStack.isEmpty()) {
                            stackIndex = OPERATORS.indexOf(operatorStack.peek());
                        } else { // if stack is empty, set index to -1 to exit loop
                            stackIndex = -1;
                        }
                    }
                    // if the top operator is * or / and current is * or /, pop one to RPN
                    if ((stackIndex == 1 || stackIndex == 2) && symbolIndex < 3) {
                        rpn.add(operatorStack.pop());
                    }
                    // if the top operator is * or / and current is + or -, pop all operators  from stack to RPN
                    if ((stackIndex == 1 || stackIndex == 2) && symbolIndex > 2) {
                        while (stackIndex == 2 || stackIndex == 1) {
                            rpn.add(operatorStack.pop());
                            // check the next operator at the top of the stack
                            if (!operatorStack.isEmpty()) {
                                stackIndex = OPERATORS.indexOf(operatorStack.peek());
                            } else { // if stack is empty, set index to -1 to exit loop
                                stackIndex = -1;
                            }
                        }
                    }
                    // if the top operator is + or - and current is + or -, pop all operators from stack to RPN
                    while ((stackIndex == 3 || stackIndex == 4) && symbolIndex > 2) {
                        rpn.add(operatorStack.pop());
                        // check the next operator at the top of the stack
                        if (!operatorStack.isEmpty()) {
                            stackIndex = OPERATORS.indexOf(operatorStack.peek());
                        } else { // if stack is empty, set index to -1 to exit loop
                            stackIndex = -1;
                        }
                    }
                }
                // push the current operator onto the stack
                operatorStack.push(String.valueOf(symbol));
            }
            // if it's the last character in the formula, add any remaining operand to RPN
            if (i == formula.length() - 1 && !operand.isEmpty()) {
                operand = addToPRN(rpn, operand);
//                if (!operand.isEmpty()) {
//                    operatorStack.push(operand.toString());
//                    operand = new StringBuilder();
//                }
            }
        }
        // pop any remaining operators from the stack to RPN
        while (!operatorStack.isEmpty()) {
            rpn.add(operatorStack.pop());
        }
        return rpn;
    }

    /**
     * Adds the built operand to the RPN stack if it's not empty.
     *
     * @param stack  - the RPN stack
     * @param number - the built operand
     * @return - a new StringBuilder for the next operand
     */
    private static StringBuilder addToPRN(LinkedList<String> stack, StringBuilder number) {
        if (!number.isEmpty()) {
//            for (Map.Entry<String, Character> entry : TRIG_FUNC.entrySet()) {
//                if (entry.getKey().contentEquals(number)) {
//                    number = new StringBuilder();
//                    number.append(entry.getValue());
//                    return number;
//                }
//            }
            stack.add(String.valueOf(number));
        }
        return new StringBuilder();
    }

    /**
     * Performs a basic mathematical operation on the given operands.
     *
     * @param operator - the operator as a string
     * @param operand  - array of operands
     * @return - the result of the operation
     */
    private static double makeBaseOperation(String operator, double[] operand) {
        // handle division by zero
        if (operator.equals("/") && operand[1] == 0) {
            System.out.println("Zero division error. Fix the errors in the expression. Returning 0.0");
            return 0.0;
        }
        return switch (operator) {
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
//            case "2" -> Math.log(operand[0]);
//            case "L" -> Math.log10(operand[0]);
//            case "e" -> exp(operand[0]);
//            case "m" -> Math.min(operand[0], operand[1]);
//            case "M" -> Math.max(operand[0], operand[1]);
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

    /**
     * Sets the number of operands required for each operator.
     */
    private static void setOperandsCount() {
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

//    private static void setOperators() {
//        TRIG_FUNC.put("cos", 'c');
//        TRIG_FUNC.put("acos", 'C');
//        TRIG_FUNC.put("sin", 's');
//        TRIG_FUNC.put("asin", 'S');
//        TRIG_FUNC.put("tan", 't');
//        TRIG_FUNC.put("atan", 'T');
//        TRIG_FUNC.put("sqrt", 'q');
//        TRIG_FUNC.put("log2", '2');
//        TRIG_FUNC.put("ln", 'l');
//        TRIG_FUNC.put("log10", 'L');
//        TRIG_FUNC.put("exp", 'e');
//        TRIG_FUNC.put("max", 'M');
//        TRIG_FUNC.put("min", 'm');
//    }
}

