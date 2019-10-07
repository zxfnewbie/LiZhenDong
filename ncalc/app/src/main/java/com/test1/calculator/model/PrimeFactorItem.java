package com.test1.calculator.model;

import com.test1.calculator.evaluator.MathEvaluator;

public class PrimeFactorItem extends ExprInput {
    private String number;

    public String getNumber() {
        return number;
    }

    public PrimeFactorItem(String number) {

        this.number = number;
    }

    @Override
    public boolean isError(MathEvaluator evaluator) {
        return false;
    }

    @Override
    public String getInput() {
        return "FactorInteger(" + number + ")";
    }
}
