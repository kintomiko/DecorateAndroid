package com.example.kin.decorate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.kin.decorate.common.ImageLoader;
import com.example.kin.decorate.common.Singleton;

import java.util.List;

/**
 * Created by kindai on 15/3/19.
 */
public class ImgAdapter extends ArrayAdapter<String> {
    private Context mContext;

    public ImgAdapter(Context mContext, int resource, int textViewResourceId, List<String> items) {
        super(mContext, resource, textViewResourceId, items);
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            img = new ImageView(mContext);
            img.setLayoutParams(new GridView.LayoutParams(400, 400));
            img.setPadding(8, 8, 8, 8);
            Singleton.getInstance(mContext).imageLoader.DisplayImage(
                    getItem(position).toString(),
                    R.drawable.ic_plusone_small_off_client,
                    img);
        }
        else {
            img = (ImageView) convertView;
        }
        img.setId(position);

        return img;
    }
}
