package com.example.pc.lifeassistant.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.adapter.RemindAdapter;
import com.example.pc.lifeassistant.bean.ApkVersionInfo;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.bean.RemindInfo;
import com.example.pc.lifeassistant.bean.WeatherData;
import com.example.pc.lifeassistant.ui.LoginActivity;
import com.example.pc.lifeassistant.ui.MainActivity;
import com.example.pc.lifeassistant.ui.NoteActivity;
import com.example.pc.lifeassistant.util.APKVersionCodeUtils;
import com.example.pc.lifeassistant.util.AVService;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.DialogNewVersion;
import com.example.pc.lifeassistant.util.DownloadUtil;
import com.example.pc.lifeassistant.util.HttpConstant;
import com.example.pc.lifeassistant.util.HttpEntity;
import com.example.pc.lifeassistant.util.Utils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


/**
 * Created by pc on 2018/10/29.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_home_date;
    private TextView tv_home_tem;
    private TextView tv_home_wea;
    private TextView tv_home_event_num;
    private TextView tv_home_expenditure;
    private String name;
    private String str_location;
    private LinearLayout ll_home_remind;
    private RecyclerView rl_remind;
    private FloatingActionButton btn_note;
    String city;
    int num;
    AVUser user = AVUser.getCurrentUser();
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private volatile List<CapitalInfo> capitel_expenditure;
    private volatile List<RemindInfo> remind;
    private volatile List<ApkVersionInfo> versionInfo;
    private RemindAdapter adapter;
    private DialogNewVersion.Builder new_version_builder;
    private DialogNewVersion new_version_dialog;
    private String APK_NAME = "lifeassistant";
    private ProgressDialog progressDialog;

    @SuppressLint({"HandlerLeak", "StaticFieldLeak"})
    private class showNum extends AsyncTask<Void, Void, Void> {

        //进入异步任务后被立即执行，一般操作UI提示用户。
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (user.getObjectId() == null) {
                    fragmentToActivity(LoginActivity.class);
                }
                if (null != user.get("reminders")) {
                    remind = AVService.queryRemind(user.getUsername(), user.get("reminders").toString(), Utils.now_Day());
                }
                num = AVService.queryEventNum(user.getObjectId(), Utils.now_Day());
                capitel_expenditure = AVService.expenditureCapital(user.getObjectId(), Utils.firstDay(), Utils.lastDay());
                versionInfo = AVService.queryApkVersion();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            tv_home_event_num.setText(num + "");
            tv_home_expenditure.setText(Utils.Count(capitel_expenditure) + "");
            if (remind != null && remind.size() != 0) {
                adapter = new RemindAdapter(getActivity(), remind);
                rl_remind.setLayoutManager(new LinearLayoutManager(getActivity()));
                rl_remind.setAdapter(adapter);
                ll_home_remind.setVisibility(View.VISIBLE);
                rl_remind.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setOnClick(new RemindAdapter.ClickInterface() {
                            @Override
                            public void onClick(View view, int position) {
                                AVQuery.doCloudQueryInBackground("delete from Remind where objectId='" + remind.get(position).getObjectId() + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                                    @Override
                                    public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                        // 如果 e 为空，说明保存成功
                                        if (e == null) {
                                            ToastUtil("删除成功");
                                            new showNum().execute();
                                        } else {
                                            ToastUtil("删除失败" + e.getMessage());
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            } else {
                ll_home_remind.setVisibility(View.GONE);
            }
            //更新新版本
            if (!((APKVersionCodeUtils.getVersionCode(getActivity()) + "").equals(versionInfo.get(0).getVersion()))) {
                getVersion(versionInfo.get(0).getVersioncontent());
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

    public static final HomeFragment newInstance(String name) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("name");
        }
        new_version_builder = new DialogNewVersion.Builder(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_note = getActivity().findViewById(R.id.btn_note);
        tv_home_date = getActivity().findViewById(R.id.tv_home_date);
        tv_home_tem = getActivity().findViewById(R.id.tv_home_tem);
        tv_home_wea = getActivity().findViewById(R.id.tv_home_wea);
        tv_home_event_num = getActivity().findViewById(R.id.tv_home_event_num);
        tv_home_expenditure = getActivity().findViewById(R.id.tv_home_expenditure);
        ll_home_remind = getActivity().findViewById(R.id.ll_home_remind);
        rl_remind = getActivity().findViewById(R.id.rl_home_remind);
        btn_note.setOnClickListener(this);
        mLocationClient = new LocationClient(getActivity());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        initLocation();
        mLocationClient.start();//调用LocationClient的start()方法，便可发起定位请求
        setHasOptionsMenu(true);
        //启动异步操作
        new showNum().execute();
        HttpConstant.URL += city;
        show_day();
        getDatasync();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getDatasync() {
        HttpEntity httpEntity = new HttpEntity(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ERROR", "Network Error!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                final String result = responseBody.string();
                WeatherData data = new Gson().fromJson(result, WeatherData.class);
                final String tem = data.getData()[0].getTem();
                final String wea = data.getData()[0].getWea();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_home_wea.setText(wea);
                        tv_home_tem.setText(tem);
                    }
                });
            }
        });
        httpEntity.request();

    }

    public void show_day() {
        tv_home_date.setText(Utils.nowDay());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_note:
                fragmentToActivity(NoteActivity.class);
                break;
        }
    }

    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        /**可选，设置定位模式，默认高精度LocationMode.Hight_Accuracy：高精度；
         * LocationMode. Battery_Saving：低功耗；LocationMode. Device_Sensors：仅使用设备；*/

        option.setCoorType("gcj02gcj02");
        /**可选，设置返回经纬度坐标类型，默认gcj02gcj02：国测局坐标；bd09ll：百度经纬度坐标；bd09：百度墨卡托坐标；
         海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标*/

        option.setScanSpan(0);
        /**可选，设置发起定位请求的间隔，int类型，单位ms如果设置为0，则代表单次定位，即仅定位一次，默认为0如果设置非0，需设置1000ms以上才有效*/

        option.setOpenGps(true);
        /**可选，设置是否使用gps，默认false使用高精度和仅用设备两种定位模式的，参数必须设置为true*/

        option.setLocationNotify(true);
        /**可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false*/

        option.setIgnoreKillProcess(false);
        /**定位SDK内部是一个service，并放到了独立进程。设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)*/

        option.SetIgnoreCacheException(false);
        /**可选，设置是否收集Crash信息，默认收集，即参数为false*/
        option.setIsNeedAltitude(true);/**设置海拔高度*/

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        /**可选，7.2版本新增能力如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位*/

        option.setEnableSimulateGps(false);
        /**可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false*/

        option.setIsNeedAddress(true);
        /**可选，设置是否需要地址信息，默认不需要*/

        mLocationClient.setLocOption(option);
        /**mLocationClient为第二步初始化过的LocationClient对象需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用*/
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            city = location.getCity();    //获取城市
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            int errorCode = location.getLocType();
            //errorCode = 161  定位成功
            Log.i("errorCode编码：", errorCode + "");
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

        }

    }


    private void getVersion(String versionContent) {
        showNewVersionDialog(versionContent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_version_dialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  ToastUtil("下载新应用");
                new_version_dialog.dismiss();
                permission();
            }
        });
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

    //兼容8.0安装位置来源的权限
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

    //提示有新版本发布
    private void showNewVersionDialog(String content, View.OnClickListener onCloseClickListener, View.OnClickListener onOkClickListener) {
        new_version_dialog = new_version_builder.setMessage(content)
                .closeChangeButton(onCloseClickListener)
                .okDeleteButton(onOkClickListener)
                .DialogNerVersionDialog();
        new_version_dialog.show();
    }


}




