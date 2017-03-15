package com.ywj.yjlive.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ywj.yjlive.R;
import com.ywj.yjlive.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class MeFragment extends Fragment {
    @BindView(R.id.llogin)
    Button llogin;
    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_mine, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        llogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
