package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomexu.qianzhi.R;
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

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private TabLayout tabLayout;
    private FloatingActionButton fab;

    private ZhihuDailyNewsAdapter adapter;

    private ZhihuDailyContract.Presenter presenter;

    private Calendar calendar;
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
        this.calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth =calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);

        presenter.start();

        //豆瓣精选的fab点击事件放到知乎fragment
        //fab属于activity里的View
        //如果每个fragment都去设置监听，将导致先钱设置的listener失效
        //如果将监听放到pagerAdapter中，会引起recyclerView和fab的监听冲突，fab监听失效
        //根据tabLayout的位置选择不同的dialog;
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    calendar.set(mYear, mMonth, mDay);
                    DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.clear();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            long time = calendar.getTimeInMillis();
                            presenter.loadPosts(time, true);
                        }
                    }, mYear,mMonth,mDay);

                    dialog.setMaxDate(Calendar.getInstance());
                    Calendar minDate = Calendar.getInstance();
                    minDate.set(2013, 5, 20);// 2013.5.20是知乎日报生日
                    dialog.setMinDate(minDate);
                    dialog.vibrate(false);//不要震动
                    dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    DoubanMomentFragment.newInstance().showPickDialog();
                }
            }
        });


        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的item的position
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    //判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        calendar.set(mYear, mMonth, --mDay);
                        presenter.loadMore(calendar.getTimeInMillis());
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                isSlidingToLast = dy > 0;
                //隐藏或者显示fab
                if (dy > 0) {
                    fab.hide();
                }
                fab.show();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView(View view) {
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void showError() {
        Snackbar.make(fab, "加载失败，请检查网络后重试", Snackbar.LENGTH_SHORT)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                }).show();
    }

    @Override
    public void showLoading() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });

    }

    @Override
    public void stopLoading() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    @Override
    public void showResults(final ArrayList<ZhihuDailyNews.Story> list) {
            adapter = new ZhihuDailyNewsAdapter(getContext(), list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
    }
}
