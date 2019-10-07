
package com.test1.calculator.evaluator.thread;

public interface Command<RETURN, INPUT> {
    RETURN execute(INPUT input);
}