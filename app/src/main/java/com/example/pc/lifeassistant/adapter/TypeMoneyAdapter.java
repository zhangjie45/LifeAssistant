package com.example.pc.lifeassistant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.bean.TypeMoneyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/12/8.
 */

public class TypeMoneyAdapter extends BaseAdapter {
    final int itemLength = 18;
    private List<TypeMoneyInfo> list;
    private TypeMoneyAdapter typeMoneyAdapter;
    private LayoutInflater layoutInflater;
    private int clickTemp = 0;//标识被选择的item
    private Context context;
    //  private int[] clickedList = new int[itemLength];//这个数组用来存放item的点击状态


    public void setSeclection(int posiTion) {
        clickTemp = posiTion;
    }

    public TypeMoneyAdapter(Context context, List<TypeMoneyInfo> list) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_gv_type_money, null);
            holder.tv_type_money = convertView.findViewById(R.id.tv_type_money);
            holder.tv_type_money.setText(list.get(position).getTv_type_money());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //根据点击的Item当前状态设置背景
        if (clickTemp == position) {

            holder.tv_type_money.setTextColor(Color.parseColor("#15b4fe"));
            holder.tv_type_money.setBackgroundResource(R.drawable.tv_type_money_select);
        } else {
            holder.tv_type_money.setTextColor(Color.parseColor("#737373"));
            holder.tv_type_money.setBackgroundResource(R.drawable.tv_type_money);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_type_money;
    }


}
