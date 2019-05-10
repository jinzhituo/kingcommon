package com.zhuanbang.kingcommonlib.config;

import android.app.Activity;

/**
 * 全局未登录请求处理
 * 作者：ZhengQunWei on 2018/7/11 16:08
 */
public interface NeedLoginConfiguration {

    /**
     * 跳转到登录页
     *
     * @param activity
     */
    void needLogin(Activity activity);
}
