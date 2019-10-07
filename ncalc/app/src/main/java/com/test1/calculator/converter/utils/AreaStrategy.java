
package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;

public class AreaStrategy implements Strategy {
    private Context context;

    public AreaStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {


        if ((from.equals(context.getResources().getString(R.string.areaunitsqmiles)) && to.equals((context.getResources().getString(R.string.areaunitsqkm))))) {
            double ret = 1.60934 * 1.60934 * input;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.areaunitsqkm)) && to.equals((context.getResources().getString(R.string.areaunitsqmiles))))) {
            double ret = 0.62137 * 0.62137 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(context.getResources().getString(R.string.areaunitsqm)))) {
            double ret = 1609.34 * 1609.34 * input;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.areaunitsqm)) && to.equals(context.getResources().getString(R.string.areaunitsqmiles)))) {
            double ret = input / (1609.34 * 1609.34);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(context.getResources().getString(R.string.areaunitsqcm)))) {
            double ret = input * 160934 * 160934;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqcm)) && to.equals(context.getResources().getString(R.string.areaunitsqmiles)))) {
            double ret = input / (160934 * 160934);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(context.getResources().getString(R.string.areaunitsqmm)))) {
            double ret = input * 1609340 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmm)) && to.equals(context.getResources().getString(R.string.areaunitsqmiles)))) {
            double ret = input / (1609340 * 1609340);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(context.getResources().getString(R.string.areaunitsqyard)))) {
            double ret = input * 1760 * 1760;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqyard)) && to.equals(context.getResources().getString(R.string.areaunitsqmiles)))) {
            double ret = input / (1760 * 1760);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqkm)) && to.equals(context.getResources().getString(R.string.areaunitsqm)))) {
            double ret = input * 1000 * 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqm)) && to.equals(context.getResources().getString(R.string.areaunitsqkm)))) {
            double ret = input / (1000 * 1000);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqkm)) && to.equals(context.getResources().getString(R.string.areaunitsqcm)))) {
            double ret = input * 100000 * 100000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqcm)) && to.equals(context.getResources().getString(R.string.areaunitsqkm)))) {
            double ret = input / (100000 * 100000);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqkm)) && to.equals(context.getResources().getString(R.string.areaunitsqmm)))) {
            double ret = input * 1000000 * 1000000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmm)) && to.equals(context.getResources().getString(R.string.areaunitsqkm)))) {
            double ret = input / (1000000 * 1000000);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqkm)) && to.equals(context.getResources().getString(R.string.areaunitsqyard)))) {
            double ret = 1093.6133 * 1093.6133 * input;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.areaunitsqyard)) && to.equals(context.getResources().getString(R.string.areaunitsqkm)))) {
            double ret = input / (1093.6133 * 1093.6133);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqm)) && to.equals(context.getResources().getString(R.string.areaunitsqcm)))) {
            double ret = input * 100 * 100;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqcm)) && to.equals(context.getResources().getString(R.string.areaunitsqm)))) {
            double ret = input / (100 * 100);
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.areaunitsqm)) && to.equals(context.getResources().getString(R.string.areaunitsqmm)))) {
            double ret = input * 1000 * 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmm)) && to.equals(context.getResources().getString(R.string.areaunitsqm)))) {
            double ret = input / (1000 * 1000);
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.areaunitsqm)) && to.equals(context.getResources().getString(R.string.areaunitsqyard)))) {
            double ret = 1.09361 * 1.09361 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqyard)) && to.equals(context.getResources().getString(R.string.areaunitsqm)))) {
            double ret = input / (1.09361 * 1.09361);
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.areaunitsqcm)) && to.equals(context.getResources().getString(R.string.areaunitsqmm)))) {
            double ret = (input * 10 * 10);
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqmm)) && to.equals(context.getResources().getString(R.string.areaunitsqcm)))) {
            double ret = input / (10 * 10);
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.areaunitsqcm)) && to.equals(context.getResources().getString(R.string.areaunitsqyard)))) {
            double ret = 0.01094 * 0.01094 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqyard)) && to.equals(context.getResources().getString(R.string.areaunitsqcm)))) {
            double ret = input / (0.01094 * 0.01094);
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.areaunitsqmm)) && to.equals(context.getResources().getString(R.string.areaunitsqyard)))) {
            double ret = 0.001094 * 0.001094 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.areaunitsqyard)) && to.equals(context.getResources().getString(R.string.areaunitsqmm)))) {
            double ret = input / (0.001094 * 0.001094);
            return ret;
        }

        if (from.equals(to)) {
            return input;
        }
        return 0.0;
    }

    @Override
    public String getUnitDefault() {
        return context.getResources().getString(R.string.areaunitsqm);
    }
}
