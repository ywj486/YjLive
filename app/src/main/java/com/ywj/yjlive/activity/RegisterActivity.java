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
import com.ywj.yjlive.bean.RegisterResultBean;
import com.ywj.yjlive.http.Contants;
import com.ywj.yjlive.utils.RegexTools;
import com.ywj.yjlive.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.pass_et)
    EditText passEt;
    @BindView(R.id.passtwo_et)
    EditText passtwoEt;
    @BindView(R.id.rg_btn)
    Button rgBtn;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        rgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heard = "http://img.17gexing.com/uploadfile/2015/01/2/20150103071558112.jpg";
                String phone = phoneEt.getText().toString();
                String name = nameEt.getText().toString();
                String pwd = passEt.getText().toString();
                String pwd2 = passtwoEt.getText().toString();

                if ("".equals(heard) || "".equals(phone) || "".equals(name) || "".equals(pwd) || "".equals(pwd2)) {
                    ToastUtils.show(RegisterActivity.this, "输入内容不能为空");
                    return;
                }
                if (!RegexTools.isMobile(phone)) {
                    ToastUtils.show(RegisterActivity.this, "手机号格式不正确");
                    return;
                }
                if (!RegexTools.isENG_NUM(pwd)) {
                    ToastUtils.show(RegisterActivity.this, "密码是6~16位字母数字符号组合");
                    return;
                }
                if (!pwd.equals(pwd2)) {
                    ToastUtils.show(RegisterActivity.this, "两次密码输入不一致");
                    return;
                }
                requestRegister_User(heard, phone, name, pwd);
            }
        });
    }

    private void requestRegister_User(String heard, final String phone,
                                      String name, final String pwd) {

        OkHttpUtils.post()
                .url(Contants.API.REGISTER)
                .addParams("phonjke", phone)
                .addParams("user_name", name)
                .addParams("avatar", heard)
                .addParams("sign", "486.")
                .addParams("password", pwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(RegisterActivity.this, "网络连接失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Gson gson = new Gson();

                        RegisterResultBean bean = gson.fromJson(response, RegisterResultBean.class);
                        Log.e("TAG", "bean========" + bean);
                        if (bean.getError_code() == 0) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册成功！！！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("password", pwd);
                                    startActivity(intent);
                                }
                            });
                        } else if (bean.getError_code() == 20022) {
                            Toast.makeText(RegisterActivity.this, bean.getError_code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
