package com.example.pc.lifeassistant.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.AVService;
import com.example.pc.lifeassistant.util.BaseActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by pc on 2018/11/5.
 */

public class RemindOthersActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout til_remind_id;
    private TextInputLayout til_remind_content;
    private TextInputEditText tiet_remind_id;
    private RelativeLayout rl_remind_query;
    private LinearLayout ll_remind_content;
    private LinearLayout ll_remind_ok;
    private EditText et_remind_date;
    private EditText et_remind_content;
    private Button btn_remind_save;
    String remindId;
    String remindContent;
    String remindDate;
    AVObject remind;
    private volatile List<AVUser> users;

    @SuppressLint({"HandlerLeak", "StaticFieldLeak"})
    private class findAccout extends AsyncTask<Void, Void, Void> {
        //进入异步任务后被立即执行，一般操作UI提示用户。
        @Override
        protected void onPreExecute() {
            //        ToastUtil("正在获取数据");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                users = AVService.queryAccount(tiet_remind_id.getText().toString());

            } catch (ParseException | AVException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            AVUser user = AVUser.getCurrentUser();
            String usename = "";
            for (int i = 0; i < users.size(); i++) {
                usename = users.get(i).get("reminders").toString();
            }
            if (usename.equals(user.getUsername())) {
                ll_remind_content.setVisibility(View.VISIBLE);
                ll_remind_ok.setVisibility(View.VISIBLE);
            } else {
                til_remind_id.setError("该账号暂时未把你设为可提醒对象！");
                ll_remind_content.setVisibility(View.GONE);
                ll_remind_ok.setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_others);
        initToolbar(R.id.tl_, R.id.toolbar_title, "提醒一下TA", true);
        init();
        TIETextChange(tiet_remind_id, til_remind_id, 12);
        EditTextChange(et_remind_content, til_remind_content, 30);
        initSwipeBack();

    }

    private void init() {
        til_remind_id = (TextInputLayout) findViewById(R.id.til_remind_id);
        til_remind_content = (TextInputLayout) findViewById(R.id.til_remind_content);
        tiet_remind_id = (TextInputEditText) findViewById(R.id.tiet_remind_id);
        rl_remind_query = (RelativeLayout) findViewById(R.id.rl_remind_query);
        ll_remind_content = (LinearLayout) findViewById(R.id.ll_remind_content);
        ll_remind_ok = (LinearLayout) findViewById(R.id.ll_remind_ok);
        et_remind_date = (EditText) findViewById(R.id.et_remind_date);
        et_remind_content = (EditText) findViewById(R.id.et_remind_content);
        btn_remind_save = (Button) findViewById(R.id.btn_remind_save);
        et_remind_date.setCursorVisible(false);
        et_remind_date.setFocusable(false);
        et_remind_date.setFocusableInTouchMode(false);
        et_remind_date.setOnClickListener(this);
        btn_remind_save.setOnClickListener(this);
        rl_remind_query.setOnClickListener(this);


        final AVUser user = AVUser.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_remind_date:
                date();
                break;
            case R.id.rl_remind_query:
                new findAccout().execute();
                break;
            case R.id.btn_remind_save:
                Conditionaljudgement();
                break;
        }
    }

    private void Conditionaljudgement() {
        remindId = tiet_remind_id.getText().toString();
        remindContent = et_remind_content.getText().toString();
        remindDate = et_remind_date.getText().toString();
        if (remindId.equals("")) {
            Toast.makeText(this, "账户不能为空", Toast.LENGTH_SHORT).show();
        } else if (remindContent.equals("")) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
        } else if (remindContent.length() > 30) {
            Toast.makeText(this, "内容字数超过限定长度", Toast.LENGTH_SHORT).show();
        } else if (remindDate.equals("所选日期不能小于今天")) {
            Toast.makeText(this, "所选日期不能小于今天", Toast.LENGTH_SHORT).show();
        } else {
            addRemind();
        }

    }

    private void addRemind() {
        AVUser user = AVUser.getCurrentUser();
        remind = new AVObject("Remind");
        remind.put("reminders", remindId);
        remind.put("content", remindContent);
        remind.put("date", StrToDate(remindDate));
        remind.put("addedpersonaccount", user.getUsername());
        remind.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(RemindOthersActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RemindOthersActivity.this, "Fail" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void date() {
        Calendar c = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(RemindOthersActivity.this,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        String data = year + "/" + month + "/" + dayOfMonth;
                        if (getTimeCompareSize(StrToDate(data)) == 1) {
                            //   int month = monthOfYear + 1;
                            et_remind_date.setText(year + "/" + month + "/" + dayOfMonth);
                        } else {
                            et_remind_date.setText("所选日期不能小于今天");
                        }
                    }
                }
                // 设置初始日期
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH)).show();
    }


}
