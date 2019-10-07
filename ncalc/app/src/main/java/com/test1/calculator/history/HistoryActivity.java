package com.test1.calculator.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.test1.calculator.activities.base.AbstractAppCompatActivity;
import com.test1.calculator.R;
import com.test1.calculator.tokenizer.Tokenizer;

import java.util.ArrayList;


public class HistoryActivity extends AbstractAppCompatActivity  {
    private RecyclerView mRecyclerView;
    private ImageButton btnClear;
    private HistoryAdapter mHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = findViewById(R.id.historyRecycler);
        btnClear = findViewById(R.id.btn_delete_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tokenizer tokenizer = new Tokenizer();

        mHistoryAdapter = new HistoryAdapter(this, tokenizer);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mHistoryAdapter.removeHistory(viewHolder.getAdapterPosition());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mHistoryAdapter);
        mRecyclerView.scrollToPosition(mHistoryAdapter.getItemCount() - 1);
        helper.attachToRecyclerView(mRecyclerView);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHistoryDatabase.clearHistory();
                mHistoryAdapter.clear();
                mHistoryAdapter.notifyDataSetChanged();
            }
        });
        mHistoryDatabase.clearHistory();

         }


    @Override
    protected void onPause() {
        doSave();
        super.onPause();
    }

    private void doSave() {
        ArrayList<ResultEntry> histories = mHistoryAdapter.getItemHistories();
        mHistoryDatabase.clearHistory();
        mHistoryDatabase.saveHistory(histories);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            doSave();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
