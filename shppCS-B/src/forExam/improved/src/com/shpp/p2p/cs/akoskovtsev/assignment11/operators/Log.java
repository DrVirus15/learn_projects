package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Log implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "log";
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
     * Calculates the natural logarithm of the given operand.
     * @param operands The operands for the operation.
     * @return The natural logarithm of the operand.
     */
    @Override
    public double calculate(double... operands) {
        return Math.log(operands[0]);
    }
}
