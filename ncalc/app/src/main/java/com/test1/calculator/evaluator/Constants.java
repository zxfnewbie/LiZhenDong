package com.test1.calculator.evaluator;

import java.util.regex.Pattern;


public class Constants {
    public static final char INFINITY_UNICODE = '\u221e';
    public static final char DEGREE_UNICODE = '\u00b0';
    public static final char FACTORIAL_UNICODE = '!';
    public static final char POWER_PLACEHOLDER = '\u200B';

    public static final char LEFT_PAREN = '(';
    public static final char RIGHT_PAREN = ')';

    public static final char MINUS_UNICODE = '-';
    public static final char MUL_UNICODE = '×';
    public static final char PLUS_UNICODE = '+';
    public static final char DIV_UNICODE = '÷';
    public static final char POWER_UNICODE = '^';
    public static final char EQUAL_UNICODE = '=';

    public static final String INFINITY = "Infinity";
    public static final String NAN = "NaN";
    public static final String ZERO = "0";

    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String SYNTAX_Q = "SyntaxQ";
    public static final String FROM = "From";
    public static final String TO = "To";

    public static final String INTEGRATE = "Integrate";
    public static final String PRIMITIVE = INTEGRATE;
    public static final String FACTOR = "Factor";
    public static final String SOLVE = "Solve";
    public static final String LIMIT = "Limit";
    public static final String TABLE = "Table";
    public static final String MODULE = "Mod";

    public static final String X = "x";
    public static final String Y = "y";
    public static final String WEB_SEPARATOR = "<hr>";
    public static final String DERIVATIVE = "D";
    public static final String SIMPLIFY = "Simplify";
    public static final String C = "C";
    public static final String P = "P";
    public static final String K = "K";

    public static char DECIMAL_POINT;
    public static char DECIMAL_SEPARATOR;
    public static char BINARY_SEPARATOR;
    public static char HEXADECIMAL_SEPARATOR;
    public static char MATRIX_SEPARATOR;
    public static char OCTAL_SEPARATOR;
    public static String REGEX_NUMBER;
    public static String REGEX_NOT_NUMBER;

    static {
        rebuildConstants();
    }

    /**
     * 如果区域设置发生更改，但应用程序仍在内存中，则可能需要重建这些常量
     */
    public static void rebuildConstants() {
        DECIMAL_POINT = '.';
        DECIMAL_SEPARATOR = ' ';

        // 使用Bin和Hex空格
        BINARY_SEPARATOR = ' ';
        HEXADECIMAL_SEPARATOR = ' ';
        OCTAL_SEPARATOR = ' ';

        // We have to be careful with the Matrix Separator.
        // It defaults to "," but that's a common decimal point.
        MATRIX_SEPARATOR = ',';
        String number = "A-F0-9" +
                Pattern.quote(String.valueOf(DECIMAL_POINT)) +
                Pattern.quote(String.valueOf(DECIMAL_SEPARATOR)) +
                Pattern.quote(String.valueOf(BINARY_SEPARATOR)) +
                Pattern.quote(String.valueOf(OCTAL_SEPARATOR)) +
                Pattern.quote(String.valueOf(HEXADECIMAL_SEPARATOR));

        REGEX_NUMBER = "[" + number + "]";
        REGEX_NOT_NUMBER = "[^" + number + "]";
    }
}
