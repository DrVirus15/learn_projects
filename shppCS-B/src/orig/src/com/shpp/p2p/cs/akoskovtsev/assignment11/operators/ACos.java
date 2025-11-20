package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;


import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

/**
 * The class implements the "acos" operator, which calculates the arc cosine of a given value.
 */
public class ACos implements Operator {
    /**
     *  Returns the string representation of the operator.
     */
    @Override
    public String getOperator() {
        return "acos";
    }

    /**
     * Returns the number of operands required by the operator.
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
     * Calculates the arccosine of the given operand.
     * @param operands An array containing a single operand.
     * @return The arccosine of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return Math.acos(operands[0]);
    }
}
