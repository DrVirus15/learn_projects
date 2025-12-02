package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11;

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

    private final String operator;
    private final int operandsCount;
    private final int precedence;
    private final boolean isLeftAssociativity;

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
     * 1 - low precedence (e.g., +, -)
     * 2 - medium precedence (e.g., *, /)
     * 3 - high precedence (e.g., ^)
     * 4 - very high precedence (e.g., functions like sin, cos, etc.)
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


    public abstract double calculate(double[] operands);
}
