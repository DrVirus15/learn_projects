package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class UnaryMinus implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "~";
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
     * Performs the unary minus operation on the given operand.
     * @param operands The operands for the operation.
     * @return The negated value of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return -operands[0];
    }
}
