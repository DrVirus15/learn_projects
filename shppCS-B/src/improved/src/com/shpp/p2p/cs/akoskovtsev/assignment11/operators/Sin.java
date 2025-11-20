package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Sin implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "sin";
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
     * Calculates the sine of the given operand (in radians).
     * @param operands The operands for the operation.
     * @return The sine of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return Math.sin(operands[0]);
    }
}
