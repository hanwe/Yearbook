package com.graduation.yearbook.Compoment.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.graduation.yearbook.manager.ServiceManager;

/**
 * Created by hanwe on 15/10/20.
 */
public class FunctionService extends Service
{
    private TimeRuntineBinder mTimeRuntineBinder = new TimeRuntineBinder();

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mTimeRuntineBinder;
    }


    public class TimeRuntineBinder extends Binder
    {
        private Thread mTimeThread;
        public void starTimeRuntineThread()
        {
            mTimeThread =  new Thread(TimeRunnable);
            mTimeThread.start();

//            handler.postDelayed(TimeRunnable, 2000);
        }

        public void stopTimeRuntineBinder()
        {
            handler.removeCallbacks(TimeRunnable);
            mTimeThread.interrupt();
        }
    }



    private Handler handler = new Handler();

    private boolean bFirst = true;
    private Runnable TimeRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            if(!bFirst)
            {
                Intent intent = new Intent();
                intent.setAction(ServiceManager.TYPE_TIMERUNTINE_RECEIVER);
                sendBroadcast(intent);
            }
            else
            {
                bFirst = false;
            }
            
            Log.d("hanwe", "Test");
            handler.postDelayed(TimeRunnable, 8000);
        }
    };

}
