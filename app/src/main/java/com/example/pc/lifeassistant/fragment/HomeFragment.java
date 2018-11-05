package com.example.pc.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseFragment;

/**
 * Created by pc on 2018/10/29.
 */

public class HomeFragment extends BaseFragment {
    private String name;

    public static final HomeFragment newInstance(String name) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        homeFragment.setArguments(bundle);
        return homeFragment;

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
        View view = inflater.inflate(R.layout.fragment_home, null);
        ToastUtil(name);
        return view;
    }
}
