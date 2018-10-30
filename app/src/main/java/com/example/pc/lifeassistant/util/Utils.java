package com.example.pc.lifeassistant.util;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by pc on 2018/10/30.
 */

public class Utils extends AppCompatActivity {

    public Toolbar initToolbar(int id, int titleId, String titleString) {
        Toolbar toolbar = findViewById(id);
//        toolbar.setTitle("");
        TextView textView = (TextView) findViewById(titleId);
        textView.setText(titleString);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置toolbar左侧返回按钮隐藏
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }
}
