package com.zhuanbang.kingcommonlib.base.delegate;

import android.app.Application;
import android.content.Context;

/**
 * 作者：ZhengQunWei on 2018/7/4 09:21
 */
public interface AppLifecycles {

    void attachBaseContext(Context base);

    void onCreate(Application application);

    void onTerminate(Application application);
}
