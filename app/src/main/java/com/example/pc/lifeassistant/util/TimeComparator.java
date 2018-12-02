package com.example.pc.lifeassistant.util;

import com.example.pc.lifeassistant.bean.CapitalInfo;

import java.util.Comparator;

/**
 * 对时间进行排序，在实际开发中最好要后台给按时间顺序排序好的数据
 */

public class TimeComparator implements Comparator<CapitalInfo> {



    @Override
    public int compare(CapitalInfo capitalInfo1, CapitalInfo capitalInfo2) {
        return capitalInfo2.getTime().compareTo(capitalInfo1.getTime());
    }
}
