package com.example.kin.decorate.common;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.kin.decorate.ItemListActivity;
import com.example.kin.decorate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kin on 3/18/15.
 */
public class DecorateUtils {

    public static String SHARED_PREFERENCES_NAME = "decorate";

    public static String httpBuildQuery(Map map)
    {
        StringBuilder stringbuilder = new StringBuilder();
        int i = 0;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext())
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String s;
            if (i == 0)
            {
                s = "";
            } else
            {
                s = "&";
            }
            if (entry.getValue() == null)
            {
                stringbuilder.append((new StringBuilder()).append(s).append((String)entry.getKey()).append("=").toString());
            } else
            {
                stringbuilder.append((new StringBuilder()).append(s).append((String)entry.getKey()).append("=").append((String)entry.getValue()).toString());
            }
            i++;
        }
        return stringbuilder.toString();
    }

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
    public static String getUserInfo(Context context, String s, String s1)
    {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0).getString(s, s1);
    }

    public static void notify(final Context context){
        final String url = new StringBuilder("http://123.57.248.228:8080/monitor/get_changes/?id=")
                .append(DecorateUtils.getUserInfo(context, "uid", "")).toString();
        //get changes
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(
                url, new Response.Listener<JSONArray>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String msg = jsonObject.getJSONObject("fields").getString("message");
                        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification n = new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.common_signin_btn_icon_dark)
                                .setContentTitle("有进度更新")
                                .setContentText(msg)
                                .setAutoCancel(true).build();
                        nm.notify(1, n);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                int i=0;
            }
        });

        Singleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest1);
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}
