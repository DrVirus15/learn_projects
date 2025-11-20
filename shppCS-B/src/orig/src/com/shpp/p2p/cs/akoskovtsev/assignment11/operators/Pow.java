package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Pow implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "^";
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
        return 3;
    }

    /**
     * Indicates whether the operator is left associative.
     */
    @Override
    public boolean isLeftAssociativity() {
        return false;
    }

    /**
     * Calculates the result of raising the first operand to the power of the second operand.
     * @param operands The operands for the operation.
     * @return The result of the exponentiation.
     */
    @Override
    public double calculate(double... operands) {
        return Math.pow(operands[0], operands[1]);
    }
}
