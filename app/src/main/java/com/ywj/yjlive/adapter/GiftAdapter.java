package com.ywj.yjlive.adapter;

import android.content.Context;

import com.ywj.yjlive.R;
import com.ywj.yjlive.adapter.base.BaseViewHolder;
import com.ywj.yjlive.adapter.base.SimpleAdapter;
import com.ywj.yjlive.bean.Gift;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class GiftAdapter extends SimpleAdapter<Gift> {
    public GiftAdapter(Context context,  List<Gift> datas) {
        super(context, R.layout.gift_adap_dome, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Gift item) {
//        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.gift_adap_sdv);
//        draweeView.setImageURI(Uri.parse(item.getVpImg()+""));
        holder.getImageView(R.id.gift_adap_sdv).setImageResource(item.getVpImg());
        holder.getTextView(R.id.gift_adap_tv).setText(item.getVpTv());
    }
}
