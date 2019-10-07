package com.test1.calculator.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.test1.calculator.R;
import com.test1.calculator.history.ResultEntry;
import com.test1.calculator.utils.ClipboardManager;

import java.util.ArrayList;

import io.github.kexanie.library.MathView;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    final static String TAG = "ResultAdapter";
    private Activity mActivity;
    private ArrayList<ResultEntry> mResults = new ArrayList<>();
    private OnItemListener listener = null;


    public ResultAdapter(Activity activity) {
        mActivity = activity;
    }

     public void addItem(ResultEntry item) {
        mResults.add(0, item);
        notifyItemInserted(0);
    }

    public boolean removeItem(int position) {
        if (position > mResults.size() - 1) {
            return false;
        }
        mResults.remove(position);
        notifyItemRemoved(position);
        return true;
    }



    public void clear() {
        int size = mResults.size();
        if (mResults.size() == 1) {
            mResults.clear();
            notifyItemRemoved(0);
        } else {
            mResults.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_result, parent, false);
        return new ResultViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ResultViewHolder holder, int position) {
        final ResultEntry item = mResults.get(position);
        Log.d(TAG, "onBindViewHolder: " + item.getExpression() + " = " + item.getResult());
        holder.txtMath.setText(item.getExpression());
        holder.txtResult.setText(item.getResult());
        holder.imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager.setClipboard(mActivity, item.getResult());
            }
        });
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, item.getExpression() + " = " + item.getResult());
                intent.setType("text/plain");
                mActivity.startActivity(intent);
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onEditExpr(item);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }


    public interface OnItemListener {

        void onEditExpr(ResultEntry item);

    }

    class ResultViewHolder extends RecyclerView.ViewHolder {
        MathView txtMath;
        MathView txtResult;
        CardView cardView;
        ImageView imgShare;
        ImageView imgCopy;
        View imgDelete;
        View imgEdit;

        public ResultViewHolder(View itemView) {
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