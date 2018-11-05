package com.example.pc.lifeassistant.ui;

import android.os.Bundle;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

/**
 * Created by pc on 2018/11/5.
 */

public class RemindOthersActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_others);
        initToolbar(R.id.tl_, R.id.toolbar_title, "提醒一下TA", true);
        initSwipeBack();
    }
}
