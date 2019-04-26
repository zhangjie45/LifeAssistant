package com.example.pc.lifeassistant.util;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.example.pc.lifeassistant.bean.ApkVersionInfo;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.bean.DateInfo;
import com.example.pc.lifeassistant.bean.RemindInfo;

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
        query.orderByAscending("home_date");

        try {

            return query.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }
    }

    public static List<CapitalInfo> findCapital(String user_id, String firstDay, String lastDay) throws ParseException {

        final AVQuery<CapitalInfo> userID = new AVQuery<>("Capital");
        userID.whereEqualTo("user_id", user_id);

        AVQuery<CapitalInfo> query = AVQuery.and(Collections.singletonList(userID));
        final AVQuery<CapitalInfo> startDateQuery = new AVQuery<>("Capital");
        startDateQuery.whereGreaterThanOrEqualTo("time", getDateWithDateString(firstDay));
        final AVQuery<CapitalInfo> endDateQuery = new AVQuery<>("Capital");
        endDateQuery.whereLessThan("time", getDateWithDateString(lastDay));
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

    //查询今日应完成事件数
    public static int queryEventNum(String user_id, String nowDay) throws ParseException {
        final AVQuery<DateInfo> userId = new AVQuery<>("Event");
        userId.whereEqualTo("user_id", user_id);
        final AVQuery<DateInfo> Day = new AVQuery<>("Event");
        Day.whereEqualTo("home_date", getDateWithDateString(nowDay));
        AVQuery<DateInfo> query = AVQuery.and(Arrays.asList(Day, userId));
        try {
            return query.count();
        } catch (AVException e) {
            return 0;
        }
    }

    //查询账号是否可被提醒
    public static List<AVUser> queryAccount(String remindAccount) throws ParseException, AVException {
        final AVQuery<AVUser> user = new AVQuery<>("_User");
        user.whereEqualTo("username", remindAccount);
        AVQuery<AVUser> query = AVQuery.and(Arrays.asList(user));
        try {
            return query.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }
    }

    //查询今日提醒事件
    public static List<RemindInfo> queryRemind(String user_id, String remind_id, String date) throws ParseException {
        final AVQuery<RemindInfo> reminders = new AVQuery<>("Remind");
        reminders.whereEqualTo("reminders", user_id);
        final AVQuery<RemindInfo> addedpersonaccount = new AVQuery<>("Remind");
        addedpersonaccount.whereEqualTo("addedpersonaccount", remind_id);
        final AVQuery<RemindInfo> Day = new AVQuery<>("Remind");
        Day.whereEqualTo("date", getDateWithDateString(date));
        AVQuery<RemindInfo> query = AVQuery.and(Arrays.asList(reminders, addedpersonaccount, Day));
        try {
            return query.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }

    }

    //查询服务器中软件版本号
    public static List<ApkVersionInfo> queryApkVersion() throws ParseException {
        final AVQuery<ApkVersionInfo> apkVersion = new AVQuery<>("APKVersion");
        try {
            return apkVersion.find();
        } catch (AVException e) {
            return Collections.emptyList();
        }
    }
}
