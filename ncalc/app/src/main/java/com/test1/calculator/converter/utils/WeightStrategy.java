package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;


public class WeightStrategy implements Strategy {
    private Context context;

    public WeightStrategy(Context context) {
        this.context = context;

    }

    @Override
    public String getUnitDefault() {
        return context.getResources().getString(R.string.weightunitkg);
    }

    public double Convert(String from, String to, double input) {


        if ((from.equals(context.getResources().getString(R.string.weightunitkg)) && to.equals(context.getResources().getString(R.string.weightunitgm)))) {
            double ret = (1000 * input);
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.weightunitgm)) && to.equals(context.getResources().getString(R.string.weightunitkg)))) {
            double ret = (input / 1000);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitkg)) && to.equals(context.getResources().getString(R.string.weightunitlb)))) {
            double ret = 2.2046 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitlb)) && to.equals(context.getResources().getString(R.string.weightunitkg)))) {
            double ret = 0.454 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitkg)) && to.equals(context.getResources().getString(R.string.weightunitounce)))) {
            double ret = input * 35.27396;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitounce)) && to.equals(context.getResources().getString(R.string.weightunitkg)))) {
            double ret = input * 0.02835;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitkg)) && to.equals(context.getResources().getString(R.string.weightunitmg)))) {
            double ret = input * 1000000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitmg)) && to.equals(context.getResources().getString(R.string.weightunitkg)))) {
            double ret = input / 1000000;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.weightunitgm)) && to.equals(context.getResources().getString(R.string.weightunitlb)))) {
            double ret = 0.0022 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitlb)) && to.equals(context.getResources().getString(R.string.weightunitgm)))) {
            double ret = 453.6 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitgm)) && to.equals(context.getResources().getString(R.string.weightunitmg)))) {
            double ret = input * 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitmg)) && to.equals(context.getResources().getString(R.string.weightunitgm)))) {
            double ret = input / 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitgm)) && to.equals(context.getResources().getString(R.string.weightunitounce)))) {
            double ret = input * 0.03527;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitounce)) && to.equals(context.getResources().getString(R.string.weightunitgm)))) {
            double ret = input * 28.34952;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitlb)) && to.equals(context.getResources().getString(R.string.weightunitmg)))) {
            double ret = input * 453592.37;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitmg)) && to.equals(context.getResources().getString(R.string.weightunitlb)))) {
            double ret = input / 453592.37;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitounce)) && to.equals(context.getResources().getString(R.string.weightunitmg)))) {
            double ret = input * 28349.52313;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.weightunitmg)) && to.equals(context.getResources().getString(R.string.weightunitounce)))) {
            double ret = input / 28349;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitlb)) && to.equals(context.getResources().getString(R.string.weightunitounce)))) {
            double ret = 16 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.weightunitounce)) && to.equals(context.getResources().getString(R.string.weightunitlb)))) {
            double ret = input / 16;
            return ret;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0;
    }
}
