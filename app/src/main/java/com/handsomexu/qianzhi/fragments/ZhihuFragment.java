package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.adapter.MainPagerAdapter;
import com.handsomexu.qianzhi.adapter.ZhihuDailyNewsAdapter;
import com.handsomexu.qianzhi.bean.ZhihuDailyNews;
import com.handsomexu.qianzhi.interfaces.OnRecyclerViewOnClickListener;
import com.handsomexu.qianzhi.interfaces.ZhihuDailyContract;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class ZhihuFragment extends Fragment implements ZhihuDailyContract.View {

    public static ZhihuDailyContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;
    private TabLayout mTabLayout;
    private FloatingActionButton mFab;

    private ZhihuDailyNewsAdapter mAdapter;

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;

    private ZhihuFragment() {
    }

    public static ZhihuFragment newInstance() {
        return new ZhihuFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        presenter.start();
        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView(View view) {
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);

        //豆瓣精选的fab点击事件放到知乎fragment
        //fab属于activity里的View
        //如果每个fragment都去设置监听，将导致先钱设置的listener失效
        //如果将监听放到pagerAdapter中，会引起recyclerView和fab的监听冲突，fab监听失效
        //根据tabLayout的位置选择不同的dialog;
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTabLayout.getSelectedTabPosition() == 0) {
                    mCalendar.set(mYear, mMonth, mDay);
                    DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            mCalendar.clear();
                            mCalendar.set(year, monthOfYear, dayOfMonth);
                            long time = mCalendar.getTimeInMillis();
                            presenter.loadPosts(time, true);
                        }
                    }, mYear, mMonth, mDay);

                    dialog.setMaxDate(Calendar.getInstance());
                    Calendar minDate = Calendar.getInstance();
                    minDate.set(2013, 5 - 1, 20);// 2013.5.20是知乎日报生日
                    dialog.setMinDate(minDate);
                    dialog.vibrate(false);//不要震动
                    dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                } else if (mTabLayout.getSelectedTabPosition() == 2) {
                    ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
                    MainPagerAdapter mainPagerAdapter = (MainPagerAdapter) viewPager.getAdapter();
                    mainPagerAdapter.getDoubanFragment().showPickDialog();
                }
            }
        });


        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView mRecyclerView, int newState) {
                //当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mFab.show();
                    //获取最后一个完全显示的item的position
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    //判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        mCalendar.set(mYear, mMonth, --mDay);
                        presenter.loadMore(mCalendar.getTimeInMillis());
                    }
                }
                super.onScrollStateChanged(mRecyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {

                //隐藏或者显示fab
                if (dy > 0) {
                    isSlidingToLast = true;
                    mFab.hide();
                } else {
                    mFab.show();
                }
                super.onScrolled(mRecyclerView, dx, dy);
            }
        });

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

    }


    @Override
    public void showError() {
        Snackbar.make(mFab, R.string.fail_please_retry, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ZhihuFragment.presenter.refresh();
                        GuokrFragment.presenter.refresh();
                        DoubanFragment.presenter.refresh();
                    }
                }).show();
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showUpdateComplete() {
        Toast.makeText(getContext(), R.string.update_complete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResults(final ArrayList<ZhihuDailyNews.Story> list) {
        if (mAdapter == null) {
            mAdapter = new ZhihuDailyNewsAdapter(getContext(), list);
            mAdapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    presenter.startReading(position);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
