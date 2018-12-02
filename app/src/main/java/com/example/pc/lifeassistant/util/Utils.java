package com.example.pc.lifeassistant.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pc on 2018/12/2.
 */

public class Utils {
    public static String LongtoStringFormat(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
