package com.graduation.yearbook.application;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by hanwe on 15/8/28.
 */
public class BaseApplication extends Application
{
    protected static String TAG = "";
    private static Context mContext;
    public String strFileName = "";

    public static Context getContext()
    {
        return mContext;
    }

    public static BaseApplication getInstance()
    {
        return ((BaseApplication) mContext);
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mContext = getApplicationContext();
    }
}
