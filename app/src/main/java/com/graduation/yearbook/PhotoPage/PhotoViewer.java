package com.graduation.yearbook.PhotoPage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.graduation.yearbook.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hanwe on 2015/6/4.
 */
public class PhotoViewer extends Activity implements GestureDetector.OnGestureListener, View.OnTouchListener
{
    private ArrayList<SimpleDraweeView> bitmapArray = new ArrayList<>();
    private GestureDetector gestureDetector;
    private String strPath;
    private ViewPager viewpager;
    private PhotpViewAdapter photpViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.album_main_photoview_viewpage);
        viewpager = (ViewPager) findViewById(R.id.photo_viewpager);

        ReadPhotoFormFile();

        photpViewAdapter = new PhotpViewAdapter(bitmapArray);
        viewpager.setAdapter(photpViewAdapter);
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
        System.gc();
    }

    //    private void ReadPhotoFormFile()
//    {
//        bitmapArray.clear();
//
//        File zipFile = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files/");
//        File[] files = zipFile.listFiles();
//
//        strPath = Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files/" + files[0].getName() + "/";
//        gestureDetector = new GestureDetector(this);
//        for (int i = 0; i < 150; ++i)
//        {
//            String strIndexFormat = "%03d";
//            File fimage = new File(strPath, String.format(strIndexFormat, i + 1) + ".jpg");
//
//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//            Bitmap bitmap = BitmapFactory.decodeFile(fimage.getAbsolutePath(), bmOptions);
//
//            //First
//            Bitmap bitFirst = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 2, bitmap.getHeight());
//            ImageView ivFirst = new ImageView(this);
////            ivFirst.setImageBitmap(bitFirst);
////            ivFirst.setScaleType(ImageView.ScaleType.FIT_XY);
//
//            Glide.with(this).load(fimage).into(ivFirst);
//
//            view_photo.addView(ivFirst, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//            //Second
//            Bitmap bitSecond = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2, 0, bitmap.getWidth() / 2, bitmap.getHeight());
//            ImageView ivSecond = new ImageView(this);
////            ivSecond.setImageBitmap(bitSecond);
////            ivSecond.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(this).load(fimage).into(ivSecond);
//            view_photo.addView(ivSecond, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        }
//
////        for (int i = 3; i < 150; ++i)
////        {
////            ImageView ivFirst = new ImageView(this);
////            view_photo.addView(ivFirst, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
////            ImageView ivSecond = new ImageView(this);
////            view_photo.addView(ivSecond, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
////        }
//
//        view_photo.setAutoStart(true);         // 设置自动播放功能（点击事件，前自动播放）
//
//        view_photo.setOnTouchListener(this);
//
//        view_photo.setFlipInterval(3000);
//        if (view_photo.isAutoStart() && !view_photo.isFlipping())
//        {
//            Animation lInAnim = AnimationUtils.loadAnimation(PhotoViewer.this, R.anim.push_left_in);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
//            Animation lOutAnim = AnimationUtils.loadAnimation(PhotoViewer.this, R.anim.push_left_out);     // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
//
//            view_photo.setInAnimation(lInAnim);
//            view_photo.setOutAnimation(lOutAnim);
//            view_photo.startFlipping();
//        }
//    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

//    private void setViewDynamic(ViewFlipper viewFlipper)
//    {
//        String strIndexFormat = "%03d";
//        File fimage = new File(strPath, String.format(strIndexFormat, viewFlipper.getDisplayedChild() / 2 + 1) + ".jpg");
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        Bitmap bitmap = BitmapFactory.decodeFile(fimage.getAbsolutePath(), bmOptions);
//
//        Bitmap photoBitmap;
//        if (viewFlipper.getDisplayedChild() % 2 == 0)
//        {
//            photoBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 2, bitmap.getHeight());
//        }
//        else
//        {
//            photoBitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2, 0, bitmap.getWidth() / 2, bitmap.getHeight());
//        }
//
//        ImageView imageView = (ImageView) viewFlipper.getChildAt(viewFlipper.getDisplayedChild());
////        imageView.setImageBitmap(photoBitmap);
//
//        Glide.with(this).load(fimage).into(imageView);
////        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////        view_photo.removeAllViews();
////        view_photo.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        return false;
    }
}
