package com.zhuanbang.kingcommon.config;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.zhuanbang.kingcommon.R;
import com.zhuanbang.kingcommonlib.base.BaseActivity;
import com.zhuanbang.kingcommonlib.utils.Eyes;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：Jzt on 2019/5/10
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
//        if (activity.findViewById(R.id.toolbar) != null) { //找到 Toolbar 并且替换 Actionbar
//            if (activity instanceof AppCompatActivity) {
//                ((AppCompatActivity) activity).setSupportActionBar(activity.findViewById(R.id.toolbar));
//                Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar())
//                        .setDisplayShowTitleEnabled(false);
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    activity.setActionBar(activity.findViewById(R.id.toolbar));
//                    Objects.requireNonNull(activity.getActionBar()).setDisplayShowTitleEnabled(false);
//                }
//            }
//        }
        if (activity instanceof BaseActivity) Eyes.setStatusBarLightMode(activity, Color.WHITE);
//        if (activity.findViewById(R.id.title_bar) != null) { //找到 Toolbar 的标题栏并设置标题名
//            NormalTitleBar titleBar = activity.findViewById(R.id.title_bar);
//            titleBar.setOnBackListener(v -> activity.onBackPressed());
//        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
