package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;


import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;
/**
 * The class implements the "atan" operator for calculating the arctangent of a number.
 */
public class ATan implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "atan    ";
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
     * Calculates the arctangent of the given operand.
     * @param operands An array containing a single operand.
     * @return The arctangent of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return Math.atan(operands[0]);
    }
}
