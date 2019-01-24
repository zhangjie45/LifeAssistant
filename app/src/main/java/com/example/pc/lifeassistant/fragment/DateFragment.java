package com.example.pc.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.adapter.DateAdapter;
import com.example.pc.lifeassistant.interface_.OnItemClickListener;
import com.example.pc.lifeassistant.ui.AddDateActivity;
import com.example.pc.lifeassistant.ui.RemindOthersActivity;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.DialogCustom;
import com.example.pc.lifeassistant.util.DialogDelChange;
import com.example.pc.lifeassistant.util.myItemTouchHelperCallBack;

/**
 * Created by pc on 2018/10/30.
 */

public class DateFragment extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {
    private String name;
    private TextView tv_tb_add_date;
    private RecyclerView rv_date;
    private DialogCustom.Builder remake_builder;
    private DialogCustom remake_dialog;
    private DialogDelChange.Builder del_change_builder;
    private DialogDelChange del_change_dialog;

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
        remake_builder = new DialogCustom.Builder(this.getActivity());
        del_change_builder = new DialogDelChange.Builder(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_tb_add_date = getActivity().findViewById(R.id.tv_tb_add);
        rv_date = getActivity().findViewById(R.id.rv_fragment_date);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_date.setLayoutManager(gridLayoutManager);
        //绑定adapter
        DateAdapter dateAdapter = new DateAdapter(getActivity());
        rv_date.setAdapter(dateAdapter);
        ItemTouchHelper.Callback callback = new myItemTouchHelperCallBack(dateAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rv_date);
        tv_tb_add_date.setOnClickListener(this);
        tv_tb_add_date.setOnLongClickListener(this);
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
            public void onItemLongClick(String title, String date, String remakes) {
                showChangeDelDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  ToastUtil("点击了修改");
                        del_change_dialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  ToastUtil("点击了删除");
                        del_change_dialog.dismiss();
                    }
                });
            }
        });
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
