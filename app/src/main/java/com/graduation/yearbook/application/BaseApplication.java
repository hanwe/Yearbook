package com.graduation.yearbook.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.fabric.sdk.android.Fabric;

/**
 * Created by hanwe on 15/8/28.
 */
public class BaseApplication extends Application
{
    private static Context mContext;

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

        MultiDex.install(getBaseContext());
        Fabric.with(this, new Crashlytics());

        mContext = getApplicationContext();
    }
}
