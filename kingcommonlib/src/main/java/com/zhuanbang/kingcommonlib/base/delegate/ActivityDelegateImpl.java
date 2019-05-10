package com.zhuanbang.kingcommonlib.base.delegate;

import android.app.Activity;
import android.os.Bundle;


import com.zhuanbang.kingcommonlib.base.IActivity;
import com.zhuanbang.kingcommonlib.base.IApplication;

import org.simple.eventbus.EventBus;

/**
 * 作者：ZhengQunWei on 2018/7/3 11:03
 */
public class ActivityDelegateImpl implements ActivityDelegate {

    Activity mActivity;
    IActivity mIActivity;

    public ActivityDelegateImpl(Activity activity) {
        mActivity = activity;
        mIActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mIActivity.useEventBus()) {
            EventBus.getDefault().register(mActivity);//注册到事件主线
        }
        mIActivity.setupActivityComponent(((IApplication) mActivity.getApplicationContext()).getAppComponent());
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {
        if (mIActivity != null && mIActivity.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(mActivity);
        this.mIActivity = null;
        this.mActivity = null;
    }
}
