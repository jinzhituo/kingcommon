package com.zhuanbang.kingcommonlib.base.delegate;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.LruCache;

import com.zhuanbang.kingcommonlib.base.IApplication;
import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;
import com.zhuanbang.kingcommonlib.dagger.component.DaggerAppComponent;
import com.zhuanbang.kingcommonlib.dagger.module.ApiModule;
import com.zhuanbang.kingcommonlib.dagger.module.AppModule;
import com.zhuanbang.kingcommonlib.dagger.module.GlobalConfigModule;
import com.zhuanbang.kingcommonlib.manager.ActivityLifecycle;
import com.zhuanbang.kingcommonlib.manager.ConfigModule;
import com.zhuanbang.kingcommonlib.manager.ManifestParser;
import com.zhuanbang.kingcommonlib.manager.lifecycle.ActivityLifecycleForRxLifecycle;
import com.zhuanbang.kingcommonlib.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;

/**
 * 代理Application的生命周期，执行相应的逻辑
 * 作者：ZhengQunWei on 2018/7/4 09:19
 */
@Singleton
public class AppDelegate implements IApplication, AppLifecycles {
    private Application mApplication;
    private AppComponent mAppComponent;
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    @Inject
    protected ActivityLifecycleForRxLifecycle mActivityLifecycleForRxLifecycle;
    @Inject
    public LruCache<String, Object> mLruCache;
    private List<ConfigModule> mModules;
    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    public AppDelegate(Context context) {
        this.mModules = new ManifestParser(context).parse();
        for (ConfigModule module : mModules) {
            module.injectAppLifecycle(context, mAppLifecycles);
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppComponent, "%s cannot be null,first call %s#onCreate(Application) in " +
                "%s#onCreate()", AppComponent.class.getName(), getClass().getName(), Application.class.getName());
        return mAppComponent;
    }

    @Override
    public <T> T getCommonData(String key) {
        Object o = mLruCache.get(key);
        if (o == null) return null;
        return (T) mLruCache.get(key);
    }

    @Override
    public void saveCommonData(String key, Object object) {
        if (object == null) return;
        mLruCache.put(key, object);
    }

    @Override
    public void onMainCreate() {

    }

    @Override
    public void attachBaseContext(Context base) {
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(Application application) {
        mApplication = application;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(mApplication))//提供application
                .apiModule(new ApiModule())//用于提供okhttp和retrofit的单例
                .globalConfigModule(getGlobalConfigModule(mApplication, mModules))//全局配置
                .build();
        mAppComponent.inject(this);
        mAppComponent.cache().put(ConfigModule.class.getName(), mModules);
        this.mModules = null;

        if (isApkInDebug(mApplication))
//            LeakCanary.install(application);

        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);

        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link ConfigModule}的实现类,和Glide的配置方式相似
     *
     * @return
     */
    private GlobalConfigModule getGlobalConfigModule(Context context, List<ConfigModule> modules) {

        GlobalConfigModule.Builder builder = GlobalConfigModule.builder();

        for (ConfigModule module : modules) {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }

    @Override
    public void onTerminate(Application application) {
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
