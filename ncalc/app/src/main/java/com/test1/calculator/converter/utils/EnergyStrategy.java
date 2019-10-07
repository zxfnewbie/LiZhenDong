package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;

public class EnergyStrategy implements Strategy {
    private final Context context;

    public EnergyStrategy(Context context) {
        this.context = context;
    }

    @Override
    public String getUnitDefault() {
        return context.getResources().getString(R.string.energyunitcalories);
    }

    public double Convert(String from, String to, double input) {


        if ((from.equals(context.getResources().getString(R.string.energyunitcalories)) && to.equals(context.getResources().getString(R.string.energyunitkilocalories)))) {
            double ret = input / 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.energyunitkilocalories)) && to.equals(context.getResources().getString(R.string.energyunitcalories)))) {
            double ret = input * 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.energyunitcalories)) && to.equals(context.getResources().getString(R.string.energyunitjoules)))) {
            double ret = input * 4.1868;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.energyunitjoules)) && to.equals(context.getResources().getString(R.string.energyunitcalories)))) {
            double ret = input * 0.23885;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.energyunitkilocalories)) && to.equals(context.getResources().getString(R.string.energyunitjoules)))) {
            double ret = input * 4186.8;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.energyunitjoules)) && to.equals(context.getResources().getString(R.string.energyunitkilocalories)))) {
            double ret = input / 4186.8;
            return ret;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0;
    }
}
