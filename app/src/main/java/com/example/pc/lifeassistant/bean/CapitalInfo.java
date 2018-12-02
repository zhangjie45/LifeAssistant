package com.example.pc.lifeassistant.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/12/2.
 */

public class CapitalInfo {
    private String incomeOrexpenditure;
    private String time;
    private String type;
    private int amount;

    public String getIncomeOrexpenditure() {
        return incomeOrexpenditure;
    }

    public void setIncomeOrexpenditure(String incomeOrexpenditure) {
        this.incomeOrexpenditure = incomeOrexpenditure;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<CapitalInfo> addData() {
        List<CapitalInfo> mList = new ArrayList<>();
        String[] incomeOrexpenditure = {
                "支出",
                "支出",
                "支出",
                "收入",
                "收入",
                "支出",
                "支出",
                "支出",
                "收入",
                "收入",
        };
        String[] times = {
                "20181202",
                "20181202",
                "20181202",
                "20181202",
                "20181203",
                "20181207",
                "20181208",
                "20181208",
                "20181210",
                "20181212",

        };
        String[] type = new String[]{
                "网购",
                "零食",
                "早饭",
                "红包",
                "工资",
                "午饭",
                "买菜",
                "水电费",
                "报销",
                "兼职",


        };
        int[] amount = new int[]{
                200,
                100,
                10,
                150,
                1500,
                20,
                78,
                126,
                1500,
                750,
        };
        for (int i = 0; i < times.length; i++) {
            CapitalInfo capitalInfo = new CapitalInfo();
            capitalInfo.setTime(times[i]);
            capitalInfo.setAmount(amount[i]);
            capitalInfo.setType(type[i]);
            capitalInfo.setIncomeOrexpenditure(incomeOrexpenditure[i]);
            mList.add(capitalInfo);
        }
        return mList;
    }
}

