package com.test1.calculator.evaluator.exceptions;

import java.util.Stack;

/**
 * 检查表达式是否正确
 */

public class ExpressionChecker {

    /**
     * @param expr
     * @throws NonBalanceBracketException
     * 检查表达式是否正确，括号是否配对
     */
    public static void checkBalanceBracket(String expr) throws NonBalanceBracketException {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < expr.length(); i++) {
            if (expr.charAt(i) == '(') {
                stack.push(expr.charAt(i));
            } else if (expr.charAt(i) == ')') {
                if (stack.isEmpty()) throw new NonBalanceBracketException(expr, i);
                Character open = stack.pop();
                if (open != '(') {
                    throw new NonBalanceBracketException(expr, i);
                }
            }
        }
    }


    public static void checkExpression(String expr) {
        checkBalanceBracket(expr);
    }
}
