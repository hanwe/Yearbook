package com.graduation.yearbook.util.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;

import com.graduation.yearbook.application.BaseApplication;
import com.graduation.yearbook.util.JLog;

import java.util.Locale;

/**
 * Created by hanwe on 15/7/15.
 */
public class DeviceUtil
{
    public static int getScreenOrientation(Context ctx){
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return Configuration.ORIENTATION_PORTRAIT;
            case Surface.ROTATION_90:
                return Configuration.ORIENTATION_LANDSCAPE;
            case Surface.ROTATION_180:
                return Configuration.ORIENTATION_PORTRAIT;
            default:
                return Configuration.ORIENTATION_LANDSCAPE;
        }
    }
    
    public static void turnGPSOn(Context ctx) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        ctx.sendBroadcast(intent);
    }

    @SuppressWarnings("deprecation")
    // automatic turn off the gps
    public static void turnGPSOff(Context ctx) {
        String provider = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { // if gps is enabled
            Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            ctx.sendBroadcast(poke);
        }
    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large =  ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public static boolean isOpenGps(Activity ctx) {
        LocationManager manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public static String getPackageName(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null)
            return packageInfo.applicationInfo.packageName;
        else
            return "";
    }
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        JLog.d(JLog.JosephWang, "getIMEI " + imei);
        return imei;
    }

    public static String getAndroidId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static boolean hasPermission(String src) {
        boolean isRight = false;
        if (src != null && src.equals("1")) {
            isRight = true;
        }
        return isRight;
    }

    public static String getVersionName(Context ctx) {
        PackageInfo Pinfo = null;
        try {
            Pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Pinfo.versionName;
    }

    public static String getMainiFestPackageName(Context ctx) {
        PackageInfo Pinfo = null;
        try {
            Pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Pinfo.packageName;
    }

    public static int getSDKVersion() {
        int SDK_Version;
        SDK_Version = Build.VERSION.SDK_INT;
        return SDK_Version;
    }

    public static void setVersionName(TextView version) {
        try {
            String versionName = BaseApplication.getContext().getPackageManager().getPackageInfo(BaseApplication.getContext().getPackageName(), 0).versionName;
            version.setText("" + versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     Locale.getDefault().getLanguage()       ---> en
     Locale.getDefault().getISO3Language()   ---> eng
     Locale.getDefault().getCountry()        ---> US
     Locale.getDefault().getISO3Country()    ---> USA
     Locale.getDefault().getDisplayCountry() ---> United States
     Locale.getDefault().getDisplayName()    ---> English (United States)
     Locale.getDefault().toString()          ---> en_US
     Locale.getDefault().getDisplayLanguage()---> English
     * @return
     */
    public static String getDeviceLguage()
    {
        return Locale.getDefault().getLanguage();
    }

    public static void enableRotation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }


    public static String[] getResourceArray(int id) {
        return BaseApplication.getContext().getResources().getStringArray(id);
    }

    public static String getString(int id) {
        return BaseApplication.getContext().getResources().getString(id);
    }
}
