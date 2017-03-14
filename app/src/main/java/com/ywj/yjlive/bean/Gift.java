package com.ywj.yjlive.bean;


import com.ywj.yjlive.bean.base.BaseBean;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class Gift  extends BaseBean {

    private int  vpImg;
    private String vpTv;



    public Gift() {

    }

    public void setVpImg(int vpImg) {
        this.vpImg = vpImg;
    }

    public int getVpImg() {
        return vpImg;
    }

    public String getVpTv() {
        return vpTv;
    }

    public void setVpTv(String vpTv) {
        this.vpTv = vpTv;
    }
}
