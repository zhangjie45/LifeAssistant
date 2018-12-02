package com.example.pc.lifeassistant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.util.Utils;


import java.util.List;

/**
 * Created by pc on 2018/12/2.
 */

public class CapitalAdapter extends RecyclerView.Adapter<CapitalAdapter.ViewHolder> {
    CapitalInfo capitalInfo = new CapitalInfo();
    Context context;
    List<CapitalInfo> mList = capitalInfo.addData();


    public CapitalAdapter(Context context) {
        this.context = context;
    }

    public CapitalAdapter(Context context, List<CapitalInfo> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_catipal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.time.setText(mList.get(position).getTime());
//        holder.type.setText(mList.get(position).getType());
//        holder.amount.setText(mList.get(position).getAmount() + "");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView type;
        TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
//            time = (TextView) itemView.findViewById(R.id.time);
//            type = (TextView) itemView.findViewById(R.id.type);
//            amount = (TextView) itemView.findViewById(R.id.amount);


        }
    }
}
