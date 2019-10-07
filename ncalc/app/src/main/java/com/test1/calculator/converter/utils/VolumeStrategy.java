package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;


public class VolumeStrategy implements Strategy {
    private Context context;

    public VolumeStrategy(Context context) {
        this.context = context;

    }

    @Override
    public String getUnitDefault() {
        return context.getResources().getString(R.string.volumeunitcubiccm);
    }

    public double Convert(String from, String to, double input) {


        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(context.getResources().getString(R.string.volumeunitcubiccm)))) {
            double ret = input * 100 * 100 * 100;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(context.getResources().getString(R.string.volumeunitcubicm)))) {
            double ret = input / 1000000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(context.getResources().getString(R.string.volumeunitcubicmm)))) {
            double ret = input * 1000 * 1000 * 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(context.getResources().getString(R.string.volumeunitcubicm)))) {
            double ret = input / (1000 * 1000 * 1000);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(context.getResources().getString(R.string.volumeunitcubicfeet)))) {
            double ret = input * 35.31467;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(context.getResources().getString(R.string.volumeunitcubicm)))) {
            double ret = input / 35.31467;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(context.getResources().getString(R.string.volumeunitcubicmm)))) {
            double ret = input * 1000;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(context.getResources().getString(R.string.volumeunitcubiccm)))) {
            double ret = input / 1000;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(context.getResources().getString(R.string.volumeunitcubicfeet)))) {
            double ret = input / 28316.84659;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(context.getResources().getString(R.string.volumeunitcubiccm)))) {
            double ret = input * 28316.84659;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(context.getResources().getString(R.string.volumeunitcubicfeet)))) {
            double ret = input / 28316846.592;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(context.getResources().getString(R.string.volumeunitcubicmm)))) {
            double ret = input * 28316846.592;
            return ret;
        }

        if (from.equals(to)) {
            return input;
        }
        return 0;
    }
}
