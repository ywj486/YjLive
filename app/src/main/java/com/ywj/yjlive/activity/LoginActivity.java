package com.ywj.yjlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ywj.yjlive.R;
import com.ywj.yjlive.activity.base.BaseActivity;
import com.ywj.yjlive.bean.LoginBean;
import com.ywj.yjlive.http.Contants;
import com.ywj.yjlive.utils.RegexTools;
import com.ywj.yjlive.utils.SharedPreferenceUtils;
import com.ywj.yjlive.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @BindView(R.id.login_pass_et)
    EditText loginPassEt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.reg_btn)
    Button regBtn;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        if (!"".equals(intent.getStringExtra("phone"))) {
            loginPhoneEt.setText(intent.getStringExtra("phone"));
        }

        if (!"".equals(intent.getStringExtra("password"))) {
            loginPassEt.setText(intent.getStringExtra("password"));
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private boolean initView() {
        String phone = loginPhoneEt.getText().toString();
        String pwd = loginPassEt.getText().toString();


        if ("".equals(phone) || "".equals(pwd)) {
            ToastUtils.show(this, "输入内容不能为空");
            return false;
        }

        if (!RegexTools.isMobile(phone)) {
            ToastUtils.show(this, "手机号格式不正确");
            return false;

        }

        requestLogin(phone, pwd);
        return false;
    }

    private void requestLogin(String phone, String pwd) {

        OkHttpUtils.post()
                .url(Contants.API.LOGIN)
                .addParams("phone", phone)
                .addParams("password", pwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(LoginActivity.this, "网络连接失败");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();

                        final LoginBean userBean = gson.fromJson(response, LoginBean.class);
                        Log.e("TAG", "response===" + userBean);

                        if (userBean.getResult() != null) {
                            Toast.makeText(LoginActivity.this, "登录成功！！！", Toast.LENGTH_SHORT).show();
                           //sp保存userId
                            SharedPreferenceUtils.putString(LoginActivity.this,"userId",userBean.getResult().getId()+"");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user", userBean);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            ToastUtils.show(LoginActivity.this, "用户名或密码输入错误！");
                        }
                    }
                });
    }


}