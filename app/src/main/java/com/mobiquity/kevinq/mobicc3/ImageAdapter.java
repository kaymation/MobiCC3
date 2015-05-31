package com.mobiquity.kevinq.mobicc3;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

/**
 * Created by mikeq on 5/30/2015.
 */
public class ImageAdapter extends ArrayAdapter<ListFiles.Viewtry> {
    private Context mContext;
    private ListFiles.Viewtry[] mThumbs;


    public ImageAdapter(Context c, int grid, ListFiles.Viewtry[] b) {
        super(c,grid,b);
        mThumbs = b;
    }

    public int getCount() {
        return mThumbs.length;
    }


    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(mThumbs[position].bitmap);
        imageView.setTag(1,mThumbs[position].entry);

        final OnClickListener imgViewListener = new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        imageView.setOnClickListener(imgViewListener);
        return imageView;
    }
}
