package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Log_2 implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "log2";
    }

    /**
     * Returns the number of operands the operator works with.
     */
    @Override
    public int getOperandCount() {
        return 1;
    }

    /**
     * Returns the precedence level of the operator.
     */
    @Override
    public int getPrecedence() {
        return 4;
    }

    /**
     * Indicates whether the operator is left associative.
     */
    @Override
    public boolean isLeftAssociativity() {
        return false;
    }

    /**
     * Calculates the base-2 logarithm of the given operand.
     * @param operands An array containing a single operand.
     * @return The base-2 logarithm of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return Math.log(operands[0]) / Math.log(2);
    }
}
