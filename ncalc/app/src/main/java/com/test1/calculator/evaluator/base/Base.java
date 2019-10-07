package com.test1.calculator.evaluator.base;

/**
 * 表示在编写数字时更改可用字符数。
 */
public enum Base {
    BINARY(2),
    OCTAL(8),
    DECIMAL(10),
    HEXADECIMAL(16);

    int quickSerializable;

    Base(int num) {
        this.quickSerializable = num;
    }

}
