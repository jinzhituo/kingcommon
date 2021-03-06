package com.zhuanbang.kingcommonlib.base;

public interface IPresenter {

    /**
     * 做初始化工作
     */
    void onStart();

    /**
     * 在Activity的onDestroy中调用IPresenter的onDestroy做一些资源释放的工作
     */
    void onDestroy();
}
