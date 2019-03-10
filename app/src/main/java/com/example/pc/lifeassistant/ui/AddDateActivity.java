package com.example.pc.lifeassistant.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.bean.DateInfo;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by pc on 2018/11/4.
 * 添加日程管理界面
 */

public class AddDateActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout til_add_date_title;
    private TextInputEditText tiet_add_date_title;
    private EditText et_add_remakes;
    private EditText et_add_date;
    private Button btn_save;
    private String date;
    private String week;
    private String remakers;
    private String ObjectId;
    private boolean flag_add = true;//是否进行修改操作，修改：false;添加：true
    AVObject event;

    AVUser user = AVUser.getCurrentUser();

    @Override
    protected void onStart() {
        //注册EventBus
        EventBus.getDefault().register(this);
        super.onStart();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);
        initToolbar(R.id.tl_, R.id.toolbar_title, "日程添加", true);
        init();
        TIETextChange(tiet_add_date_title, til_add_date_title, 5);
        initSwipeBack();

    }

    //获取上个页面返回到修改信息
    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(DateInfo str) throws ParseException {
        if (str != null) {
            String title = str.getHome_title();
            date = Utils.GMTtoStr(str.getHome_date() + "");
            String remakes = str.getRemakes();
            week = str.getHome_week();
            ObjectId = str.getObjectId();
            flag_add = false;
            tiet_add_date_title.setText(title);
            et_add_date.setText(date);
            et_add_remakes.setText(remakes);
            initToolbar(R.id.tl_, R.id.toolbar_title, "日程修改", true);
        }
    }

    private void init() {
        til_add_date_title = (TextInputLayout) findViewById(R.id.til_add_date_title);
        tiet_add_date_title = (TextInputEditText) findViewById(R.id.tiet_add_date_title);
        et_add_remakes = (EditText) findViewById(R.id.et_add_remakes);
        btn_save = (Button) findViewById(R.id.btn_save);
        et_add_date = (EditText) findViewById(R.id.et_add_date);
        et_add_date.setCursorVisible(false);
        et_add_date.setFocusable(false);
        et_add_date.setFocusableInTouchMode(false);
        et_add_date.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_add_date:
                date();
                break;
            case R.id.btn_save:
                Conditionaljudgement();
                break;
        }
    }

    private void Conditionaljudgement() {

        remakers = et_add_remakes.getText().toString();
        if (tiet_add_date_title.getText().toString().equals("")) {
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
        } else if (tiet_add_date_title.getText().toString().length() > 5) {
            Toast.makeText(this, "标题字数不能超过5个", Toast.LENGTH_SHORT).show();
        } else if (StrToDate(date) == null) {
            Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
        } else if (et_add_date.getText().toString().equals("所选日期不能小于今天")) {
            Toast.makeText(this, "所选日期不能小于今天", Toast.LENGTH_SHORT).show();
        } else if (remakers.equals("")) {
            remakers = "暂无备注";
            addEvent();
        } else {
            addEvent();
        }
    }


    public void addEvent() {
        if (flag_add) {
            event = new AVObject("Event");
        } else {
            event = AVObject.createWithoutData("Event", ObjectId);
        }
        event.put("home_week", week);
        event.put("home_date", StrToDate(date));
        event.put("home_title", tiet_add_date_title.getText().toString());
        event.put("remakes", remakers);
        event.put("user_id", user.getObjectId());
        event.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(AddDateActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddDateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                    if (Utils.isNetworkConnected(AddDateActivity.this)) {
                        Toast.makeText(AddDateActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void date() {
        Calendar c = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(AddDateActivity.this,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        date = year + "/" + month + "/" + dayOfMonth;
                        //    Toast.makeText(AddDateActivity.this, getWeek(data), Toast.LENGTH_SHORT).show();
                        week = getWeek(date);
                        if (getTimeCompareSize(StrToDate(date)) == 1) {
                            et_add_date.setText(year + "/" + month + "/" + dayOfMonth);
                        } else {
                            et_add_date.setText("所选日期不能小于今天");
                        }
                    }
                }
                // 设置初始日期
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除所有的粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //解除注册
        EventBus.getDefault().unregister(this);
    }
}
