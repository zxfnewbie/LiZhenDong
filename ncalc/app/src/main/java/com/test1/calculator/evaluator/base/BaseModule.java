package com.test1.calculator.evaluator.base;

import android.util.Log;

import com.test1.calculator.evaluator.Constants;

import org.javia.arity.SyntaxException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public class BaseModule extends Module {

    // 用于在文本中保留对游标的引用
    public static final char SELECTION_HANDLE = '\u2620';

    // 小数基数变化的小数位数
    private final static int PRECISION = 8;
    private static final String TAG = BaseModule.class.getName();
    // Regex to strip out things like "90" from "sin(90)"
    private final String REGEX_NUMBER;
    private final String REGEX_NOT_NUMBER;
    // 目前的设置。 默认为十进制。
    private Base mBase = Base.DECIMAL;
    // 基数改变时的监听器。
    private OnBaseChangeListener mBaseChangeListener;

    public BaseModule(Evaluator evaluator) {
        super(evaluator);

        // 修改常量以包含假字符SELECTION_HANDLE
        REGEX_NUMBER = Constants.REGEX_NUMBER
                .substring(0, Constants.REGEX_NUMBER.length() - 1) + SELECTION_HANDLE + "]";
        REGEX_NOT_NUMBER = Constants.REGEX_NOT_NUMBER
                .substring(0, Constants.REGEX_NOT_NUMBER.length() - 1) + SELECTION_HANDLE + "]";
    }

    public Base getBase() {
        return mBase;
    }

    public void setBase(Base base) {
        mBase = base;
        if (mBaseChangeListener != null) mBaseChangeListener.onBaseChange(mBase);
    }

    public String setBase(String input, Base base) throws SyntaxException {
        String text = changeBase(input, mBase, base);
        setBase(base);
        return text;
    }



    /**
     * 将文本更新到新基础。 这不会设置活动基数。
     */
    public String changeBase(final String originalText, final Base oldBase, final Base newBase) throws SyntaxException {
        if (oldBase.equals(newBase) || originalText.isEmpty() || originalText.matches(REGEX_NOT_NUMBER)) {
            return originalText;
        }
        Log.d(TAG, "changeBase: " + originalText + "; oldBase " + String.valueOf(oldBase) + "; newBase " + String.valueOf(newBase));

        String[] operations = originalText.split(REGEX_NUMBER);
        String[] numbers = originalText.split(REGEX_NOT_NUMBER);
        String[] translatedNumbers = new String[numbers.length];

        Log.d(TAG, "changeBase: operation " + operations.toString() + "; numbers " + numbers.toString());

        for (int i = 0; i < numbers.length; i++) {
            if (!numbers[i].isEmpty()) {
                switch (oldBase) {
                    case BINARY:
                        switch (newBase) {
                            case BINARY:
                                break;
                            case DECIMAL:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 2, 10);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                            case HEXADECIMAL:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 2, 16);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                            case OCTAL:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 2, 8);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                        }
                        break;
                    case OCTAL:
                        switch (newBase) {
                            case BINARY:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 8, 2);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                            case OCTAL:
                                break;
                            case DECIMAL:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 8, 10);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);

                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                            case HEXADECIMAL:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 8, 16);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                        }
                        break;
                    case DECIMAL:
                        switch (newBase) {
                            case BINARY:
                                try {

                                    translatedNumbers[i] = newBase(numbers[i], 10, 2);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                            case DECIMAL:
                                break;
                            case HEXADECIMAL:
                                try {

                                    translatedNumbers[i] = newBase(numbers[i], 10, 16);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                            case OCTAL:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 10, 8);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                        }
                        break;
                    case HEXADECIMAL:
                        switch (newBase) {
                            case BINARY:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 16, 2);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);

                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                            case DECIMAL:
                                try {
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                    translatedNumbers[i] = newBase(numbers[i], 16, 10);
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, numbers[i] + " is not a number", e);
                                    throw new SyntaxException();
                                }
                                break;
                            case HEXADECIMAL:
                                break;
                            case OCTAL:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 16, 8);
                                    Log.d(TAG, "translatedNumbers " + i + " = " + translatedNumbers[i]);
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                                break;
                        }
                        break;
                }
            }
        }
        String text = "";
        Object[] o = removeWhitespace(operations);
        Object[] n = removeWhitespace(translatedNumbers);
        if (originalText.substring(0, 1).matches(REGEX_NUMBER)) {
            for (int i = 0; i < o.length && i < n.length; i++) {
                text += n[i];
                text += o[i];
            }
        } else {
            for (int i = 0; i < o.length && i < n.length; i++) {
                text += o[i];
                text += n[i];
            }
        }
        if (o.length > n.length) {
            text += o[o.length - 1];
        } else if (n.length > o.length) {
            text += n[n.length - 1];
        }
//        Log.d(TAG, "changeBase return " + text);
        return text;
    }

    public String newBase(String originalNumber, int originalBase, int base) throws SyntaxException {
        String[] split = originalNumber.split(Pattern.quote(getDecimalPoint() + ""));

        //去除多余空格. 例如 "6 "
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }
        if (split.length == 0) {
            split = new String[1];
            split[0] = "0";
        }
        if (split[0].isEmpty()) {
            split[0] = "0";
        }
        if (originalBase != 10) {
            split[0] = Long.toString(Long.parseLong(split[0], originalBase));
        }
        String wholeNumber = "";
        switch (base) {
            case 2:
                wholeNumber = Long.toBinaryString(Long.parseLong(split[0]));
                break;
            case 10:
                wholeNumber = split[0];
                break;
            case 16:
                wholeNumber = Long.toHexString(Long.parseLong(split[0]));
                break;
            case 8:
                wholeNumber = Long.toOctalString(Long.parseLong(split[0]));
                break;
        }
        if (split.length == 1) return wholeNumber.toUpperCase(Locale.US);

        // 捕获溢出（它是一个小数，它可以（略微）舍入
        if (split[1].length() > 13) {
            split[1] = split[1].substring(0, 13);
        }

        double decimal = 0;
        if (originalBase != 10) {
            String decimalFraction = Long.toString(Long.parseLong(split[1], originalBase)) + "/" + originalBase + "^" + split[1].length();
            decimal = getSolver().eval(decimalFraction);
        } else {
            decimal = Double.parseDouble("0." + split[1]);
        }
        if (decimal == 0) return wholeNumber.toUpperCase(Locale.US);

        String decimalNumber = "";
        for (int i = 0; decimal != 0 && i <= PRECISION; i++) {
            decimal *= base;
            int id = (int) Math.floor(decimal);
            decimal -= id;
            decimalNumber += Integer.toHexString(id);
        }
        return (wholeNumber + getDecimalPoint() + decimalNumber).toUpperCase(Locale.US);
    }

    private Object[] removeWhitespace(String[] strings) {
        ArrayList<String> formatted = new ArrayList<String>(strings.length);
        for (String s : strings) {
            if (s != null && !s.isEmpty()) formatted.add(s);
        }
        return formatted.toArray();
    }



    public int getBaseNumber(Base base) {
        switch (base) {
            case BINARY:
                return 2;
            case OCTAL:
                return 8;
            case DECIMAL:
                return 10;
            case HEXADECIMAL:
                return 16;
            default:
                return 10;
        }
    }

    public interface OnBaseChangeListener {
        void onBaseChange(Base newBase);
    }
}
