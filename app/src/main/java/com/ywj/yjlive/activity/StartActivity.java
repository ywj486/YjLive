package com.ywj.yjlive.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ywj.yjlive.R;
import com.ywj.yjlive.activity.base.BaseActivity;
import com.ywj.yjlive.adapter.MyViewpagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class StartActivity extends BaseActivity {
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tiyan)
    Button tiyan;
    private Context mContext;
    private ArrayList<ImageView> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        ButterKnife.bind(this);

        mContext = this;
        getdata();
        vp.setAdapter(new MyViewpagerAdapter(list));

        tiyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterHome();
            }
        });

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("当前滑动=====" + position);
                if (position == 3) {
                    tiyan.setVisibility(View.VISIBLE);
                } else {
                    tiyan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 获得Viewpager的页面集合
     */
    private void getdata() {
        list = new ArrayList<ImageView>();
        int[] img = new int[]{
                R.drawable.xiaoying019,
                R.drawable.xiaoying014,
                R.drawable.xiaoying025,
                R.drawable.xiaoying022
        };

        for (int i = 0; i < img.length; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setBackgroundResource(img[i]);
            list.add(iv);
        }

    }

    /**
     * 进入主页面
     */
    public void enterHome() {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        this.finish();
    }

    /**
     * 调用onActivityResult在处理完毕之后 在请求页面关闭之后
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }
}