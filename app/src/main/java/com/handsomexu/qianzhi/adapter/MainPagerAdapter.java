package com.handsomexu.qianzhi.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.handsomexu.qianzhi.fragments.DoubanMomentFragment;
import com.handsomexu.qianzhi.fragments.GuokrFragment;
import com.handsomexu.qianzhi.fragments.ZhihuDailyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    public MainPagerAdapter(FragmentManager fm, Context context
            , ZhihuDailyFragment zhihuDailyFragment
            , GuokrFragment guokrFragment
            , DoubanMomentFragment doubanMomentFragment) {
        super(fm);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(zhihuDailyFragment);
        mFragmentList.add(guokrFragment);
        mFragmentList.add(doubanMomentFragment);

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
