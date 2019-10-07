package com.test1.calculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractEvaluatorActivity;
import com.test1.calculator.calc.BasicCalculatorActivity;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.exceptions.ExpressionChecker;
import com.test1.calculator.evaluator.thread.Command;
import com.test1.calculator.model.IntegrateItem;

import java.util.ArrayList;

/**
 * 积分（f（x），{x，a，b}）
 * 函数f（x）与变量x的积分，下限a，上限b
 *
 */
public class IntegrateActivity extends AbstractEvaluatorActivity {
    private boolean isDataNull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.integrate));
        mHint1.setHint(getString(R.string.enter_function));
        btnSolve.setText(R.string.solve);

        //隐藏和显示窗口
        mLayoutLimit.setVisibility(View.VISIBLE);
        getIntentData();
        editFrom.setText("");


    }

    /**
     * 从activity获取数据启动
     */
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                mInputFormula.setText(data);
                isDataNull = false;
                clickEvaluate();
            }
        }
    }



    @Override
    protected String getExpression() {
        String inp = mInputFormula.getCleanText();

        //检查是否为空输入
        String from = editFrom.getText().toString();
        if (from.isEmpty()) {
            editFrom.requestFocus();
            editFrom.setError(getString(R.string.enter_limit));
            return null;
        }

        try {
            ExpressionChecker.checkExpression(from);
        } catch (Exception e) {
            hideKeyboard();
            handleExceptions(editFrom, e);
            return null;
        }

        //检查是否为空输入
        String to = editTo.getText().toString();
        if (to.isEmpty()) {
            editTo.requestFocus();
            editTo.setError(getString(R.string.enter_limit));
            return null;
        }

        try {
            ExpressionChecker.checkExpression(to);
        } catch (Exception e) {
            hideKeyboard();
            handleExceptions(editTo, e);
            return null;
        }

        IntegrateItem integrateItem = new IntegrateItem(inp,
                editFrom.getText().toString(),
                editTo.getText().toString());
        return integrateItem.getInput();
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {
                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));

                String decimal = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.DECIMAL));

                ArrayList<String> result = new ArrayList<>();
                result.add(fraction);
                result.add(decimal);
                return result;
            }
        };
    }


}
