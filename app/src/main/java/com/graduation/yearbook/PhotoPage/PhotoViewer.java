package com.graduation.yearbook.PhotoPage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.graduation.yearbook.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hanwe on 2015/6/4.
 */
public class PhotoViewer extends Activity implements GestureDetector.OnGestureListener, View.OnTouchListener
{
    private ArrayList<ImageView> bitmapArray = new ArrayList<ImageView>();
    private GestureDetector gestureDetector;
    private String strPath;
    private ViewPager viewpager;
    private PhotpViewAdapter photpViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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

//        File DirDiPath = new File( Environment.getExternalStorageDirectory() + "/media");
        if(files != null && files.length > 0)
        {
            strPath = Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files/" + files[0].getName() + "/";

            for (int i = 0; i < 150; ++i)
            {
                String strIndexFormat = "%03d";
                File fimage = new File(strPath, String.format(strIndexFormat, i + 1) + ".jpg");

                //First
                ImageView imageView_First = new ImageView(this);
                Glide.with(this).load(fimage).into(imageView_First);
                imageView_First.setScaleType(ImageView.ScaleType.FIT_XY);
                bitmapArray.add(imageView_First);

                //Second
                ImageView imageView_Second = new ImageView(this);
                Glide.with(this).load(fimage).into(imageView_Second);
                imageView_Second.setScaleType(ImageView.ScaleType.FIT_XY);
                bitmapArray.add(imageView_Second);
            }
        }
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

//        view_photo.getInAnimation().setAnimationListener(new Animation.AnimationListener()
//        {
//            public void onAnimationStart(Animation animation)
//            {
//                JLog.d("目前page =" + view_photo.getDisplayedChild());
//                JLog.d("ViewFilpper  Start");
//
////                setViewDynamic(view_photo);
//            }
//
//            public void onAnimationRepeat(Animation animation)
//            {
//                JLog.d("目前page =" + view_photo.getDisplayedChild());
//                JLog.d("ViewFilpper  Repeat");
//            }
//
//            public void onAnimationEnd(Animation animation)
//            {
//                JLog.d("目前page =" + view_photo.getDisplayedChild());
//                JLog.d("ViewFilpper  End");
//            }
//        });
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
//        view_photo.stopFlipping();             // 点击事件后，停止自动播放
//        view_photo.setAutoStart(false);
        return gestureDetector.onTouchEvent(event);         // 注册手势事件
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
//        view_photo.setAutoStart(true);         // 设置自动播放功能（点击事件，前自动播放）
//
//        view_photo.setFlipInterval(3000);
//        if (view_photo.isAutoStart() && !view_photo.isFlipping())
//        {
//            view_photo.startFlipping();
//        }
//
//
//        if (e2.getX() - e1.getX() > 120)
//        {            // 从左向右滑动（左进右出）
//            Animation rInAnim = AnimationUtils.loadAnimation(PhotoViewer.this, R.anim.push_right_in);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）
//            Animation rOutAnim = AnimationUtils.loadAnimation(PhotoViewer.this, R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
//
//            view_photo.setInAnimation(rInAnim);
//            view_photo.setOutAnimation(rOutAnim);
//
//            JLog.d("目前page =" + view_photo.getDisplayedChild());
//            view_photo.showPrevious();
//            return true;
//        }
//        else if (e2.getX() - e1.getX() < -120)
//        {        // 从右向左滑动（右进左出）
//            Animation lInAnim = AnimationUtils.loadAnimation(PhotoViewer.this, R.anim.push_left_in);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
//            Animation lOutAnim = AnimationUtils.loadAnimation(PhotoViewer.this, R.anim.push_left_out);     // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
//
//            view_photo.setInAnimation(lInAnim);
//            view_photo.setOutAnimation(lOutAnim);
//
//            JLog.d("目前page =" + view_photo.getDisplayedChild());
//            view_photo.showNext();
//            return true;
//        }


        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        return false;
    }
}
