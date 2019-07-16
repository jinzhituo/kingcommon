package com.zhuanbang.kingcommonlib.download;

import android.util.Log;

import org.simple.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * —————————————————————————————————————
 * About: 观察者
 * —————————————————————————————————————
 */
public class DownloadObserver implements Observer<DownloadInfo> {

    public Disposable d;//可以用于取消注册的监听者
    public DownloadInfo downloadInfo;
    private long updateTime;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(DownloadInfo value) {
        this.downloadInfo = value;
        if (updateTime == 0) {
            updateTime = System.currentTimeMillis();
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD);
            EventBus.getDefault().post(downloadInfo);
        } else if (System.currentTimeMillis() - updateTime > 1000) {
            updateTime = System.currentTimeMillis();
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD);
            EventBus.getDefault().post(downloadInfo);
        }

    }

    @Override
    public void onError(Throwable e) {
        Log.d("My_Log", "onError");
        if (DownLoadManager2.getInstance().getDownloadUrl(downloadInfo.getUrl())) {
            DownLoadManager2.getInstance().pauseDownload(downloadInfo.getUrl());
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_ERROR);
            EventBus.getDefault().post(downloadInfo);
        } else {
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_PAUSE);
            EventBus.getDefault().post(downloadInfo);
        }

    }

    @Override
    public void onComplete() {
        Log.d("My_Log", "onComplete");
        if (downloadInfo != null) {
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_OVER);
            EventBus.getDefault().post(downloadInfo);
        }
    }
}
