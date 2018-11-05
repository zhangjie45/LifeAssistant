package com.example.pc.lifeassistant.util;

import android.app.Fragment;
import android.content.Intent;
import android.widget.Toast;

import com.example.pc.lifeassistant.ui.AddDateActivity;

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
}
