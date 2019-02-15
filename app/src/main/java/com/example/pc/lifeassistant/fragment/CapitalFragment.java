package com.example.pc.lifeassistant.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.adapter.CapitalAdapter;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.bean.DateInfo;
import com.example.pc.lifeassistant.ui.AddCapitalActivity;
import com.example.pc.lifeassistant.util.AVService;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.ItemDecoration;
import com.example.pc.lifeassistant.util.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/10/30.
 */

public class CapitalFragment extends BaseFragment implements View.OnClickListener {
    private String name;
    private TextView tv_tb_add_date;
    private TextView tv_monthly_incomel;
    private TextView tv_monthly_expenditure;
    private RecyclerView recyclerView;
    CapitalAdapter adapter;
    private volatile List<CapitalInfo> capitel;
    private volatile List<CapitalInfo> capitel_income;
    private volatile List<CapitalInfo> capitel_expenditure;
    AVUser user = AVUser.getCurrentUser();

    @SuppressLint("StaticFieldLeak")
    private class showCapital extends AsyncTask<Void, Void, Void> {
        //进入异步任务后被立即执行，一般操作UI提示用户。
        @Override
        protected void onPreExecute() {
            //        ToastUtil("正在获取数据");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            capitel = AVService.findCapital(user.getObjectId());
            try {

                capitel_income = AVService.incomeCapital(user.getObjectId(), Utils.firstDay(), Utils.lastDay());
                capitel_expenditure = AVService.expenditureCapital(user.getObjectId(), Utils.firstDay(), Utils.lastDay());
            } catch (ParseException e) {
                ToastUtil(e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void result) {
            adapter = new CapitalAdapter(getActivity(), capitel);
            recyclerView.clearAnimation();
            recyclerView.setAdapter(adapter);
            tv_monthly_incomel.setText(Utils.inComeCount(capitel_income) + "元");
            tv_monthly_expenditure.setText(Utils.inComeCount(capitel_expenditure) + "元");
        }
    }

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
        tv_monthly_incomel = getActivity().findViewById(R.id.tv_monthly_income);
        tv_monthly_expenditure = getActivity().findViewById(R.id.tv_monthly_expenditure);
        recyclerView = getActivity().findViewById(R.id.fm_captial_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tv_tb_add_date.setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("ok", "onStart");
        //启动异步操作
        new showCapital().execute();
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
