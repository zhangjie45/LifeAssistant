package com.example.pc.lifeassistant.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            //   Date d2 = df.parse("2004-01-02 11:30:24");
            long diff = event_time.getTime() - d1.getTime();//这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);
            //     Log.e("倒计时结果-----》", days + "");
//            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
//            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");

        } catch (Exception e) {
            Log.e("倒计时计算出现错误：", e.getMessage());
        }

        return days;
    }
}
