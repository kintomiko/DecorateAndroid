package com.example.kin.decorate.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by kin on 3/18/15.
 */
public class Singleton {
    private Singleton(Context context){ mCtx=context; }
    private static Singleton instance;
    private static Context mCtx;

    public static synchronized Singleton getInstance(Context context){
        if(instance == null){
            instance = new Singleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx);
        }
        return mRequestQueue;
    }

    private RequestQueue mRequestQueue;

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
