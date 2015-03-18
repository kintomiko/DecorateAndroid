package com.example.kin.decorate;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by kin on 3/18/15.
 */
public class DecorateUtils {
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
}
