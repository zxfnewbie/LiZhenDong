package com.test1.calculator.evaluator;

import java.util.ArrayList;


public class CustomFunctions {
    private static final String COMBINATION = "Comb(n_, k_):=(factorial(Ceiling(n))/(factorial(Ceiling(k))*factorial(Ceiling(n-k))))";
    private static final String BINOMIAL = "Perm(n_, k_):=(factorial(Ceiling(n)) / " +
            "(factorial(Ceiling(n - k))))";
    private static final String CUBEROOT = "cbrt(x_):= x^(1/3)";
    private static final String CEILING = "Ceil(x_):=Ceiling(x)";

    public static ArrayList<String> getAllCustomFunctions() {
        ArrayList<String> functions = new ArrayList<>();
        functions.add(COMBINATION);
        functions.add(BINOMIAL);
        functions.add(CUBEROOT);
        functions.add(CEILING);
        return functions;
    }
}
