package com.zhuanbang.kingcommonlib.config;

import com.google.gson.GsonBuilder;

/**
 * 提供给使用者自定义Gson如果不配置将使用默认的
 * 作者：Jzt on 2019/5/10
 */
public interface GsonConfiguration {

    void configGson(GsonBuilder gsonBuilder);
}
