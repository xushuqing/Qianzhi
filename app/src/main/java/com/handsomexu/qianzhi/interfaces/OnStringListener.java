package com.handsomexu.qianzhi.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public interface OnStringListener {

    /**
     * 请求成功时回调
     * @param result
     */
    void onSuccess(String result);

    /**
     * 请求失败时回调
     * @param error
     */
    void onFailure(VolleyError error);
}
