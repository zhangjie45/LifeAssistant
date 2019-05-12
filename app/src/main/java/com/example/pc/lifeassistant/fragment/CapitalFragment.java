package com.example.pc.lifeassistant.fragment;


import android.annotation.SuppressLint;
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

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.adapter.CapitalAdapter;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.interface_.OnItemClickListener;
import com.example.pc.lifeassistant.ui.AddCapitalActivity;
import com.example.pc.lifeassistant.util.AVService;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.DialogDelChange;
import com.example.pc.lifeassistant.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.Date;
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

    private DialogDelChange.Builder del_change_builder;
    private DialogDelChange del_change_dialog;

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
            try {
                capitel = AVService.findCapital(user.getObjectId(), Utils.firstDay(), Utils.lastDay());
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
            tv_monthly_incomel.setText(Utils.Count(capitel_income) + "元");
            tv_monthly_expenditure.setText(Utils.Count(capitel_expenditure) + "元");
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(String title, String date, String remakes) {

                }


                @Override
                public void onItemLongClick(String week,final String title, final String amount, final Date date, final String remakes, final String ObjectId, final String incomeOrexpenditure, final Integer type_position) {
                    // ToastUtil("点击了删除" + ObjectId);
                    showChangeDelDialog(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentToActivity(AddCapitalActivity.class);
                            CapitalInfo capitalInfo = new CapitalInfo();
                            capitalInfo.setType(title);
                            capitalInfo.setType_position(type_position);
                            capitalInfo.setObjectId(ObjectId);
                            capitalInfo.setTime(date);
                            capitalInfo.setIncomeOrexpenditure(incomeOrexpenditure);
                            capitalInfo.setRemakes(remakes);
                            capitalInfo.setAmount(amount);

                            EventBus.getDefault().postSticky(capitalInfo);
                            del_change_dialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AVQuery.doCloudQueryInBackground("delete from Capital where objectId='" + ObjectId + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                                @Override
                                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                    // 如果 e 为空，说明保存成功
                                    if (e == null) {
                                        ToastUtil("删除成功");
                                        new showCapital().execute();
                                    } else {
                                        ToastUtil("删除失败" + e.getMessage());
                                    }
                                }
                            });
                            del_change_dialog.dismiss();
                        }
                    });
                }
            });
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
        del_change_builder = new DialogDelChange.Builder(this.getActivity());

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
        tv_tb_add_date = getActivity().findViewById(R.id.tv_tb_capital_add);
        tv_monthly_incomel = getActivity().findViewById(R.id.tv_monthly_income);
        tv_monthly_expenditure = getActivity().findViewById(R.id.tv_monthly_expenditure);
        recyclerView = getActivity().findViewById(R.id.fm_captial_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tv_tb_add_date.setOnClickListener(this);
        setHasOptionsMenu(true);
    }


    @Override
    public void onStart() {
        super.onStart();
      //  Log.i("ok", "onStart");
        //启动异步操作
        new showCapital().execute();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tb_capital_add:
                fragmentToActivity(AddCapitalActivity.class);
                break;
        }
    }

    //展示自定义dialog
    private void showChangeDelDialog(View.OnClickListener onChangeClickListener, View.OnClickListener onDeleteClickListener) {
        del_change_dialog = del_change_builder.setMessage()
                .setChangeButton(onChangeClickListener)
                .setDeleteButton(onDeleteClickListener)
                .DelChangeButtonDialog();
        del_change_dialog.show();
    }


}
