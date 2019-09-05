package com.lsy.module_home.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.module_home.R;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean.Article, BaseViewHolder> {
    public ArticleAdapter(List data) {
        super(R.layout.item_article, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean.Article item) {
        helper.setText(R.id.txt_title, item.getTitle())
                .setText(R.id.txt_author, item.getAuthor())
                .setText(R.id.txt_classify, item.getSuperChapterName() + "/" + item.getChapterName())
                .setText(R.id.txt_time, item.getNiceDate());
        if (item.isCollect()) {
            ((ImageView) helper.getView(R.id.iv_collect)).setImageResource(R.mipmap.ic_collected);
        } else {
            ((ImageView) helper.getView(R.id.iv_collect)).setImageResource(R.mipmap.ic_collect);
        }
        if (item.getType() == 0) {
            if (item.isFresh()) {
                helper.getView(R.id.txt_tag1).setVisibility(View.VISIBLE);
                helper.setText(R.id.txt_tag1, "新");
            } else {
                helper.getView(R.id.txt_tag1).setVisibility(View.GONE);
            }
        } else {
            helper.getView(R.id.txt_tag1).setVisibility(View.VISIBLE);
            helper.setText(R.id.txt_tag1, "置顶");
        }

        if (item.getTags().size() > 0) {
            helper.getView(R.id.txt_tag2).setVisibility(View.VISIBLE);
            helper.setText(R.id.txt_tag2, item.getTags().get(0).getName());
        } else {
            helper.getView(R.id.txt_tag2).setVisibility(View.GONE);
        }

    }
}
