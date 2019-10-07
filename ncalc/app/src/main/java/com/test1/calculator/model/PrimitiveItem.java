package com.test1.calculator.model;

import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.Constants;
import com.test1.calculator.evaluator.FormatExpression;


public class PrimitiveItem extends ExprInput {
       private static final String TAG = "PrimitiveItem";
    private String input;
    private String var = "x";

    public PrimitiveItem(String input) {
        this.input = FormatExpression.appendParenthesis(input);
    }

    @Override
    public boolean isError(MathEvaluator evaluator) {
        return evaluator.isSyntaxError(input);
    }

    public String getInput() {
        //build mResult
        String res = Constants.PRIMITIVE +
                Constants.LEFT_PAREN +
                input + "," + var +
                Constants.RIGHT_PAREN;
        //pri(2x + sin(x), x)
             return res;
    }
}
