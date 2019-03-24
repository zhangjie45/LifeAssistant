package com.example.pc.lifeassistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.DialogEditPW;

/**
 * Created by pc on 2019/3/24.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_userinfo_name;
    private TextView tv_userinfo_mail;
    private TextView tv_userinfo_phone;
    private LinearLayout ll_userinfo_changpw;
    private DialogEditPW.Builder builder;
    private DialogEditPW dialogEditPW;

    private TextInputLayout til_dialog_new_password;
    private TextInputLayout til_dialog_new_password_again;
    private TextInputEditText tiet_dialog_new_password;
    private TextInputEditText tiet_dialog_new_password_again;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initToolbar(R.id.tl_, R.id.toolbar_title, "账户信息", true);
        init();
        ShowInfo();
        builder = new DialogEditPW.Builder(UserInfoActivity.this);
    }

    private void init() {
        tv_userinfo_name = (TextView) findViewById(R.id.tv_userinfo_name);
        tv_userinfo_mail = (TextView) findViewById(R.id.tv_userinfo_mail);
        tv_userinfo_phone = (TextView) findViewById(R.id.tv_userinfo_phone);
        ll_userinfo_changpw = (LinearLayout) findViewById(R.id.ll_userinfo_changpw);

        ll_userinfo_changpw.setOnClickListener(this);
    }

    private void ShowInfo() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            tv_userinfo_name.setText(user.getUsername());
            String userPhoneNum = user.getMobilePhoneNumber();
            if (userPhoneNum == null) {
                userPhoneNum = "还未添加手机号";
            }
            tv_userinfo_phone.setText(userPhoneNum);
            tv_userinfo_mail.setText(user.getEmail());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_userinfo_changpw:
                editPwDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogEditPW.dismiss();
                    }

                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tiet_dialog_new_password = dialogEditPW.findViewById(R.id.tiet_dialog_new_password);
                        tiet_dialog_new_password_again = dialogEditPW.findViewById(R.id.tiet_dialog_new_password_again);
                        if (!tiet_dialog_new_password.getText().toString().equals(tiet_dialog_new_password_again.getText().toString())) {
                            Toast.makeText(UserInfoActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                        } else {
                            editPw(tiet_dialog_new_password_again.getText().toString());
                            dialogEditPW.dismiss();
                            AVUser.logOut();
//                            Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
//                           startActivity(intent);
                            finish();
                        }

                    }
                });
                break;
        }

    }

    private void editPw(String newPW) {
        AVUser.getCurrentUser().put("password", newPW);
        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfoActivity.this, "修改失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void editPwDialog(View.OnClickListener cancel, View.OnClickListener ok) {
        dialogEditPW = builder.setMessage()
                .setCancelButton(cancel)
                .setOkButton(ok)
                .ButtonDialog();
        dialogEditPW.show();
    }

}
