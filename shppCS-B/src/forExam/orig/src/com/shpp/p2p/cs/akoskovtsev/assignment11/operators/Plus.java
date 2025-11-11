package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Plus implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "+";
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
        return 1;
    }

    /**
     * Indicates whether the operator is left associative.
     */
    @Override
    public boolean isLeftAssociativity() {
        return true;
    }

    /**
     * Calculates the sum of the given operands.
     *
     * @param operands The operands for the operation.
     * @return The result of adding the operands.
     */
    @Override
    public double calculate(double... operands) {
        return operands[0] + operands[1];
    }
}
