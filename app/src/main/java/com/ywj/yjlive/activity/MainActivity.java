package com.ywj.yjlive.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ywj.yjlive.R;
import com.ywj.yjlive.activity.base.FrameActivity;
import com.ywj.yjlive.fragment.MeFragment;
import com.ywj.yjlive.fragment.TopFragmentTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FrameActivity {

    @BindView(R.id.home)
    ImageView imgHome;
    @BindView(R.id.live)
    ImageView imgLive;
    @BindView(R.id.mine)
    ImageView imgMine;
    @BindView(R.id.content)
    ViewPager content;
    private FragmentPagerAdapter mAdapter;

    private List<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        mFragments.add(TopFragmentTabLayout.newInstance());
        mFragments.add(new MeFragment());
        mAdapter = new FragmentPagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        content.setAdapter(mAdapter);
    }
    @OnClick({R.id.home, R.id.mine,R.id.live})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                content.setCurrentItem(0);
                break;
            case R.id.mine:
                content.setCurrentItem(1);
                break;
            case R.id.live:
//                Intent intent=new Intent(this,CameraActivity.class);
//                startActivity(intent);
                CameraActivity.startActivity(getApplicationContext(), 0,
                        "rtmp://uplive.geekniu.com/live", 15, 800,
                        48, 0, 1, 1,  3,
                        1, 3, false, false);
                break;
        }
    }
    private   long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
