package com.example.pc.lifeassistant.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseFragment;

/**
 * Created by pc on 2018/10/30.
 */

public class CaptialFragment extends BaseFragment implements View.OnClickListener {
    private String name;
    private TextView tv_tb_add_date;

    public static final CaptialFragment newInstance(String name) {
        CaptialFragment captialFragment = new CaptialFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        captialFragment.setArguments(bundle);
        return captialFragment;

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
        View view = inflater.inflate(R.layout.fragment_capital, null);
        tv_tb_add_date = getActivity().findViewById(R.id.tv_tb_add);
        tv_tb_add_date.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        ToastUtil("你点击了资金管理界面的添加按钮");
    }
}
