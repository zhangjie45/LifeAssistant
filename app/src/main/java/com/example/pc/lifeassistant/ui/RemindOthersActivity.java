package com.example.pc.lifeassistant.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

import java.util.Calendar;

/**
 * Created by pc on 2018/11/5.
 */

public class RemindOthersActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout til_remind_id;
    private TextInputLayout til_remind_content;
    private TextInputEditText tiet_remind_id;
    private EditText et_remind_date;
    private EditText et_remind_content;


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
        et_remind_date = (EditText) findViewById(R.id.et_remind_date);
        et_remind_content = (EditText) findViewById(R.id.et_remind_content);
        et_remind_date.setCursorVisible(false);
        et_remind_date.setFocusable(false);
        et_remind_date.setFocusableInTouchMode(false);
        et_remind_date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_remind_date:
                date();
                break;

        }
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
                        if (getTimeCompareSize(year, monthOfYear, dayOfMonth) == 1) {
                            int month = monthOfYear + 1;
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
