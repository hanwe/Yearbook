package com.graduation.yearbook.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by hanwe on 15/10/13.
 */
public class GCMInstanceIDListenerService extends InstanceIDListenerService
{
    private static final String TAG = "GCMInstanceIDListenerService";

    @Override
    public void onTokenRefresh()
    {
        super.onTokenRefresh();

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
