package com.zhuanbang.kingcommon.config;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zhuanbang.kingcommon.MainActivity;
import com.zhuanbang.kingcommon.api.HostType;
import com.zhuanbang.kingcommonlib.base.delegate.AppLifecycles;
import com.zhuanbang.kingcommonlib.dagger.module.GlobalConfigModule;
import com.zhuanbang.kingcommonlib.manager.ConfigModule;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * 作者：Jzt on 2019/5/10
 */
public class GlobalConfig implements ConfigModule {


    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.setHostUrlConfiguration(new HostType()).setNeedLoginConfiguration(MainActivity::startAction)
                .setImageLoadOptionConfig(() -> new RequestOptions().centerCrop().skipMemoryCache(true)
                //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
                .diskCacheStrategy(DiskCacheStrategy.NONE)    //跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.DATA)    //只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)    //只缓存最终的图片
        );

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建时重复利用已经创建的 Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则
                // setRetainInstance(true) 无效
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {

            }
        });
    }
}
