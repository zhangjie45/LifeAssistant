package com.example.pc.lifeassistant.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/11/24.
 */

public class DateInfo {
    String home_title;
    Integer home_day;
    String home_date;
    String home_week;
    String remakes;

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
    }

    public String getHome_title() {
        return home_title;
    }

    public void setHome_title(String home_title) {
        this.home_title = home_title;
    }

    public Integer getHome_day() {
        return home_day;
    }

    public void setHome_day(Integer home_day) {
        this.home_day = home_day;
    }

    public String getHome_date() {
        return home_date;
    }

    public void setHome_date(String home_date) {
        this.home_date = home_date;
    }

    public String getHome_week() {
        return home_week;
    }

    public void setHome_week(String home_week) {
        this.home_week = home_week;
    }

    public List<DateInfo> addData() {
        List<DateInfo> list = new ArrayList();
        DateInfo dateInfo = new DateInfo();
        dateInfo.setHome_title("恋爱纪念日");
        dateInfo.setHome_day(2);
        dateInfo.setHome_date("2018.05.05");
        dateInfo.setHome_week("星期四");
        dateInfo.setRemakes("恋爱纪念日的备注");
        list.add(dateInfo);

        dateInfo = new DateInfo();
        dateInfo.setHome_title("工作会议");
        dateInfo.setHome_day(5);
        dateInfo.setHome_date("2018.07.03");
        dateInfo.setHome_week("星期一");
        dateInfo.setRemakes("工作会议的备注");
        list.add(dateInfo);

        dateInfo = new DateInfo();
        dateInfo.setHome_title("生日");
        dateInfo.setHome_day(23);
        dateInfo.setHome_date("2018.01.25");
        dateInfo.setHome_week("星期天");
        dateInfo.setRemakes("生日的备注");
        list.add(dateInfo);

        dateInfo = new DateInfo();
        dateInfo.setHome_title("结婚纪念日");
        dateInfo.setHome_day(34);
        dateInfo.setHome_date("2018.12.09");
        dateInfo.setHome_week("星期二");
        dateInfo.setRemakes("结婚纪念日的备注");
        list.add(dateInfo);

        dateInfo = new DateInfo();
        dateInfo.setHome_title("2019年");
        dateInfo.setHome_day(134);
        dateInfo.setHome_date("2019.12.31");
        dateInfo.setHome_week("星期四");
        dateInfo.setRemakes("2019年的备注");
        list.add(dateInfo);

        dateInfo = new DateInfo();
        dateInfo.setHome_title("高考");
        dateInfo.setHome_day(322);
        dateInfo.setHome_date("2019.06.07");
        dateInfo.setHome_week("星期五");
        dateInfo.setRemakes("高考的备注");
        list.add(dateInfo);
        return list;

    }
}

