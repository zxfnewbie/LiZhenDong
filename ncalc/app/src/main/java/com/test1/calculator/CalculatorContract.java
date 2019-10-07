
package com.test1.calculator;

import android.support.annotation.StringRes;


public class CalculatorContract {

    public interface Display {


        void clear();

    }

    public interface ResourceAccess {
        String getString(@StringRes int id);
    }

    public interface Presenter {

        Display getDisplay();

    }
}
