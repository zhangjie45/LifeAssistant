package com.example.pc.lifeassistant.util;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

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
        //  setContentView(getResViewId());
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

    /*
    判断日期是否超过今天 1：超过 2：等于 3：没超过
     */
    public static int getTimeCompareSize(int selectYear, int selectMonth, int selectDay) {
        int i = 0;
        Calendar c = Calendar.getInstance();
        if (selectYear > c.get(Calendar.YEAR) || selectMonth > c.get(Calendar.MONTH) || selectDay > c.get(Calendar.DAY_OF_MONTH)) {
            i = 1;
        } else if (selectYear == c.get(Calendar.YEAR) && selectMonth == c.get(Calendar.MONTH) && selectDay == c.get(Calendar.DAY_OF_MONTH)) {
            i = 2;
        } else {
            i = 3;
        }
        return i;
    }

}
