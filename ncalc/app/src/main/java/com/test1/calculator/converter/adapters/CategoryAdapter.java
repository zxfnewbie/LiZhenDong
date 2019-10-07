package com.test1.calculator.converter.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test1.calculator.R;

import java.util.ArrayList;

/**
 * 活动单位转换器父级的自定义适配器
 *
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private String arrTitle[];
    private ArrayList<Integer> arrImg = new ArrayList<>();
    private Context context;
    private OnItemClickListener mListener;


    public CategoryAdapter(ArrayList<Integer> arrImg, Context context) {
        this.arrImg = arrImg;
        this.context = context;
        this.arrTitle = context.getResources().getStringArray(R.array.unit);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoty_unit_converter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(arrTitle[position]);
        if (position < arrImg.size()) {
            holder.imageView.setImageResource(arrImg.get(position));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onItemClick(position, arrTitle[position]);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mListener != null) mListener.onItemLongClick();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrTitle.length;
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, String text);

        void onItemLongClick();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_img);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}