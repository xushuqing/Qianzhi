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

import java.util.Random;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class MainFragment extends Fragment{

    private Context context;
    private MainPagerAdapter adapter;

    private TabLayout tabLayout;

    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private DoubanMomentFragment doubanMomentFragment;

    private ZhihuDailyPresenter zhihuDailyPresenter;
    private GuokrPresenter guokrPresenter;
    private DoubanMomentPresenter doubanMomentPresenter;


    public MainFragment(){}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();

        //Fragment状态恢复
        if(savedInstanceState != null){
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState,"zhihu");
            guokrFragment = (GuokrFragment) manager.getFragment(savedInstanceState,"guokr");
            doubanMomentFragment = (DoubanMomentFragment) manager.getFragment(savedInstanceState,"douban");
        }else{
            //创建Fragment实例
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            guokrFragment = GuokrFragment.newInstance();
            doubanMomentFragment = DoubanMomentFragment.newInstance();
        }

        //创建Presenter实例
        zhihuDailyPresenter = new ZhihuDailyPresenter(context,zhihuDailyFragment);
        guokrPresenter = new GuokrPresenter(context,guokrFragment);
        doubanMomentPresenter = new DoubanMomentPresenter(context,doubanMomentFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_main,container,false);


        //初始化控件
        initView(view);

        //显示菜单
        setHasOptionsMenu(true);

        //当TabLayou位置为果壳精选时，隐藏FloatingActionButton
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                if(tab.getPosition() == 1){
                    fab.hide();
                }else {
                    fab.show();
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
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        //设置离线数为3
        viewPager.setOffscreenPageLimit(3);
        adapter = new MainPagerAdapter(getChildFragmentManager(),context,zhihuDailyFragment
        ,guokrFragment,doubanMomentFragment);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_feel_lucky:
                feelLucky();
        }
        return true;
    }

    //保存状态

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState,"zhihu",zhihuDailyFragment);
        manager.putFragment(outState,"guokr",guokrFragment);
        manager.putFragment(outState,"douban",doubanMomentFragment);
    }

    private void feelLucky() {
        int type = new Random().nextInt(3);
        switch (type){
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
    public MainPagerAdapter getAdapter(){
        return adapter;
    }
}
