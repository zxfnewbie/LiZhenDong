package com.test1.calculator.model;

/**
 * LimitItem
 * Created by Duy on 29-Dec-16.
 */

public class LimitItem extends IntegrateItem {
    /**
     * Limit item
     *
     * @param input - function
     * @param to    - upper limit, x -> inf
     */
    public LimitItem(String input, String to) {
        super(input, "", to);
    }

    @Override
    public String getInput() {
        return "Limit(" + input + "," + var + " -> " + to + ")";
    }
}
