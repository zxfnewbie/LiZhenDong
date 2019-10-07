package com.test1.calculator.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.test1.calculator.tokenizer.Tokenizer;


public abstract class AbstractCalculatorActivity extends AbstractNavDrawerActionBarActivity implements ICalculator {
    public Tokenizer mTokenizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTokenizer = new Tokenizer();
    }


    /**
     * 插入要显示的文本 - 不清除屏幕
     * <p/>
     * 仅用于计算器（基础，科学，复杂）
     *
     * @param s - text
     */
    public abstract void insertText(String s);

    /**
     * 插入运算符到显示
     * 不清除屏幕
     *
     * @param s - operator
     */
    public abstract void insertOperator(String s);


    public abstract void setTextDisplay(String text);


}
