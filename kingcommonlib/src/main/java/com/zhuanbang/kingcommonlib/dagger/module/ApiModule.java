package com.zhuanbang.kingcommonlib.dagger.module;

import android.util.Log;

import com.google.gson.Gson;
import com.zhuanbang.kingcommonlib.config.OkhttpConfiguration;
import com.zhuanbang.kingcommonlib.http.GlobalHttpHandler;
import com.zhuanbang.kingcommonlib.http.interceptor.CommonInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：Jzt on 2019/5/10
 */
@Module
public class ApiModule {
    private static final int TIME_OUT = 30;

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder, @Nullable OkhttpConfiguration okhttpConfiguration,
                                     @Named("LoggingInterceptor") @Nullable Interceptor interceptor, @Named("CommInterceptor") @Nullable Interceptor comminterceptor, @Nullable List<Interceptor> interceptorList, @Nullable final GlobalHttpHandler globalHttpHandler) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS).addInterceptor
                (chain -> {
                    Request build = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
                    return chain.proceed(build);
                }).addNetworkInterceptor(interceptor);
        if (globalHttpHandler != null) {
            builder.addInterceptor(c -> c.proceed(globalHttpHandler.onHttpRequestBefore(c, c.request())));
        }
        if (interceptorList != null) for (Interceptor in : interceptorList) {
            builder.addInterceptor(in);
        }
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        if (comminterceptor != null) {
            builder.addInterceptor(comminterceptor);
        } else {
            builder.addInterceptor(new CommonInterceptor());
        }
        if (okhttpConfiguration != null) okhttpConfiguration.configOkHttp(builder);
        return builder.build();
    }

    @Named("LoggingInterceptor")
    @Singleton
    @Provides
    Interceptor provideInterceptor() {
        return new HttpLoggingInterceptor(message -> Log.i("OKHttp---->", message)).setLevel(HttpLoggingInterceptor
                .Level.BODY);//打印请求信息的拦截器
    }


    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(Gson gson) {
        return new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory
                (GsonConverterFactory.create(gson));
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }
}
