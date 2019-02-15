package com.example.pc.lifeassistant.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.pc.lifeassistant.bean.CapitalInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static Calendar cale = null;

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

    //当前月第一天
    public static String firstDay() {
        cale = Calendar.getInstance();
        String firstday;
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());

        return firstday + " 00:00:00";
    }

    //当前月最后一天
    public static String lastDay() {
        String lastday;
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        return lastday + " 00:00:00";
    }

    //计算当月的收入与住处
    public static double Count(List<CapitalInfo> capital) {
        double amount = 0;
        for (int i = 0; i < capital.size(); i++) {
            double num = Double.parseDouble(capital.get(i).getAmount());
            amount += num;
        }
        return amount;
    }
}
