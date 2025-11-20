package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Tan implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "tan";
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
     * Calculates the tangent of the given operand.
     * @param operands The operands for the operation.
     * @return The tangent of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return Math.tan(operands[0]);
    }
}
