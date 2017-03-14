package com.ywj.yjlive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ywj.yjlive.R;


/**
 * 该Fragment用于对直播或观看直播的初始化
 * 直播的控件的创建以及销毁等等都可以在这里进行操作，这样就与我们自己的交互代码分离了
 *
 * Success is the sum of small efforts, repeated day in and day out.
 * 成功就是日复一日那一点点小小努力的积累。

 */
public class LiveViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liveview, container, false);
    }
}