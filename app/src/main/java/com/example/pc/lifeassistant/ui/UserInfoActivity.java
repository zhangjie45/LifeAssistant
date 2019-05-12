package com.example.pc.lifeassistant.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.DialogEditPW;

import org.w3c.dom.Text;

/**
 * Created by pc on 2019/3/24.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_userinfo_name;
    private EditText et_userinfo_mail;
    private EditText et_userinfo_phone;
    private EditText et_userinfo_remind_account;
    private TextView tv_phone_verification_userinfo;
    private Button btn_userinfo_ok;
    private LinearLayout ll_userinfo_changpw;
    private DialogEditPW.Builder builder;
    private DialogEditPW dialogEditPW;

    private TextInputLayout til_dialog_new_password;
    private TextInputLayout til_dialog_new_password_again;
    private TextInputEditText tiet_dialog_new_password;
    private TextInputEditText tiet_dialog_new_password_again;
    AVUser user = AVUser.getCurrentUser();

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
        et_userinfo_name = (EditText) findViewById(R.id.et_userinfo_name);
        et_userinfo_mail = (EditText) findViewById(R.id.et_userinfo_mail);
        et_userinfo_phone = (EditText) findViewById(R.id.et_userinfo_phone);
        tv_phone_verification_userinfo = (TextView) findViewById(R.id.tv_phone_verification_userinfo);
        btn_userinfo_ok = (Button) findViewById(R.id.btn_userinfo_ok);
        et_userinfo_remind_account = (EditText) findViewById(R.id.et_userinfo_remind_account);
        ll_userinfo_changpw = (LinearLayout) findViewById(R.id.ll_userinfo_changpw);
        ll_userinfo_changpw.setOnClickListener(this);
        btn_userinfo_ok.setOnClickListener(this);
        tv_phone_verification_userinfo.setOnClickListener(this);
    }

    private void ShowInfo() {
        final AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            user.fetchInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null) {
                        et_userinfo_name.setText(user.getUsername());
                        String userPhoneNum = user.getMobilePhoneNumber();
                        boolean phoneVerification = (boolean) user.get("mobilePhoneVerified");
                        if (userPhoneNum == null) {
                            userPhoneNum = "";
                            tv_phone_verification_userinfo.setVisibility(View.GONE);
                        }else{
                            if (!phoneVerification) {
                                tv_phone_verification_userinfo.setVisibility(View.VISIBLE);
                                //sendVerificationMsg(userPhoneNum);
                            }
                        }
                        if (null == user.get("reminders")) {
                            et_userinfo_remind_account.setHint("请输入可以提醒您的账号");
                        } else {
                            et_userinfo_remind_account.setText(user.get("reminders").toString());
                        }

                        et_userinfo_phone.setText(userPhoneNum);
                        et_userinfo_mail.setText(user.getEmail());
                    } else {
                        Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
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

                        } else if (tiet_dialog_new_password.getText().toString().equals("") || tiet_dialog_new_password_again.getText().toString().equals("")) {
                            Toast.makeText(UserInfoActivity.this, "密码格式有误", Toast.LENGTH_SHORT).show();
                        } else {
                            editPw(tiet_dialog_new_password_again.getText().toString());
                            dialogEditPW.dismiss();
                            AVUser.logOut();
                            finish();
                        }
                    }
                });
                break;
            case R.id.btn_userinfo_ok:

                AVUser.getCurrentUser().put("reminders", et_userinfo_remind_account.getText());
                AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            user.fetchInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    if (e == null) {
                                        String reminders = avObject.getString("reminders");
                                        Toast.makeText(UserInfoActivity.this, "账号：" + reminders + "有权限对您进行提醒！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(UserInfoActivity.this, "修改失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.tv_phone_verification_userinfo:
                Toast.makeText(this, "绑定手机号服务暂未开通", Toast.LENGTH_SHORT).show();
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

    public void sendVerificationMsg(final String mobilePhone) {
        AVUser.requestMobilePhoneVerifyInBackground(mobilePhone, new RequestMobileCodeCallback() {

            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(UserInfoActivity.this, "验证短信发送成功，号码为：" + mobilePhone, Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("发送失败：", e.getMessage());
                    Toast.makeText(UserInfoActivity.this, "验证短信发送失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (et_userinfo_name.getText().toString().equals("") || et_userinfo_mail.getText().toString().equals("")) {
            Toast.makeText(this, "信息不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        AVUser.getCurrentUser().put("username", et_userinfo_name.getText().toString());
        AVUser.getCurrentUser().put("email", et_userinfo_mail.getText().toString());
        AVUser.getCurrentUser().put("mobilePhoneNumber", et_userinfo_phone.getText().toString());
        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // Toast.makeText(UserInfoActivity.this, "修改个人信息成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
