package com.ywj.yjlive.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ywj.yjlive.R;
import com.ywj.yjlive.adapter.base.BaseViewHolder;
import com.ywj.yjlive.adapter.base.SimpleAdapter;
import com.ywj.yjlive.bean.PlayInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class FindAdapter extends SimpleAdapter<PlayInfo> {
    public FindAdapter(Context context, List<PlayInfo> datas) {
        super(context, R.layout.homefind_item_dome, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, PlayInfo item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.hot_find_img_hHead);
        draweeView.setImageURI(Uri.parse(item.getHeadIcon()));
        draweeView = (SimpleDraweeView) holder.getView(R.id.hot_find_img_hShow);
        draweeView.setImageURI(Uri.parse(item.getInformationImage()));
        holder.getTextView(R.id.hot_find_tv_state).setText(item.getStatus());
        holder.getTextView(R.id.hot_find_tv_hName).setText(item.getName());
        holder.getTextView(R.id.hot_find_tv_hSite).setText(item.getPlace());
        Log.e("TAG","进convert来了---------------------------------");


    }
}
