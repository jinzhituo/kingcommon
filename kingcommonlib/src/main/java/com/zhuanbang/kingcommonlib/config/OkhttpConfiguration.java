package com.zhuanbang.kingcommonlib.config;

import okhttp3.OkHttpClient;

/**
 * 作者：ZhengQunWei on 2018/6/29 14:41
 */
public interface OkhttpConfiguration {
    /**
     * 配置OkHttpClient
     *
     * @param builder
     */
    void configOkHttp(OkHttpClient.Builder builder);
}
