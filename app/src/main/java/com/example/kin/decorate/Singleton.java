package com.example.kin.decorate;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by kin on 3/18/15.
 */
public class Singleton {
    private Singleton(Context context){ mCtx=context; }
    private static Singleton instance;
    private static Context mCtx;
    public static String SHARED_PREFERENCES_NAME = "decorate";

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
    public static void setUserInfo(Context context, Map map)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).edit();
        java.util.Map.Entry entry;
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); editor.putString((String)entry.getKey(), (String)entry.getValue()))
        {
            entry = (java.util.Map.Entry)iterator.next();
        }

        editor.commit();
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
