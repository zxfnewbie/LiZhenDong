package com.test1.calculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractEvaluatorActivity;
import com.test1.calculator.calc.BasicCalculatorActivity;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.exceptions.ExpressionChecker;
import com.test1.calculator.evaluator.thread.Command;
import com.test1.calculator.model.LimitItem;
import com.google.common.collect.Lists;

import java.util.ArrayList;



public class LimitActivity extends AbstractEvaluatorActivity {
    private boolean isDataNull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.limit));
        mHint1.setHint(getString(R.string.enter_function));
        btnSolve.setText(R.string.eval);

        mLayoutLimit.setVisibility(View.VISIBLE);
        mHint1.setHint("");
        editFrom.post(new Runnable() {
            @Override
            public void run() {
                editFrom.setText("x → ");
                editFrom.setEnabled(false);
                editFrom.setHint(null);
                editFrom.setGravity(Gravity.END);
            }
        });

        getIntentData();


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
        String expr = mInputFormula.getCleanText();
        String limit = editTo.getText().toString();

        try {
            ExpressionChecker.checkExpression(limit);
        } catch (Exception e) {
            hideKeyboard();
            handleExceptions(editTo, e);
            return null;
        }

        LimitItem limitItem = new LimitItem(expr, limit);
        return limitItem.getInput();
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {

                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));

                return Lists.newArrayList(fraction);
            }
        };
    }
}
