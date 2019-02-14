package com.example.pc.lifeassistant.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pc on 2018/12/2.
 */

public class Utils {
//    public static String LongtoStringFormat(long time) {
//        Date currentTime = new Date(time);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd");
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

    //计算倒计时
    public static Long CountDown(String current_time, Date event_time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long days = 0;
        try {
            Date d1 = df.parse(current_time);
            long diff = event_time.getTime() - d1.getTime();//这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);


        } catch (Exception e) {
            Log.e("倒计时计算出现错误：", e.getMessage());
        }

        return days;
    }

    //判断当前是否有网络
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //GMT格式转换
    public static String GMTtoStr(String GMTTime) throws ParseException {

        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
        Date date = sf.parse(GMTTime);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
}
