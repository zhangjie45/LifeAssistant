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

    //    public List<CapitalInfo> addData() {
//        List<CapitalInfo> mList = new ArrayList<>();
//        String[] incomeOrexpenditure = {
//                "支出",
//                "支出",
//                "支出",
//                "收入",
//                "收入",
//                "支出",
//                "支出",
//                "支出",
//                "收入",
//                "收入",
//        };
//        String[] times = {
//                "20181202",
//                "20181202",
//                "20181202",
//                "20181202",
//                "20181203",
//                "20181207",
//                "20181208",
//                "20181208",
//                "20181210",
//                "20181212",
//
//        };
//        String[] type = new String[]{
//                "网购",
//                "零食",
//                "早饭",
//                "红包",
//                "工资",
//                "午饭",
//                "买菜",
//                "水电费",
//                "报销",
//                "兼职",
//
//
//        };
//        int[] amount = new int[]{
//                200,
//                100,
//                10,
//                150,
//                1500,
//                20,
//                78,
//                126,
//                1500,
//                750,
//        };
//        for (int i = 0; i < times.length; i++) {
//            CapitalInfo capitalInfo = new CapitalInfo();
//         //   capitalInfo.setTime(times[i]);
//            capitalInfo.setAmount(amount[i]);
//            capitalInfo.setType(type[i]);
//            capitalInfo.setIncomeOrexpenditure(incomeOrexpenditure[i]);
//            mList.add(capitalInfo);
//        }
//        return mList;
//    }
}

