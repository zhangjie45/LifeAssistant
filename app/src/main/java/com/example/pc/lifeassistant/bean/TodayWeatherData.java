package com.example.pc.lifeassistant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by pc on 2019/2/19.
 */

public class TodayWeatherData extends BaseData implements Parcelable {
    private String day;
    private String date;
    private String week;
    private String wea;
    private Integer air;
    private String air_level;
    private String tem1;
    private String tem2;
    private String tem;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public Integer getAir() {
        return air;
    }

    public void setAir(Integer air) {
        this.air = air;
    }

    public String getAir_level() {
        return air_level;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    private TodayWeatherData(Parcel source) {
        this.day = source.readString();
        this.date = source.readString();
        this.week = source.readString();
        this.wea = source.readString();
        this.air = source.readInt();
        this.air_level = source.readString();
        this.tem1 = source.readString();
        this.tem2 = source.readString();
        this.tem = source.readString();

    }

    public static final Creator<TodayWeatherData> CREATOR = new Creator<TodayWeatherData>() {
        @Override
        public TodayWeatherData createFromParcel(Parcel in) {
            return new TodayWeatherData(in);
        }

        @Override
        public TodayWeatherData[] newArray(int size) {
            return new TodayWeatherData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.day);
        dest.writeString(this.date);
        dest.writeString(this.week);
        dest.writeString(this.wea);
        dest.writeInt(this.air);
        dest.writeString(this.air_level);
        dest.writeString(this.tem1);
        dest.writeString(this.tem2);
        dest.writeString(this.tem);
    }
}
