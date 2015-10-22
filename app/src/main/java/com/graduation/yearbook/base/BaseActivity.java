package com.graduation.yearbook.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.graduation.yearbook.Compoment.receiver.FunctionReceiver;
import com.graduation.yearbook.Interface.serviceBinderInterface;
import com.graduation.yearbook.PhotoPage.PhotoStart;
import com.graduation.yearbook.PhotoPage.PhotoViewer;
import com.graduation.yearbook.manager.ServiceManager;
import com.graduation.yearbook.util.JLog;

/**
 * Created by hanwe on 15/10/21.
 */
public class BaseActivity extends Activity implements serviceBinderInterface
{
    protected String TAG;
    protected activityReceiver mFunctionReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TAG = ((Object) this).getClass().getSimpleName();
    }

    protected void registerReceiver()
    {
        mFunctionReceiver = new activityReceiver();
        if(TAG.contains(PhotoViewer.class.getSimpleName()))
        {
            ServiceManager.getInstance().bindeRregisterReceiverForTimeRuntine(this , mFunctionReceiver);
        }
    }

    @Override
    public void binderResult(ComponentName name, IBinder service)
    {

    }

    public class activityReceiver extends FunctionReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            receiverData(intent);
        }
    }

    protected void receiverData(Intent intent) {
    }

}
