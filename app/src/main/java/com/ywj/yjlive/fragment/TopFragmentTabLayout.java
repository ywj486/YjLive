package com.ywj.yjlive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ywj.yjlive.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class TopFragmentTabLayout extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_home)
    ViewPager mViewPager;
    View view;
    TopFragmentTabLayout tb;
    private LayoutInflater mInflater;
    private List<Fragment> mFragment = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;


    public static TopFragmentTabLayout newInstance(){
        return new TopFragmentTabLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.top_tab_home,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        HotFragment hf=new  HotFragment();
        final FindFragment mf=FindFragment.newInstance();
        mFragment.add(hf);
        mFragment.add(mf);
        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {

                return mFragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragment.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
        initTabLine();
    }
    private void initTabLine() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(getString(R.string.home_tab_name_remen));
        mTabLayout.getTabAt(1).setText(getString(R.string.home_tab_name_jingxuan));
    }

}