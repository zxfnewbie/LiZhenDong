package com.test1.calculator.converter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractAppCompatActivity;
import com.test1.calculator.converter.adapters.ItemUnitConverter;
import com.test1.calculator.converter.adapters.UnitAdapter;
import com.test1.calculator.converter.utils.AreaStrategy;
import com.test1.calculator.converter.utils.BitrateStrategy;
import com.test1.calculator.converter.utils.EnergyStrategy;
import com.test1.calculator.converter.utils.LengthStrategy;
import com.test1.calculator.converter.utils.PowerStrategy;
import com.test1.calculator.converter.utils.Strategy;
import com.test1.calculator.converter.utils.TemperatureStrategy;
import com.test1.calculator.converter.utils.TimeStratery;
import com.test1.calculator.converter.utils.VelocityStrategy;
import com.test1.calculator.converter.utils.VolumeStrategy;
import com.test1.calculator.converter.utils.WeightStrategy;

import java.util.ArrayList;

public class ConverterActivity extends AbstractAppCompatActivity {

    String TAG = ConverterActivity.class.getName();
    RecyclerView mRecycleView;
    private Spinner mSpinner;
    private EditText mEditText;
    private Strategy strategy;
    private String mArrUnit[];
    private UnitAdapter mUnitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter_child);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        int pos = bundle.getInt("POS");
        String name = bundle.getString("NAME");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        setUpSpinnerAndStratery(pos);
    }


    private void setUpSpinnerAndStratery(int pos) {
        mArrUnit = new String[]{};
        switch (pos) {
            case 0: //temp
                mArrUnit = getResources().getStringArray(R.array.temp);
                setStrategy(new TemperatureStrategy(getApplicationContext()));
                break;
            case 1: //WEight
                mArrUnit = getResources().getStringArray(R.array.weight);
                setStrategy(new WeightStrategy(getApplicationContext()));
                break;
            case 2: //temp
                mArrUnit = getResources().getStringArray(R.array.length);
                setStrategy(new LengthStrategy(getApplicationContext()));
                break;
            case 3: //temp
                mArrUnit = getResources().getStringArray(R.array.power);
                setStrategy(new PowerStrategy(getApplicationContext()));
                break;
            case 4: //temp
                mArrUnit = getResources().getStringArray(R.array.energy);
                setStrategy(new EnergyStrategy(getApplicationContext()));
                break;
            case 5: //temp
                mArrUnit = getResources().getStringArray(R.array.velocity);
                setStrategy(new VelocityStrategy(getApplicationContext()));
                break;
            case 6: //temp
                setStrategy(new AreaStrategy(getApplicationContext()));
                mArrUnit = getResources().getStringArray(R.array.area);
                break;
            case 7: //temp
                mArrUnit = getResources().getStringArray(R.array.volume);
                setStrategy(new VolumeStrategy(getApplicationContext()));
                break;
            case 8: //temp
                mArrUnit = getResources().getStringArray(R.array.bitrate);
                setStrategy(new BitrateStrategy(getApplicationContext()));
                break;
            case 9: //temp
                mArrUnit = getResources().getStringArray(R.array.time);
                setStrategy(new TimeStratery(getApplicationContext()));
                break;


        }
        ArrayAdapter<String> adapterUnit = new ArrayAdapter<>(ConverterActivity.this, android.R.layout.simple_list_item_1, mArrUnit);
        adapterUnit.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinner.setAdapter(adapterUnit);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initView() {
        mEditText = findViewById(R.id.editInput);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateListView();
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        String text = bundle.getString("input", "");
        mEditText.append(text);

        mSpinner = findViewById(R.id.spinner_unit);
        mRecycleView = findViewById(R.id.listview);
        mRecycleView.setHasFixedSize(true);
        LinearLayoutManager mLayout = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayout);
        mUnitAdapter = new UnitAdapter(this, new ArrayList<ItemUnitConverter>());
        mRecycleView.setAdapter(mUnitAdapter);

    }

    private void updateListView() {
        String currentUnit = mSpinner.getSelectedItem().toString();
        String defaultUnit = strategy.getUnitDefault();
        Log.i("currentUnit", currentUnit);
        ArrayList<ItemUnitConverter> list = new ArrayList<>();
        if (!mEditText.getText().toString().equals("")) {
            try {
                double input = Double.parseDouble(mEditText.getText().toString());
                double defaultValue = strategy.Convert(currentUnit, defaultUnit, input);
                Log.e("INP", String.valueOf(input));
                Log.e("DEF", String.valueOf(defaultValue));

                for (String anArrUnit : mArrUnit) {
                    double res = strategy.Convert(defaultUnit, anArrUnit, defaultValue);
                    ItemUnitConverter itemUnitConverter = new ItemUnitConverter();
                    itemUnitConverter.setTitle(anArrUnit);
                    itemUnitConverter.setRes(String.valueOf(res));
                    list.add(itemUnitConverter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                double input = 0d;
                double defaultValue = strategy.Convert(currentUnit, defaultUnit, input);
                Log.e("INP", String.valueOf(input));
                Log.e("DEF", String.valueOf(defaultValue));

                for (String anArrUnit : mArrUnit) {
                    double res = strategy.Convert(defaultUnit, anArrUnit, defaultValue);
                    ItemUnitConverter itemUnitConverter = new ItemUnitConverter();
                    itemUnitConverter.setTitle(anArrUnit);
                    itemUnitConverter.setRes(String.valueOf(res));
                    list.add(itemUnitConverter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mUnitAdapter.clear();
        mUnitAdapter.addAll(list);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }


}


