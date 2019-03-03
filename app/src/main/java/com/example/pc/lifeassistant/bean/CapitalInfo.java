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
    private String amount;
    private String reakes;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReakes() {
        return reakes;
    }

    public void setReakes(String reakes) {
        this.reakes = reakes;
    }

}

