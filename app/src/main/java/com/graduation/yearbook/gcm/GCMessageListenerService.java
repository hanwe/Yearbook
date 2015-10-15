package com.graduation.yearbook.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

public class GCMessageListenerService extends GcmListenerService
{
    private static final String TAG = "GCMessageListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data)
    {
        String message = data.getString("message");

        sendNotification(message);
    }

    private void sendNotification(String message)
    {
        //推播顯示視窗
    }
}
