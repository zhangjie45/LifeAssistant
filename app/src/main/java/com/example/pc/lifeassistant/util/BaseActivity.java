package com.example.pc.lifeassistant.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.bean.DateInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by pc on 2018/10/30.
 */
//SwipeBackActivity
public class BaseActivity extends SwipeBackActivity {


    private SwipeBackLayout swipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Leancloud
        AVObject.registerSubclass(DateInfo.class);
        AVObject.registerSubclass(CapitalInfo.class);
        AVOSCloud.initialize(this, "07jspnPs0LVvAFhOgf36OrG7-gzGzoHsz", "NzRwCw50TJDziJy73QWqEvby");
        //  setContentView(getResViewId());
        //调试日志
        //AVOSCloud.setDebugLogEnabled(true);
        //初始化右滑退出
        initSwipeBack();
    }

    /**
     * 初始化右滑退出
     */
    public void initSwipeBack() {
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        swipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        swipeBackLayout.setEdgeSize(200);
    }

    /**
     * 关闭右滑退出
     */
    protected void closeSwipeBack() {
        setSwipeBackEnable(false);
    }

    public Toolbar initToolbar(int id, int titleId, String titleString, boolean homeAsUp) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        TextView textView = (TextView) findViewById(titleId);
        textView.setText(titleString);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //根据homeAsUp的值设置toolbar左侧返回按钮隐藏
            actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return toolbar;
    }

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据)
     *
     * @param activity       发起跳转的Activity实例
     * @param TargetActivity 目标Activity实例
     */
    public static void skipAnotherActivity(Activity activity,
                                           Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据,不销毁当前Activity)
     *
     * @param activity       发起跳转的Activity实例
     * @param TargetActivity 目标Activity实例
     */
    public static void skipAnotherActivityNoFinish(Activity activity,
                                                   Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);

    }


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
    判断日期是否超过今天 1：超过 2：等于 3：没超过
     */
    public static int getTimeCompareSize(Date selectDate) {
        int i = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
        //获取当前时间

        Calendar c = Calendar.getInstance();
        int now_month = c.get(Calendar.MONTH) + 1;
        String now_data = c.get(Calendar.YEAR) + "/" + now_month + "/" + c.get(Calendar.DAY_OF_MONTH);
        Log.e("选择日期:", simpleDateFormat.format(selectDate));
        Log.e("今日:", simpleDateFormat.format(StrToDate(now_data)));
        int result = StrToDate(now_data).compareTo(selectDate);
        if (selectDate.getTime() > StrToDate(now_data).getTime()) {
            Log.e("比较结果", "选择日期大于今日");
            i = 1;
        } else if (result == 0) {
            Log.e("比较结果", "选择日期等于今日");
            i = 2;
        } else {
            i = 3;
            Log.e("比较结果", "选择日期小于今日");
        }
        return i;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */

    public String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }


        return Week;
    }


    /*
        TextInputEditText情况下  判断字数是否超过要求
     */
    public void TIETextChange(TextInputEditText textInputEditText, final TextInputLayout textInputLayout, final int minLength) {
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > minLength) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("字数超过" + minLength + "个");
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*
      EditText情况下  判断字数是否超过要求
   */
    public void EditTextChange(EditText editText, final TextInputLayout textInputLayout, final int minLength) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > minLength) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("字数超过" + minLength + "个");
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}
