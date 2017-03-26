package com.handsomexu.qianzhi.ClassImpl;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.handsomexu.qianzhi.interfaces.OnStringListener;
import com.handsomexu.qianzhi.network.VolleySingleton;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class StringModelImpl {
    private Context mContext;
    private OnStringListener mOnStringListener;

    public StringModelImpl(Context context) {
        this.mContext = context;
    }

    public void load(String url, final OnStringListener listener) {
        mOnStringListener = listener;

        StringRequest request = new StringRequest(url, onSuccessListener, onErrorListener);

        VolleySingleton.getVolleySingleton(mContext).addToRequestQueue(request);
    }


    Response.Listener onSuccessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            mOnStringListener.onSuccess(response);
        }
    };

    Response.ErrorListener onErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mOnStringListener.onFailure(error);
        }
    };
}
