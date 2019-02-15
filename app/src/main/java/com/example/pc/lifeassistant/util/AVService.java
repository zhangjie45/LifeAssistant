package com.example.pc.lifeassistant.util;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.bean.DateInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by pc on 2019/1/24.
 */

public class AVService {

    public static Date getDateWithDateString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateFormat.parse(dateString);
        return date;
    }

    public static List<DateInfo> findDate(String creator_id, Date event_date) {
        // Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event_date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        event_date = calendar.getTime();
        final AVQuery<DateInfo> creatorID = new AVQuery<>("Event");
        creatorID.whereEqualTo("user_id", creator_id);
        final AVQuery<DateInfo> eventDate = new AVQuery<>("Event");
        eventDate.whereGreaterThan("home_date", event_date);
        // eventDate.whereLessThan("date", date);
        AVQuery<DateInfo> query = AVQuery.and(Arrays.asList(creatorID, eventDate));
        query.setCachePolicy(AVQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.setMaxCacheAge(24 * 3600 * 1000); //设置为一天，单位毫秒
        boolean isInCache = query.hasCachedResult();
        query.orderByAscending("home_date");
        try {

            Log.e("缓存结果", isInCache + "");
            return query.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }
    }

    public static List<CapitalInfo> findCapital(String user_id) {

        final AVQuery<CapitalInfo> userID = new AVQuery<>("Capital");
        userID.whereEqualTo("user_id", user_id);

        AVQuery<CapitalInfo> query = AVQuery.and(Collections.singletonList(userID));
        query.orderByDescending("time");
        try {

            return query.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }
    }


    public static List<CapitalInfo> incomeCapital(String user_id, String firstDay, String lastDay) throws ParseException {

        final AVQuery<CapitalInfo> userId = new AVQuery<>("Capital");
        userId.whereEqualTo("user_id", user_id);
        final AVQuery<CapitalInfo> startDateQuery = new AVQuery<>("Capital");
        startDateQuery.whereGreaterThanOrEqualTo("time", getDateWithDateString(firstDay));
        final AVQuery<CapitalInfo> endDateQuery = new AVQuery<>("Capital");
        endDateQuery.whereLessThan("time", getDateWithDateString(lastDay));
        AVQuery<CapitalInfo> query = AVQuery.and(Arrays.asList(startDateQuery, endDateQuery, userId));
        query.whereEqualTo("incomeOrexpenditure", "收入");
        try {
            return query.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }
    }

    public static List<CapitalInfo> expenditureCapital(String user_id, String firstDay, String lastDay) throws ParseException {
        final AVQuery<CapitalInfo> userId = new AVQuery<>("Capital");
        userId.whereEqualTo("user_id", user_id);
        final AVQuery<CapitalInfo> startDateQuery = new AVQuery<>("Capital");
        startDateQuery.whereGreaterThanOrEqualTo("time", getDateWithDateString(firstDay));
        final AVQuery<CapitalInfo> endDateQuery = new AVQuery<>("Capital");
        endDateQuery.whereLessThan("time", getDateWithDateString(lastDay));
        AVQuery<CapitalInfo> query = AVQuery.and(Arrays.asList(startDateQuery, endDateQuery, userId));
        query.whereEqualTo("incomeOrexpenditure", "支出");
        try {
            return query.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }
    }

//    public static boolean delDate(String objectId) {
//        boolean del_flag = false;
//
//        return del_flag;
//    }

}
