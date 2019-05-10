package com.zhuanbang.kingcommonlib.http.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公共参数拦截器
 * 作者：ZhengQunWei on 2018/7/11 13:54
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder().scheme(oldRequest.url().scheme()).host
                (oldRequest.url().host());

        // 新的请求
        Request newRequest = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body()).url
                (authorizedUrlBuilder.build()).build();

        return chain.proceed(newRequest);
    }

}
