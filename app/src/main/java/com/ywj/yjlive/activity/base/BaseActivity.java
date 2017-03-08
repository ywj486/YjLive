package com.ywj.yjlive.activity.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * 封装toast方法使继承其的类可以方便快捷的调用toast方法
     *
     * @param msg
     */
    protected void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 封装Intent跳转方法继承其的类可以调用
     */
    protected void openActivity(Class<?> cls) {
        Intent in = new Intent();
        in.setClass(this, cls);
        startActivity(in);
    }

    protected LayoutInflater getInflater() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        return layoutInflater;

    }
}
