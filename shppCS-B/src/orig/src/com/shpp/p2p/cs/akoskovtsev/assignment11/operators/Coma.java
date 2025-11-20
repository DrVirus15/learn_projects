package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Coma implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return ",";
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
        return 0;
    }

    /**
     * Indicates whether the operator is left associative.
     */
    @Override
    public boolean isLeftAssociativity() {
        return false;
    }

    /**
     * This method is not applicable for coma and returns operand[0].
     */
    @Override
    public double calculate(double... operands) {
        return operands[0];
    }
}
