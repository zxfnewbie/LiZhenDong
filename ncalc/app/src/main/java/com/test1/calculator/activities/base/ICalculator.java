package com.test1.calculator.activities.base;
public interface ICalculator {
    void onResult(final String result);

    void onError(final String errorResourceId);

    void onDelete();

    void clickClear();

    void onEqual();
}
