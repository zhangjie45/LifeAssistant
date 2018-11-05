package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

/**
 * Created by pc on 2018/11/4.
 * 添加日程管理界面
 */

public class AddDateActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);
        initToolbar(R.id.tl_, R.id.toolbar_title, "日程添加");
        initSwipeBack();
    }
}
