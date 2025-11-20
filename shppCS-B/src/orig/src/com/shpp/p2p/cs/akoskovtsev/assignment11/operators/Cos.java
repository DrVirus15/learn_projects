package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Cos implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "cos";
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
     * Performs the cosine operation on the given operand.
     * @param operands The operands for the operation.
     */
    @Override
    public double calculate(double... operands) {
        return Math.cos(operands[0]);
    }
}
