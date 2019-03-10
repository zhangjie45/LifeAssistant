package com.example.pc.lifeassistant.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pc on 2018/12/2.
 */
@AVClassName("Capital")
public class CapitalInfo extends AVObject {
    private String objectId;
    private String incomeOrexpenditure;
    private Date time;
    private String type;
    private int type_position;


    private String amount;
    private String remakes;

    public String getIncomeOrexpenditure() {
        return incomeOrexpenditure;
    }

    public void setIncomeOrexpenditure(String incomeOrexpenditure) {
        this.incomeOrexpenditure = incomeOrexpenditure;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getType_position() {
        return type_position;
    }

    public void setType_position(int type_position) {
        this.type_position = type_position;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
    }

}

