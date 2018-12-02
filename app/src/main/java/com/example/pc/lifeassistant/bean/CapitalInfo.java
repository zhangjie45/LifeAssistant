package com.example.pc.lifeassistant.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/12/2.
 */

public class CapitalInfo {
    private String time;
    private String type;
    private int amount;

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
        String[] times = {
                "20170507",
                "20170507",
                "20170507",
                "20170507",
                "20170507",
                "20170507",
        };
        String[] type = new String[]{
                "去小北门拿快递",
                "跟同事一起聚餐",
                "写文档",
                "和产品开会",
                "整理开会内容",
                "提交代码到git上"
        };
        int[] amount = new int[]{
                15,
                15,
                15,
                15,
                15,
                15,
        };
        for (int i = 0; i < times.length; i++) {
            CapitalInfo capitalInfo = new CapitalInfo();
            capitalInfo.setTime(times[i]);
            capitalInfo.setAmount(amount[i]);
            capitalInfo.setType(times[i]);
            mList.add(capitalInfo);
        }
        return mList;
    }
}

