package com.graduation.yearbook.Compoment.view.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by hanwe on 15/10/20.
 */
public class FunctionIntentService extends IntentService
{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FunctionIntentService(String name)
    {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {

    }
}
