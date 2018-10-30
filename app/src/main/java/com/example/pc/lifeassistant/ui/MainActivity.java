package com.example.pc.lifeassistant.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

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
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home_fill, getString(R.string.item_home)).setInactiveIconResource(R.drawable.home).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.location_fill, getString(R.string.item_location)).setInactiveIconResource(R.drawable.location).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.like_fill, getString(R.string.item_like)).setInactiveIconResource(R.drawable.like).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.person_fill, getString(R.string.item_person)).setInactiveIconResource(R.drawable.person).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
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
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_nar_bar);

    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                initToolbar(R.id.tl_, R.id.toolbar_title, "首页");
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance(getString(R.string.item_person));
                }
                transaction.replace(R.id.sub_content, homeFragment);
                break;
            case 1:

                initToolbar(R.id.tl_, R.id.toolbar_title, "日程管理");
                if (dateFragment == null) {
                    dateFragment = DateFragment.newInstance(getString(R.string.item_person));
                }
                transaction.replace(R.id.sub_content, dateFragment);
                break;
            case 2:

                initToolbar(R.id.tl_, R.id.toolbar_title, "资金管理");
                if (captialFragment == null) {
                    captialFragment = CaptialFragment.newInstance(getString(R.string.item_person));
                }
                transaction.replace(R.id.sub_content, captialFragment);
                break;
            case 3:
                initToolbar(R.id.tl_, R.id.toolbar_title, "设置");
                if (settingFragment == null) {
                    settingFragment = SettingFragment.newInstance(getString(R.string.item_person));
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
