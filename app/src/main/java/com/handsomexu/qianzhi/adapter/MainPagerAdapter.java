package com.handsomexu.qianzhi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.handsomexu.qianzhi.fragments.DoubanFragment;

import java.util.List;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private String[] mTitles;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
        mTitles = new String[]{"知乎日报", "果壳阅读", "豆瓣一刻"};
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public DoubanFragment getDoubanFragment() {
        return (DoubanFragment) mFragmentList.get(2);
    }
}
