package com.graduation.yearbook.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.graduation.yearbook.Compoment.receiver.FunctionReceiver;
import com.graduation.yearbook.Compoment.service.FunctionService;
import com.graduation.yearbook.Interface.serviceBinderInterface;
import com.graduation.yearbook.PhotoPage.PhotoViewer;

/**
 * Created by hanwe on 15/10/21.
 */
public class ServiceManager
{
    private static ServiceManager serviceManagerInstance;
    public static final String TYPE_TIMERUNTINE_RECEIVER = "android.yearbook.timeruntine";
    private static Intent intent;
    private serviceBinderInterface mserviceBinderInterface;

    public static ServiceManager getInstance()
    {
        if (serviceManagerInstance == null)
        {
            serviceManagerInstance = new ServiceManager();
        }
        return serviceManagerInstance;
    }


    //Binder
    public void bindeRregisterReceiverForTimeRuntine(Context context, FunctionReceiver mFunctionReceiver)
    {
        mserviceBinderInterface = (serviceBinderInterface) context;

        IntentFilter mIntimeruntine = new IntentFilter(TYPE_TIMERUNTINE_RECEIVER);
        context.registerReceiver(mFunctionReceiver, mIntimeruntine);
        intent = new Intent(context, FunctionService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mserviceBinderInterface.binderResult(name, service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    };
}
