package com.test1.calculator.evaluator;

import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.interfaces.IExpr;

import java.io.StringWriter;



public class LaTexFactory {
    /**
     * 将结果转换为latex以显示在{@link io.github.kexanie.library.MathView}上
     */
    public static String toLaTeX(IExpr result) {
        StringWriter stringWriter = new StringWriter();
        TeXUtilities texEngine = MathEvaluator.getInstance().getTexEngine();
        texEngine.toTeX(result, stringWriter);
        return "$$" + stringWriter + "$$";
    }
}
