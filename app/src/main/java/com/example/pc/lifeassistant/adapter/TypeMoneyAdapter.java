package com.example.pc.lifeassistant.adapter;

import android.content.Context;
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
    private List<TypeMoneyInfo> list;
    private TypeMoneyAdapter typeMoneyAdapter;
    private LayoutInflater layoutInflater;

    public TypeMoneyAdapter(Context context, List<TypeMoneyInfo> list) {
        this.list = list;
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
        View view = null;
        if (layoutInflater != null){
            view = layoutInflater.inflate(R.layout.item_gv_type_money,null);
            TextView tv_type_money = view.findViewById(R.id.tv_type_money);
            tv_type_money.setText(list.get(position).getTv_type_money());
        }
        return view;
    }
}
