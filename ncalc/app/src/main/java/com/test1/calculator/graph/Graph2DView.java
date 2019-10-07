package com.test1.calculator.graph;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.test1.calculator.evaluator.DecimalFactory;

import java.text.DecimalFormat;

import edu.hws.jcm.data.ParseError;
import edu.hws.jcm.data.Parser;


public class Graph2DView extends View implements OnTouchListener {

    public static final int[][] colors = new int[][]{
            {204, 0, 0},
            {102, 153, 255},
            {255, 102, 0},
            {0, 204, 0},
            {255, 204, 0},
            {0, 51, 153}};
    public static final String TAG = "Graph2D";
    public static Parser constantParser = new Parser(Parser.STANDARD_FUNCTIONS | Parser.OPTIONAL_PARENS
            | Parser.OPTIONAL_STARS | Parser.OPTIONAL_SPACES
            | Parser.BRACES | Parser.BRACKETS);

    static {
        GraphMath.setUpParser(constantParser);
    }

    private final DecimalFormat mDecimalFormat = new DecimalFormat("#.##");
    private final TextPaint mTextHintAxis = new TextPaint();
    private final Paint mFunctionPaint = new Paint();

    public String[] functions, paramX, paramY;
    public boolean[] graphable = new boolean[]{false, false, false, false, false, false};
    public Context context;
    public double mMinX = -3, mMaxX = 3, mMinY = -5, mMaxY = 5, scaleX = 1,
            scaleY = 1, initX = 0, initY = 0, startPolar = -1 * Math.PI, endPolar = Math.PI,
            startT = -20, endT = 20;
    public GraphHelper mGraphHelper;
    public float startX, startY, pinchDist;
    public int width, height, interA, interB, mode;
    private Paint axisPaint = new Paint();
    private double tracexVal, traceyVal, traceDeriv;
    private int traceFun = -1;
    private boolean allowMove;
    private boolean trace = false;
    private boolean deriv = false;
    private boolean choose = false;
    private boolean rect = true;

    private Paint.FontMetrics mTextAxisFontMetrics;

    public Graph2DView(Context context) {
        super(context);
        setUp(context);
    }


    public Graph2DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp(context);

    }

    public Graph2DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context);
    }

    public void setUp(Context c) {
        if (!isInEditMode()) {
            context = c;
            setDisplay();
            setMode();
            importFunctions();
            mGraphHelper = new GraphHelper(this);

            axisPaint.setColor(Color.WHITE);
            axisPaint.setStrokeWidth(4f);

            mTextHintAxis.setAntiAlias(true);
            mTextHintAxis.setTypeface(Typeface.MONOSPACE);
            mTextHintAxis.setColor(Color.WHITE);
            mTextHintAxis.setTextSize(spTpPx(10));
            mTextAxisFontMetrics = mTextHintAxis.getFontMetrics();

            mFunctionPaint.setStrokeWidth(dpTpPx(2));
            mFunctionPaint.setAntiAlias(true);

            mGraphHelper = new GraphHelper(this);
        }
    }

    private float spTpPx(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }

    private float dpTpPx(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private void setDisplay() {
        width = getWidth();
        height = getHeight();
        if (width == 0) width = 1;
        if (height == 0) height = 1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        if (width == 0) width = 1;
        if (height == 0) height = 1;
    }

    public void importFunctions() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        functions = new String[6];
        try {
            initX = constantParser.parse(sp.getString("rkX", "")).getVal();
            initY = constantParser.parse(sp.getString("rkY", "")).getVal();
        } catch (ParseError e) {
            initX = 0;
            initY = 0;
        }
        for (int i = 0; i < 6; i++) {
            functions[i] = sp.getString("f" + (i + 1), "");
        }
    }

    /*
     * 重绘 com.duy.example.com.test1.calculator.graph
     */
    public void drawGraph() {
        Log.d(TAG, "drawGraph");
        setDisplay();
        importFunctions();
        mGraphHelper = new GraphHelper(this);
        invalidate();
    }

    private void drawFunction(Canvas canvas) {
        for (int i = 0; i < 6; i++) {
            if (graphable[i]) {
                mFunctionPaint.setColor(Color.rgb(colors[i][0], colors[i][1], colors[i][2]));
                double y1 = mGraphHelper.getVal(i, mMinX);
                double y2 = y1;
                for (int j = 0; j < width; j++) {
                    y1 = y2;
                    y2 = mGraphHelper.getVal(i, mMinX + ((double) j + 1) * (mMaxX - mMinX) / width);
                    if (y1 != Double.POSITIVE_INFINITY && y2 != Double.POSITIVE_INFINITY) {
                        if ((y1 >= 0 || y1 < 0) && (y2 >= 0 || y2 < 0)) {
                            if (!((y1 > 20) && (y2 < -20)) && !((y1 < -20) && (y2 > 20))) {
                                if(j>getxPixel(0)){
                                    canvas.drawLine(j, getyPixel(y1), j + 1, getyPixel(y2), mFunctionPaint);
                                }

                            }
                        }
                    }
                }
            }
        }
    }


    /*
     * 将跟踪值或导数值绘制到 com.duy.example.com.test1.calculator.graph
     */
    private void drawTrace(Canvas canvas) {
        if (traceFun != -1) {
            // 将颜色设置为与函数相同的颜色
            Paint paint = new Paint();
            paint.setColor(Color.rgb(colors[traceFun][0], colors[traceFun][1], colors[traceFun][2]));
            paint.setTextSize((width + height) / 70);

            // 将值显示到屏幕上, 以便更简洁地显示
            double roundX = Math.round(tracexVal * 1000) / 1000.0;
            double roundY = Math.round(traceyVal * 10000) / 10000.0;
            double roundD = Math.round(traceDeriv * 10000) / 10000.0;
            if (trace && !choose) {
                // 在点处绘制一个小 x
                canvas.drawLine(getxPixel(tracexVal) - 8,
                        getyPixel(traceyVal) - 8, getxPixel(tracexVal) + 8,
                        getyPixel(traceyVal) + 8, paint);
                canvas.drawLine(getxPixel(tracexVal) + 8,
                        getyPixel(traceyVal) - 8, getxPixel(tracexVal) - 8,
                        getyPixel(traceyVal) + 8, paint);

                paint.setColor(Color.rgb(colors[traceFun][0], colors[traceFun][1],
                        colors[traceFun][2]));
                // 在左上角为函数值绘制文本
                canvas.drawText("f(" + roundX + ")=" + roundY, 20, height / 2, paint);
            } else if (deriv && !choose) {
                // 在适当的点绘制与曲线相切的线
                canvas.drawLine(getxPixel(tracexVal - (traceyVal - mMinY)
                        / traceDeriv), height, getxPixel(tracexVal
                        + (mMaxY - traceyVal) / traceDeriv), 0, paint);

                Log.d("abcx1", String.valueOf(tracexVal - (traceyVal - mMinY)/ traceDeriv));
                Log.d("abcy1", String.valueOf(height));
                Log.d("abcx2", String.valueOf(tracexVal+ (mMaxY - traceyVal) / traceDeriv));

                paint.setColor(Color.rgb(colors[traceFun][0], colors[traceFun][1],
                        colors[traceFun][2]));
                // 在函数值的右上角绘制文本
                canvas.drawText("f'(" + roundX + ")=" + roundD, 20, height / 2, paint);
            }
        }
    }

    private void setMode() {
        rect = true;
    }

      /*
     * 将  x 坐标转换为相应的 x android 值
     * Android坐标轴转视觉坐标轴
     */
    private double getX(float x) {
        return (x / width) * (mMaxX - mMinX) + mMinX;
    }

    /*
     * 将 android x 值转换为相应的 x 坐标
     * 视觉坐标轴转Android坐标轴
     */
    private int getxPixel(double x) {
        return (int) (width * (x - mMinX) / (mMaxX - mMinX));
    }

    /*
     *将  y 坐标转换为相应的android y 值
     * Android坐标轴转视觉坐标轴
     */
    private double getY(float y) {
        return (height - y) * (mMaxY - mMinY) / height + mMinY;
    }

    /*
     * 将  android y 值转换为其对应的 y 坐标
     * 视觉坐标轴转Android坐标轴
     */
    private int getyPixel(double y) {
        return (int) (height - height * (y - mMinY) / (mMaxY - mMinY));
    }

    public boolean isEmpty() {
        for (boolean b : graphable) {
            if (b) return false;
        }
        return true;
    }

    /*
     * 调用时调用无效
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(255, 0, 0, 0);
        drawAxis(canvas);
        drawFunction(canvas);

        if (!choose) {
            if (trace || deriv) {
                drawTrace(canvas);
            }
        }
    }

    private void drawAxis(Canvas canvas) {
        //预算
        final double lengthX = (mMaxX - mMinX);         //6
        final double step = Double.parseDouble(DecimalFactory.round(lengthX / 8, 2));
        final double minX = (Math.round(mMinX / step)) * step;
        final double maxX = Math.round(mMaxX / step) * step;

        int fontHeight = (int) (mTextAxisFontMetrics.descent - mTextAxisFontMetrics.ascent);
        int fontWidth = (int) mTextHintAxis.measureText(" ");

        //绘制 x 提示
        for (double i = minX; i < maxX; i += step) {
            int y = getyPixel(0);
            int yText = fontHeight;
            if (y < 0) {
                y = 0;
            } else if (y > height - 20) {
                y = height;
                yText = -fontHeight;
            }

            int xValue = getxPixel(i);

            canvas.drawLine(xValue, 0, xValue, height, mTextHintAxis);
            String text = mDecimalFormat.format(i);
            canvas.drawText(text, xValue - fontWidth * text.length() / 2, y + yText, mTextHintAxis);
        }

        final double minY = Math.round(mMinY / step) * step;
        final double maxY = Math.round(mMaxY / step) * step;
        for (double i = minY; i < maxY; i += step) {
            int x = getxPixel(0);
            int y1 = getyPixel(i);
            int xText = fontWidth;
            if (x < 0) {
                x = 0;
            } else if (x > width - 20) {
                x = width;
                xText = -fontWidth;
            }
            canvas.drawLine(0, y1, width, y1, mTextHintAxis);
            String text = mDecimalFormat.format(i);
            canvas.drawText(text, x + xText, y1, mTextHintAxis);
        }

        // 绘制坐标轴
        int x0 = getxPixel(0);
        int y0 = getyPixel(0);
        Log.d("aixsx", String.valueOf(x0));
        Log.d("aixsy", String.valueOf(y0));
        canvas.drawLine(x0, 0, x0, height, axisPaint);
        canvas.drawLine(0, y0, width, y0, axisPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.onTouchEvent(event);
    }

    /*
     * 当用户触摸屏幕时调用
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 在最初的按压
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 如果跟踪或导数处于打开状态
            if (trace || deriv) {
                if (!choose) {
                    tracexVal = getX(event.getX());
                    traceyVal = mGraphHelper.getVal(traceFun, tracexVal);
                    traceDeriv = mGraphHelper.getDerivative(traceFun, tracexVal);

                    Log.d("tracexVal", String.valueOf(tracexVal));
                    Log.d("traceyVal", String.valueOf(traceyVal));
                    Log.d("traceDeriv", String.valueOf(traceDeriv));

                    invalidate();
                }
            } else {
                startX = event.getX();
                startY = event.getY();
                allowMove = true;
            }
            return true;
            // 如果用户移动了他们的手指
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 移动跟踪或导数
            if (trace || deriv) {
                if (!choose) {
                    if (rect) {
                        tracexVal = getX(event.getX());
                        traceyVal = mGraphHelper.getVal(traceFun, tracexVal);
                        traceDeriv = mGraphHelper.getDerivative(traceFun, tracexVal);
                    }
                    invalidate();
                }
            } else {
                touchMove(event);
            }
            return true;
            //当用户完成手势时重置值
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pinchDist = -1;
            allowMove = false;
            if (choose) {
                double x = getX(event.getX());
                double y = getY(event.getY());
                double[] distance = new double[6];
                distance[0] = Double.MAX_VALUE;
                for (int i = 0; i < 6; i++) {
                    if (graphable[i]) {
                        distance[i] = Math.abs(mGraphHelper.getVal(i, x) - y);
                    }
                }
                int smallest = 0;
                for (int i = 1; i < 6; i++) {
                    if (graphable[i]) {
                        if (distance[smallest] > distance[i]) {
                            smallest = i;
                        }
                    }
                }
                traceFun = smallest;
            }
            choose = false;
        }

        return false;
    }

    /*
     * 关闭跟踪派生功能
     */
    public boolean setDeriv(boolean dr) {
        if (!isEmpty()) {
            deriv = dr;
            choose = true;
            invalidate();
            return true;
        }
        deriv = false;
        return false;
    }


    /*
     * 将跟踪功能设置为关闭
     */
    public boolean setTrace(boolean tr) {
        if (!isEmpty()) {
            trace = tr;
            choose = true;
            invalidate();
            return true;
        }
        trace = false;
        return false;
    }


    /*
     * 移动 com.duy.example.com.test1.calculator.graph
     */
    private void touchMove(MotionEvent event) {
        float x = startX - event.getX();
        float y = event.getY() - startY;
        double difX = (mMaxX - mMinX) * x / width;
        double difY = (mMaxY - mMinY) * y / height;
        mMinX += difX;
        mMaxX += difX;
        mMinY += difY;
        mMaxY += difY;
        startX = event.getX();
        startY = event.getY();
        invalidate();
    }

    /*
     * 放大和缩小 com.duy.example.com.test1.calculator.graph
     */
    public void zoom(float perc) {
        double realWidth = mMaxX - mMinX;
        double realHeight = mMaxY - mMinY;
        mMaxX += realWidth * perc / 2;
        mMinX -= realWidth * perc / 2;
        mMinY -= realHeight * perc / 2;
        mMaxY += realHeight * perc / 2;
        scaleX += scaleX * perc;
        scaleY += scaleY * perc;
        invalidate();
    }

}