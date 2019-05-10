package com.zhuanbang.kingcommonlib.http;

import android.util.LruCache;

import com.zhuanbang.kingcommonlib.base.IRepositoryManager;
import com.zhuanbang.kingcommonlib.config.HostUrlConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * 网络请求Service类管理（根据多BaseUrl设置可以实现不同BaseUrl的service）
 * 作者：ZhengQunWei on 2018/6/28 14:57
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private HostUrlConfiguration mHostUrlConfiguration;
    private LruCache<String, Object> mRetrofitServiceCache;
    OkHttpClient mOkHttpClient;
    Retrofit.Builder mBuilder;
    static final int MAX_SIZE = 1024;

    @Inject
    public RepositoryManager(HostUrlConfiguration hostUrlConfiguration, OkHttpClient okHttpClient, Retrofit.Builder
            builder) {
        mHostUrlConfiguration = hostUrlConfiguration;
        mOkHttpClient = okHttpClient;
        mBuilder = builder;
    }

    @Override
    public <T> T getRetrofitService(String hostType, Class<T> service) {
        return configService(mHostUrlConfiguration.getHostUrl(hostType), service);
    }

    private <T> T configService(String baseUrl, Class<T> service) {
        //判断缓存是否空，为空先创建缓存
        if (mRetrofitServiceCache == null) mRetrofitServiceCache = new LruCache<>(MAX_SIZE);
        T retrofitService;
        synchronized (mRetrofitServiceCache) {
            //先从缓存中取，如果没有则重新创建
            Object object = mRetrofitServiceCache.get(baseUrl + service.getSimpleName());
            if (object == null) {
                //根据hostType和Service创建不同的service实现
                Retrofit retrofit = mBuilder.client(mOkHttpClient).baseUrl(baseUrl).build();
                object = retrofit.create(service);
                //创建完存进缓存中
                mRetrofitServiceCache.put(baseUrl + service.getSimpleName(), object);
            }
            retrofitService = (T) object;
        }
        return retrofitService;
    }

//    @Override
//    public UploadApi getUploadService() {
//        return configService(mHostUrlConfiguration.getUploadHost(), UploadApi.class);
//    }

    @Override
    public <T> T getRetrofitService(Class<T> service) {
        return getRetrofitService(null, service);
    }
}
