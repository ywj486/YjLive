package com.ywj.yjlive.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/19 0019.
 */
public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);

        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isFirstRun) {
            Log.e("debug", "第一次运行");
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            Intent intent = new Intent();
            intent.setClass(FirstActivity.this, StartActivity.class);
            startActivity(intent);
        } else {
            Log.e("debug", "不是第一次运行");
            Intent intent = new Intent();
            intent.setClass(FirstActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}