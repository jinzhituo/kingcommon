package com.zhuanbang.kingcommonlib.config;

import okhttp3.OkHttpClient;

/**
 * 作者：Jzt on 2019/5/10
 */
public interface OkhttpConfiguration {
    /**
     * 配置OkHttpClient
     *
     * @param builder
     */
    void configOkHttp(OkHttpClient.Builder builder);
}
