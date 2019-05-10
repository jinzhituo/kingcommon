package com.zhuanbang.kingcommonlib.config;

import com.google.gson.GsonBuilder;

/**
 * 提供给使用者自定义Gson如果不配置将使用默认的
 * 作者：ZhengQunWei on 2018/6/29 11:35
 */
public interface GsonConfiguration {

    void configGson(GsonBuilder gsonBuilder);
}
