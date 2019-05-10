package com.zhuanbang.kingcommonlib.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.zhuanbang.kingcommonlib.base.delegate.AppDelegate;
import com.zhuanbang.kingcommonlib.base.delegate.AppLifecycles;
import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;
import com.zhuanbang.kingcommonlib.utils.AppUtils;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

/**
 * 作者：Jzt on 2019/5/10 10:41
 */
public class BaseApplication extends Application implements IApplication {
    private AppLifecycles mAppDelegate;
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
        if (mAppDelegate != null) this.mAppDelegate.onCreate(this);
        if (isAppMainProcess()) {
            //do something for init
            onMainCreate();
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        if (mAppDelegate == null) this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public static boolean isAppMainProcess() {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(BaseApplication.getAppContext(), pid);
            String packName = AppUtils.getPackageName(getAppContext());
            if (TextUtils.isEmpty(process)) {
                return false;
            } else if (packName.equalsIgnoreCase(process)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 根据Pid得到进程名
     */
    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return ((IApplication) mAppDelegate).getAppComponent();
    }

    @Override
    public <T> T getCommonData(String key) {
        return ((IApplication) mAppDelegate).getCommonData(key);
    }

    @Override
    public void saveCommonData(String key, Object object) {
        ((IApplication) mAppDelegate).saveCommonData(key, object);
    }

    @Override
    public void onMainCreate() {

    }
}
