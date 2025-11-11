package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Max implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "max";
    }

    /**
     * Returns the number of operands the operator works with.
     */
    @Override
    public int getOperandCount() {
        return 2;
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
     * Calculates the maximum of two operands.
     * @param operands the operands to compare
     * @return the maximum value among the operands
     */
    @Override
    public double calculate(double... operands) {
        return Math.max(operands[0], operands[1]);
    }
}
