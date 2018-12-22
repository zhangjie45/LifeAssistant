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

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

import java.util.Calendar;

/**
 * Created by pc on 2018/11/4.
 * 添加日程管理界面
 */

public class AddDateActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout til_add_date_title;
    private TextInputEditText tiet_add_date_title;
    private EditText et_add_date;
    private Button btn_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);
        initToolbar(R.id.tl_, R.id.toolbar_title, "日程添加", true);
        init();
        TIETextChange(tiet_add_date_title, til_add_date_title, 5);

        initSwipeBack();

    }

    private void init() {
        til_add_date_title = (TextInputLayout) findViewById(R.id.til_add_date_title);
        tiet_add_date_title = (TextInputEditText) findViewById(R.id.tiet_add_date_title);
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

                break;
        }
    }

//    public void editTextChage() {
//        tiet_add_date_title.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() > 5) {
//                    til_add_date_title.setErrorEnabled(true);
//                    til_add_date_title.setError("字数超过5个");
//                } else {
//                    til_add_date_title.setErrorEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }

    public void date() {
        Calendar c = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(AddDateActivity.this,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (getTimeCompareSize(year, monthOfYear, dayOfMonth) == 1) {
                            int month = monthOfYear + 1;
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


}
