package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11;

/**
 * Enumeration representing mathematical operators and functions. Each operator has its symbol, number of operands,
 * precedence level, and associativity. The enum also provides a method to perform the calculation based on the operator.
 */
public enum Operator {
    PLUS("+", 2, 1, true) {
        @Override
        public double calculate(double[] operands) {
            return operands[0] + operands[1];
        }
    },
    MINUS("-", 2, 1, true) {
        @Override
        public double calculate(double[] operands) {
            return operands[0] - operands[1];
        }
    },
    MULTIPLY("*", 2, 2, true) {
        @Override
        public double calculate(double[] operands) {
            return operands[0] * operands[1];
        }
    },
    DIV("/", 2, 2, true) {
        @Override
        public double calculate(double[] operands) {
            if (operands[1] == 0) {
                throw new ArithmeticException("Mathematical error: Division by zero.");
            }
            return operands[0] / operands[1];
        }
    },
    POW("^", 2, 3, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.pow(operands[0], operands[1]);
        }
    },
    OPEN_BRACKET("(", 0, 0, false) {
        @Override
        public double calculate(double[] operands) {
            return 0;
        }
    },
    CLOSE_BRACKET(")", 0, 0, false) {
        @Override
        public double calculate(double[] operands) {
            return 0;
        }
    },
    COS("cos", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.cos(operands[0]);
        }
    },
    A_COS("acos", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.acos(operands[0]);
        }
    },
    SIN("sin", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.sin(operands[0]);
        }
    },
    A_SIN("asin", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.asin(operands[0]);
        }
    },
    TAN("tan", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.tan(operands[0]);
        }
    },
    A_TAN("atan", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.atan(operands[0]);
        }
    },
    EXP("exp", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.exp(operands[0]);
        }
    },
    LOG("log", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.log(operands[0]);
        }
    },
    LOG_2("log2", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.log(operands[0]) / Math.log(2);
        }
    },
    LOG_10("log10", 1, 4, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.log10(operands[0]);
        }
    },
    SQRT("sqrt", 1, 3, false) {
        @Override
        public double calculate(double[] operands) {
            return Math.sqrt(operands[0]);
        }
    },
    UNARY_MINUS("~", 1, 3, false) {
        @Override
        public double calculate(double... operands) {
            return -operands[0];
        }
    };

    /**
     * The symbol representing the operator ("+", "-", "*", "/" "^" "cos" "sin" "tan" etc.).
     */
    private final String operator;
    /**
     * The number of operands the operator works with (1 for unary operators, 2 for binary operators).
     */
    private final int operandsCount;
    /**
     * The precedence level of the operator. Higher values indicate higher precedence.
     */
    private final int precedence;
    /**
     * Indicates if the operator is left associative.
     */
    private final boolean isLeftAssociativity;

    /**
     * Constructor to initialize the operator with its properties.
     *
     * @param operator            - the symbol representing the operator
     * @param operandsCount       - number of operands the operator works with
     * @param precedence          - precedence level of the operator
     * @param isLeftAssociativity - indicates if the operator is left associative
     */
    Operator(String operator, int operandsCount, int precedence, boolean isLeftAssociativity) {
        this.operator = operator;
        this.operandsCount = operandsCount;
        this.precedence = precedence;
        this.isLeftAssociativity = isLeftAssociativity;
    }

    /**
     * Get the symbol representing the operator ("+", "-", "*", "/" "^" "cos" "sin" "tan" etc.).
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Get the number of operands the operator works with (1 for unary operators, 2 for binary operators).
     */
    public int getOperandsCount() {
        return operandsCount;
    }

    /**
     * Get the precedence level of the operator. Higher values indicate higher precedence.
     * 0 - brackets
     * 1 - low precedence (+, -)
     * 2 - medium precedence ( *, /)
     * 3 - high precedence (^, unary minus, sqrt)
     * 4 - very high precedence (sin, cos, etc.)
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Check if the operator is left associative.
     */
    public boolean isLeftAssociativity() {
        return isLeftAssociativity;
    }

    /**
     * An abstract method to perform the calculation based on the operator.
     *
     * @param operands - an array of operands for the calculation
     * @return the result of the calculation
     */
    public abstract double calculate(double[] operands);
}
