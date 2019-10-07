package com.test1.calculator.activities.base;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.test1.calculator.R;
import com.test1.calculator.calc.BasicCalculatorActivity;
import com.test1.calculator.activities.DerivativeActivity;
import com.test1.calculator.activities.IntegrateActivity;
import com.test1.calculator.activities.LimitActivity;
import com.test1.calculator.calc.LogicCalculatorActivity;
import com.test1.calculator.activities.PrimitiveActivity;
import com.test1.calculator.activities.SolveEquationActivity;
import com.test1.calculator.converter.UnitCategoryActivity;
import com.test1.calculator.graph.GraphActivity;


public abstract class AbstractNavDrawerActionBarActivity extends AbstractAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    protected DrawerLayout mDrawerLayout;
    private Handler handler = new Handler();

    /**
     * 用户点击返回
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else
            super.onBackPressed();
    }

    /**
     * 当用户点击历史记录关闭抽屉界面
     * 并在用户点击项目导航时关闭
     */
    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }


    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mDrawerLayout.addDrawerListener(this);
        setupHeaderNavigation(navigationView);
    }

    private void setupHeaderNavigation(NavigationView navigationView) {
        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
            }
        });
    }

    /**
     * 项目导航事件单击
     *
     * @param item - item
     * @return boolean
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        closeDrawer();
        Intent intent;
        switch (id) {
            case R.id.nav_sci_calc:
                intent = new Intent(getApplicationContext(), BasicCalculatorActivity.class);
                startIntent(intent);
                break;
            case R.id.nav_graph:
                intent = new Intent(getApplicationContext(), GraphActivity.class);
                startIntent(intent);
                break;
            case R.id.nav_unit:
                intent = new Intent(getApplicationContext(), UnitCategoryActivity.class);
                startIntent(intent);
                break;
            case R.id.nav_base:
                intent = new Intent(getApplicationContext(), LogicCalculatorActivity.class);
                startIntent(intent);
                break;
                   case R.id.nav_solve_equation:
                intent = new Intent(getApplicationContext(), SolveEquationActivity.class);
                startIntent(intent);
                break;

             case R.id.nav_derivitive:
                intent = new Intent(getApplicationContext(), DerivativeActivity.class);
                startIntent(intent);
                break;
            case R.id.nav_limit:
                intent = new Intent(getApplicationContext(), LimitActivity.class);
                startIntent(intent);
                break;
            case R.id.nav_integrate:
                intent = new Intent(getApplicationContext(), IntegrateActivity.class);
                startIntent(intent);
                break;
            case R.id.nav_primitive:
                intent = new Intent(getApplicationContext(), PrimitiveActivity.class);
                startIntent(intent);
                break;



        }
        return true;
    }




    private void startIntent(final Intent intent) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 100);
    }



    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        hideKeyboard(null);
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
