package com.example.pc.lifeassistant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.bean.RemindInfo;
import com.example.pc.lifeassistant.util.Utils;

import java.text.ParseException;
import java.util.List;

/**
 * Created by pc on 2019/4/7.
 */

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.ViewHolder> {
    Context context;
    List<RemindInfo> mList;
    LayoutInflater mInflater;
    private ClickInterface clickInterface;

    public RemindAdapter(Context context) {
        this.context = context;
    }

    public RemindAdapter(Context context, List<RemindInfo> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);

    }

    public void setOnClick(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_remind, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            holder.tv_home_reminder_account.setText(mList.get(position).getAddedpersonaccount());
            holder.tv_home_reminder_content.setText(mList.get(position).getContent());
            holder.tv_home_reminder_date.setText(Utils.GMTtoStr(mList.get(position).getCreatedAt().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.iv_home_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickInterface != null) {
                    clickInterface.onClick(v, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_remind;
        private TextView tv_home_reminder_account;
        private TextView tv_home_reminder_content;
        private TextView tv_home_reminder_date;
        private ImageView iv_home_del;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_remind = itemView.findViewById(R.id.cv_remind);
            tv_home_reminder_account = itemView.findViewById(R.id.tv_home_reminder_account);
            tv_home_reminder_content = itemView.findViewById(R.id.tv_home_reminder_content);
            tv_home_reminder_date = itemView.findViewById(R.id.tv_home_reminder_date);
            iv_home_del = itemView.findViewById(R.id.iv_home_del);
        }
    }

    //回调接口
    public interface ClickInterface {
        void onClick(View view, int position);
    }
}
