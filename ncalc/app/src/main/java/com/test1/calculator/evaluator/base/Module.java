package com.test1.calculator.evaluator.base;

import com.test1.calculator.evaluator.Constants;

/**
 * BaseModule的父类，GraphModule
 */
public class Module {

    // 在需要数学时使用
    private final Evaluator mEvaluator;


    public Module(Evaluator evaluator) {
        mEvaluator = evaluator;
    }


    public char getDecimalPoint() {
        return Constants.DECIMAL_POINT;
    }


    public Evaluator getSolver() {
        return mEvaluator;
    }
}
