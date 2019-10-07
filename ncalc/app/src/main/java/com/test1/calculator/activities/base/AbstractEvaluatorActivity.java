package com.test1.calculator.activities.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.test1.calculator.CalculatorPresenter;
import com.test1.calculator.R;
import com.test1.calculator.adapters.ResultAdapter;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.exceptions.ExpressionChecker;
import com.test1.calculator.evaluator.exceptions.ParsingException;
import com.test1.calculator.evaluator.thread.BaseThread;
import com.test1.calculator.evaluator.thread.CalculateThread;
import com.test1.calculator.evaluator.thread.Command;
import com.test1.calculator.history.ResultEntry;
import com.test1.calculator.view.ResizingEditText;

import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.ArrayList;


public abstract class AbstractEvaluatorActivity extends AbstractNavDrawerActionBarActivity
        implements View.OnClickListener {
    protected String TAG = AbstractEvaluatorActivity.class.getName();
    protected EditText editFrom, editTo;
    protected LinearLayout mLayoutLimit;
    protected SharedPreferences mPreferences;
    protected Handler mHandler = new Handler();
    protected Button btnSolve;
    protected ResizingEditText mInputFormula;
    protected ViewGroup mDisplayForeground;
    protected ContentLoadingProgressBar mProgress;
    protected AppCompatSpinner mSpinner;
    protected Button btnClear;
    protected EditText editParam;
    protected ResizingEditText mInputDisplay2;
    protected TextInputLayout mHint1;
    protected TextInputLayout mHint2;
    protected RecyclerView mResultView;
    private ResultAdapter mResultAdapter;
    private CalculatorPresenter mPresenter;
    private final View.OnKeyListener mFormulaOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_NUMPAD_ENTER:
                case KeyEvent.KEYCODE_ENTER:
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        clickEvaluate();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator);
        initView();
        createData();
    }

    /**
     * 恢复输入
     */
    @Override
    protected void onResume() {
        super.onResume();
        String input = mCalculatorSetting.getString("input_key" + getClass().getSimpleName());
        mInputFormula.setText(input);
    }

    /**
     * 保存输入
     */
    @Override
    protected void onPause() {
        super.onPause();
        mCalculatorSetting.put("input_key" + getClass().getSimpleName(),
                mInputFormula.getText().toString());
    }

    private void initView() {
        btnSolve = findViewById(R.id.btn_solve);
        mInputFormula = findViewById(R.id.edit_input);
        mDisplayForeground = findViewById(R.id.the_clear_animation);
        mProgress = findViewById(R.id.progress_bar);
        mSpinner = findViewById(R.id.spinner);
        btnClear = findViewById(R.id.btn_clear);
        editParam = findViewById(R.id.edit_params);
        mInputDisplay2 = findViewById(R.id.edit_input_2);
        mHint1 = findViewById(R.id.hint_1);
        mHint2 = findViewById(R.id.hint_2);

        btnClear.setOnClickListener(this);
        btnSolve.setOnClickListener(this);
        mProgress.hide();
        editFrom = findViewById(R.id.edit_lower);
        editTo = findViewById(R.id.edit_upper);
        mLayoutLimit = findViewById(R.id.layout_limit);
        mLayoutLimit.setVisibility(View.GONE);
        mInputFormula.setOnKeyListener(mFormulaOnKeyListener);

        mResultView = findViewById(R.id.rc_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        mResultView.setHasFixedSize(true);
        mResultView.setLayoutManager(linearLayoutManager);
        mResultAdapter = new ResultAdapter(this);
        mResultView.setAdapter(mResultAdapter);

    }

    /**
     * 新建一个对象进行共享
     */
    private void createData() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                clickClear();
                break;
            case R.id.btn_solve:
                clickEvaluate();
                break;

        }
    }

    public void clickClear() {
        mInputFormula.setText("");
        editFrom.setText("");
        editTo.setText("");
        mInputDisplay2.setText("");
    }

    /**
     * 计算结果
     */
    @CallSuper
    public void clickEvaluate() {
        //如果输入为空。则不进行计算
        if (mInputFormula.getText().toString().isEmpty()) {
            mInputFormula.requestFocus();
            mInputFormula.setError(getString(R.string.enter_expression));
            return;
        }

        try {
            ExpressionChecker.checkExpression(mInputFormula.getCleanText());
        } catch (Exception e) {
            hideKeyboard();
            handleExceptions(mInputFormula, e);
            return;
        }

        String expr = getExpression();
        if (expr == null) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            return;
        }
        Command<ArrayList<String>, String> command = getCommand();

        mProgress.show();
        btnSolve.setEnabled(false);
        btnClear.setEnabled(false);
        hideKeyboard();
        mResultAdapter.clear();

        CalculateThread calculateThread = new CalculateThread(mPresenter,
                EvaluateConfig.loadFromSetting(this), new BaseThread.ResultCallback() {
            @Override
            public void onSuccess(ArrayList<String> result) {
                hideKeyboard();
                mProgress.hide();
                btnSolve.setEnabled(true);
                btnClear.setEnabled(true);

                for (String entry : result) {
                    mResultAdapter.addItem(new ResultEntry("", entry));
                }

                if (mResultAdapter.getItemCount() > 0) {
                    mResultView.scrollToPosition(0);
                }
            }

            @Override
            public void onError(Exception e) {
                handleExceptions(mInputFormula, e);
                mProgress.hide();
                btnSolve.setEnabled(true);
                btnClear.setEnabled(true);
            }
        });
        calculateThread.execute(command, expr);
    }

    protected void handleExceptions(EditText editText, Exception e) {
        if (e instanceof SyntaxError) {
            int start = Math.min(editText.length(), ((SyntaxError) e).getColumnIndex() - 1);
            int end = Math.min(editText.length(), ((SyntaxError) e).getColumnIndex());
            editText.setSelection(start, end);
            mResultAdapter.clear();
        } else if (e instanceof MathException) {
            mResultAdapter.clear();
        } else if (e instanceof ParsingException) {
            int start = Math.min(editText.length(), ((ParsingException) e).getIndex());
            int end = Math.min(editText.length(), ((ParsingException) e).getIndex() + 1);
            editText.setSelection(start, end);
            mResultAdapter.clear();
        } else {
            mResultAdapter.clear();
        }
        Log.i("test","2");
        editText.setError("请输入正确的表达式！");
    }

    protected String getExpression() {
        return mInputFormula.getCleanText();
    }


    @Nullable
    public abstract Command<ArrayList<String>, String> getCommand();


}
