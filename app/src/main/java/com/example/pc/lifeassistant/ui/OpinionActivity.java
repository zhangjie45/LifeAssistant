package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.widget.Button;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

/**
 * Created by pc on 2019/1/5.
 */

public class OpinionActivity extends BaseActivity {
    private Button opinion_btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        initToolbar(R.id.tl_, R.id.toolbar_title, "意见反馈", true);
        init();
    }

    private void init() {
        opinion_btn_ok = (Button) findViewById(R.id.opinion_btn_ok);
    }
}
