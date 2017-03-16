package com.ywj.yjlive.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/19 0019.
 */
public class MyViewpagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> list;

    public MyViewpagerAdapter(ArrayList<ImageView> list) {
        this.list = list;
    }


    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;

    }

    //	初始化条目 ，当前展示的view视图,ViewPager具有预加载机制，默认预加载前后各一张只能保存3张

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(list.get(position % list.size()));
        return list.get(position % list.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(list.get(position));

    }
}

