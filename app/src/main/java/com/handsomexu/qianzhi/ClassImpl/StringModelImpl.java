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
    private Context context;

    public StringModelImpl(Context context){
        this.context = context;
    }

    public void load(String url, final OnStringListener listener){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
            }
        });
            VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }

}
