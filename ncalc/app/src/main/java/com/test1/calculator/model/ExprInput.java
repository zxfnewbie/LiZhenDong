package com.test1.calculator.model;

import com.test1.calculator.evaluator.MathEvaluator;



public abstract class ExprInput {
    /**
     * check error syntax for input
     *
     * @param evaluator - Class for evaluate
     * @return - true if input error
     */
    public abstract boolean isError(MathEvaluator evaluator);

    /**
     * build and return input
     * <p>
     * such as:
     * <p>
     * Solve(2x + 1, x)
     * Int(x, x)
     * D(2x + 3)
     * Int(sqrt(x), {x, 2, 4}
     *
     * @return - input
     */
    public abstract String getInput();


}
