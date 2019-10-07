package com.test1.calculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;

import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractEvaluatorActivity;
import com.test1.calculator.calc.BasicCalculatorActivity;
import com.test1.calculator.evaluator.EvaluateConfig;
import com.test1.calculator.evaluator.MathEvaluator;
import com.test1.calculator.evaluator.thread.Command;
import com.test1.calculator.model.SolveItem;
import com.test1.calculator.tokenizer.Tokenizer;

import java.util.ArrayList;

import static com.test1.calculator.R.string.solve;


public class SolveEquationActivity extends AbstractEvaluatorActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private boolean isDataNull = true;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.solve_equation);
        btnSolve.setText(solve);
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
                data = new Tokenizer().getNormalExpression(data);
                isDataNull = false;
                if (!data.isEmpty()) {
                    clickEvaluate();
                }
            }
        }
    }

    @Override
    protected String getExpression() {
        String expr = mInputFormula.getCleanText();
        SolveItem solveItem = new SolveItem(expr);
        return solveItem.getInput();
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {
                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().solveEquation(input,
                        config.setEvalMode(EvaluateConfig.FRACTION), SolveEquationActivity.this);

                String decimal = MathEvaluator.getInstance().solveEquation(input,
                        config.setEvalMode(EvaluateConfig.DECIMAL), SolveEquationActivity.this);

                ArrayList<String> result = new ArrayList<>();
                result.add(fraction);
                result.add(decimal);
                return result;
            }
        };
    }

}
