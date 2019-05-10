package com.zhuanbang.kingcommonlib.dagger.module;

import com.bumptech.glide.request.RequestOptions;
import com.zhuanbang.kingcommonlib.config.GsonConfiguration;
import com.zhuanbang.kingcommonlib.config.HostUrlConfiguration;
import com.zhuanbang.kingcommonlib.config.ImageLoadOptionConfig;
import com.zhuanbang.kingcommonlib.config.NeedLoginConfiguration;
import com.zhuanbang.kingcommonlib.config.OkhttpConfiguration;
import com.zhuanbang.kingcommonlib.http.GlobalHttpHandler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

/**
 * 作者：Jzt on 2019/5/10
 */
@Module
public class GlobalConfigModule {
    private HostUrlConfiguration mHostUrlConfiguration;
    private GsonConfiguration mGsonConfiguration;
    private List<Interceptor> mInterceptorList;
    private OkhttpConfiguration mOkhttpConfiguration;
    private GlobalHttpHandler mGlobalHttpHandler;
    private NeedLoginConfiguration mNeedLoginConfiguration;
    private ImageLoadOptionConfig mImageLoadOptionConfig;

    private GlobalConfigModule(Builder builder) {
        this.mHostUrlConfiguration = builder.mHostUrlConfiguration;
        this.mGsonConfiguration = builder.mGsonConfiguration;
        this.mInterceptorList = builder.mInterceptorList;
        this.mOkhttpConfiguration = builder.mOkhttpConfiguration;
        this.mGlobalHttpHandler = builder.mGlobalHttpHandler;
        this.mNeedLoginConfiguration = builder.mNeedLoginConfiguration;
        this.mImageLoadOptionConfig = builder.mImageLoadOptionConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Singleton
    @Provides
//    @NonNull
    @Nullable
    NeedLoginConfiguration provideNeedConfiguration() {
        return mNeedLoginConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    GsonConfiguration provideGsonConfiguration() {
        return mGsonConfiguration;
    }

    @Singleton
    @Provides
    @NonNull
    HostUrlConfiguration provideHostConfiguration() {
        return mHostUrlConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors() {
        return mInterceptorList;
    }

    @Singleton
    @Provides
    @Nullable
    OkhttpConfiguration provideOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

    @Singleton
    @Provides
    @NonNull
    RequestOptions provideImageLoadOption() {
        return mImageLoadOptionConfig.configImageLoadOption();
    }

    /**
     * 提供处理 Http 请求和响应结果的处理类
     *
     * @return
     */
    @Singleton
    @Provides
    @Nullable
    GlobalHttpHandler provideGlobalHttpHandler() {
        return mGlobalHttpHandler;
    }

    public static final class Builder {
        private HostUrlConfiguration mHostUrlConfiguration;
        private GsonConfiguration mGsonConfiguration;
        private List<Interceptor> mInterceptorList;
        private OkhttpConfiguration mOkhttpConfiguration;
        private GlobalHttpHandler mGlobalHttpHandler;
        private NeedLoginConfiguration mNeedLoginConfiguration;
        private ImageLoadOptionConfig mImageLoadOptionConfig;

        public Builder setHostUrlConfiguration(HostUrlConfiguration hostUrlConfiguration) {
            mHostUrlConfiguration = hostUrlConfiguration;
            return this;
        }

        public Builder setGsonConfiguration(GsonConfiguration gsonConfiguration) {
            mGsonConfiguration = gsonConfiguration;
            return this;
        }

        public Builder setInterceptorList(List<Interceptor> interceptorList) {
            if (mInterceptorList == null) mInterceptorList = new ArrayList<>();
            this.mInterceptorList.addAll(interceptorList);
            return this;
        }

        public Builder setOkhttpConfiguration(OkhttpConfiguration okhttpConfiguration) {
            mOkhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder setGlobalHttpHandler(GlobalHttpHandler globalHttpHandler) {
            mGlobalHttpHandler = globalHttpHandler;
            return this;
        }

        public Builder setNeedLoginConfiguration(NeedLoginConfiguration needLoginConfiguration) {
            mNeedLoginConfiguration = needLoginConfiguration;
            return this;
        }

        public Builder setImageLoadOptionConfig(ImageLoadOptionConfig imageLoadOptionConfig) {
            mImageLoadOptionConfig = imageLoadOptionConfig;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }
}
