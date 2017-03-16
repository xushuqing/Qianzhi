package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class BookmarkFragment extends Fragment{
    private BookmarkFragment(){
    }
    public static BookmarkFragment newInstance() {

        Bundle args = new Bundle();

        BookmarkFragment fragment = new BookmarkFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
