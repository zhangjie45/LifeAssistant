package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.SharedPreferencesHelper;

/**
 * Created by pc on 2018/11/5.
 */

public class NoteActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_note;
    private TextView tv_tb_add;
    private String et_content;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initToolbar(R.id.tl_, R.id.toolbar_title, "随手记", true);
        init();
        ObtainContent();
        initSwipeBack();
        Clear();
    }

    private void Clear() {
        tv_tb_add.setVisibility(View.VISIBLE);
        tv_tb_add.setText(R.string.clear);
        tv_tb_add.setOnClickListener(this);
    }

    private void ObtainContent() {
        String str = sharedPreferencesHelper.getSharedPreference("note_key", "").toString();
        if (str.equals("")) {
            et_note.setHint(R.string.note_null);
        } else {
            et_note.setText(str);

        }

    }

    private void init() {
        et_note = (EditText) findViewById(R.id.et_note);
        sharedPreferencesHelper = new SharedPreferencesHelper(
                NoteActivity.this, "note");
        tv_tb_add = (TextView) findViewById(R.id.tv_tb_add);


    }

    @Override
    public void onClick(View v) {
        et_note.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        et_content = et_note.getText().toString();
        sharedPreferencesHelper.put("note_key", et_content);

    }
}
