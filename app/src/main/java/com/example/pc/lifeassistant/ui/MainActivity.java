package com.example.pc.lifeassistant.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
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
    private TextView tv_tb_add;

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
        HomeFragment homeFragment = HomeFragment.newInstance(getString(R.string.item_home));
        transaction.replace(R.id.sub_content, homeFragment).commit();

    }

    public void init() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_nar_bar);
        tv_tb_add = (TextView) findViewById(R.id.tv_tb_add);

    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                tv_tb_add.setVisibility(View.GONE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "首页", false);
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance(getString(R.string.item_home));
                }
                transaction.replace(R.id.sub_content, homeFragment);
                break;
            case 1:
                tv_tb_add.setVisibility(View.VISIBLE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "日程管理", false);
                if (dateFragment == null) {
                    dateFragment = DateFragment.newInstance(getString(R.string.item_date));
                }
                transaction.replace(R.id.sub_content, dateFragment);
                break;
            case 2:
                tv_tb_add.setVisibility(View.VISIBLE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "资金管理", false);
                if (capitalFragment == null) {
                    capitalFragment = CapitalFragment.newInstance(getString(R.string.item_capital));
                }
                transaction.replace(R.id.sub_content, capitalFragment);
                break;
            case 3:
                tv_tb_add.setVisibility(View.GONE);
                initToolbar(R.id.tl_, R.id.toolbar_title, "设置", false);
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
