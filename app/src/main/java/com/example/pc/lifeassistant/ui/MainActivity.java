package com.example.pc.lifeassistant.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.fragment.CapitalFragment;
import com.example.pc.lifeassistant.fragment.DateFragment;
import com.example.pc.lifeassistant.fragment.HomeFragment;
import com.example.pc.lifeassistant.fragment.SettingFragment;
import com.example.pc.lifeassistant.util.BaseActivity;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private HomeFragment homeFragment;
    private DateFragment dateFragment;
    private CapitalFragment capitalFragment;
    private SettingFragment settingFragment;
    private TextView tv_tb_date_add;
    private TextView tv_tb_capital_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        event();


    }

    private void event() {
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setActiveColor(R.color.colorAccent);//值得一提，模式跟背景的设置都要在添加tab前面，不然不会有效果。
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home_fill, getString(R.string.item_home)).setInactiveIconResource(R.drawable.home).setActiveColorResource(R.color.bottom_navigation_item_color).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.date_fill, getString(R.string.item_date)).setInactiveIconResource(R.drawable.date).setActiveColorResource(R.color.bottom_navigation_item_color).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.capital_fill, getString(R.string.item_capital)).setInactiveIconResource(R.drawable.capital).setActiveColorResource(R.color.bottom_navigation_item_color).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.setting_fill, getString(R.string.item_setting)).setInactiveIconResource(R.drawable.setting).setActiveColorResource(R.color.bottom_navigation_item_color).setInActiveColorResource(R.color.black_1))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
         initToolbar(R.id.tl_, R.id.toolbar_title, "首页", false);
         setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.sub_content, homeFragment);
        transaction.commit();
    }

    public void init() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_nar_bar);
        tv_tb_date_add = (TextView) findViewById(R.id.tv_tb_date_add);
        tv_tb_capital_add = (TextView) findViewById(R.id.tv_tb_capital_add);

    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragments(transaction);
        switch (position) {
            case 0:
                tv_tb_date_add.setVisibility(View.GONE);
                tv_tb_capital_add.setVisibility(View.GONE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "首页", false);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.sub_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                tv_tb_date_add.setVisibility(View.VISIBLE);
                tv_tb_capital_add.setVisibility(View.GONE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "日程管理", false);
                if (dateFragment == null) {
                    dateFragment = new DateFragment();
                    transaction.add(R.id.sub_content, dateFragment);
                } else {
                    transaction.show(dateFragment);
                }
                break;
            case 2:
                tv_tb_date_add.setVisibility(View.GONE);
                tv_tb_capital_add.setVisibility(View.VISIBLE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "资金管理", false);
                if (capitalFragment == null) {
                    capitalFragment = new CapitalFragment();
                    transaction.add(R.id.sub_content, capitalFragment);
                } else {
                    transaction.show(capitalFragment);
                }
                break;
            case 3:
                //显示、加载Fragment
                tv_tb_date_add.setVisibility(View.GONE);
                tv_tb_capital_add.setVisibility(View.GONE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "设置", false);
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.sub_content, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
                break;
            default:

        }
        transaction.commit();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (dateFragment != null) {
            transaction.hide(dateFragment);
        }
        if (capitalFragment != null) {
            transaction.hide(capitalFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }

    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


}
