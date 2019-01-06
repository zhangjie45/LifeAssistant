package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

/**
 * Created by pc on 2019/1/5.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button register_btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

    }

    private void init() {
        register_btn_ok = (Button) findViewById(R.id.register_btn_ok);


        register_btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_ok:
                finish();
        }
    }
}
