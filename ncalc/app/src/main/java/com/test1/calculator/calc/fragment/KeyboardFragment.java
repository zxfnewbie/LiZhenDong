package com.test1.calculator.calc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test1.calculator.R;
import com.test1.calculator.calc.KeyboardListener;
import com.test1.calculator.view.ButtonID;
import com.test1.calculator.view.CalcButton;
import com.test1.calculator.view.CalculatorEditText;


public class KeyboardFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    public static final String TAG = "KeyboardFragment";
    @Nullable
    private KeyboardListener mCalculatorListener;

    public static KeyboardFragment newInstance() {

        Bundle args = new Bundle();

        KeyboardFragment fragment = new KeyboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCalculatorListener = (KeyboardListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pad_basic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvent(view);
    }

    private void addEvent(View view) {
        for (int id : ButtonID.getIdBasic()) {
            try {
                View v = view.findViewById(id);
                if (v != null) {
                    v.setOnClickListener(this);
                    v.setOnLongClickListener(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (mCalculatorListener == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_ten_power:
                mCalculatorListener.insertText("10^");
                break;
            case R.id.btn_power_2:
                mCalculatorListener.insertText("^");
                mCalculatorListener.insertText("2");
                break;
            case R.id.btn_power_3:
                mCalculatorListener.insertText("^");
                mCalculatorListener.insertText("3");
                break;
            case R.id.btn_delete:
                mCalculatorListener.onDelete();
                break;
            case R.id.btn_clear:
                mCalculatorListener.clickClear();
                break;
            case R.id.btn_equal:
                mCalculatorListener.onEqual();
                break;
            case R.id.btn_ans:
                mCalculatorListener.insertText("Ans");
                break;
            case R.id.btn_sqrt:
                mCalculatorListener.insertText("Sqrt(" + CalculatorEditText.CURSOR + ")");
                break;
            default:
                if (view instanceof CalcButton) {
                    CalcButton calcButton = (CalcButton) view;
                    String text = calcButton.getText().toString();
                    if (text.length() >= 2) {
                        mCalculatorListener.insertText(text + "(" + CalculatorEditText.CURSOR + ")");
                    } else {
                        mCalculatorListener.insertText(((Button) view).getText().toString());
                    }
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mCalculatorListener == null) {
            return false;
        }
        switch (view.getId()) {
            case R.id.btn_delete:
                mCalculatorListener.clickClear();
                return true;
        }
        return false;
    }
}
