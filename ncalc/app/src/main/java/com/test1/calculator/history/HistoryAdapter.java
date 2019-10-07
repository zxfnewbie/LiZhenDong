package com.test1.calculator.history;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test1.calculator.R;
import com.test1.calculator.data.DatabaseHelper;
import com.test1.calculator.tokenizer.Tokenizer;
import com.test1.calculator.utils.ClipboardManager;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<ResultEntry> itemHistories = new ArrayList<>();
    private Activity context;
    private DatabaseHelper database;
    private Tokenizer tokenizer;

    public HistoryAdapter(Activity context, Tokenizer tokenizer) {
        this.context = context;
        database = new DatabaseHelper(context);
        this.itemHistories = database.getAllItemHistory();
        this.tokenizer = tokenizer;

    }

    public ArrayList<ResultEntry> getItemHistories() {
        return itemHistories;
    }


    public void removeHistory(int position) {
        itemHistories.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ResultEntry resultEntry = itemHistories.get(position);
        holder.txtMath.setText(tokenizer.getLocalizedExpression(resultEntry.getExpression()));

        holder.txtResult.setText(resultEntry.getResult());

        holder.imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager.setClipboard(context, resultEntry.getExpression());
            }
        });
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, resultEntry.getExpression() + " = " + resultEntry.getResult());
                intent.setType("text/plain");
                context.startActivity(intent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });

    }

    public boolean removeItem(int position) {
        if (position > itemHistories.size() - 1) {
            return false;
        }
        itemHistories.remove(position);
        notifyItemRemoved(position);
        return true;
    }

    @Override
    public int getItemCount() {
        return itemHistories.size();
    }

    public void clear() {
        itemHistories.clear();
        notifyItemRangeRemoved(0, getItemCount());
    }



      public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMath;
        TextView txtResult;

        CardView cardView;
        ImageView imgShare;
        ImageView imgCopy;
        View imgDelete;
        View imgEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMath = itemView.findViewById(R.id.txt_input);
            txtResult = itemView.findViewById(R.id.txt_result);
            cardView = itemView.findViewById(R.id.card_view);
            imgShare = itemView.findViewById(R.id.img_share_item);

            imgCopy = itemView.findViewById(R.id.img_copy_item);
            imgDelete = itemView.findViewById(R.id.img_delete_item);
            imgEdit = itemView.findViewById(R.id.img_edit);

        }
    }
}
