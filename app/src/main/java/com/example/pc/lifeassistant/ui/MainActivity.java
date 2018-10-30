package com.example.pc.lifeassistant.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.fragment.CaptialFragment;
import com.example.pc.lifeassistant.fragment.DateFragment;
import com.example.pc.lifeassistant.fragment.HomeFragment;
import com.example.pc.lifeassistant.fragment.SettingFragment;
import com.example.pc.lifeassistant.util.Utils;

public class MainActivity extends Utils implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private HomeFragment homeFragment;
    private DateFragment dateFragment;
    private CaptialFragment captialFragment;
    private SettingFragment settingFragment;

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
        initToolbar(R.id.tl_, R.id.toolbar_title, "首页");
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        HomeFragment homeFragment = HomeFragment.newInstance(getString(R.string.item_home));
        transaction.replace(R.id.sub_content, homeFragment).commit();

    }

    public void init() {
        bottomNavigationBar = findViewById(R.id.bottom_nar_bar);

    }
    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                initToolbar(R.id.tl_, R.id.toolbar_title, "首页");
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance(getString(R.string.item_setting));
                }
                transaction.replace(R.id.sub_content, homeFragment);
                break;
            case 1:
                initToolbar(R.id.tl_, R.id.toolbar_title, "日程管理");
                if (dateFragment == null) {
                    dateFragment = DateFragment.newInstance(getString(R.string.item_setting));
                }
                transaction.replace(R.id.sub_content, dateFragment);
                break;
            case 2:

                initToolbar(R.id.tl_, R.id.toolbar_title, "资金管理");
                if (captialFragment == null) {
                    captialFragment = CaptialFragment.newInstance(getString(R.string.item_setting));
                }
                transaction.replace(R.id.sub_content, captialFragment);
                break;
            case 3:
                initToolbar(R.id.tl_, R.id.toolbar_title, "设置");
                if (settingFragment == null) {
                    settingFragment = SettingFragment.newInstance(getString(R.string.item_setting));
                }
                transaction.replace(R.id.sub_content, settingFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
