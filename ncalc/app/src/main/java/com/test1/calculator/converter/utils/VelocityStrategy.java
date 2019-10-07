package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;


public class VelocityStrategy implements Strategy {
    private Context context;

    public VelocityStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {


        if ((from.equals(context.getResources().getString(R.string.velocityunitmilesperh)) && to.equals(context.getResources().getString(R.string.velocityunitkmph)))) {
            double ret = input * 1.60934;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitkmph)) && to.equals(context.getResources().getString(R.string.velocityunitmilesperh)))) {
            double ret = input * 0.62137;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitmilesperh)) && to.equals(context.getResources().getString(R.string.velocityunitmeterpers)))) {
            double ret = input * 1609.34 / 3600;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitmeterpers)) && to.equals(context.getResources().getString(R.string.velocityunitmilesperh)))) {
            double ret = input * 3600 / 1609.34;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitmilesperh)) && to.equals(context.getResources().getString(R.string.velocityunitfeetpers)))) {
            double ret = input * 5280 / 3600;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitfeetpers)) && to.equals(context.getResources().getString(R.string.velocityunitmilesperh)))) {
            double ret = input * 3600 / 5280;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitkmph)) && to.equals(context.getResources().getString(R.string.velocityunitmeterpers)))) {
            double ret = input * 1000 / 3600;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitmeterpers)) && to.equals(context.getResources().getString(R.string.velocityunitkmph)))) {
            double ret = input * 3600 / 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitkmph)) && to.equals(context.getResources().getString(R.string.velocityunitfeetpers)))) {
            double ret = input * 3280.84 / 3600;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitfeetpers)) && to.equals(context.getResources().getString(R.string.velocityunitkmph)))) {
            double ret = input * 3600 / 3280.84;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.velocityunitmeterpers)) && to.equals(context.getResources().getString(R.string.velocityunitfeetpers)))) {
            double ret = input * 3.28084;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.velocityunitfeetpers)) && to.equals(context.getResources().getString(R.string.velocityunitmeterpers)))) {
            double ret = input / 3.28084;
            return ret;
        }

        if (from.equals(to)) {
            return input;
        }
        return 0;
    }

    @Override
    public String getUnitDefault() {
        return context.getResources().getString(R.string.velocityunitmeterpers);
    }
}
