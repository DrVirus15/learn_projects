package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Multiply implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "*";
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
        return 2;
    }

    /**
     * Indicates whether the operator is left associative.
     */
    @Override
    public boolean isLeftAssociativity() {
        return true;
    }

    /**
     * Performs the operation on the given operands.
     *
     * @param operands The operands for the operation.
     * @return The result of multiplying the operands.
     */
    @Override
    public double calculate(double... operands) {
        return operands[0] * operands[1];
    }
}
