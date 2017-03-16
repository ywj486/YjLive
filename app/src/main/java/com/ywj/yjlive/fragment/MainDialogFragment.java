package com.ywj.yjlive.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.ywj.yjlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 可以看得出来这是个全屏dailogFragment，他的内部有一个pager
 * 分别控制着EmptyFragment与LayerFragment
 * TransparentFragment：什么都没有
 * layerFragment：交互界面
 * 这样就达到了滑动隐藏交互的需求，这样做也是为了避免我们自定义动画时，显示卡顿的问题
 *
 */

public class MainDialogFragment extends DialogFragment {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private LayerFragment layerFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new TransparentFragment();/*返回空界面的fragment*/
                } else if (position == 1) {
                    return layerFragment = new LayerFragment();/*返回交互界面的fragment*/
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
        viewpager.setCurrentItem(1);/*设置默认显示交互界面*/
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);/*同时将界面改为resize已达到软键盘弹出时LiveFragment不会跟随移动*/
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.MainDialog) {/*设置MainDialogFragment的样式，这里的代码最好还是用我的，大家不要改动*/
            @Override
            public void onBackPressed() {
                super.onBackPressed();
                getActivity().finish();
            }
        };
        return dialog;
    }


    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                layerFragment.hideKeyboard();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
