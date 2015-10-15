package com.graduation.yearbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.graduation.yearbook.Dialog.ChoseFunction;
import com.graduation.yearbook.PhotoPage.PhotoStart;
import com.graduation.yearbook.gcm.RegistrationIntentService;

import java.net.URLEncoder;


public class LoginAblumActivity extends Activity implements Animation.AnimationListener
{
    private String TAG = "LoginAblumActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private ImageButton imgbu_add;
    private Animation toLargeAnimation;
    private Animation toSmallAnimation;
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        imgbu_add = (ImageButton) findViewById(R.id.album_imgbu_add);

        toLargeAnimation = AnimationUtils.loadAnimation(LoginAblumActivity.this, R.anim.to_large);
        toSmallAnimation = AnimationUtils.loadAnimation(LoginAblumActivity.this, R.anim.to_small);

        toLargeAnimation.setAnimationListener(LoginAblumActivity.this);
        toSmallAnimation.setAnimationListener(LoginAblumActivity.this);
        imgbu_add.startAnimation(toSmallAnimation);

        addListenerOnButton();
        //註冊GCM
        registerGCM();
    }

    public void addListenerOnButton()
    {

        imgbu_add.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                switch (v.getId())
                {
                    case R.id.album_imgbu_add:
                    {
                        ChoseFunction m_ChoseFunction = new ChoseFunction(LoginAblumActivity.this, R.style.add_dialog);
                        m_ChoseFunction.show();

                        Window dialogWindow = m_ChoseFunction.getWindow();
                        android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        lp.width = 500;
                        lp.height = 500;
                        dialogWindow.setBackgroundDrawable(new BitmapDrawable());
                        dialogWindow.setAttributes(lp);


                        //                        Intent intent = new Intent();
                        //                        intent.setClass(MainActivity.this, PhotoPager.class);
                        //                        startActivity(intent);
                        //                        finish();
                        break;
                    }
                }

            }


        });

    }

    private void registerGCM()
    {
        if (checkPlayServices())
        {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        else
        {

        }
    }

    private boolean checkPlayServices()
    {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (apiAvailability.isUserResolvableError(resultCode))
            {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    @Override
    public void onAnimationStart(Animation animation)
    {

    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        if (animation.hashCode() == toLargeAnimation.hashCode())
        {
            imgbu_add.startAnimation(toSmallAnimation);
        }
        else
        {
            imgbu_add.startAnimation(toLargeAnimation);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {

    }

    // 接收 ZXing 掃描後回傳來的結果
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                // ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");

                Intent intentDownload = new Intent();
                intentDownload.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentDownload.setClass(LoginAblumActivity.this, PhotoStart.class);
                String strQueryURl = URLEncoder.encode("http://www.ilikeus.com.tw/static_file/ilikeus/zh_TW/album/4/20/4-20.zip");
                intentDownload.putExtra("ScanInfo", "http://www.ilikeus.com.tw/static_file/ilikeus/zh_TW/album/4/20/4-20.zip");
                startActivity(intentDownload); //這行一定要放在這函式的最後
            }
            else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "取消掃描", Toast.LENGTH_LONG).show();
            }
        }
    }
}
