package com.zhuanbang.kingcommonlib.config;

import android.app.Activity;

/**
 * 全局未登录请求处理
 * 作者：Jzt on 2019/5/10
 */
public interface NeedLoginConfiguration {

    /**
     * 跳转到登录页
     *
     * @param activity
     */
    void needLogin(Activity activity);
}
