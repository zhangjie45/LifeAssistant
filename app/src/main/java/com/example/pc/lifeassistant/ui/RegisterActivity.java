package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.RegexUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pc on 2019/1/5.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private Button register_btn_ok;
    private EditText tiet_register_user;
    private EditText tiet_register_email;
    private TextInputEditText tiet_register_password;
    private TextInputEditText tiet_register_passwordAgain;

    private TextInputLayout til_register_user;
    private TextInputLayout til_register_email;
    private TextInputLayout til_register_password;
    private TextInputLayout til_register_passwordAgain;


    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        tilFormat();

    }


    private void init() {
        register_btn_ok = (Button) findViewById(R.id.register_btn_ok);
        tiet_register_user = (EditText) findViewById(R.id.tiet_register_user);
        tiet_register_email = (EditText) findViewById(R.id.tiet_register_email);
        tiet_register_password = (TextInputEditText) findViewById(R.id.tiet_register_password);
        tiet_register_passwordAgain = (TextInputEditText) findViewById(R.id.tiet_register_passwordAgain);

        til_register_user = (TextInputLayout) findViewById(R.id.til_register_user);
        til_register_email = (TextInputLayout) findViewById(R.id.til_register_email);
        til_register_password = (TextInputLayout) findViewById(R.id.til_register_password);
        til_register_passwordAgain = (TextInputLayout) findViewById(R.id.til_register_passwordAgain);

        register_btn_ok.setOnClickListener(this);
    }

    private void tilFormat() {
        til_register_user.setHint("请输入用户名");
        til_register_email.setHint("请输入邮箱");
        til_register_password.setHint("请输入密码");
        til_register_passwordAgain.setHint("请再次输入密码");

        tiet_register_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!RegexUtils.validateEmail(tiet_register_email.getText().toString())) {
                    til_register_email.setError("邮箱格式错误");
                    til_register_email.setErrorEnabled(true);
                } else {
                    til_register_email.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tiet_register_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!RegexUtils.validatePassword(tiet_register_password.getText().toString())) {
                    til_register_password.setError("密码长度在6~12位之间");
                    til_register_password.setErrorEnabled(true);
                } else {
                    til_register_password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tiet_register_passwordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!RegexUtils.validatePasswordAgain(tiet_register_password.getText().toString(), tiet_register_passwordAgain.getText().toString())) {
                    til_register_passwordAgain.setError("两次密码不相同");
                    til_register_passwordAgain.setErrorEnabled(true);
                } else {
                    til_register_passwordAgain.setErrorEnabled(false);
                    password = tiet_register_password.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注册操作
            case R.id.register_btn_ok:
                if ("".equals(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    AVUser user = new AVUser();// 新建 AVUser 对象实例
                    user.setUsername(tiet_register_user.getText().toString());// 设置用户名
                    user.setPassword(password);// 设置密码
                    user.setEmail(tiet_register_email.getText().toString());// 设置邮箱
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                finish();
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            } else {
                                // 失败的原因可能有多种，常见的是用户名已经存在。
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("注册发生错误---->", e.getMessage());
                            }
                        }
                    });
                }

        }
    }


}
