package com.zhuanbang.kingcommonlib.download;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public class DownLoadManager {
    private static DownLoadManager instance;
    private static Retrofit mRetrofit;

    private DownLoadManager() {
        buildNetWork();
    }

    public static DownLoadManager getInstance() {
        if (instance == null) {
            instance = new DownLoadManager();
        }
        return instance;
    }

    public void load(String downUrl, final ProgressCallBack callBack) {
        mRetrofit.create(ApiService.class).download(downUrl).subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(callBack::saveFile).observeOn(AndroidSchedulers.mainThread())
                //在主线程中更新ui
                .subscribe(new DownLoadSubscriber<>(callBack));
    }


    private void buildNetWork() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.163.gg/")
                .build();
    }

    private interface ApiService {
        @Streaming
        @GET
        Observable<ResponseBody> download(@Url String url);
    }
}
