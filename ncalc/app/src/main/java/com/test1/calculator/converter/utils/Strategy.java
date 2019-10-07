
package com.test1.calculator.converter.utils;


public interface Strategy {
    String getUnitDefault();

    double Convert(String from, String to, double input);
}
