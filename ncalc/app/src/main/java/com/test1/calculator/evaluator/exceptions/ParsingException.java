package com.test1.calculator.evaluator.exceptions;


public class ParsingException extends RuntimeException {

    private String expr;
    private int index;
    private String msg;

    public ParsingException(String expr, int index) {

        this.expr = expr;
        this.index = index;
    }


    public int getIndex() {
        return index;
    }

    public String getExpr() {
        return expr;
    }
}
