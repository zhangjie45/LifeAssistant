package com.example.pc.lifeassistant.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.bean.RemindInfo;
import com.example.pc.lifeassistant.bean.WeatherData;
import com.example.pc.lifeassistant.ui.LoginActivity;
import com.example.pc.lifeassistant.ui.NoteActivity;
import com.example.pc.lifeassistant.util.AVService;
import com.example.pc.lifeassistant.util.BaseFragment;
import com.example.pc.lifeassistant.util.HttpConstant;
import com.example.pc.lifeassistant.util.HttpEntity;
import com.example.pc.lifeassistant.util.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


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

    private FloatingActionButton btn_note;
    public final int MSG_DOWN_FAIL = 1;
    public final int MSG_DOWN_SUCCESS = 2;
    public final int MSG_DOWN_START = 3;
    String city;
    int num;
    AVUser user = AVUser.getCurrentUser();
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private volatile List<CapitalInfo> capitel_expenditure;
    private volatile List<RemindInfo> remind;

    @SuppressLint({"HandlerLeak", "StaticFieldLeak"})
    private class showNum extends AsyncTask<Void, Void, Void> {

        //进入异步任务后被立即执行，一般操作UI提示用户。
        @Override
        protected void onPreExecute() {
            //        ToastUtil("正在获取数据");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (user.getObjectId() == null) {
                    fragmentToActivity(LoginActivity.class);
                }
                num = AVService.queryEventNum(user.getObjectId(), Utils.now_Day());
                capitel_expenditure = AVService.expenditureCapital(user.getObjectId(), Utils.firstDay(), Utils.lastDay());
                remind = AVService.queryRemind(user.getUsername(), "Tom", Utils.now_Day());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            tv_home_event_num.setText(num + "");
            tv_home_expenditure.setText(Utils.Count(capitel_expenditure) + "");
            for (int i = 0; i < remind.size(); i++) {
                //  Log.i("remind内容："+ remind.get(i).getContent());
            }
            // Log.i("remind内容："+remind.get[]);
            ToastUtil(remind.size() + "");
        }
    }

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
        btn_note.setOnClickListener(this);
        mLocationClient = new LocationClient(getActivity());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        initLocation();
        mLocationClient.start();//调用LocationClient的start()方法，便可发起定位请求
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
                Log.i("RESPONSE", "Network Response!");
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
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
////                    Message msg = new Message();
////                    msg.what = MSG_DOWN_SUCCESS;
////                    msg.obj = str_location;
////                    handler_location.sendMessage(msg);
////                    //初始化
//                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
//                    Request request = new Request.Builder()
//                            .url("https://www.tianqiapi.com/api/?version=v1&city=" + city.substring(0, city.length() - 1))//请求接口。如果需要传参拼接到接口后面。
//                            .build();//创建Request 对象
//                    Response response = null;
//                    response = client.newCall(request).execute();//得到Response 对象
//                    if (response.isSuccessful()) {
//                        Log.i("请求结果", "OK");
////                        Log.i("kwwl", "response.code()==" + response.code());
////                        Log.i("kwwl", "response.message()==" + response.message());
////                        Log.i("kwwl", "res==" + response.body().string());
//                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

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

}




