package com.shpp.p2p.cs.test;

public enum Operator implements improved.src.com.shpp.p2p.cs.akoskovtsev.assignment11.Operator {
    PLUS("+", 2, 1, true),
    MINUS("-", 2, 1, true)
    ;

    private final String operator;
    private final int count;
    private final int precedence;
    private final boolean isLeftAssociativity;

    Operator(String operator, int count, int precedence, boolean isLeftAssociativity) {
        this.operator = operator;
        this.count = count;
        this.precedence = precedence;
        this.isLeftAssociativity = isLeftAssociativity;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public int getOperandCount() {
        return count;
    }

    @Override
    public int getPrecedence() {
        return precedence;
    }

    @Override
    public boolean isLeftAssociativity() {
        return isLeftAssociativity;
    }

    @Override  //TODO винести в інше місце?
    public double calculate(double... operands) {
        return 0;
    }
}
