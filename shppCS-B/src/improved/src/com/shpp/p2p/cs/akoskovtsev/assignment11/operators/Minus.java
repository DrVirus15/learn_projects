package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Minus implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "-";
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
     * Performs the subtraction operation on the provided operands.
     * @param operands The operands for the operation.
     * @return The result of subtracting the second operand from the first.
     */
    @Override
    public double calculate(double... operands) {
        return operands[0] - operands[1];
    }
}
