package com.example.pc.lifeassistant.interface_;

import java.util.Date;

/**
 * Created by pc on 2019/1/6.
 */

public interface OnItemClickListener {
    void onItemClick(String title, String date, String remakes);

    void onItemLongClick(String week, String title, String amount, Date date, String remakes, String incomeOrexpenditure, String ObjectId, Integer type_position);


}
