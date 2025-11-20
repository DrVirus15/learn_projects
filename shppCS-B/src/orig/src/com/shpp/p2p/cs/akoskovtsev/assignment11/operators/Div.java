package orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.operators;

import orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator;

public class Div implements Operator {
    /**
     * Returns the operator symbol.
     */
    @Override
    public String getOperator() {
        return "/";
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
        return 2;
    }

    /**
     * Indicates whether the operator is left associative.
     */
   @Override
   public boolean isLeftAssociativity(){
        return true;
   }

   /** Performs division of two operands.
    * @param operands an array containing two operands
    * @return the result of dividing the first operand by the second
    * @throws ArithmeticException if the second operand is zero
    */
    @Override
    public double calculate(double... operands) {
        if (operands[1] == 0) {
            throw new ArithmeticException();
        }
        return operands[0] / operands[1];
    }
}
