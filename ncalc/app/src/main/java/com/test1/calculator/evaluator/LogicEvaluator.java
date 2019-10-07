package com.test1.calculator.evaluator;


import com.test1.calculator.evaluator.base.Base;
import com.test1.calculator.evaluator.base.Evaluator;
import com.test1.calculator.tokenizer.Tokenizer;

import org.javia.arity.SyntaxException;


public abstract class LogicEvaluator {
    public static final int RESULT_OK = 1;
    public static final int RESULT_ERROR = -1;
    public static final int INPUT_EMPTY = 2;
    public static final int RESULT_ERROR_WITH_INDEX = 3;
    public static final String ERROR_INDEX_STRING = "?";
    protected final Tokenizer mTokenizer;
    private final Evaluator mEvaluator;

    public LogicEvaluator() {
        mEvaluator = new Evaluator();
        mTokenizer = new Tokenizer();
    }

    public void evaluateBase(CharSequence expr, EvaluateCallback callback) {
        evaluate(expr.toString(), callback);
    }

    public abstract MathEvaluator getEvaluator();

    private void evaluate(String expr, EvaluateCallback callback) {
        expr = FormatExpression.cleanExpression(expr, mTokenizer);
        try {
            String result = mEvaluator.evaluate(expr, getEvaluator());
            result = mTokenizer.getLocalizedExpression(result);
            callback.onEvaluated(expr, result, LogicEvaluator.RESULT_OK);
        } catch (SyntaxException e) {
            String result = e.message + ";" + e.position;
            callback.onEvaluated(expr, result, LogicEvaluator.RESULT_ERROR_WITH_INDEX);
        } catch (Exception e) {
        }
    }

    public void setBase(String expr, Base base, EvaluateCallback callback) {
        try {
            String result;
            result = mEvaluator.getBaseModule().setBase(expr, base);
            if (result.isEmpty()) {
                callback.onEvaluated(expr, result, INPUT_EMPTY);
                return;
            }
            callback.onEvaluated(expr, result, RESULT_OK);
        } catch (SyntaxException e) {
        }
    }

    public Evaluator getBaseEvaluator() {
        return mEvaluator;
    }


    public interface EvaluateCallback {
        void onEvaluated(String expr, String result, int errorResourceId);

        void onCalculateError(Exception e);
    }
}