package com.example.pc.lifeassistant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.interface_.OnItemClickListener;
import com.example.pc.lifeassistant.util.ItemTouchHelperAdapter;
import com.example.pc.lifeassistant.bean.DateInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by pc on 2018/11/24.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    //adapter

    DateInfo mData = new DateInfo();
    LayoutInflater mInflater;
    List<DateInfo> mList = mData.addData();
    Context context;
    OnItemClickListener onItemClickListener;


    public DateAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_date, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (mList.get(position).getHome_day() < 5) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.item_remind));
        } else if (mList.get(position).getHome_day() < 10 && mList.get(position).getHome_day() >= 5) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.event_agency));
        }
        holder.home_title.setText(mList.get(position).getHome_title());
        holder.home_day.setText(mList.get(position).getHome_day().toString());
        holder.home_date.setText(mList.get(position).getHome_date());
        holder.home_week.setText(mList.get(position).getHome_week());
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(mList.get(position).getHome_title(), mList.get(position).getHome_date(), mList.get(position).getRemakes());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick("", "", "");
                    return false;
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public void onItemDelete(int position) {
        //移除数据
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView home_title;
        private TextView home_day;
        private TextView home_date;
        private TextView home_week;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            home_title = itemView.findViewById(R.id.home_title);
            home_day = itemView.findViewById(R.id.home_day);
            home_date = itemView.findViewById(R.id.home_date);
            home_week = itemView.findViewById(R.id.home_week);
            cardView = itemView.findViewById(R.id.item_my_recyclerview);

        }
    }

}
