package com.example.pc.lifeassistant.util;

import android.app.Fragment;
import android.content.Intent;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pc on 2018/10/30.
 */

public class BaseFragment extends Fragment {

    public void ToastUtil(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void fragmentToActivity(Class activityName) {
        Intent intent = new Intent(getActivity(), activityName);
        startActivity(intent);
    }
//    public void fragmentToActivity(Class activityName) {
//        Intent intent = new Intent(getActivity(), activityName);
//        startActivity(intent);
//        getActivity().onBackPressed();
//    }
     /*
    字符串转日期
     */
public static Date StrToDate(String str) {

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    Date date = null;
    try {
        date = format.parse(str);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return date;
}

    /*
    日期转字符串

     */
    public static String DateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
