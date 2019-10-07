package com.test1.calculator.model;

import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.Constants;
import com.test1.calculator.evaluator.FormatExpression;



public class DerivativeItem extends ExprInput {
    private static final String TAG = "DerivativeItem";
    private String input;
    private String var = "x";
    private String level = "1";

    public DerivativeItem(String input, String var, String level) {
        this.input = FormatExpression.cleanExpression(input);
        if (!var.isEmpty()) this.var = var;     //if var = "", do not set var
        this.level = level;
    }

    @Override
    public boolean isError(MathEvaluator evaluator) {
        return evaluator.isSyntaxError(input);
    }

    public String getInput() {
        //建立mResult
        String res = Constants.DERIVATIVE +
                Constants.LEFT_PAREN +
                input + "," + "{" + var + "," + level + "}" +
                Constants.RIGHT_PAREN;
        //pri(2x + sin(x), x)
              return res;
    }

    @Override
    public String toString() {
        return this.getInput();
    }

   }
