package com.ywj.yjlive.adapter;

import android.content.Context;
import android.net.Uri;

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
    }

    @Override
    protected void convert(BaseViewHolder holder, Live.ResultBean.ListBean item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.hot_news_img_hHead);
        draweeView.setImageURI(Uri.parse(item.getData().getPic()));
        draweeView = (SimpleDraweeView) holder.getView(R.id.hot_news_img_hShow);
        draweeView.setImageURI(Uri.parse(item.getData().getPic()));
        holder.getTextView(R.id.hot_news_tv_state).setText(item.getData().getStatus()==0?"直播中":"已结束");
        holder.getTextView(R.id.hot_news_tv_hName).setText(item.getData().getLive_name());
        holder.getTextView(R.id.hot_news_tv_hSite).setText("北京");

    }
}
