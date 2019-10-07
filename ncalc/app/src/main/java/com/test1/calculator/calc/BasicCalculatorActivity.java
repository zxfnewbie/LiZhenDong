package com.test1.calculator.calc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.test1.calculator.InputState;
import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractCalculatorActivity;
import com.test1.calculator.calc.fragment.KeyboardFragment;
import com.test1.calculator.data.CalculatorSetting;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.LogicEvaluator;
import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.base.Evaluator;
import com.test1.calculator.history.HistoryActivity;
import com.test1.calculator.history.ResultEntry;
import com.test1.calculator.model.DerivativeItem;
import com.test1.calculator.model.PrimeFactorItem;
import com.test1.calculator.model.SolveItem;
import com.test1.calculator.view.CalculatorEditText;

import io.github.kexanie.library.MathView;


public class BasicCalculatorActivity extends AbstractCalculatorActivity
        implements LogicEvaluator.EvaluateCallback, KeyboardListener, View.OnClickListener {
    public static final String TAG = BasicCalculatorActivity.class.getSimpleName();
    public static final String DATA = "DATA_BUNDLE";
    private static final int REQ_CODE_HISTORY = 1111;
    private static final int REQ_CODE_DEFINE_VAR = 1234;
    private static final int REQ_CODE_SPEECH_INPUT = 1235;

    /**
     * 当表达式更改时计算
     */

    private final Handler mHandler = new Handler();

    public ContentLoadingProgressBar mProgress;
    public FrameLayout mAnimateSolve;
    private FrameLayout mContainerSolve;
    private DrawerLayout mDrawerLayout;
    private CalculatorEditText mInputDisplay;
    private ViewGroup mDisplayForeground;
    private MathView mReview;
    private FloatingActionButton mFabClose;
    private View mCurrentButton = null;
    private CalculatorState mCurrentState = CalculatorState.INPUT;
    private MathEvaluator mEvaluator;
    private final View.OnKeyListener mFormulaOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_NUMPAD_ENTER:     //小键盘按键回车
                case KeyEvent.KEYCODE_ENTER:            //回车键
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {       //判断是否松开按键
                        onEqual();      //计算
                    }
                    return true;
            }
            return false;
        }
    };
    private InputState mInputState = InputState.PAD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvaluator = MathEvaluator.getInstance();
        setContentView(R.layout.activity_basic_calculator);
        bindView();
        mInputDisplay.setShowSoftInputOnFocus(false);
        mInputDisplay.setOnKeyListener(mFormulaOnKeyListener);
        mInputDisplay.getCleanText();
        setInputState(InputState.PAD);
        setState(CalculatorState.INPUT);
        initKeyboard();

        findViewById(R.id.img_history).setOnClickListener(this);
        findViewById(R.id.history_text).setOnClickListener(this);
        onChangeModeFraction();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_history:      //历史记录
              history();
                break;
            case R.id.history_text:
                    history();
                    break;
        }
    }


    private void bindView() {
        mReview = findViewById(R.id.math_view);
        mDisplayForeground = findViewById(R.id.the_clear_animation);
        mInputDisplay = findViewById(R.id.txtDisplay);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mContainerSolve = findViewById(R.id.container_solve);
        mAnimateSolve = findViewById(R.id.result_animation);
        mProgress = findViewById(R.id.progress_bar_main);
        mCalculatorSetting.setFraction(mCalculatorSetting.useFraction());
        onChangeModeFraction();
     }

    private void initKeyboard() {
        KeyboardFragment keyboardFragment = KeyboardFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_keyboard, keyboardFragment, KeyboardFragment.TAG);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onResume() {
        super.onResume();
        mInputDisplay.setText("");
    }
    public void history(){
        Intent intent = new Intent(BasicCalculatorActivity.this, HistoryActivity.class);
        startActivityForResult(intent, REQ_CODE_HISTORY);
    }
     /**
     * 设置输入状态
     *
     * @param pad - enum {@link InputState}
     */
    private void setInputState(InputState pad) {
        mInputState = pad;
    }


    public void onDelete() {
        mInputDisplay.backspace();
    }

    public void onError(final String error) {
        mInputDisplay.setText("");
        setState(CalculatorState.ERROR);
        setTextError("请输入正确的表达式");

    }

    public void setTextResult(String result) {
        mReview.setText(result);
    }

    public void setTextError(String msg) {
        mReview.setText(msg);
    }

    public void onResult(final String result) {
        setTextDisplay(result.replace("\\", "").replace("\n", ""));
        setTextResult("");
    }


    public void insertText(String text) {
        //如果不是运算符，则set text display为null
        boolean b = mInputDisplay.getSelectionStart() == mInputDisplay.getCleanText().length() + 1;
        if (mCurrentState == CalculatorState.RESULT && !Evaluator.isOperator(text) && b) {
            mInputDisplay.clear();
        }
        mInputDisplay.insert(text);
    }

    public void clickClear() {
        mInputDisplay.clear();
        mReview.setText("");


    }

    public void onEqual() {
        String text = mInputDisplay.getCleanText(); //获取文本
        setState(CalculatorState.EVALUATE);
        try {
            mEvaluator.evaluateWithResultNormal(this,text, BasicCalculatorActivity.this,
                    EvaluateConfig.loadFromSetting(this));
        }catch (Exception e){
            Toast.makeText(this,"请输入正确的表达式",Toast.LENGTH_SHORT).show();
        }

        clickClear();

    }


    /**
     * 设置状态
     *
     * @param state - state
     */
    void setState(CalculatorState state) {
        mCurrentState = state;
    }

    /**
     * insert text to display
     *
     * @param opt - operator
     */
    public void insertOperator(String opt) {
        insertText(opt);
    }

    @Override
    public void setTextDisplay(final String textDisplay) {
        mInputDisplay.post(new Runnable() {
            @Override
            public void run() {
                mInputDisplay.setText(mTokenizer.getLocalizedExpression(textDisplay));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        CalculatorSetting preferences = new CalculatorSetting(this);
        String math = mInputDisplay.getCleanText();
        preferences.put(CalculatorSetting.INPUT_MATH, math);
        mReview.setText("");
    }

    @Override
    public void onEvaluated(String expr, String result, int resultId) {
        if (resultId == LogicEvaluator.RESULT_OK) {
            if (mCurrentState == CalculatorState.EVALUATE) {
                onResult(result);
                saveHistory(expr, result, true);
                mEvaluator.define("ans", result);
            } else if (mCurrentState == CalculatorState.INPUT) {
                if (result == null) {
                    setTextResult("");
                } else {
                    setTextResult(result);
                }
            }
        }
        clickClear();
    }

    @Override
    public void onCalculateError(Exception e) {
        if (mCurrentState == CalculatorState.INPUT) {
            mInputDisplay.setText("");//clear
        } else if (mCurrentState == CalculatorState.EVALUATE) {
            onError(getResources().getString(R.string.error) + " " + e.getMessage());
            mInputDisplay.setText("");
        }
    }

    public void clickSolveEquation() {
        String inp = mTokenizer.getNormalExpression(mInputDisplay.getCleanText());
        if (inp.isEmpty()) {
            Toast.makeText(this, R.string.enter_expression, Toast.LENGTH_SHORT).show();
            return;
        }
        final SolveItem item = new SolveItem(inp);
        if (!item.getInput().contains("x")) {
            Toast.makeText(this, R.string.not_variable, Toast.LENGTH_SHORT).show();
            return;
        }
          }


    /**
     * 关闭求解结果和动画
     */
    public void closeMathView() {
        setInputState(InputState.PAD);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mContainerSolve.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected boolean saveHistory(String expr, String result, boolean ensureResult) {

        mHistoryDatabase.saveHistory(new ResultEntry(expr, result));
        return false;
    }

    public void clickFactorPrime() {
        String inp = mTokenizer.getNormalExpression(mInputDisplay.getCleanText());
        if (inp.isEmpty()) {
            Toast.makeText(this, R.string.enter_expression, Toast.LENGTH_SHORT).show();
            return;
        }
        PrimeFactorItem item = new PrimeFactorItem(mInputDisplay.getCleanText());
       }

    protected void onChangeModeFraction() {
        Log.d(TAG, "onChangeModeFraction() called");
        try{
            mEvaluator.evaluateWithResultAsTex(this,mInputDisplay.getCleanText(),
                    BasicCalculatorActivity.this, EvaluateConfig.loadFromSetting(BasicCalculatorActivity.this));
        }catch (Exception e){
          Log.i("test","clear");
            Toast.makeText(this,"请输入正确的表达式",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawers();
                return;
            }
        }
        if (mInputState == InputState.RESULT_VIEW) {
            closeMathView();
            return;
        }
        super.onBackPressed();
    }


    public void clickDerivative() {
        String inp = mTokenizer.getNormalExpression(mInputDisplay.getCleanText());
        if (inp.isEmpty()) {
            Toast.makeText(this, R.string.enter_expression, Toast.LENGTH_SHORT).show();
            return;
        }

        //在计算之前检查错误
        DerivativeItem item = new DerivativeItem(mInputDisplay.getCleanText(), "x", "1");
           }

    public void clickGraph() {
   }


    public enum CalculatorState {
        INPUT,
        EVALUATE, RESULT, ERROR
    }



}
