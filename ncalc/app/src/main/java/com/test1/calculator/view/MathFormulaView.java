package com.test1.calculator.view;

import android.content.Context;
import android.util.AttributeSet;

import io.github.kexanie.library.MathView;

import static io.github.kexanie.library.MathView.Engine.KATEX;


public class MathFormulaView extends MathView {
    public MathFormulaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) setup(context);
    }

    /**
     * 为数学视图设置支持缩放
     */
    private void setup(Context context) {
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);

        //隐藏控制视图
        getSettings().setDisplayZoomControls(false);
        setEngine(KATEX);

    }
}
