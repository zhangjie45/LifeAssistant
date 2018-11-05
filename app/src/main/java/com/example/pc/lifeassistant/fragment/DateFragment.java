package com.example.pc.lifeassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.ui.AddDateActivity;
import com.example.pc.lifeassistant.util.BaseFragment;

import org.w3c.dom.Text;

/**
 * Created by pc on 2018/10/30.
 */

public class DateFragment extends BaseFragment implements View.OnClickListener {
    private String name;
    private TextView tv_tb_add_date;

    public static final DateFragment newInstance(String name) {
        DateFragment dateFragment = new DateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        dateFragment.setArguments(bundle);
        return dateFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("name");
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, null);
        tv_tb_add_date = getActivity().findViewById(R.id.tv_tb_add);
        tv_tb_add_date.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //    ToastUtil("你点击了日程管理界面的添加按钮");
        fragmentToActivity(AddDateActivity.class);
    }
}
