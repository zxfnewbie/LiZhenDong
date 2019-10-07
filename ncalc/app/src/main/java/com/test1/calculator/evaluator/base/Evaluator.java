package com.test1.calculator.evaluator.base;

import com.test1.calculator.evaluator.Constants;
import com.test1.calculator.evaluator.MathEvaluator;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;


public class Evaluator {
    public static final long serialVersionUID = 4L;
    /**
     * 解决数学问题
     * <p/>
     * 基本数学+函数（trig，pi）
     *矩阵
     * Hex和Bin转换
     */
    // 用于解决基本数学问题
    private static final Symbols symbols = new Symbols();
    private BaseModule mBaseModule;

    private String TAG = Evaluator.class.getName();

    public Evaluator() {
        mBaseModule = new BaseModule(this);
    }

    public static boolean equal(String a, String b) {
        return clean(a).equals(clean(b));
    }

    public static String clean(String equation) {
        return equation
                .replace('-', Constants.MINUS_UNICODE)
                .replace('/', Constants.DIV_UNICODE)
                .replace('*', Constants.MUL_UNICODE)
                .replace(Constants.INFINITY, Character.toString(Constants.INFINITY_UNICODE));
    }

    public static boolean isOperator(char c) {
        return ("" + Constants.PLUS_UNICODE +
                Constants.MINUS_UNICODE +
                Constants.DIV_UNICODE +
                Constants.MUL_UNICODE +
                Constants.POWER_UNICODE
        ).indexOf(c) != -1;
    }

    public static boolean isOperator(String c) {
        return isOperator(c.charAt(0));
    }

    /**
     * 检查数字
     *
     * @param number - char
     * @return true if is digit
     */
    public static boolean isDigit(char number) {
        return String.valueOf(number).matches(Constants.REGEX_NUMBER);
    }


    /**
     * 输入一个方程式作为字符串
     * ex: sin(150)
     * 并获得mResult返回。
     */
    public String evaluate(String input, MathEvaluator evaluator) throws SyntaxException {
        if (input.trim().isEmpty()) {
            return "";
        }
        String decimalInput = convertToDecimal(input);
        String result = "";
        try {
            result = evaluator.evaluateWithResultNormal(decimalInput);
            if (result.toLowerCase().equals(Constants.TRUE) || result.toLowerCase().equals(Constants.FALSE)) {
                return result;
            }
            result = clean(mBaseModule.changeBase(result, Base.DECIMAL, mBaseModule.getBase()));
               } catch (Exception e) {
            e.printStackTrace();
            throw new SyntaxException();
        }
        return result;
    }


    public double eval(String input) throws SyntaxException {
        return symbols.eval(input);
    }
    public String convertToDecimal(String input) throws SyntaxException {
        return mBaseModule.changeBase(input, mBaseModule.getBase(), Base.DECIMAL);
    }

    public Base getBase() {
        return mBaseModule.getBase();
    }

    public void setBase(Base base) {
        mBaseModule.setBase(base);
    }

    public BaseModule getBaseModule() {
        return mBaseModule;
    }

}

