package com.example.pc.lifeassistant.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by pc on 2018/11/24.
 */
@AVClassName("Event")
public class DateInfo extends AVObject {
    String objectId;
    String user_id;
    String home_title;
    Integer home_day;
    Date home_date;
    String home_week;
    String remakes;


    @Override
    public String getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

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

    public Date getHome_date() {
        return home_date;
    }

    public void setHome_date(Date home_date) {
        this.home_date = home_date;
    }

    public String getHome_week() {
        return home_week;
    }

    public void setHome_week(String home_week) {
        this.home_week = home_week;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}

