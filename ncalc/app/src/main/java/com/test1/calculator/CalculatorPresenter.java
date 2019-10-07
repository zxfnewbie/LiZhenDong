package com.test1.calculator;

import android.support.annotation.NonNull;


public class CalculatorPresenter implements CalculatorContract.Presenter {

    private CalculatorContract.Display display;
    private CalculatorContract.ResourceAccess resourceAccess;

    public CalculatorPresenter(@NonNull CalculatorContract.Display display,
                               @NonNull CalculatorContract.ResourceAccess resourceAccess) {
        this.display = display;
        this.resourceAccess = resourceAccess;
    }


    @Override
    public CalculatorContract.Display getDisplay() {
        return null;
    }

}
