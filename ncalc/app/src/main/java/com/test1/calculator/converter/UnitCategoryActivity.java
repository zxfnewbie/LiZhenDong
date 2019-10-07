package com.test1.calculator.converter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.test1.calculator.R;
import com.test1.calculator.activities.base.AbstractNavDrawerActionBarActivity;
import com.test1.calculator.converter.adapters.CategoryAdapter;

import java.util.ArrayList;


public class UnitCategoryActivity extends AbstractNavDrawerActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected static final String TAG = UnitCategoryActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_unit_converter_acitvity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.ic_temp);
        arrayList.add(R.drawable.ic_weight);
        arrayList.add(R.drawable.ic_length);
        arrayList.add(R.drawable.ic_power);
        arrayList.add(R.drawable.ic_power);
        arrayList.add(R.drawable.ic_speed);
        arrayList.add(R.drawable.ic_area);
        arrayList.add(R.drawable.ic_cubic);
        arrayList.add(R.drawable.ic_bitrate);
        arrayList.add(R.drawable.ic_time);

        RecyclerView recyclerView = findViewById(R.id.rcview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        CategoryAdapter adapter = new CategoryAdapter(arrayList, UnitCategoryActivity.this);
        adapter.setListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, String text) {
                startActivity(pos, text);
            }

            @Override
            public void onItemLongClick() {

            }
        });
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        super.onNavigationItemSelected(item);
        return true;
    }


    void startActivity(int position, String text) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.key_pos), position);
        bundle.putString(getString(R.string.key_name), text);
        Intent intent = new Intent(UnitCategoryActivity.this, ConverterActivity.class);
        intent.putExtra(getString(R.string.key_data), bundle);
        startActivity(intent);
    }


}
