package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Min implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "min";
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
     * Calculates the minimum of two operands.
     * @param operands The operands to compare.
     * @return The minimum value among the operands.
     */
    @Override
    public double calculate(double... operands) {
        return Math.min(operands[0], operands[1]);
    }
}
