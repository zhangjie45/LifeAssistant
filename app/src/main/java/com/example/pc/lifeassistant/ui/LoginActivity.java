package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

/**
 * Created by pc on 2019/1/1.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button login_btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        closeSwipeBack();
    }

    private void init() {
        login_btn_ok = (Button) findViewById(R.id.login_btn_ok);

        login_btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_ok:
                skipAnotherActivity(this, MainActivity.class);
        }
    }
}
