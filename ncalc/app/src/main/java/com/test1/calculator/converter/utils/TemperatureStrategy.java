package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;

public class TemperatureStrategy implements Strategy {
    private Context context;

    public TemperatureStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {
        if ((from.equals(context.getResources().getString(R.string.temperatureunitc)) && to.equals((context.getResources().getString(R.string.temperatureunitf))))) {
            return (input * 9 / 5) + 32;
        }
        if ((from.equals(context.getResources().getString(R.string.temperatureunitf)) && to.equals((context.getResources().getString(R.string.temperatureunitc))))) {
            return (input - 32) * 5 / 9;
        }
        if ((from.equals(context.getResources().getString(R.string.temperatureunitc)) && to.equals((context.getResources().getString(R.string.temperatureunitk))))) {
            return input + 273;
        }
        if ((from.equals(context.getResources().getString(R.string.temperatureunitk)) && to.equals((context.getResources().getString(R.string.temperatureunitc))))) {
            return input - 273;
        }
        if ((from.equals(context.getResources().getString(R.string.temperatureunitc))
                && to.equals((context.getResources().getString(R.string.temperatureunitn))))) {
            return input * 33 / 100;
        }
        if ((from.equals(context.getResources().getString(R.string.temperatureunitn)) &&
                to.equals((context.getResources().getString(R.string.temperatureunitc))))) {
            return input * 100 / 33;
        }

        if (from.equals(to)) {
            return input;
        }

        return 0.0;
    }

    @Override
    public String getUnitDefault() {

        return context.getResources().getString(R.string.temperatureunitc);
    }

}
