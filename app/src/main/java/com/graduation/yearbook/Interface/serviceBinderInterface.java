package com.graduation.yearbook.Interface;

import android.content.ComponentName;
import android.os.IBinder;

/**
 * Created by hanwe on 15/10/21.
 */
public interface serviceBinderInterface
{
    public void binderResult(ComponentName name, IBinder service);
}
