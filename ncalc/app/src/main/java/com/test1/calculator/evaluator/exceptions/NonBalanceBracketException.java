package com.test1.calculator.evaluator.exceptions;


public class NonBalanceBracketException extends ParsingException {

    public NonBalanceBracketException(String expr, int index) {
        super(expr, index);
    }

    @Override
    public String getMessage() {
        return "Non balance bracket of " + getExpr() + " at " + getIndex();
    }
}
