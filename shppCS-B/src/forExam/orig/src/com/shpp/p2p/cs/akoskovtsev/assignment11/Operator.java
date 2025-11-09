package forExam.orig.src.com.shpp.p2p.cs.akoskovtsev.assignment11;

/**
 * This interface is used to define mathematical operators for an expression evaluator.
 * Each operator must implement methods to provide its symbol, operand count, precedence,
 * associativity, and the calculation logic.
 */
public interface Operator {
    /**
     * Get the symbol representing the operator ("+", "-", "*", "/" "^" "cos" "sin" "tan" etc.).
     */
    String getOperator();

    /**
     * Get the number of operands the operator works with (1 for unary operators, 2 for binary operators).
     */
    int getOperandCount();

    /**
     * Get the precedence level of the operator. Higher values indicate higher precedence.
     */
    int getPrecedence();

    /**
     * Check if the operator is left associative.
     */
    boolean isLeftAssociativity();

    /**
     * Perform the calculation using the provided operands.
     *
     * @param operands The operands for the operation.
     */
    double calculate(double... operands);
}
