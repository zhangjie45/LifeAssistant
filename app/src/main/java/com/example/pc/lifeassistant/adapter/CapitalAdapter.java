package com.example.pc.lifeassistant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.util.Utils;


import java.text.ParseException;
import java.util.List;

/**
 * Created by pc on 2018/12/2.
 */

public class CapitalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int EMPTY_VIEW = 1;
    private final int INCOME_VIEW = 2;
    private final int EXPENDITURE_VIEW = 3;
    Context context;
    List<CapitalInfo> mList;


    public CapitalAdapter(Context context) {
        this.context = context;
    }

    public CapitalAdapter(Context context, List<CapitalInfo> mList) {
        this.context = context;
        this.mList = mList;
    }


    @Override
    public int getItemViewType(int position) {
        Log.i("获取到的position---》", mList.get(position).getType());
        if (mList.size() == 0) {
            return EMPTY_VIEW;
        } else if (mList.get(position) == null) {
            return EMPTY_VIEW;
        } else if (mList.get(position).getIncomeOrexpenditure().equals("收入")) {
            return INCOME_VIEW;
        } else if (mList.get(position).getIncomeOrexpenditure().equals("支出")) {
            return EXPENDITURE_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == EMPTY_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.item_caption_empty, parent, false);
            return new EmptyViewHolder(view);
        } else if (viewType == INCOME_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.item_caption_income, parent, false);
            return new InComeViewHolder(view);
        } else if (viewType == EXPENDITURE_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.item_caption_expenditure, parent, false);
            return new ExpenditureViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_catipal, parent, false);
            return new EmptyViewHolder(view);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {

        } else if (holder instanceof InComeViewHolder) {
            InComeViewHolder viewHolder = (InComeViewHolder) holder;

            try {
                viewHolder.type.setText(mList.get(position).getType());
                viewHolder.time.setText(Utils.GMTtoStr(mList.get(position).getDate("time") + ""));
                viewHolder.amount.setText(mList.get(position).getAmount());
            } catch (ParseException e) {
                e.printStackTrace();
            }


        } else if (holder instanceof ExpenditureViewHolder) {
            ExpenditureViewHolder viewHolder = (ExpenditureViewHolder) holder;
            try {
                viewHolder.type.setText(mList.get(position).getType());
                viewHolder.time.setText(Utils.GMTtoStr(mList.get(position).getDate("time") + ""));
                viewHolder.amount.setText(mList.get(position).getAmount());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
        //  return 0;
    }


    class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView time;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.capital_time);
        }
    }

    class InComeViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView type;
        TextView amount;

        public InComeViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.capital_time);
            type = (TextView) itemView.findViewById(R.id.capital_type);
            amount = (TextView) itemView.findViewById(R.id.capital_amount);

        }
    }

    class ExpenditureViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView type;
        TextView amount;

        public ExpenditureViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.capital_time);
            type = (TextView) itemView.findViewById(R.id.capital_type);
            amount = (TextView) itemView.findViewById(R.id.capital_amount);
        }
    }


}
