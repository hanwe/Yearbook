package com.graduation.yearbook.PhotoPage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.graduation.yearbook.Compoment.service.FunctionService;
import com.graduation.yearbook.Interface.serviceBinderInterface;
import com.graduation.yearbook.R;
import com.graduation.yearbook.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hanwe on 2015/6/4.
 */
public class PhotoViewer extends BaseActivity
{
    private ArrayList<SimpleDraweeView> bitmapArray = new ArrayList<>();
    private String strPath;
    private ViewPager viewpager;
    private PhotpViewAdapter photpViewAdapter;
    private FunctionService.TimeRuntineBinder timeBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.album_main_photoview_viewpage);
        viewpager = (ViewPager) findViewById(R.id.photo_viewpager);

        initData();
        ReadPhotoFormFile();

        photpViewAdapter = new PhotpViewAdapter(bitmapArray);
        viewpager.setAdapter(photpViewAdapter);
    }

    private void initData()
    {
        //註冊receiver
        registerReceiver();
    }

    private void ReadPhotoFormFile()
    {
        bitmapArray.clear();

        File zipFile = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files/");
        File[] files = zipFile.listFiles();

        if (files != null && files.length > 0)
        {
            strPath = Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files/" + files[0].getName() + "/";

            for (int i = 0; i < 150; ++i)
            {
                String strIndexFormat = "%03d";
                File fimage = new File(strPath, String.format(strIndexFormat, i + 1) + ".jpg");

                //First
                SimpleDraweeView imageView_First = new SimpleDraweeView(this);
                imageView_First.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
                PointF focusPoint_First = new PointF();
                focusPoint_First.x = 0f;
                focusPoint_First.y = 0f;
                imageView_First.getHierarchy().setActualImageFocusPoint(focusPoint_First);
                DraweeController controller_First = Fresco.newDraweeControllerBuilder()
                                                          .setUri(Uri.fromFile(fimage))
                                                          .build();

                imageView_First.setController(controller_First);
                bitmapArray.add(imageView_First);

                //Second
                SimpleDraweeView imageView_Second = new SimpleDraweeView(this);
                imageView_Second.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
                PointF focusPoint_Second = new PointF();
                focusPoint_Second.x = 1f;
                focusPoint_Second.y = 1f;
                imageView_Second.getHierarchy().setActualImageFocusPoint(focusPoint_Second);
                DraweeController controller_Second = Fresco.newDraweeControllerBuilder()
                                                           .setUri(Uri.fromFile(fimage))
                                                           .build();
                imageView_Second.setController(controller_Second);
                bitmapArray.add(imageView_Second);
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        viewpager.setAdapter(null);
        bitmapArray.clear();

        //停止binder
        if(timeBinder != null)
        {
            timeBinder.stopTimeRuntineBinder();
        }
        System.gc();
    }

    @Override
    public void binderResult(ComponentName name, IBinder service)
    {
        super.binderResult(name, service);
        timeBinder = (FunctionService.TimeRuntineBinder)service;
        timeBinder.starTimeRuntineThread();
    }

    @Override
    protected void receiverData(Intent intent)
    {
        super.receiverData(intent);

        int totalcount = bitmapArray.size();
        int currentItem = viewpager.getCurrentItem();
        int toItem = currentItem + 1 == totalcount ? 0 : currentItem + 1;

        viewpager.setCurrentItem(toItem, true);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

}
