package com.ywj.yjlive.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ywj.yjlive.R;
import com.ywj.yjlive.adapter.base.BaseViewHolder;
import com.ywj.yjlive.adapter.base.SimpleAdapter;
import com.ywj.yjlive.bean.Live;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class HotAdapter  extends SimpleAdapter<Live.ResultBean.ListBean> {
    public HotAdapter(Context context, List<Live.ResultBean.ListBean> datas) {
        super(context, R.layout.homenew_item_dome, datas);
        Log.e("TAG","HotAdapter---------------------------------"+datas.size());
    }

    @Override
    protected void convert(BaseViewHolder holder, Live.ResultBean.ListBean item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.hot_news_img_hHead);
        draweeView.setImageURI(Uri.parse(item.getData().getPic()));
        draweeView = (SimpleDraweeView) holder.getView(R.id.hot_news_img_hShow);
        draweeView.setImageURI(Uri.parse(item.getData().getPic()));
        holder.getTextView(R.id.hot_news_tv_state).setText(item.getData().getStatus()==0?"直播":"重播");
        holder.getTextView(R.id.hot_news_tv_hName).setText(item.getData().getLive_name());
        holder.getTextView(R.id.hot_news_tv_hSite).setText("上海");
        Log.e("TAG","进convert来了---------------------------------1");
//        item.getUser().getUser_data().getUser_name()  头像

    }
}
