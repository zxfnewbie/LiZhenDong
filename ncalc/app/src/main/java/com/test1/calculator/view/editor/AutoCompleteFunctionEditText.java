package com.test1.calculator.view.editor;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.AttributeSet;


public class AutoCompleteFunctionEditText extends android.support.v7.widget.AppCompatMultiAutoCompleteTextView {

    private final Handler mHandler = new Handler();
    private HighlightWatcher mHighlightWatcher = new HighlightWatcher();
    private boolean isEnableTextListener;
    private final Runnable mUpdateHighlight = new Runnable() {
        @Override
        public void run() {
            highlight(getEditableText());
        }
    };


    public AutoCompleteFunctionEditText(Context context) {
        super(context);
        init();
    }

    public AutoCompleteFunctionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoCompleteFunctionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAutoSuggestEnable(boolean enable) {
        if (!enable) {
            setAdapter(null);
        } else {
            init();
        }
    }

    private void init() {
        if (!isInEditMode()) {
            setTokenizer(new FunctionTokenizer());
            setThreshold(1);
            enableTextChangeListener();
        }
    }


    private void enableTextChangeListener() {
        if (!isEnableTextListener) {
            addTextChangedListener(mHighlightWatcher);
            isEnableTextListener = true;
        }
    }

    private void disableTextChangeListener() {
        this.isEnableTextListener = false;
        removeTextChangedListener(mHighlightWatcher);
    }

    public void highlight(Editable editable) {
        disableTextChangeListener();
        StyleSpan[] spans = editable.getSpans(0, editable.length(), StyleSpan.class);
        for (StyleSpan span : spans) {
            editable.removeSpan(span);
        }
        enableTextChangeListener();
    }

    public class FunctionTokenizer implements Tokenizer {
        String token = "!@#$%^&*()_+-={}|[]:'<>/<.?1234567890";

        @Override
        public int findTokenStart(CharSequence text, int cursor) {
            int i = cursor;
            while (i > 0 && !token.contains(Character.toString(text.charAt(i - 1)))) {
                i--;
            }
            while (i < cursor && text.charAt(i) == ' ') {
                i++;
            }
            return i;
        }

        @Override
        public int findTokenEnd(CharSequence text, int cursor) {
            int i = cursor;
            int len = text.length();

            while (i < len) {
                if (token.contains(Character.toString(text.charAt(i - 1)))) {
                    return i;
                } else {
                    i++;
                }
            }

            return len;
        }

        @Override
        public CharSequence terminateToken(CharSequence text) {
            int i = text.length();

            while (i > 0 && text.charAt(i - 1) == ' ') {
                i--;
            }

            if (i > 0 && token.contains(Character.toString(text.charAt(i - 1)))) {
                return text;
            } else {
                if (text instanceof Spanned) {
                    SpannableString sp = new SpannableString(text);
                    TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                            Object.class, sp, 0);
                    return sp;
                } else {
                    return text;
                }
            }
        }
    }

    class HighlightWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mHandler.removeCallbacks(mUpdateHighlight);
            mHandler.postDelayed(mUpdateHighlight, 1000);
        }
    }
}
