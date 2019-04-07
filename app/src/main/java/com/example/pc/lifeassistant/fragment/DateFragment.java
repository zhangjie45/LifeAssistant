package com.example.pc.lifeassistant.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.baoyz.widget.PullRefreshLayout;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.adapter.DateAdapter;
import com.example.pc.lifeassistant.bean.DateInfo;
import com.example.pc.lifeassistant.interface_.OnItemClickListener;
import com.example.pc.lifeassistant.ui.AddDateActivity;
import com.example.pc.lifeassistant.ui.RemindOthersActivity;
import com.example.pc.lifeassistant.util.AVService;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.DialogCustom;
import com.example.pc.lifeassistant.util.DialogDelChange;
import com.example.pc.lifeassistant.util.SharedPreferencesHelper;
import com.example.pc.lifeassistant.util.myItemTouchHelperCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

/**
 * Created by pc on 2018/10/30.
 */

public class DateFragment extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {
    private String name;
    private TextView tv_tb_add_date;
    private RecyclerView rv_date;
    private PullRefreshLayout date_swipe_refresh;

    private DialogCustom.Builder remake_builder;
    private DialogCustom remake_dialog;
    private DialogDelChange.Builder del_change_builder;
    private DialogDelChange del_change_dialog;
    SharedPreferencesHelper sharedPreferencesHelper;
    private volatile List<DateInfo> events;
    AVUser user = AVUser.getCurrentUser();


    @SuppressLint("StaticFieldLeak")
    private class showEvents extends AsyncTask<Void, Void, Void> {


        //进入异步任务后被立即执行，一般操作UI提示用户。
        @Override
        protected void onPreExecute() {
            //        ToastUtil("正在获取数据");
            super.onPreExecute();
        }

        //执行较为费时的操作。
        @Override
        protected Void doInBackground(Void... voids) {
            Date date = new Date(System.currentTimeMillis());
            events = AVService.findDate(user.getObjectId(), date);
//
//            String date_events_str = sharedPreferencesHelper.getSharedPreference("date_events", "").toString();
//            if (date_events_str.length() == 0) {
//                Gson date_events = new Gson();
//                String date_events_json = date_events.toJson(events);
//                sharedPreferencesHelper.put("date_events", date_events_json);
//                events = AVService.findDate(user.getObjectId(), date);
//            } else {
//                if (!date_events_str.equals("")) {
//                    Gson gson = new Gson();
//                    events = gson.fromJson(date_events_str, new TypeToken<List<UserBean>>() {
//                    }.getType());
//                }
//            }

            //   sharedPreferencesHelper.put("date_events", events);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            final DateAdapter dateAdapter = new DateAdapter(getActivity(), events);
            rv_date.setAdapter(dateAdapter);
            date_swipe_refresh.setRefreshing(false);
            ItemTouchHelper.Callback callback = new myItemTouchHelperCallBack(dateAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(rv_date);
            dateAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(String title, String date, String remakes) {
                    //   ToastUtil(position + "");
                    showSingleButtonDialog(title, date, remakes, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remake_dialog.dismiss();
                        }
                    });
                }


                @Override
                public void onItemLongClick(final String week, final String title, String amount, final Date date, final String remakes, final String ObjectId, String incomeOrexpenditure, Integer type_position) {
                    showChangeDelDialog(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentToActivity(AddDateActivity.class);
                            DateInfo dateInfo = new DateInfo();
                            dateInfo.setObjectId(ObjectId);
                            dateInfo.setRemakes(remakes);
                            dateInfo.setHome_date(date);
                            dateInfo.setHome_title(title);
                            dateInfo.setHome_week(week);
                            EventBus.getDefault().postSticky(dateInfo);
                            del_change_dialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AVQuery.doCloudQueryInBackground("delete from Event where objectId='" + ObjectId + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                                @Override
                                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                    // 如果 e 为空，说明保存成功
                                    if (e == null) {
                                        ToastUtil("删除成功");
                                        new showEvents().execute();
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


    public static DateFragment newInstance(String name) {
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
        remake_builder = new DialogCustom.Builder(this.getActivity());
        del_change_builder = new DialogDelChange.Builder(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, null);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "Date");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_tb_add_date = getActivity().findViewById(R.id.tv_tb_add);
        rv_date = getActivity().findViewById(R.id.rv_fragment_date);
        date_swipe_refresh = getActivity().findViewById(R.id.date_swipe_refresh);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_date.setLayoutManager(gridLayoutManager);
        //启动异步操作
        new showEvents().execute();
        tv_tb_add_date.setOnClickListener(this);
        tv_tb_add_date.setOnLongClickListener(this);
        date_swipe_refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new showEvents().execute();
            }
        });

    }


    //当fragment被打开时重新请求
    @Override
    public void onStart() {
        super.onStart();
        new showEvents().execute();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tb_add:
                fragmentToActivity(AddDateActivity.class);
                break;
        }

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tb_add:
                fragmentToActivity(RemindOthersActivity.class);
                break;
        }
        return true;
    }

    //展示自定义dialog
    private void showSingleButtonDialog(String title, String date, String remakes, View.OnClickListener onClickListener) {
        remake_dialog = remake_builder.setMessage(title, date, remakes)
                .setSingleButton(onClickListener)
                .createSingleButtonDialog();
        remake_dialog.show();
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
