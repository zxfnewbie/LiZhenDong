package com.test1.calculator.evaluator;

import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class DecimalFactory {

    public static DecimalFormat format;

    static {
        format = new DecimalFormat("#.#####");
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
    }

    private final MathEvaluator mEvaluator;

    /**
     *使用格式小数分隔符
     */

    public DecimalFactory(MathEvaluator mEvaluator) {
        this.mEvaluator = mEvaluator;
    }

    /**
     * round decimal value
     *
     * @param val - input
     * @param i   - number of decimal
     * @return - String
     */
    public static String round(double val, int i) {
        java.math.BigDecimal bigDecimal = new java.math.BigDecimal(val);
        String res = bigDecimal.round(new MathContext(i + 1)).toString();
        return res;
    }



}
