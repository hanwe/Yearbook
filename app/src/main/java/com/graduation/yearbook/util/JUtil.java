package com.graduation.yearbook.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.IntentCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

import com.graduation.yearbook.application.BaseApplication;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author JosephWang
 */

/***
 * Code Name Version
 * Api level (no code name) 1.0
 * API level 1 (no code name) 1.1
 * API level 2 Cupcake 1.5
 * API level 3, NDK 1 Donut 1.6
 * API level 4, NDK 2 Eclair 2.0
 * API level 5 Eclair 2.0.1
 * API level 6 Eclair 2.1
 * API level 7, NDK 3 Froyo 2.2.x
 * API level 8, NDK 4 Gingerbread 2.3 - 2.3.2
 * API level 9, NDK 5 Gingerbread 2.3.3 - 2.3.7
 * API level 10 Honeycomb 3.0
 * API level 11 Honeycomb 3.1
 * API level 12,NDK 6 Honeycomb 3.2.x
 * API level 13 Ice Cream Sandwich 4.0.1 - 4.0.2
 * API level 14,NDK 7 Ice Cream Sandwich 4.0.3 - 4.0.4
 * API level 15,NDK 8 Jelly Bean 4.1.x
 * API level 16 Jelly Bean 4.2.x
 * API level 17 Jelly Bean 4.3.x
 * API level 18 KitKat 4.4 - 4.4.2
 * API level 19 KitKat (for wearable)4.4 API level 20 Lollipop 5.0 API level 21
 */
public class JUtil {
    public static ThreadFactory threadFactory = Executors.defaultThreadFactory();

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // km (change this constant to get miles)
        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLon = (lon2 - lon1) * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d * 1000;
    }

    /**
     * ???�???��??��?第�?�? * @param input
     *
     * @return result
     */
    public static int roundingFloatToInt(float input) {
        BigDecimal decimal = new BigDecimal(input);
        decimal.setScale(1, BigDecimal.ROUND_HALF_UP);
        return decimal.intValue();
    }

    @SuppressWarnings("deprecation")
    public static void setTextEllipsizeToEnd(final TextView textView) {
        if (textView != null && textView.getText() != null && textView.getText().toString() != null && textView.getText().toString().length() > 0) {
            ViewTreeObserver vto = textView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    textView.setVisibility(View.INVISIBLE);
                    textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    CharSequence charSequence = TextUtils.ellipsize(textView.getText(), textView.getPaint(), textView.getMeasuredWidth() - 5, TextUtils.TruncateAt.END);
                    textView.setText(charSequence);
                    textView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public static RejectedExecutionHandler executionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.remove(r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static void allowNetWorkRunOnUI() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static boolean checkInternet(Context context) {
        if (context == null)
            return true;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * corePoolSize : the number of threads to keep in the pool, even if they
     * are idle, unless allowCoreThreadTimeOut is set .</br> maximumPoolSize :
     * the maximum number of threads to allow in the pool.</br> keepAliveTime :
     * when the number of threads is greater than the core, this is the maximum
     * time that excess idle threads will wait for new tasks before terminating.
     * </br> unit : the time unit for the keepAliveTime argument workQueue : the
     * queue to use for holding tasks before they are executed. This queue will
     * hold only the Runnable tasks submitted by the execute method.
     * threadFactory : the factory to use when the executor creates a new thread
     * handler : the handler to use when execution is blocked because the thread
     * bounds and queue capacities are reached
     *
     * @return ThreadPoolExecutor
     */
    public static ThreadPoolExecutor getNewThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int lockingQueueSize) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 300, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<Runnable>(lockingQueueSize), threadFactory, executionHandler);
    }

    public static byte[] convertIntToByteArray(int res) {
        return ByteBuffer.allocate(4).putInt(res).array();
    }


    public static String[] listToArray(List<String> data) {
        String[] result = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i);
        }
        return result;
    }

    public static boolean hasInternet()
    {
        return hasInternet(BaseApplication.getContext());
    }

    public static boolean hasInternet(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable())
            return false;
        else
            return true;
    }

    public static int dip2px(float dipValue) {
        return dip2px(BaseApplication.getContext(), dipValue);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return px2dip(BaseApplication.getContext(),  pxValue);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * **** android.permission.GET_TASKS 已被depreciatin 需要移除 *************
     */
    public static boolean isLastOneActivity(Class<? extends  FragmentActivity> act) {
        return isLastOneActivity(BaseApplication.getContext(), act);
    }

    public static boolean isLastOneActivity(Context ctx, Class<? extends  FragmentActivity> act) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 21) {
            List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses();
            if (!tasks.isEmpty() && tasks.size() > 0) {
                if (tasks.get(0).processName.contains(act.getSimpleName())) {
                    return true;
                }
            }
        } else {
            List<RunningTaskInfo> tasks = manager.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                if (tasks.get(0).numActivities == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void performTouchDown(View view) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        float x = view.getLeft() + 1.1f;
        float y = view.getTop() + 2.2f;
        // List of meta states found here:
        // developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, metaState);
        view.dispatchTouchEvent(motionEvent);
    }

    public static void performTouchUp(View view) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        float x = 0.1f;
        float y = 0.2f;
        // List of meta states found here:
        // developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState);
        view.dispatchTouchEvent(motionEvent);
    }

    public static void performTouchMove(View view) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        float x = view.getLeft() + 1.1f;
        float y = view.getTop() + 2.2f;
        // List of meta states found here:
        // developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, metaState);
        view.dispatchTouchEvent(motionEvent);
    }

    public static IntentFilter getIntentFilter(String... action) {
        IntentFilter filter = new IntentFilter();
        for (String each : action) {
            filter.addAction(each);
        }
        return filter;
    }

    public static IntentFilter getIntentFilter(List<String> action) {
        IntentFilter filter = new IntentFilter();
        for (String each : action) {
            filter.addAction(each);
        }
        return filter;
    }

    public static boolean notEmpty(Collection<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean notEmpty(Map<?, ?> map) {
        if (map != null && map.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean notEmpty(Object[] data) {
        if (data != null && data.length > 0) {
            return true;
        }
        return false;
    }

    public static boolean notEmpty(Set<?> set) {
        if (set != null && set.size() > 0) {
            return true;
        }
        return false;
    }

    public static void startClearTopIntent(FragmentActivity act, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            act.startActivity(intent);
        } else {
            ComponentName cn = intent.getComponent();
            Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
            act.startActivity(mainIntent);
        }
        act.finish();
    }


    public static boolean hasStringInIntentData(Intent data, String tag) {
        if (data != null && data.getExtras() != null) {
            return TextUtils.isEmpty(data.getExtras().getString(tag));
        }
        return false;
    }

    public static boolean hasIntentExtras(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            return true;
        }
        return false;
    }

    public static boolean hasIntentData(Intent intent) {
        if (intent != null && intent.getData() != null) {
            return true;
        }
        return false;
    }



    public static boolean hasScheme(Intent intent, String scheme) {
        if (intent != null && intent.getData() != null && !TextUtils.isEmpty(intent.getScheme()) && intent.getScheme().equals(scheme)) {
            return true;
        }
        return false;
    }



    public static String getStringFromIntent(FragmentActivity act, String tag)
    {
        if (act.getIntent() != null && act.getIntent().getExtras() != null) {
            return act.getIntent().getExtras().getString(tag);
        }
        return "";
    }

    @SuppressWarnings("deprecation")
    public static void setBackGround(View v, int drawable) {
        v.setBackgroundDrawable(BaseApplication.getContext().getResources().getDrawable(drawable));
    }

    public static boolean notEmpty(SparseArray<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public static Comparator<Integer> getIntegerComparator() {
        return new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return lhs.compareTo(rhs);
            }
        };
    }

    public static boolean isAPPInstall(String packageName)
    {
        return isAPPInstall(BaseApplication.getContext(), packageName);
    }

    public static boolean isAPPInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static boolean isAppOnForeground() {
        return isAppOnForeground(BaseApplication.getContext());
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager mActivityManager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        String mPackageName = context.getPackageName();
        List<RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            if (mPackageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void objectArrayToArryList(ArrayList list, Object[] resource)
    {
        if (notEmpty(resource))
        {
            for (Object each : resource)
            {
                list.add(each);
            }
        }
    }

    public static boolean smallThanTens(int res) {
        if (res < 10 && res > 0) {
            return true;
        }
        return false;
    }

    public static boolean smallThanTens(String res) {
        if (TextUtils.isDigitsOnly(res) && Integer.parseInt(res) < 10 && Integer.parseInt(res) > 0) {
            return true;
        }
        return false;
    }

    public static  String getString(int stringId)
    {
        return BaseApplication.getContext().getResources().getString(stringId);
    }

    public static  int getColor(int colorId)
    {
        return BaseApplication.getContext().getResources().getColor(colorId);
    }

    public static int getInt(int intId)
    {
        return BaseApplication.getContext().getResources().getInteger(intId);
    }

}