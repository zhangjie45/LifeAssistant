package com.example.pc.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.ui.NoteActivity;
import com.example.pc.lifeassistant.util.BaseFragment;


/**
 * Created by pc on 2018/10/29.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private String name;
    private FloatingActionButton btn_note;


    public static final HomeFragment newInstance(String name) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("name");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_note = getActivity().findViewById(R.id.btn_note);
        btn_note.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_note:
                fragmentToActivity(NoteActivity.class);
                break;
        }
    }


}




