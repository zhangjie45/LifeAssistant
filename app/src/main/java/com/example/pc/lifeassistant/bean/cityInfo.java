package com.example.pc.lifeassistant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pc on 2019/2/19.
 */

public class cityInfo extends BaseData implements Parcelable{
    private String city;
    private String cityId;
    private String parent;
    private String updateTime;

    protected cityInfo(Parcel in) {
        city = in.readString();
        cityId = in.readString();
        parent = in.readString();
        updateTime = in.readString();
    }

    public static final Creator<cityInfo> CREATOR = new Creator<cityInfo>() {
        @Override
        public cityInfo createFromParcel(Parcel in) {
            return new cityInfo(in);
        }

        @Override
        public cityInfo[] newArray(int size) {
            return new cityInfo[size];
        }
    };

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(cityId);
        dest.writeString(parent);
        dest.writeString(updateTime);
    }
}
