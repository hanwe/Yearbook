package com.graduation.yearbook.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.graduation.yearbook.R;
import java.io.IOException;

/**
 * Created by hanwe on 15/10/13.
 */
public class RegistrationIntentService extends IntentService
{
    private static final String TAG = "RegistrationIntentService";

    public RegistrationIntentService()
    {
        super(TAG);
    }

    public RegistrationIntentService(String name)
    {
        super(name);
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {

        String token = null;
        try
        {
            InstanceID instanceID = InstanceID.getInstance(this);
            token = instanceID.getToken(getString(R.string.Sender_Id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.d(TAG, "GCM Registration Token: " + token);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
