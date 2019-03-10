package com.example.pc.lifeassistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.SharedPreferencesHelper;

/**
 * Created by pc on 2018/12/16.
 */

public class AddCapitalRemakesDialog extends BaseActivity {
    private EditText dialog_content;
    private TextView dialog_date;
    private Button dialog_btn_ok;

    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_capital_remakes);
        initToolbar(R.id.tl_, R.id.toolbar_title, "备注", true);
        initSwipeBack();
        init();
        ObtainContent();
    }

    public void init() {
        sharedPreferencesHelper = new SharedPreferencesHelper(
                AddCapitalRemakesDialog.this, "CapitalRemakes");
        dialog_content = (EditText) findViewById(R.id.dialog_content);
        dialog_date = (TextView) findViewById(R.id.dialog_date);
        dialog_btn_ok = (Button) findViewById(R.id.dialog_btn_ok);
        dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", dialog_content.getText().toString().trim());
                setResult(3, intent);
                //关闭当前activity
                finish();
            }
        });

    }

    private void ObtainContent() {
        String str = sharedPreferencesHelper.getSharedPreference("capital_remakes_key", "").toString();
        if (str.equals("") || str.equals("暂无备注")) {
            dialog_content.setHint(R.string.capital_remakes_null);
        } else {
            dialog_content.setText(str);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferencesHelper.put("capital_remakes_key", dialog_content.getText().toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
