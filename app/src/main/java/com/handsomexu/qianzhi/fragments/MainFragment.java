package com.handsomexu.qianzhi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.adapter.MainPagerAdapter;
import com.handsomexu.qianzhi.presenter.DoubanMomentPresenter;
import com.handsomexu.qianzhi.presenter.GuokrPresenter;
import com.handsomexu.qianzhi.presenter.ZhihuDailyPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class MainFragment extends Fragment {

    private Context mContext;
    private MainPagerAdapter mAdapter;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;
    private List<Fragment> mFragmentList;

    private ZhihuFragment mZhihuFragment;
    private GuokrFragment mGuokrFragment;
    private DoubanFragment mDoubanFragment;

    private ZhihuDailyPresenter zhihuDailyPresenter;
    private GuokrPresenter guokrPresenter;
    private DoubanMomentPresenter doubanMomentPresenter;


    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentList = new ArrayList<>();

        this.mContext = getActivity();

        //Fragment状态恢复
        if (savedInstanceState != null) {
            FragmentManager manager = getChildFragmentManager();
            mZhihuFragment = (ZhihuFragment) manager.getFragment(savedInstanceState, "zhihuFragment");
            mGuokrFragment = (GuokrFragment) manager.getFragment(savedInstanceState, "guokrFragment");
            mDoubanFragment = (DoubanFragment) manager.getFragment(savedInstanceState, "doubanFragment");
        }
        //创建Fragment实例
        mZhihuFragment = ZhihuFragment.newInstance();
        mGuokrFragment = GuokrFragment.newInstance();
        mDoubanFragment = DoubanFragment.newInstance();
        mFragmentList.add(mZhihuFragment);
        mFragmentList.add(mGuokrFragment);
        mFragmentList.add(mDoubanFragment);

        //创建Presenter实例
        zhihuDailyPresenter = new ZhihuDailyPresenter(mContext, mZhihuFragment);
        guokrPresenter = new GuokrPresenter(mContext, mGuokrFragment);
        doubanMomentPresenter = new DoubanMomentPresenter(mContext, mDoubanFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //初始化控件
        initView(view);

        //显示菜单
        setHasOptionsMenu(true);

        //当TabLayou位置为果壳精选时，隐藏FloatingActionButton
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                if (tab.getPosition() == 1) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;

    }

    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(3);
        mAdapter = new MainPagerAdapter(getFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feel_lucky:
                this.feelLucky();
        }
        return true;
    }

    //保存状态
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "zhihuFragment", mZhihuFragment);
        manager.putFragment(outState, "guokrFragment", mGuokrFragment);
        manager.putFragment(outState, "doubanFragment", mDoubanFragment);
    }

    private void feelLucky() {
        int type = new Random().nextInt(3);
        switch (type) {
            case 0:
                zhihuDailyPresenter.feelLucky();
                break;
            case 1:
                guokrPresenter.feelLucky();
                break;
            case 2:
                doubanMomentPresenter.feelLucky();
                break;
        }
    }

}
