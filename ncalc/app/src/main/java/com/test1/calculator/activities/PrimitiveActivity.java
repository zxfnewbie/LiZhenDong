package com.test1.calculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractEvaluatorActivity;
import com.test1.calculator.calc.BasicCalculatorActivity;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.thread.Command;
import com.test1.calculator.model.PrimitiveItem;

import java.util.ArrayList;

/**
 *积分（f（x），{x，a，b}）
 * 函数f（x）与变量x的积分，从a到b
 */

public class PrimitiveActivity extends AbstractEvaluatorActivity {
    private boolean isDataNull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutLimit.setVisibility(View.GONE);
        setTitle(R.string.primitive);
        mHint1.setHint(getString(R.string.enter_function));
        btnSolve.setText(R.string.solve);

        //从另一个activity接收数据
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
                clickEvaluate();
            }
        }
    }

    @Override
    protected String getExpression() {
        String expr = mInputFormula.getCleanText();
        PrimitiveItem primitiveItem = new PrimitiveItem(expr);
        return primitiveItem.getInput();
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
