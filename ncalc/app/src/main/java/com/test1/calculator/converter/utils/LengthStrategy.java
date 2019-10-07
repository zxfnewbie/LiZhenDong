package com.test1.calculator.converter.utils;

import android.content.Context;

import com.test1.calculator.R;


public class LengthStrategy implements Strategy {
    private Context context;

    public LengthStrategy(Context context) {
        this.context = context;
    }

    @Override
    public String getUnitDefault() {
        return context.getResources().getString(R.string.lengthunitm);
    }

    public double Convert(String from, String to, double input) {


        //Application app = UnitConverterActivity.StrategyClass
        if ((from.equals(context.getResources().getString(R.string.lengthunitkm)) && to.equals(context.getResources().getString(R.string.lengthunitmile)))) {
            double ret = 0.62137 * input;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitmile)) && to.equals(context.getResources().getString(R.string.lengthunitkm)))) {
            double ret = 1.60934 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmile)) && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = 1609.34 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitm)) && to.equals(context.getResources().getString(R.string.lengthunitmile)))) {
            double ret = input / 1609.34;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmile)) && to.equals(context.getResources().getString(R.string.lengthunitcm)))) {
            double ret = 160934 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitcm)) && to.equals(context.getResources().getString(R.string.lengthunitmile)))) {
            double ret = input / 160934;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmile)) && to.equals(context.getResources().getString(R.string.lengthunitmm)))) {
            double ret = input * 1609340;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmm)) && to.equals(context.getResources().getString(R.string.lengthunitmile)))) {
            double ret = input / 1609340;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmile)) && to.equals(context.getResources().getString(R.string.lengthunitinch)))) {
            double ret = 63360 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitinch)) && to.equals(context.getResources().getString(R.string.lengthunitmile)))) {
            double ret = input / 63360;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitmile)) && to.equals(context.getResources().getString(R.string.lengthunitfeet)))) {
            double ret = 5280 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitfeet)) && to.equals(context.getResources().getString(R.string.lengthunitmile)))) {
            double ret = input / 5280;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitkm)) && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input * 1000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitm)) && to.equals(context.getResources().getString(R.string.lengthunitkm)))) {
            double ret = 0.001 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitkm)) && to.equals(context.getResources().getString(R.string.lengthunitcm)))) {
            double ret = 100000 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitcm)) && to.equals(context.getResources().getString(R.string.lengthunitkm)))) {
            double ret = input / 100000;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitkm)) && to.equals(context.getResources().getString(R.string.lengthunitmm)))) {
            double ret = 1000000 * input;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitmm)) && to.equals(context.getResources().getString(R.string.lengthunitkm)))) {
            double ret = input / 1000000;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitkm)) && to.equals(context.getResources().getString(R.string.lengthunitfeet)))) {
            double ret = input * 3280.84;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitfeet)) && to.equals(context.getResources().getString(R.string.lengthunitkm)))) {
            double ret = input / 3280.84;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitkm)) && to.equals(context.getResources().getString(R.string.lengthunitinch)))) {
            double ret = input * 39370.1;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitinch)) && to.equals(context.getResources().getString(R.string.lengthunitkm)))) {
            double ret = input / 39370.1;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitm)) && to.equals(context.getResources().getString(R.string.lengthunitcm)))) {
            double ret = 100 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitcm)) && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input / 100;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitm)) && to.equals(context.getResources().getString(R.string.lengthunitmm)))) {
            double ret = 1000 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmm)) && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input / 1000;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitm)) && to.equals(context.getResources().getString(R.string.lengthunitinch)))) {
            double ret = 100 * input / 2.54;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitinch)) && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = 2.54 * input / 100;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitm)) && to.equals(context.getResources().getString(R.string.lengthunitfeet)))) {
            double ret = input * 3.28084;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitfeet)) && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input / 3.28084;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitcm)) && to.equals(context.getResources().getString(R.string.lengthunitmm)))) {
            double ret = 10 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmm)) && to.equals(context.getResources().getString(R.string.lengthunitcm)))) {
            double ret = input / 10;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitinch)) && to.equals(context.getResources().getString(R.string.lengthunitcm)))) {
            double ret = 2.54 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitcm)) && to.equals(context.getResources().getString(R.string.lengthunitinch)))) {
            double ret = input / 2.54;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitcm)) && to.equals(context.getResources().getString(R.string.lengthunitfeet)))) {
            double ret = input * 0.03281;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitfeet)) && to.equals(context.getResources().getString(R.string.lengthunitcm)))) {
            double ret = input * 30.48;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmm)) && to.equals(context.getResources().getString(R.string.lengthunitfeet)))) {
            double ret = 0.00328 * input;
            return ret;
        }


        if ((from.equals(context.getResources().getString(R.string.lengthunitfeet)) && to.equals(context.getResources().getString(R.string.lengthunitmm)))) {
            double ret = input * 304.8;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitmm)) && to.equals(context.getResources().getString(R.string.lengthunitinch)))) {
            double ret = input / 25.4;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitinch)) && to.equals(context.getResources().getString(R.string.lengthunitmm)))) {
            double ret = input * 25.4;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitfeet)) && to.equals(context.getResources().getString(R.string.lengthunitinch)))) {
            double ret = 12 * input;
            return ret;
        }

        if ((from.equals(context.getResources().getString(R.string.lengthunitinch)) && to.equals(context.getResources().getString(R.string.lengthunitfeet)))) {
            double ret = input / 12;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitnm))
                && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input / 10e9;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitm))
                && to.equals(context.getResources().getString(R.string.lengthunitnm)))) {
            double ret = input * 10e9;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitmicrom))
                && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input / 10e6;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitm))
                && to.equals(context.getResources().getString(R.string.lengthunitmicrom)))) {
            double ret = input * 10e6;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitpm))
                && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input / 10e12;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitm))
                && to.equals(context.getResources().getString(R.string.lengthunitpm)))) {
            double ret = input * 10e12;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitdm))
                && to.equals(context.getResources().getString(R.string.lengthunitm)))) {
            double ret = input / 10;
            return ret;
        }
        if ((from.equals(context.getResources().getString(R.string.lengthunitm))
                && to.equals(context.getResources().getString(R.string.lengthunitdm)))) {
            double ret = input * 10;
            return ret;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0;
    }

}
