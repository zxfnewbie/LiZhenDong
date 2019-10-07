package com.test1.calculator.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.test1.calculator.R;
import com.test1.calculator.evaluator.EvaluateConfig;

/**
 * 应用程序的设置
 */
public class CalculatorSetting {

    public static final long serialVersionUID = 310L;

    public static final String INPUT_MATH = "inp_math";
      public static final String INPUT_BASE = "INPUT_BASE";
    public static final String RESULT_BASE = "RESULT_BASE";
    public static final String BASE = "BASE";

    /**
     * 与mSetting.xml文件上的key_pref_fraction重复
     */
    public static final String IS_FRACTION = "fraction_mode";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private Context context;

    public CalculatorSetting(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sharedPreferences.edit();
    }



    public boolean useFraction() {
        return sharedPreferences.getBoolean(CalculatorSetting.IS_FRACTION, false);
    }

    public void setFraction(boolean b) {
        editor.putBoolean(IS_FRACTION, b).commit();
    }


    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }



    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int def) {
        try {
            return sharedPreferences.getInt(key, def);
        } catch (Exception e) {
            String value = getString(context.getString(R.string.key_pref_precision));
            try {
                return Integer.parseInt(value);
            } catch (Exception e1) {

            }
        }
        return def;
    }


    public String getString(String key) {
        try {
            return sharedPreferences.getString(key, "");
        } catch (Exception e) {
            return "";
        }
    }

    public String getString(String key, String def) {
        try {
            return sharedPreferences.getString(key, def);
        } catch (Exception e) {
            return def;
        }
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


    public SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public int getPrecision() {
        return getInt(context.getString(R.string.key_pref_precision));
    }


    public boolean getBoolean(String key, boolean def) {
        return sharedPreferences.getBoolean(key, def);

    }

    public static EvaluateConfig createEvaluateConfig(Context context) {
        CalculatorSetting setting = new CalculatorSetting(context);
        EvaluateConfig config = EvaluateConfig.newInstance();
        config.setEvalMode(setting.useFraction() ? EvaluateConfig.FRACTION : EvaluateConfig.DECIMAL);
        config.setRoundTo(setting.getPrecision());
        return config;
    }

}
