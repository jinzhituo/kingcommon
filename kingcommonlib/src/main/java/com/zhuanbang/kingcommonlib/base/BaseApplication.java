package com.zhuanbang.kingcommonlib.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * 作者：Jzt on 2019/5/10 10:41
 */
public class BaseApplication extends Application {
    private static Application mApplication;

    public static Context getAppContext() {
        return mApplication;
    }

    public static Resources getAppResources() {
        return mApplication.getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
