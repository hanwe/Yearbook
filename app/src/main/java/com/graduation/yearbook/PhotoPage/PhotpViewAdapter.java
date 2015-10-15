package com.graduation.yearbook.PhotoPage;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by hanwe on 2015/6/4.
 */
public class PhotpViewAdapter extends PagerAdapter
{

    private ArrayList<ImageView> bitmapArray = new ArrayList<ImageView>();

    public PhotpViewAdapter( ArrayList<ImageView> bitmapArray)
    {
        this.bitmapArray = bitmapArray;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(bitmapArray.get(position % bitmapArray.size()));
        return bitmapArray.get(position % bitmapArray.size());
    }

    @Override
    public int getCount()
    {
        return bitmapArray.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(bitmapArray.get(position % bitmapArray.size()));
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}
