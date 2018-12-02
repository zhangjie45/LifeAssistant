package com.example.pc.lifeassistant.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.adapter.CapitalAdapter;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.ui.AddCapitalActivity;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.ItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/10/30.
 */

public class CapitalFragment extends BaseFragment implements View.OnClickListener {
    private String name;
    private TextView tv_tb_add_date;
    private RecyclerView recyclerView;
    CapitalAdapter adapter;
    private List<CapitalInfo> mList = new ArrayList<>();


    public static final CapitalFragment newInstance(String name) {
        CapitalFragment capitalFragment = new CapitalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        capitalFragment.setArguments(bundle);
        return capitalFragment;

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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_tb_add_date = getActivity().findViewById(R.id.tv_tb_add);
        recyclerView = getActivity().findViewById(R.id.fm_captial_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
            adapter = new CapitalAdapter(getActivity());
        recyclerView.clearAnimation();
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new ItemDecoration(getActivity()));
        tv_tb_add_date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tb_add:
                fragmentToActivity(AddCapitalActivity.class);
                break;
        }


    }


}
