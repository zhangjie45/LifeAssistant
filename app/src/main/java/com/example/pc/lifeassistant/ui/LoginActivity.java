package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.DialogForgetPw;
import com.example.pc.lifeassistant.util.RegexUtils;
import com.example.pc.lifeassistant.util.SharedPreferencesHelper;

/**
 * Created by pc on 2019/1/1.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button login_btn_ok;
    private TextView tv_login_register;
    private TextView tv_login_note;
    private TextView tv_forgetPw_login;
    private TextView et_email_forget;
    private TextInputEditText tiet_login_user;
    private TextInputEditText tiet_login_password;
    private AppCompatCheckBox checkbox_remember_login;
    boolean login_flag = false;
    private DialogForgetPw.Builder builder;
    private DialogForgetPw dialogForgetPw;
    private SharedPreferencesHelper sharedPreferencesHelper;
    final String REMEMBER_PWD_PREF = "rememberPwd";
    final String ACCOUNT_PREF = "account";
    final String PASSWORD_PREF = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    testLeanCloud();
        setContentView(R.layout.activity_login);
        init();
        PersistenceLogin();
        closeSwipeBack();
        builder = new DialogForgetPw.Builder(LoginActivity.this);
    }

    private void init() {
        sharedPreferencesHelper = new SharedPreferencesHelper(LoginActivity.this, "AccountInfo");
        login_btn_ok = (Button) findViewById(R.id.login_btn_ok);
        tv_login_register = (TextView) findViewById(R.id.tv_login_register);
        tv_login_note = (TextView) findViewById(R.id.tv_login_note);
        tv_forgetPw_login = (TextView) findViewById(R.id.tv_forgetPw_login);
        tiet_login_user = (TextInputEditText) findViewById(R.id.tiet_login_user);
        tiet_login_password = (TextInputEditText) findViewById(R.id.tiet_login_password);
        checkbox_remember_login = (AppCompatCheckBox) findViewById(R.id.checkbox_remember_login);
        login_btn_ok.setOnClickListener(this);
        tv_login_register.setOnClickListener(this);
        tv_login_note.setOnClickListener(this);
        tv_forgetPw_login.setOnClickListener(this);
        FailAccount();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_ok:
                final String user = tiet_login_user.getText().toString();
                final String pw = tiet_login_password.getText().toString();
                if ("".equals(user) || "".equals(pw)) {
                    Toast.makeText(this, "请检查格式", Toast.LENGTH_SHORT).show();
                } else {
                    AVUser.logInInBackground(user, pw, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e != null) {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (checkbox_remember_login.isChecked()) {
                                    cacheAccount(user, pw);
                                } else {
                                    sharedPreferencesHelper.clear();
                                }
                                skipAnotherActivity(LoginActivity.this, MainActivity.class);
                            }

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
            case R.id.tv_forgetPw_login:
                forgetPwDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogForgetPw.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_email_forget = dialogForgetPw.findViewById(R.id.et_email_forget);
                        final String email = et_email_forget.getText().toString();
                        if (email.equals("")) {
                            Toast.makeText(LoginActivity.this, "请输入注册邮箱！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!RegexUtils.validateEmail(email)) {
                            Toast.makeText(LoginActivity.this, "邮箱格式有误！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        AVUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Toast.makeText(LoginActivity.this, " 已向注册邮箱：" + email + "m 发送重置密码链接，注意查收！", Toast.LENGTH_SHORT).show();
                                    dialogForgetPw.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "发送失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                });
                break;
        }
    }

    public void PersistenceLogin() {
        //获取缓存中的用户信息
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            currentUser.fetchInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null) {
                        Toast.makeText(LoginActivity.this, "更新账号信息完成", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //如果不为空则直接进入首页面
            skipAnotherActivity(this, MainActivity.class);
        }
    }

    public void forgetPwDialog(View.OnClickListener cancel, View.OnClickListener ok) {
        dialogForgetPw = builder.setMessage().setCancelButton(cancel).setOkButton(ok).ButtonDialog();
        dialogForgetPw.show();
    }

    public void FailAccount() {
        Boolean isRemember = (Boolean) sharedPreferencesHelper.getSharedPreference(REMEMBER_PWD_PREF, false);
        if (isRemember) {
            tiet_login_user.setText((String) sharedPreferencesHelper.getSharedPreference(ACCOUNT_PREF, ""));
            tiet_login_password.setText((String) sharedPreferencesHelper.getSharedPreference(PASSWORD_PREF, ""));
            checkbox_remember_login.setChecked(true);
        }
    }

    public void cacheAccount(String username, String password) {
        sharedPreferencesHelper.put(REMEMBER_PWD_PREF, true);
        sharedPreferencesHelper.put(ACCOUNT_PREF, username);
        sharedPreferencesHelper.put(PASSWORD_PREF, password);

    }
}
