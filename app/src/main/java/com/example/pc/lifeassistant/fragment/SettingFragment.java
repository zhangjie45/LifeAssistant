package com.example.pc.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.ui.OpinionActivity;
import com.example.pc.lifeassistant.util.BaseFragment;


/**
 * Created by pc on 2018/10/30.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private String name;
    private TextView tv_setting_opinion;
    private LinearLayout ll_setting_opinion;

    public static final SettingFragment newInstance(String name) {
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        settingFragment.setArguments(bundle);
        return settingFragment;

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
        View view = inflater.inflate(R.layout.fragment_setting, null);
        tv_setting_opinion = view.findViewById(R.id.tv_setting_opinion);
        ll_setting_opinion = view.findViewById(R.id.ll_setting_opinion);
        //  ToastUtil(name);
        ll_setting_opinion.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setting_opinion:
                fragmentToActivity(OpinionActivity.class);
                break;
        }
    }
}
