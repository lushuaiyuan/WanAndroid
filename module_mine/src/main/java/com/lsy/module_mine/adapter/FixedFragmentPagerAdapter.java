package com.lsy.module_mine.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FixedFragmentPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] mFragments = null;
    private String[] mTitles = null;

    public FixedFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setTitles(String... titles) {
        mTitles = titles;
    }

    public void setFragmentList(Fragment... fragments) {
        mFragments = fragments;
        notifyDataSetChanged();
    }



    @Override
    public Fragment getItem(int position) {
         return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && mTitles.length == getCount()) {
            return mTitles[position];
        }
        Fragment fragment = mFragments[position];
        if (fragment instanceof PageTitle) {
            PageTitle pageTitle = (PageTitle) fragment;
            return pageTitle.getPageTitle();
        }
        return "";
    }
    public interface PageTitle {
        CharSequence getPageTitle();
    }
}
