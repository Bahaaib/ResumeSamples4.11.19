package com.resumesamples.resumesamples;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by bogdan on 10/1/15.
 */
public class MyApp extends Application {
    private static Context context;
    private static String appName = "";
    private static boolean isDebuggable;
    private static String appVersion = "";
    private static int buildNumber = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appName = getString(R.string.app_name);
        isDebuggable = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = pInfo.versionName;
            buildNumber = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return context;
    }

    public static boolean isDebugBuild() {
        return isDebuggable;
    }

    public static String getLogTag() {
        return appName + " v:" + appVersion + " b:" + String.valueOf(buildNumber);
    }

    public static String getAppVersion() {
        return appVersion;
    }

    public static int buildNumber() {
        return buildNumber;
    }
}
