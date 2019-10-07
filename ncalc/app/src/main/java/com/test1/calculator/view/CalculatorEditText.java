package com.test1.calculator.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import com.test1.calculator.R;
import com.test1.calculator.evaluator.Constants;
import com.test1.calculator.evaluator.base.Evaluator;
import com.test1.calculator.utils.TextUtil;

import java.util.Arrays;
import java.util.List;

/**
 * FormattedNumberEditText为NumberEditText添加了更多高级功能。
 * <p/>
 *
 */
public class CalculatorEditText extends ResizingEditText {
    public static final String TAG = "CalculatorEditText";
    public static final char CURSOR = '\u273f';

    /**
     * 启用文本观察器
     */
    private final TextWatcher mCursorWatcher = new TextWatcher() {
        private CharSequence s;
        private int start;
        private int before;
        private int count;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            this.s = s;
            this.start = start;
            this.before = before;
            this.count = count;
        }

        @Override
        public void afterTextChanged(Editable s) {
            for (int i = start; i < start + count; i++) {
                if (s.charAt(i) == CURSOR) {
                    s.delete(i, i + 1);
                    setSelection(i);
                    break;
                }
            }
        }
    };

    private List<String> mKeywords;
    private boolean mIsInserting = false;
    private boolean mCheckSyntax = false;


    public CalculatorEditText(Context context) {
        super(context);
        setUp(context, null);
    }

    public CalculatorEditText(Context context, AttributeSet attr) {
        super(context, attr);
        setUp(context, attr);
    }

    private void setUp(Context context, @Nullable AttributeSet attrs) {
        addTextChangedListener(mCursorWatcher);
        invalidateKeywords(context);
    }

    public void invalidateKeywords(Context context) {
        mKeywords = Arrays.asList(
                context.getString(R.string.fun_arcsin) + "(",
                context.getString(R.string.fun_arccos) + "(",
                context.getString(R.string.fun_arctan) + "(",
                context.getString(R.string.arcsin) + "(",
                context.getString(R.string.arccos) + "(",
                context.getString(R.string.arctan) + "(",
                context.getString(R.string.fun_sin) + "(",
                context.getString(R.string.fun_cos) + "(",
                context.getString(R.string.fun_tan) + "(",
                context.getString(R.string.sin) + "(",
                context.getString(R.string.cos) + "(",
                context.getString(R.string.tan) + "(",
                context.getString(R.string.fun_arccsc) + "(",
                context.getString(R.string.fun_arcsec) + "(",
                context.getString(R.string.fun_arccot) + "(",
                context.getString(R.string.fun_csc) + "(",
                context.getString(R.string.fun_sec) + "(",
                context.getString(R.string.fun_cot) + "(",
                context.getString(R.string.fun_log) + "(",
                context.getString(R.string.mod) + "(",
                context.getString(R.string.fun_ln) + "(",
                context.getString(R.string.op_cbrt) + "(",
                context.getString(R.string.tanh) + "(",
                context.getString(R.string.cosh) + "(",
                context.getString(R.string.sinh) + "(",
                context.getString(R.string.log2) + "(",
                context.getString(R.string.log10) + "(",
                context.getString(R.string.abs) + "(",
                context.getString(R.string.sgn) + "(",
                context.getString(R.string.floor) + "(",
                context.getString(R.string.ceil) + "(",
                context.getString(R.string.arctanh) + "(",
                context.getString(R.string.sum) + "(",
                context.getString(R.string.diff) + "(",
                context.getString(R.string.avg) + "(",
                context.getString(R.string.vari) + "(",
                context.getString(R.string.stdi) + "(",
                context.getString(R.string.mini) + "(",
                context.getString(R.string.maxi) + "(",
                context.getString(R.string.min) + "(",
                context.getString(R.string.max) + "(",
                context.getString(R.string.std) + "(",
                context.getString(R.string.mean) + "(",
                context.getString(R.string.sqrt_sym) + "(",
                context.getString(R.string.log2) + "(",
                context.getString(R.string.log10) + "(",
                context.getString(R.string.cot) + "(",
                context.getString(R.string.exp) + "(",
                context.getString(R.string.sign) + "(",
                context.getString(R.string.arg) + "(",
                context.getString(R.string.gcd_up) + "(",
                context.getString(R.string.log2) + "(",
                context.getString(R.string.ln) + "(",
                context.getString(R.string.ln) + "(",
                context.getString(R.string.log2) + "(",
                context.getString(R.string.arcsinh) + "(",
                context.getString(R.string.arccosh) + "(",
                context.getString(R.string.arctanh) + "(",
                context.getString(R.string.op_cbrt) + "(",
                context.getString(R.string.permutations) + "(",
                context.getString(R.string.binomial) + "(",
                context.getString(R.string.trunc) + "(",
                context.getString(R.string.max) + "(",
                context.getString(R.string.min) + "(",
                context.getString(R.string.mod) + "(",
                context.getString(R.string.gcd) + "(",
                context.getString(R.string.lcm) + "(",
                context.getString(R.string.sign) + "(",
                context.getString(R.string.rnd) + "(",
                context.getString(R.string.ans),
                context.getString(R.string.mtrue),
                context.getString(R.string.mfalse),
                context.getString(R.string.eq)
        );
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (getText() != null) {
            setSelection(getText().length());
        }
    }

    /**
     * 返回文本，重新格式化文本。
     *
     * @return - String
     */
    public String getCleanText() {
        return TextUtil.getCleanText(this);
    }

    public void insert(String delta) {
        if (mCheckSyntax) {
            String currentText = getText().toString();
            int selectionStart = getSelectionStart();
            int selectionEnd = getSelectionEnd();
            String textBeforeInsertionHandle = currentText.substring(0, selectionStart);
            String textAfterInsertionHandle = currentText.substring(selectionEnd);

            // 为小数点和运算符添加额外规则
            if (delta.length() == 1) {
                char text = delta.charAt(0);

                // 不允许相同数字中的两个点
                if (text == Constants.DECIMAL_POINT) {
                    int p = selectionStart - 1;
                    while (p >= 0 && Evaluator.isDigit(getText().charAt(p))) {
                        if (getText().charAt(p) == Constants.DECIMAL_POINT) {
                            return;
                        }
                        --p;
                    }
                    p = selectionStart;
                    while (p < getText().length() && Evaluator.isDigit(getText().charAt(p))) {
                        if (getText().charAt(p) == Constants.DECIMAL_POINT) {
                            return;
                        }
                        ++p;
                    }
                }

                char prevChar = selectionStart > 0 ? getText().charAt(selectionStart - 1) : '\0';

                // 不允许第一个字符成为运算符
                if (selectionStart == 0 && Evaluator.isOperator(text)
                        && text != Constants.MINUS_UNICODE) {
                    return;
                }

                // 不允许多个连续的运算符
                if (Evaluator.isOperator(text) &&
                        text != Constants.MINUS_UNICODE) {
                    while (Evaluator.isOperator(prevChar)) {
                        if (selectionStart == 1) {
                            return;
                        }

                        --selectionStart;
                        prevChar = selectionStart > 0 ? getText().charAt(selectionStart - 1) : '\0';
                        textBeforeInsertionHandle = textBeforeInsertionHandle.substring(0, selectionStart);
                    }
                }
                // 不允许两个度符号
                Log.d(TAG, "insert: " + text + " " + (text == Constants.DEGREE_UNICODE));
                if (text == Constants.DEGREE_UNICODE
                        && Evaluator.isOperator(prevChar)) {
                    return;
                }
            }
            mIsInserting = true;
            setText(textBeforeInsertionHandle + delta + textAfterInsertionHandle);
            setSelection(selectionStart + delta.length());
            mIsInserting = false;
        } else {
            int selectionStart = Math.max(0, getSelectionStart());
            getText().insert(selectionStart, delta);
        }
    }

    public void clear() {
        setText(null);
    }


    public void backspace() {
        // 检查并删除关键字
        String text = getText().toString();
        int selectionHandle = getSelectionStart();
        String textBeforeInsertionHandle = text.substring(0, selectionHandle);
        String textAfterInsertionHandle = text.substring(selectionHandle);

        for (String s : mKeywords) {
            if (textBeforeInsertionHandle.endsWith(s)) {
                int deletionLength = s.length();
                String newText = textBeforeInsertionHandle.substring(0, textBeforeInsertionHandle.length() - deletionLength) + textAfterInsertionHandle;
                setText(newText);
                setSelection(selectionHandle - deletionLength);
                return;
            }
        }

        // 重写NumberEditText的方法 - 因为逗号可能会消失，因此会使事情复杂化
        if (selectionHandle != 0) {
            setText(textBeforeInsertionHandle.substring(0, textBeforeInsertionHandle.length()
                    - 1) + textAfterInsertionHandle);

            if (getText().length() == text.length() - 2) {
                // 删除了2个字符（可能是逗号和数字）
                selectionHandle -= 2;
            } else {
                --selectionHandle;
            }

            setSelection(selectionHandle);
        }
    }


}
