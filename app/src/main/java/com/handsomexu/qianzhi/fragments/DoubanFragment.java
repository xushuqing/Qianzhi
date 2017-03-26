package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.adapter.DoubanMomentAdapter;
import com.handsomexu.qianzhi.bean.DoubanMoment;
import com.handsomexu.qianzhi.interfaces.DoubanMomentContract;
import com.handsomexu.qianzhi.interfaces.OnRecyclerViewOnClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class DoubanMomentFragment extends Fragment implements DoubanMomentContract.View {

    public static DoubanMomentContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;
    private FloatingActionButton fab;
    private DoubanMomentAdapter mAdapter;

    private Calendar calendar;
    private int mYear;
    private int mMonth;
    private int mDay;

    private DoubanMomentFragment() {
    }

    public static DoubanMomentFragment newInstance() {
        DoubanMomentFragment fragment = new DoubanMomentFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        presenter.start();
        return view;
    }

    @Override
    public void showError() {
        Snackbar.make(fab, R.string.fail_please_retry, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ZhihuFragment.presenter.refresh();
                        GuokrFragment.presenter.refresh();
                        DoubanMomentFragment.presenter.refresh();
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
    public void showResults(ArrayList<DoubanMoment.Post> list) {
        if (mAdapter == null) {
            mAdapter = new DoubanMomentAdapter(getContext(), list);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setRecyclerViewOnItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    presenter.startReading(position);
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    public void showPickDialog() {
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                calendar.clear();
                calendar.set(year, monthOfYear, dayOfMonth);
                long time = calendar.getTimeInMillis();
                presenter.loadPosts(time, true);
            }
        }, mYear, mMonth, mDay);

        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        minDate.set(2014, 5 - 1, 12);//豆瓣一刻生日
        dialog.setMinDate(minDate);
        dialog.vibrate(false);//不要震动
        dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void setPresenter(DoubanMomentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //The RecyclerView is not currently scrolling.(没有滑动)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();//隐藏fab
                    int itemCount = manager.getItemCount();
                    int lastItem = manager.findLastVisibleItemPosition();
                    if (lastItem == (itemCount - 1) && isSlidToLast) {//滑到最后一个item
                        calendar.set(mYear, mMonth, --mDay);
                        presenter.loadMore(calendar.getTimeInMillis());
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                    isSlidToLast = true;
                } else {
                    fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }
}
