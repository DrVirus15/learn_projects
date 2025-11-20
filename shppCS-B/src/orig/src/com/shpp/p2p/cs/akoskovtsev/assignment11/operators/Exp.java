package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Exp implements Operator {

    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "exp";
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
     * Performs the exponential operation on the given operand.
     * @param operands The operands for the operation.
     * @return The result of the exponential operation.
     */
    @Override
    public double calculate(double... operands) {
        return Math.exp(operands[0]);
    }
}
