package com.test1.calculator.history;

import java.io.Serializable;
import java.util.Date;

/**
 * 适配器历史记录的项目
 * <p/>
 */
public class ResultEntry implements Serializable {
    public static final int TYPE_SCIENCE = 0;


    public static final long serialVersionUID = 4L;
    private int type = 0;
    private String expression = "";
    private String result = "";

    private int color = 0;
    private long time = 0; //id

    public ResultEntry(String expression, String res) {
        this(expression, res, 0, new Date().getTime());
    }


    public ResultEntry(String expression, String result, int color, long time) {
        this(expression, result, color, time, TYPE_SCIENCE);
    }

    public ResultEntry(String expression, String result, int color, long time, int type) {
        this.expression = expression;
        this.result = result;
        this.color = color;
        this.time = time;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.expression + " = " + this.result;
    }

    public long getTime() {
        return time;
    }

}
