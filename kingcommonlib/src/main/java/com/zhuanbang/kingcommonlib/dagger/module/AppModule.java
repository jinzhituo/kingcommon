package com.zhuanbang.kingcommonlib.dagger.module;

import android.app.Application;
import android.util.LruCache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhuanbang.kingcommonlib.base.IRepositoryManager;
import com.zhuanbang.kingcommonlib.config.GsonConfiguration;
import com.zhuanbang.kingcommonlib.http.RepositoryManager;


import javax.inject.Singleton;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;

/**
 * 作者：Jzt on 2019/5/10
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return this.mApplication;
    }

    @Singleton
    @Provides
    Gson provideGson(@Nullable GsonConfiguration gsonConfiguration) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //如果没有配置则使用默认的
        if (gsonConfiguration == null) {
            gsonConfiguration = (gsonBuilder1) -> {
                gsonBuilder1.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")//日期格式化
                        .serializeNulls()//支持序列化Null的参数
                        .enableComplexMapKeySerialization();//支持序列化key为Object的map，默认只能序列化key为String的map
            };
        }
        gsonConfiguration.configGson(gsonBuilder);
        return gsonBuilder.create();
    }

    /**
     * 提供请求接口管理组件
     */
    @Singleton
    @Provides
    IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }

//    @Singleton
//    @Provides
//    UploadModel provideUploadModel(IRepositoryManager repositoryManager){
//        return new UploadModel(repositoryManager);
//    }

    /**
     * 提供缓存
     *
     * @return
     */
    @Singleton
    @Provides
    LruCache<String, Object> provideCache() {
        return new LruCache<>(4 * 1024 * 1024);
    }

}
