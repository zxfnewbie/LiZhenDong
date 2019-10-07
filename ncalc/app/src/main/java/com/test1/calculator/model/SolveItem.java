package com.test1.calculator.model;

import com.test1.calculator.evaluator.Constants;
import com.test1.calculator.evaluator.FormatExpression;
import com.test1.calculator.evaluator.MathEvaluator;



public class SolveItem extends ExprInput {
    private String leftExpr = "x";
    private String rightExpr = "0";

    private String var = "x";

    public SolveItem(String inp) {
        processInput(inp);
    }

    /**
     * process input
     *
     * @param inp - expression
     */
    private void processInput(String inp) {
        while (inp.contains("==")) inp = inp.replace("==", "="); //clear ==
        if (inp.contains("X")) {
            var = "X";
        } else {
            var = "x";
        }

        //2x + 1 = 2 ....
        if (inp.contains("=")) {
            String[] s = inp.split("=");
            if (s.length >= 2) { //"2x + 1 = 2 = 3" -> only use "2x + 1 = 2"
                if (leftExpr.isEmpty()) leftExpr = "0";
                leftExpr = FormatExpression.appendParenthesis(s[0]);
                if (rightExpr.isEmpty()) rightExpr = "0";
                rightExpr = FormatExpression.appendParenthesis(s[1]);
            } else { //"2x + 1 =" -> index exception because length s = 1
                leftExpr = FormatExpression.appendParenthesis(s[0]);
                rightExpr = "0";
            }
        } else { // 2x + 1
            leftExpr = FormatExpression.appendParenthesis(inp);
            rightExpr = "0";
        }
    }

    @Override
    public boolean isError(MathEvaluator evaluator) {
        boolean b = evaluator.isSyntaxError(leftExpr);
        if (b) return true;
        b = evaluator.isSyntaxError(rightExpr);
        return b;
    }

    public String getInput() {
        return Constants.SOLVE + Constants.LEFT_PAREN
                + leftExpr + " == " + rightExpr + " ," + var +
                Constants.RIGHT_PAREN;
    }

    @Override
    public String toString() {
        return this.leftExpr + Character.toString(Constants.EQUAL_UNICODE) + this.rightExpr;
    }

    /**
     * return input expression
     *
     * @return - String
     */
    public String getExpr() {
        return this.leftExpr + "==" + rightExpr;
    }
}
