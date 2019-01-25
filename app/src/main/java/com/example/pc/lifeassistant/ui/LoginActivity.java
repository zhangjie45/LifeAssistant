package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;

/**
 * Created by pc on 2019/1/1.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button login_btn_ok;
    private TextView tv_login_register;
    private TextView tv_login_note;
    private TextInputEditText tiet_login_user;
    private TextInputEditText tiet_login_password;
    boolean login_flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    testLeanCloud();
        setContentView(R.layout.activity_login);
        init();
        PersistenceLogin();
        closeSwipeBack();
    }

    private void init() {
        login_btn_ok = (Button) findViewById(R.id.login_btn_ok);
        tv_login_register = (TextView) findViewById(R.id.tv_login_register);
        tv_login_note = (TextView) findViewById(R.id.tv_login_note);
        tiet_login_user = (TextInputEditText) findViewById(R.id.tiet_login_user);
        tiet_login_password = (TextInputEditText) findViewById(R.id.tiet_login_password);
        login_btn_ok.setOnClickListener(this);
        tv_login_register.setOnClickListener(this);
        tv_login_note.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_ok:
                String user = tiet_login_user.getText().toString();
                String pw = tiet_login_password.getText().toString();
                if ("".equals(user) || "".equals(pw)) {
                    Toast.makeText(this, "请检查格式", Toast.LENGTH_SHORT).show();
                } else {
                    AVUser.logInInBackground(user, pw, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            skipAnotherActivity(LoginActivity.this, MainActivity.class);
                        }
                    });
                }
                break;
            case R.id.tv_login_register:
                skipAnotherActivityNoFinish(this, RegisterActivity.class);
                break;
            case R.id.tv_login_note:
                skipAnotherActivityNoFinish(this, NoteActivity.class);
                break;
        }
    }

    public void PersistenceLogin() {
        //获取缓存中的用户信息
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            //如果不为空则直接进入首页面
            skipAnotherActivity(this, MainActivity.class);
            Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_SHORT).show();
        }
    }


//    public void testLeanCloud() {
//        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words", "Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
//                    // Log.d("saved", "success!");
//                } else {
//                    Toast.makeText(LoginActivity.this, "error" + e.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}
