package com.example.pc.lifeassistant.util;

import java.text.SimpleDateFormat;

/**
 * Created by pc on 2018/12/2.
 */

public class DateUtil {

    private static long duration;

    /**
     * 系统时间转换为年月日
     *
     * @param timestamp
     * @return
     */

    public static String timestamp2y(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(timestamp * 1000);
    }

    public static String timestamp2ymd(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(timestamp * 1000);
    }

    public static String toymdhms(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timestamp * 1000);
    }

}
