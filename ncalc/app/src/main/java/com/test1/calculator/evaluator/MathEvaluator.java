package com.test1.calculator.evaluator;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.test1.calculator.R;
import com.test1.calculator.evaluator.exceptions.ExpressionChecker;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.ArrayList;


/**
 * Evaluator
 * <p>
 * - 方程式求解
 * - 积分
 * - 导数
 * ...
 * <p>
 * 计算表达式
 * <p>、
 */

public class MathEvaluator extends LogicEvaluator {
    private static final MathEvaluator MATH_EVALUATOR = new MathEvaluator();
    Activity activity=new Activity();
    private static final String ANS_VAR = "ans";
    private static final String TAG = "BigEvaluator";
    /**
     * 计算引擎
     */
    private final ExprEvaluator mExprEvaluator;
    /**
     * 把方程式转换成 latex
     */
    private final TeXUtilities mTexEngine;

    private MathEvaluator() {
        mExprEvaluator = new ExprEvaluator();
        mTexEngine = new TeXUtilities(mExprEvaluator.getEvalEngine(), true);
        for (String function : CustomFunctions.getAllCustomFunctions()) {
            mExprEvaluator.eval(function);
        }
    }

    @NonNull
    public static MathEvaluator getInstance() {
        return MATH_EVALUATOR;
    }



    /**
     * @param exprInput The expression to calculate
     * @return The  value of the expression
     * @throws IllegalArgumentException If the user has input a invalid expression
     */
    public IExpr evaluateSimple(String exprInput, EvaluateConfig config) {
        IExpr result;
        if (config.getEvaluateMode() == EvaluateConfig.DECIMAL) {
            result = evaluate("N(" + exprInput + ")");
        } else {
            result = evaluate(exprInput);
        }
        return result;
    }

    public TeXUtilities getTexEngine() {
        return mTexEngine;
    }

    public IExpr evaluate(String exprInput) {
        return mExprEvaluator.evaluate(exprInput);
    }

    @Override
    public MathEvaluator getEvaluator() {
        return this;
    }


    /**
     * 计算表达式，结果将通过接口返回回调
     * #EvaluateCallback
     *
     * @param expression - input expression S
     * @param callback   - interface for call back event
     * @ {@link EvaluateCallback}
     */
    public void evaluateWithResultNormal(Context context,String expression, EvaluateCallback callback,
                                         EvaluateConfig config) {
        //判断输入是否为空
        if (expression.isEmpty()) {
            callback.onEvaluated(expression, "", INPUT_EMPTY);
            return;
        }

        expression = FormatExpression.cleanExpression(expression, mTokenizer);
        expression = addUserDefinedVariable(expression); //$ans = ...

        IExpr iExpr = evaluateSimple(expression, config);
        callback.onEvaluated(expression, iExpr.toString(), RESULT_OK);

    }

    private String addUserDefinedVariable(String expression) {
        if (!expression.contains("ans")) return expression;
        try {
            if (mExprEvaluator.getVariable("ans").toString().equals("null")) {
                expression = expression.replace("ans", "(0)");
            } else {
                expression = expression.replace("ans", "(" + mExprEvaluator.getVariable("ans") + ")");
            }
        } catch (Exception e) {
            expression = expression.replace("ans", "(0)");
        }
        return expression;
    }

    /**
     * 从资源中获取字符串
     *
     * @param id - resource id
     * @return - String object
     */
    public String getString(int id) {
        return "";
    }

    public String evaluateWithResultNormal(String expression) {
        IExpr iExpr = evaluateSimple(expression,
                EvaluateConfig.newInstance().setEvalMode(EvaluateConfig.FRACTION));
        return iExpr.toString();
    }

    /**
     * 返回函数的导数
     * 导数计算
     */
    public String derivativeFunction(String diffStr, EvaluateConfig config) {
        if (config.getEvaluateMode() == EvaluateConfig.DECIMAL) {
            diffStr = "N(" + diffStr + ")";
        }
        String result = evaluateWithResultAsTex(diffStr, config);

        //结果
        StringBuilder builder = new StringBuilder();
        builder.append(result);
        if (builder.toString().contains("log")) {
            builder.append(Constants.WEB_SEPARATOR)
                    .append(getString(R.string.ln_hint)); //log(x) 是自然对数
        }
        return builder.toString();
    }

    /**
     * 返回函数的积分
     * 积分运算
     * 极限运算
     * 不定积分
     */
    public String evaluateWithResultAsTex(String exprStr, EvaluateConfig config) {
        //如果输入为空，不执行
        if (exprStr.isEmpty()) {
            return exprStr;
        }

        exprStr = FormatExpression.cleanExpression(exprStr, mTokenizer);
        exprStr = addUserDefinedVariable(exprStr); //$ans = ...

        ExpressionChecker.checkExpression(exprStr);

        IExpr result = evaluateSimple(exprStr, config);
        return LaTexFactory.toLaTeX(result);
    }

    /**
     * @see MathEvaluator#evaluateWithResultAsTex(String, EvaluateConfig)
     * <p>
     * 返回函数的导数
     */
    public void evaluateWithResultAsTex(Context context,String exprStr, EvaluateCallback callback, EvaluateConfig config) {
        String result = evaluateWithResultAsTex(exprStr, config);
        callback.onEvaluated(exprStr, result, RESULT_OK);
//        try {
//
//        } catch (Exception e) {
//            Toast.makeText(context,"请输入正确的表达式",Toast.LENGTH_SHORT).show();
//        }
    }

    /**
     * 求解方程并返回字符串结果
     */
    public String solveEquation(String solveStr, final EvaluateConfig config, Context context) {
        if (config.getEvaluateMode() == EvaluateConfig.DECIMAL) {
            solveStr = "N(" + solveStr + ")";
        }
        String roots = mExprEvaluator.evaluate(solveStr).toString();

        if (roots.toLowerCase().contains("solve")) {
            return context.getString(R.string.not_find_root);
        } else if (roots.contains("{}")) {
            return context.getString(R.string.no_root);
        }

        roots = roots.replaceAll("\\s+", "").replaceAll("->", "==");
        int j = 1;
        ArrayList<String> listRoot = new ArrayList<>();
        for (int i = 1; i < roots.length() - 1; i++) {
            if (roots.charAt(i) == '}') {
                String tmp = roots.substring(j + 1, i);
                i += 2;
                j = i;
                listRoot.add(tmp);
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < listRoot.size(); i++) {
            result.append(evaluateWithResultAsTex(listRoot.get(i), config));
        }
        return result.toString();
    }


    /**
     * 定义函数或变量
     *
     * @param var   - name of function or variable
     * @param value - expression or value
     */
    public void define(String var, String value) {
        try {
            IExpr iExpr = mExprEvaluator.evaluate(value);
            mExprEvaluator.defineVariable(var, iExpr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 语法错误检查
     *
     * @param expr - input expression
     * @return - true if error
     */
    public boolean isSyntaxError(String expr) {
        try {
            mExprEvaluator.getEvalEngine().parse(expr);
        } catch (SyntaxError e) {

            return true;
        } catch (MathException e) {

            return true;
        } catch (Exception e) {

            return true;
        }
        return false;
    }


}
