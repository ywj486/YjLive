package com.ywj.yjlive.adapter;

import android.content.Context;

import com.ywj.yjlive.R;
import com.ywj.yjlive.adapter.base.BaseViewHolder;
import com.ywj.yjlive.adapter.base.SimpleAdapter;
import com.ywj.yjlive.bean.Text;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class MagAdapter extends SimpleAdapter<Text> {

    public MagAdapter(Context context, int layoutResId, List<Text> datas) {
        super(context, R.layout.masg_item_dome, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Text item) {
        holder.getTextView(R.id.msg_text).setText(item.getText());

    }
}
