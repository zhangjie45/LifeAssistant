package com.example.pc.lifeassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.ui.LoginActivity;
import com.example.pc.lifeassistant.ui.OpinionActivity;
import com.example.pc.lifeassistant.util.BaseFragment;


/**
 * Created by pc on 2018/10/30.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private String name;
    private TextView tv_setting_opinion;
    private TextView tv_setting_name;
    private TextView tv_setting_phone;
    private TextView tv_setting_email;
    private Button btn_setting_logOut;
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
        tv_setting_name = view.findViewById(R.id.tv_setting_name);
        tv_setting_phone = view.findViewById(R.id.tv_setting_phone);
        tv_setting_email = view.findViewById(R.id.tv_setting_email);
        btn_setting_logOut = view.findViewById(R.id.btn_setting_logOut);
        ll_setting_opinion = view.findViewById(R.id.ll_setting_opinion);
        //  ToastUtil(name);
        ll_setting_opinion.setOnClickListener(this);
        btn_setting_logOut.setOnClickListener(this);
        //用户信息展示
        ShowInfo();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setting_opinion:

                fragmentToActivity(OpinionActivity.class);
                break;
            case R.id.btn_setting_logOut:
                AVUser.logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
        }
    }


    private void ShowInfo() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            tv_setting_name.setText(user.getUsername());
            tv_setting_phone.setText(user.getMobilePhoneNumber());
            tv_setting_email.setText(user.getEmail());
        }

    }

}
