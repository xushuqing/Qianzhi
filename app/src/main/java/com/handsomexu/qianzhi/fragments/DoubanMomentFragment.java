package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomexu.qianzhi.R;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class DoubanMomentFragment extends Fragment{
    private DoubanMomentFragment(){
    }
    public static DoubanMomentFragment newInstance() {

        Bundle args = new Bundle();

        DoubanMomentFragment fragment = new DoubanMomentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zhihu,container,false);
    }
}
