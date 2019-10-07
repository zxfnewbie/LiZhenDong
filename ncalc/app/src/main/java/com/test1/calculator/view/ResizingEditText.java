package com.test1.calculator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.test1.calculator.R;
import com.test1.calculator.utils.TextUtil;

/**
 * 重新选择 Edittext 将尝试将文本调整为尽可能大的大小 (可选
 * 最大值和最小值)。还可以使用 Getvarvabletextsize () 来查看字体大小将
 * 被使用。
 *
 *  */




public class ResizingEditText extends BaseEditText {
    private final Paint mTempPaint = new TextPaint();
    private float mMaximumTextSize;
    private float mMinimumTextSize;
    private float mStepTextSize;
    // 如果宽度允许，请尝试使用尽可能大的文本
    private int mWidthConstraint = -1;
    private int mHeightConstraint = -1;
    private OnTextSizeChangeListener mOnTextSizeChangeListener;

    public ResizingEditText(Context context) {
        super(context);
        setUp(context, null);
    }

    public ResizingEditText(Context context, AttributeSet attr) {
        super(context, attr);
        setUp(context, attr);
    }

    private void setUp(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.ResizingEditText, 0, 0);
            mMaximumTextSize = a.getDimension(R.styleable.ResizingEditText_maxTextSize, getTextSize());
            mMinimumTextSize = a.getDimension(R.styleable.ResizingEditText_minTextSize, getTextSize());
            mStepTextSize = a.getDimension(R.styleable.ResizingEditText_stepTextSize, (mMaximumTextSize - mMinimumTextSize) / 3);
            a.recycle();

            setTextSize(TypedValue.COMPLEX_UNIT_PX, mMaximumTextSize);
            setMinimumHeight((int) (mMaximumTextSize * 1.2) + getPaddingBottom() + getPaddingTop());
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        invalidateTextSize();
    }

    protected void invalidateTextSize() {
        float oldTextSize = getTextSize();
        float newTextSize = getVariableTextSize(getText().toString());
        if (oldTextSize != newTextSize) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
        }
    }

    @Override
    public void setTextSize(int unit, float size) {
        final float oldTextSize = getTextSize();
        super.setTextSize(unit, size);
        if (mOnTextSizeChangeListener != null && getTextSize() != oldTextSize) {
            mOnTextSizeChangeListener.onTextSizeChanged(this, oldTextSize);
        }
    }

    public float getVariableTextSize(String text) {
        if (mWidthConstraint < 0 || mMaximumTextSize <= mMinimumTextSize) {
            // Not measured, bail early.
            return getTextSize();
        }

        // 计算未正确测量的指数。
        int exponents = TextUtil.countOccurrences(text, '^');

        // 逐步增加文本大小，直到文本不再适合。
        float lastFitTextSize = mMinimumTextSize;
        while (lastFitTextSize < mMaximumTextSize) {
            final float nextSize = Math.min(lastFitTextSize + mStepTextSize, mMaximumTextSize);
            mTempPaint.setTextSize(nextSize);
            if (mTempPaint.measureText(text) > mWidthConstraint) {
                break;
            } else if (nextSize + nextSize * exponents / 2 > mHeightConstraint) {
                break;
            } else {
                lastFitTextSize = nextSize;
            }
        }

        return lastFitTextSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthConstraint =
                MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        mHeightConstraint =
                MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getVariableTextSize(getText().toString()));
    }

    public String getCleanText() {
        return this.getText().toString();
    }

    public void insert(String delta) {
        String currentText = getText().toString();
        int selectionHandle = getSelectionStart();
        String textBeforeInsertionHandle = currentText.substring(0, selectionHandle);
        String textAfterInsertionHandle = currentText.substring(selectionHandle);
        setText(textBeforeInsertionHandle + delta + textAfterInsertionHandle);
        setSelection(selectionHandle + delta.length());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        invalidateTextSize();
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    public interface OnTextSizeChangeListener {

        void onTextSizeChanged(TextView textView, float oldSize);
    }

}
