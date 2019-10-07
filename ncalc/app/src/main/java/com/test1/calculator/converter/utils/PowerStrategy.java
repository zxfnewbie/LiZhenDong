package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;


public class PowerStrategy implements Strategy {
    private Context context;

    @Override
    public String getUnitDefault() {
        return context.getResources().getString(R.string.powerunithorsepower);
    }

    public PowerStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {


        if ((from.equals(context.getResources().getString(R.string.powerunitwatts))
                && to.equals(context.getResources().getString(R.string.powerunithorsepower)))) {
            double ret = 0.00134 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.powerunithorsepower))
                && to.equals(context.getResources().getString(R.string.powerunitwatts)))) {
            double ret = 745.7 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.powerunitwatts))
                && to.equals(context.getResources().getString(R.string.powerunitkilowatts)))) {
            double ret = input / 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.powerunitkilowatts))

                && to.equals(context.getResources().getString(R.string.powerunitwatts)))) {
            double ret = input * 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.powerunitkilowatts))
                && to.equals(context.getResources().getString(R.string.powerunithorsepower)))) {
            double ret = input * 1.34102;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.powerunithorsepower))
                && to.equals(context.getResources().getString(R.string.powerunitkilowatts)))) {
            double ret = input * 0.7457;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.powerunithorsepower))
                && to.equals(context.getResources().getString(R.string.powerunitmegawatt)))) {
            double ret = input * 745.69 / 1000 / 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.powerunitmegawatt))
                && to.equals(context.getResources().getString(R.string.powerunithorsepower)))) {
            double ret = input / 745.69 * 1000 * 1000;
            return ret;
        }

//
        if ((from.equals(context.getResources().getString(R.string.powerunithorsepower))
                && to.equals(context.getResources().getString(R.string.powerunitgigawatt)))) {
            double ret = input * 745.69 / 1000 / 1000 / 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.powerunitgigawatt))
                && to.equals(context.getResources().getString(R.string.powerunithorsepower)))) {
            double ret = input / 745.69 * 1000 * 1000 * 1000;
            return ret;
        }

        if (from.equals(to)) {
            return input;
        }
        return 0.0;
    }

}
