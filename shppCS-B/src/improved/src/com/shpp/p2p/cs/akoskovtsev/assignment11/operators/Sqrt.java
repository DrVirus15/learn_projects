package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Sqrt implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "sqrt";
    }

    /**
     * Returns the number of operands the operator works with.
     */
    @Override
    public int getOperandCount() {
        return 1;
    }

    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public boolean isLeftAssociativity() {
        return false;
    }

    @Override
    public double calculate(double... operands) {
        return Math.sqrt(operands[0]);
    }
}
