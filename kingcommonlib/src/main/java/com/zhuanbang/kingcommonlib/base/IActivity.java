package com.zhuanbang.kingcommonlib.base;

import android.os.Bundle;

import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;

import androidx.annotation.NonNull;

public interface IActivity {

    /**
     * 提供 AppComponent(提供所有的单例对象)给实现类,进行 Component 依赖
     *
     * @param appComponent
     */
    void setupActivityComponent(@NonNull AppComponent appComponent);

    /**
     * 获取layout
     */
    int getLayoutId();

    /**
     * 初始化数据
     */
    void initData(Bundle savedInstanceState);

    /**
     * 初始化view
     */
    void initView();

    /**
     * 是否需要使用EventBus
     *
     * @return
     */
    boolean useEventBus();

    /**
     * 是否使用Fragment
     *
     * @return
     */
    boolean useFragment();

}
