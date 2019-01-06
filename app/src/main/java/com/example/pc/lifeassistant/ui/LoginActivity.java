package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

/**
 * Created by pc on 2019/1/1.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button login_btn_ok;
    private TextView tv_login_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        closeSwipeBack();
    }

    private void init() {
        login_btn_ok = (Button) findViewById(R.id.login_btn_ok);
        tv_login_register = (TextView) findViewById(R.id.tv_login_register);
        login_btn_ok.setOnClickListener(this);
        tv_login_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_ok:
                skipAnotherActivity(this, MainActivity.class);
                break;
            case R.id.tv_login_register:
                skipAnotherActivityNoFinish(this, RegisterActivity.class);
                break;
        }
    }
}
