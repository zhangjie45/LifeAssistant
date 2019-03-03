package com.example.pc.lifeassistant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by pc on 2019/2/19.
 */

public class WeatherData extends BaseData implements Parcelable {
    private String time;
    private cityInfo cityInfo;
    private String date;
    private String message;
    private int status;

    private TodayWeatherData[] data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public cityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(cityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TodayWeatherData[] getData() {
        return data;
    }

    public void setData(TodayWeatherData[] data) {
        this.data = data;
    }

    private WeatherData(Parcel in) {
        this.time = in.readString();
        this.cityInfo = in.readParcelable(cityInfo.class.getClassLoader());

        this.date = in.readString();
        this.message = in.readString();
        this.status = in.readInt();
        Parcelable[] parcelables = in.readParcelableArray(TodayWeatherData.class.getClassLoader());
        if (parcelables != null) {
            this.data = Arrays.copyOf(parcelables, parcelables.length, TodayWeatherData[].class);
        }
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeParcelable(this.cityInfo, flags);
        dest.writeInt(this.status);
        dest.writeString(this.date);
        dest.writeString(this.message);
        dest.writeInt(this.status);
        dest.writeParcelableArray(this.data, flags);
    }
}
