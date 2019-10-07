package com.test1.calculator.graph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.test1.calculator.activities.base.AbstractAppCompatActivity;
import com.test1.calculator.R;

import static android.view.View.VISIBLE;


public class GraphActivity extends AbstractAppCompatActivity {
    public static final String DATA = GraphActivity.class.getName();
    public static final String FUNC = GraphActivity.class.getName();
    public static final String TAG = "GraphActivity";

    private static final int REQUEST_CODE = 1212;
    private static final String GRAPH_STATED = "GRAPH_STATED";
    private SharedPreferences preferences;

    Graph2DView mGraph2D;
    SwitchCompat imgTrace;
    SwitchCompat imgDervi;
    SwitchCompat mModeGraph;
    private boolean isTrace = false, isDerivative = false;
    private int mode;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);

        mGraph2D = findViewById(R.id.graph_2d);
        imgTrace = findViewById(R.id.img_trace);
        imgDervi = findViewById(R.id.btn_der);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        imgTrace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b != isTrace) changeModeTrace(b);
            }
        });

        imgDervi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b != isDerivative) changeModeDerivative(b);
            }
        });

        findViewById(R.id.img_add_fun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFunction();
            }
        });

        findViewById(R.id.img_zoom_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomIn();
            }
        });

        findViewById(R.id.img_zoom_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomOut();
            }
        });


        //显示函数图像
        invalidate();


        if (!mCalculatorSetting.getBoolean(GRAPH_STATED, false)) {
            SharedPreferences.Editor editor = mCalculatorSetting.getEditor();
            editor.putString("f1", "x^2");
            editor.apply();
            editor.putBoolean(GRAPH_STATED, true);
        }

        receiveData(); //intent 数据

    }

    private void addFunction() {
        Intent intent = new Intent(GraphActivity.this, GraphAddFunction.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void zoomIn() {
              mGraph2D.zoom((float) -.5);
    }

    private void zoomOut() {
        mGraph2D.zoom((float) .5);
    }


    private void changeModeDerivative(boolean isDerivative) {
        this.isTrace = false;
        this.isDerivative = isDerivative;

        //禁用跟踪模式
        mGraph2D.setTrace(false);

        //调用此方法时，模式导数始终会更改
        boolean result = mGraph2D.setDeriv(this.isDerivative);

        if (!result) {
            this.isDerivative = false;
            Toast.makeText(GraphActivity.this, getString(R.string.noFunDisp),
                    Toast.LENGTH_LONG).show();
        } else if (this.isDerivative) {
            Toast.makeText(GraphActivity.this, getString(R.string.tapFun),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 将图形更改为模式isTrace，用户无法移动图形视图
     *
     * @param isTrace - isTrace enable
     */
    private void changeModeTrace(boolean isTrace) {
        //如果启用，则禁用模式衍生
        this.isDerivative = false;
        imgDervi.setChecked(false);
        mGraph2D.setDeriv(false);

        //设置模式跟踪
        this.isTrace = isTrace;
        boolean result = mGraph2D.setTrace(this.isTrace);

        if (!result) {
            this.isTrace = false;
            Toast.makeText(GraphActivity.this,
                    getString(R.string.noFunDisp), Toast.LENGTH_LONG).show();
        } else if (this.isTrace) {
            Toast.makeText(GraphActivity.this,
                    getString(R.string.tapFun), Toast.LENGTH_LONG).show();
        }
    }

    private void invalidate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgTrace.setVisibility(VISIBLE);
                imgDervi.setVisibility(VISIBLE);
            }
        }, 100);

        mGraph2D.setVisibility(View.VISIBLE);
        mGraph2D.drawGraph();
        mode = GraphMode.TWO_D;

    }

    private void receiveData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(DATA);
        if (bundle != null) {
            String fx = bundle.getString(FUNC);
            if (!fx.isEmpty()) mCalculatorSetting.getEditor().putString("f1", fx).apply();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mode = mCalculatorSetting.getInt("GraphMode", GraphMode.TWO_D);
        invalidate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCalculatorSetting.getEditor().putInt("GraphMode", mode).apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      delete();
    }
    public  void delete(){
        SharedPreferences.Editor edit = preferences.edit();
        for (int i = 0; i < 6; i++) {
            edit.putString("f" + (i + 1), "");
        }
        edit.apply();
    }
}
