package com.example.pc.lifeassistant.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.bean.ApkVersionInfo;
import com.example.pc.lifeassistant.ui.LoginActivity;
import com.example.pc.lifeassistant.ui.OpinionActivity;
import com.example.pc.lifeassistant.ui.UserInfoActivity;
import com.example.pc.lifeassistant.util.APKVersionCodeUtils;
import com.example.pc.lifeassistant.util.AVService;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.DownloadUtil;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


/**
 * Created by pc on 2018/10/30.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private String name;
    private TextView tv_setting_opinion;
    private TextView tv_setting_name;
    private TextView tv_setting_phone;
    private TextView tv_setting_email;
    private TextView tv_setting_version;
    private Button btn_setting_logOut;
    private LinearLayout ll_setting_opinion;
    private LinearLayout ll_setting_userinfo;
    private LinearLayout ll_setting_cache;
    private LinearLayout ll_setting_version;

    private ProgressDialog progressDialog;
    private volatile List<ApkVersionInfo> versionInfo;
    private volatile List<AVUser> users;
    private String APK_NAME = "lifeassistant";
    AVUser user = AVUser.getCurrentUser();

    public class showInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                versionInfo = AVService.queryApkVersion();

                users = AVService.queryUserInfo(user.getObjectId());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        protected void onPostExecute(Void aVoid) {
            tv_setting_name.setText(users.get(0).getUsername());
            String userPhoneNum = users.get(0).getMobilePhoneNumber();
            if (null == userPhoneNum ||userPhoneNum.equals("")) {
                userPhoneNum = "还未添加手机号";
            }
            tv_setting_phone.setText(userPhoneNum);
            tv_setting_email.setText(users.get(0).getEmail());
            if (!((APKVersionCodeUtils.getVersionCode(getActivity()) + "").equals(versionInfo.get(0).getVersion()))) {
                tv_setting_version.setText("有新版本");
                tv_setting_version.setTextColor(getResources().getColor(R.color.event_agency));
            } else {
                tv_setting_version.setText(APKVersionCodeUtils.getVerName(getActivity()));
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    progressDialog.cancel();
                    installApkO(getActivity(), Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + APK_NAME + ".apk");
                    break;
                case 11:
                    progressDialog.setProgress((Integer) msg.obj);
                    break;
                case 1:
                    ToastUtil("下载异常");
                    break;
            }
        }
    };


    public static final SettingFragment newInstance(String name) {
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("name");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        tv_setting_opinion = view.findViewById(R.id.tv_setting_opinion);
        tv_setting_name = view.findViewById(R.id.tv_setting_name);
        tv_setting_phone = view.findViewById(R.id.tv_setting_phone);
        tv_setting_email = view.findViewById(R.id.tv_setting_email);
        tv_setting_version = view.findViewById(R.id.tv_setting_version);
        btn_setting_logOut = view.findViewById(R.id.btn_setting_logOut);
        ll_setting_opinion = view.findViewById(R.id.ll_setting_opinion);
        ll_setting_userinfo = view.findViewById(R.id.ll_setting_userinfo);
        ll_setting_cache = view.findViewById(R.id.ll_setting_cache);
        ll_setting_version = view.findViewById(R.id.ll_setting_version);
        //  ToastUtil(name);
        ll_setting_opinion.setOnClickListener(this);
        btn_setting_logOut.setOnClickListener(this);
        ll_setting_userinfo.setOnClickListener(this);
        ll_setting_cache.setOnClickListener(this);
        ll_setting_version.setOnClickListener(this);
        setHasOptionsMenu(true);
        //用户信息展示
        ShowInfo();
        new showInfo().execute();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new showInfo().execute();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setting_opinion:
                fragmentToActivity(OpinionActivity.class);
                break;
            case R.id.btn_setting_logOut:
                AVUser.logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
            case R.id.ll_setting_userinfo:
                fragmentToActivity(UserInfoActivity.class);
                break;
            case R.id.ll_setting_version:
                if (tv_setting_version.getText().equals(("有新版本"))) {
                    permission();
                } else {
                    ToastUtil("当前为最新版本");
                }
                break;
            case R.id.ll_setting_cache:
                break;
        }
    }


    private void ShowInfo() {
        if (user != null) {
            tv_setting_name.setText(user.getUsername());
            String userPhoneNum = user.getMobilePhoneNumber();
            if (null == userPhoneNum||userPhoneNum.equals("")) {
                userPhoneNum = "还未添加手机号";
            }
            tv_setting_phone.setText(userPhoneNum);
            tv_setting_email.setText(user.getEmail());
        }
        String versionCode = APKVersionCodeUtils.getVersionCode(getActivity()) + "";
        String versionName = APKVersionCodeUtils.getVerName(getActivity());
        if (versionCode != null && versionName != null) {
            tv_setting_version.setText(versionName);
        }
    }

    //开启权限
    private void permission() {
        if (Build.VERSION.SDK_INT >= 24) {//如果是6.0以上的
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        new Thread(downloadApk).start();
        progressDialog = new ProgressDialog(getActivity());    //进度条，在下载的时候实时更新进度，提高用户友好度
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("请稍候...");
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    //执行更新软件操作
    Runnable downloadApk = new Runnable() {

        @Override
        public void run() {
            String url = versionInfo.get(0).getUrl();
            DownloadUtil.get().download(url, String.valueOf(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)), APK_NAME + ".apk", new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(File file) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    //下载完成进行相关逻辑操作
                    Message msg = mHandler.obtainMessage();
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onDownloading(int progress) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = 11;
                    msg.obj = progress;
                    // Log.i("进度：", progress + "");

                    mHandler.sendMessage(msg);

                }

                @Override
                public void onDownloadFailed(Exception e) {
                    //下载异常进行相关提示操作
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.obj = e;
                    mHandler.sendMessage(msg);

                }
            });
        }
    };

    /**
     * 兼容8.0安装位置来源的权限
     */
    private void installApkO(Context context, String downloadApkPath) {
        if (context == null || TextUtils.isEmpty(downloadApkPath)) {
            return;
        }
        File apkFile = new File(downloadApkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            Uri contentUri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                contentUri = FileProvider.getUriForFile(context, "com.example.pc.lifeassistant.fileprovider", apkFile);
            } else {
                contentUri = Uri.fromFile(apkFile);
            }
            Runtime.getRuntime().exec("chmod 777 " + apkFile.getCanonicalPath());
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            //查询所有符合 intent 跳转目标应用类型的应用
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            //然后全部授权
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            context.startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            new showInfo().execute();
        }
    }
}
