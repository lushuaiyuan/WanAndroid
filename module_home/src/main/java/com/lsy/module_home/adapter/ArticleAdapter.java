package com.lsy.module_home.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.module_home.R;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean.Article, BaseViewHolder> {
    private final int TEXT_TYPE = 1;

    public ArticleAdapter(List data) {
        super(R.layout.item_article, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean.Article item) {
        ((QMUIRelativeLayout) helper.getView(R.id.ll_home_item)).setRadius(QMUIDisplayHelper.dp2px(mContext, 8));
        helper.setText(R.id.txt_title, item.getTitle())
                .setText(R.id.txt_author, item.getAuthor())
                .setText(R.id.txt_classify, item.getSuperChapterName() + "." + item.getChapterName())
                .setText(R.id.txt_time, item.getNiceDate());
        if (item.isCollect()) {
            ((ImageView) helper.getView(R.id.iv_collect)).setImageResource(R.mipmap.ic_collected);
        } else {
            ((ImageView) helper.getView(R.id.iv_collect)).setImageResource(R.mipmap.ic_collect);
        }
        helper.addOnClickListener(R.id.iv_collect);
        ImageView iv_img = helper.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(item.getEnvelopePic())) {
            Glide.with(mContext).load(item.getEnvelopePic()).into(iv_img);
            iv_img.setVisibility(View.VISIBLE);
        } else {
            iv_img.setVisibility(View.GONE);
        }
        TextView tvTag1 = helper.getView(R.id.txt_tag1);
        if (item.getType() == 0) {
            if (item.isFresh()) {
                tvTag1.setVisibility(View.VISIBLE);
                tvTag1.setText("新");
            } else {
                tvTag1.setVisibility(View.GONE);
            }
        } else {
            tvTag1.setVisibility(View.VISIBLE);
            tvTag1.setText("置顶");
        }
        if (item.getTags().size() > 0) {
            helper.getView(R.id.txt_tag2).setVisibility(View.VISIBLE);
            helper.setText(R.id.txt_tag2, item.getTags().get(0).getName());
        } else {
            helper.getView(R.id.txt_tag2).setVisibility(View.GONE);
        }

    }
}
