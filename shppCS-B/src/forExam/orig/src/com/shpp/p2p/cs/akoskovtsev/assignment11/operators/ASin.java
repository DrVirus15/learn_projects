package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

/**
 * The class implements the "asin" operator, which calculates the arc sine of a number.
 */
public class ASin implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "asin";
    }

    /**
     * Returns the number of operands required by the operator.
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
     * Calculates the arcsine of the given operand.
     *
     * @param operands The operands for the operation.
     * @return The arcsine of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return Math.asin(operands[0]);
    }
}
