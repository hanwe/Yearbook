package com.graduation.yearbook.PhotoPage;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by hanwe on 2015/6/4.
 */
public class PhotpViewAdapter extends PagerAdapter
{

    private ArrayList<SimpleDraweeView> bitmapArray = new ArrayList<SimpleDraweeView>();

    public PhotpViewAdapter( ArrayList<SimpleDraweeView> bitmapArray)
    {
        this.bitmapArray = bitmapArray;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(bitmapArray.get(position % bitmapArray.size()));

        Log.d("hanwe", "postion = " +  position);
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
        container.removeView((SimpleDraweeView)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}
