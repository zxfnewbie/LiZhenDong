package com.test1.calculator.evaluator;

import android.content.Context;

import com.test1.calculator.data.CalculatorSetting;

public class EvaluateConfig implements Cloneable {
    //触发模式
    public static final int DEGREE = 0;
    public static final int RADIAN = 1;
    public static final int GRADIAN = 2;

    //计算模式
    public static final int DECIMAL = 0;
    public static final int FRACTION = 1;
    private int trigMode = RADIAN;
    private int evalMode = DECIMAL;
    private int roundTo = 10;
    private int[] variableToKeep = new int[]{};



    private EvaluateConfig() {
    }

    public static EvaluateConfig loadFromSetting(Context c) {
        return CalculatorSetting.createEvaluateConfig(c);
    }


    public static EvaluateConfig newInstance() {
        return new EvaluateConfig();
    }




    public int getEvaluateMode() {
        return evalMode;
    }

    public EvaluateConfig setEvalMode(int evalMode) {
        this.evalMode = evalMode;
        return this;
    }


    public EvaluateConfig setRoundTo(int roundTo) {
        this.roundTo = roundTo;
        return this;
    }
}
