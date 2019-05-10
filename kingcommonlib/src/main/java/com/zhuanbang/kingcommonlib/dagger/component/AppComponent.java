package com.zhuanbang.kingcommonlib.dagger.component;

import android.app.Application;
import android.util.LruCache;

import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zhuanbang.kingcommonlib.base.IRepositoryManager;
import com.zhuanbang.kingcommonlib.config.NeedLoginConfiguration;
import com.zhuanbang.kingcommonlib.dagger.module.ApiModule;
import com.zhuanbang.kingcommonlib.dagger.module.AppModule;
import com.zhuanbang.kingcommonlib.dagger.module.GlobalConfigModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * 作者：ZhengQunWei on 2018/6/29 10:47
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class, GlobalConfigModule.class})
public interface AppComponent {

    Application application();


//    AppManager appManager();

    IRepositoryManager repositoryManager();

    NeedLoginConfiguration needLoginConfig();

//    UploadModel uploadModel();

    OkHttpClient okHttpClient();

    LruCache<String, Object> cache();

    RequestOptions imageLoadOption();

    //gson
    Gson gson();

//    void inject(AppDelegate application);


}