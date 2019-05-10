package com.zhuanbang.kingcommonlib.base;

import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;

import androidx.annotation.NonNull;

public interface IApplication {

    @NonNull
    AppComponent getAppComponent();
    /**
     * 从全局缓存中取出对应的数据(比如登录信息)
     *
     * @param key
     * @return
     */
    <T> T getCommonData(String key);

    /**
     * 把全局公用信息存在全局缓存中（不能存大的数据比如图片）
     *
     * @param key
     * @param object
     */
    void saveCommonData(String key, Object object);

    /**
     * 只在主线程做初始化的操作
     *
     */
    void onMainCreate();
}
