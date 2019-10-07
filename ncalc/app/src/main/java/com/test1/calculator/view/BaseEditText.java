package com.test1.calculator.view;

import android.content.Context;
import android.util.AttributeSet;

import com.test1.calculator.view.editor.AutoCompleteFunctionEditText;

/**
 * 基础编辑文本
 */

public class BaseEditText extends AutoCompleteFunctionEditText {
    public BaseEditText(Context context) {
        super(context);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
