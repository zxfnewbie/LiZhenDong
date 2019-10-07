package com.test1.calculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;

import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractEvaluatorActivity;
import com.test1.calculator.calc.BasicCalculatorActivity;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.thread.Command;
import com.test1.calculator.model.DerivativeItem;
import com.google.common.collect.Lists;

import java.util.ArrayList;

import static com.test1.calculator.R.string.derivative;

public class DerivativeActivity extends AbstractEvaluatorActivity {
    private boolean isDataNull = true;

    private ArrayList<String> mLevel = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.derivative));
        btnSolve.setText(derivative);

        //增加数据
        for (int i = 1; i < 21; i++) {
            mLevel.add(String.valueOf(i));
        }

        //设置数据适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DerivativeActivity.this,
                android.R.layout.simple_list_item_single_choice, mLevel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setVisibility(View.VISIBLE);
        mSpinner.setAdapter(adapter);

        getIntentData();

          }

    /**
     * 从activity获取数据启动它
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
        String lever = mSpinner.getSelectedItem().toString();
        DerivativeItem derivativeItem = new DerivativeItem(expr, "x", lever);
        return derivativeItem.getInput();
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {
                EvaluateConfig config = EvaluateConfig.loadFromSetting(DerivativeActivity.this);
                String fraction = MathEvaluator.getInstance().derivativeFunction(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));
                return Lists.newArrayList(fraction);
            }
        };
    }


}
