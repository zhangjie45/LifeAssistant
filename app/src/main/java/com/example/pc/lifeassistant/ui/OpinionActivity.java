package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.Utils;

/**
 * Created by pc on 2019/1/5.
 */

public class OpinionActivity extends BaseActivity implements View.OnClickListener {
    private Button opinion_btn_ok;
    private EditText et_opinion_contactInformation;
    private EditText et_opinion_content;
    AVUser user = AVUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        initToolbar(R.id.tl_, R.id.toolbar_title, "意见反馈", true);
        init();
    }

    private void init() {
        opinion_btn_ok = (Button) findViewById(R.id.opinion_btn_ok);
        et_opinion_contactInformation = (EditText) findViewById(R.id.et_opinion_contactInformation);
        et_opinion_content = (EditText) findViewById(R.id.et_opinion_content);
        opinion_btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opinion_btn_ok:
                Conditionaljudgement();
                break;
        }
    }

    private void Conditionaljudgement() {
        if (et_opinion_contactInformation.getText().toString().equals("")) {
            Toast.makeText(this, "联系方式不能为空", Toast.LENGTH_SHORT).show();
        } else if (et_opinion_content.getText().toString().equals("")) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            addOpinion();
        }
    }

    private void addOpinion() {
        final AVObject feedBack = new AVObject("Feedback");
        feedBack.put("userId", user.getObjectId());
        feedBack.put("contactInformation", et_opinion_contactInformation.getText().toString());
        feedBack.put("content", et_opinion_content.getText().toString());
        feedBack.put("uploadTime", Utils.nowDay());
        feedBack.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(OpinionActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(OpinionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                    if (Utils.isNetworkConnected(OpinionActivity.this)) {
                        Toast.makeText(OpinionActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
