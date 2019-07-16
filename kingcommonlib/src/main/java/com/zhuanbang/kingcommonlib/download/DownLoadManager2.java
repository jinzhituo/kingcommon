package com.zhuanbang.kingcommonlib.download;

import com.zhuanbang.kingcommonlib.config.Constant;


import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadManager2 {
    private static final AtomicReference<DownLoadManager2> INSTANCE = new AtomicReference<>();
    private OkHttpClient mClient;
    private HashMap<String, Call> downCalls; //用来存放各个下载的请求

    public static DownLoadManager2 getInstance() {
        for (; ; ) {
            DownLoadManager2 current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new DownLoadManager2();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private DownLoadManager2() {
        downCalls = new HashMap<>();
        mClient = new OkHttpClient.Builder().build();
    }

    /**
     * 查看是否在下载任务中
     *
     * @param url
     * @return
     */
    public boolean getDownloadUrl(String url) {
        return downCalls.containsKey(url);
    }

    /**
     * 开始下载
     *
     * @param url 下载请求的网址
     */
    public synchronized void download(String url) {

        Observable.just(url)
                .filter(new Predicate<String>() { // 过滤 call的map中已经有了,就证明正在下载,则这次不下载
                    @Override
                    public boolean test(String s) {
                        return !downCalls.containsKey(s);
                    }
                })
                .flatMap(new Function<String, ObservableSource<?>>() { // 生成 DownloadInfo
                    @Override
                    public ObservableSource<?> apply(String s) {
                        return Observable.just(createDownInfo(s));
                    }
                })
                .map(new Function<Object, DownloadInfo>() { // 如果已经下载，重新命名
                    @Override
                    public DownloadInfo apply(Object o) {
                        return getRealFileName((DownloadInfo) o);
                    }
                })
                .flatMap(new Function<DownloadInfo, ObservableSource<DownloadInfo>>() { // 下载
                    @Override
                    public ObservableSource<DownloadInfo> apply(DownloadInfo downloadInfo) {
                        return Observable.create(new DownloadSubscribe(downloadInfo));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程中回调
                .subscribeOn(Schedulers.io()) //  在子线程中执行
                .subscribe(new DownloadObserver()); //  添加观察者，监听下载进度
    }

    /**
     * 下载取消或者暂停
     *
     * @param url
     */
    public synchronized void pauseDownload(String url) {
        Call call = downCalls.get(url);
        if (call != null) {
            call.cancel();//取消
        }
        downCalls.remove(url);
    }

    /**
     * 取消下载 删除本地文件
     *
     * @param info
     */
    public synchronized void cancelDownload(DownloadInfo info) {
        pauseDownload(info.getUrl());
        info.setProgress(0);
        info.setDownloadStatus(DownloadInfo.DOWNLOAD_CANCEL);
        EventBus.getDefault().post(info);
//        ArmsUtils.deleteFile(info.getFileName());
    }

    /**
     * 取消下载 删除本地文件
     *
     * @param url
     * @param fileName
     */
    public synchronized void cancelDownload(String url, String fileName) {
        pauseDownload(url);
//        ArmsUtils.deleteFile(fileName);
    }

    /**
     * 创建DownInfo
     *
     * @param url 请求网址
     * @return DownInfo
     */
    private synchronized DownloadInfo createDownInfo(String url) {
        DownloadInfo downloadInfo = new DownloadInfo(url);
        long contentLength = getContentLength(url);//获得文件大小
        downloadInfo.setTotal(contentLength);
        String fileName = url.substring(url.lastIndexOf("/"));
        downloadInfo.setFileName(fileName);
        return downloadInfo;
    }

    /**
     * 如果文件已下载重新命名新文件名
     *
     * @param downloadInfo
     * @return
     */
    private synchronized DownloadInfo getRealFileName(DownloadInfo downloadInfo) {
        String fileName = downloadInfo.getFileName();
        long downloadLength = 0, contentLength = downloadInfo.getTotal();
        File path = new File(Constant.FILE_PATH);
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(Constant.FILE_PATH, fileName);
        if (file.exists()) {
            //找到了文件,代表已经下载过,则获取其长度
            downloadLength = file.length();
        }
        //设置改变过的文件名/大小
        downloadInfo.setProgress(downloadLength);
        downloadInfo.setFileName(file.getName());
        return downloadInfo;
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
        private DownloadInfo downloadInfo;

        public DownloadSubscribe(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {
            String url = downloadInfo.getUrl();
            long downloadLength = downloadInfo.getProgress();//已经下载好的长度
            //初始进度信息
            e.onNext(downloadInfo);
            Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                    .addHeader("RANGE", "bytes=" + downloadLength + "-")
                    .url(url)
                    .build();
            Call call = mClient.newCall(request);
            downCalls.put(url, call);//把这个添加到call里,方便取消

            Response response = call.execute();
            File file = new File(Constant.FILE_PATH, downloadInfo.getFileName());
            InputStream is = null;
            FileOutputStream fileOutputStream = null;
            try {
                is = response.body().byteStream();
                fileOutputStream = new FileOutputStream(file, true);
                byte[] buffer = new byte[2048];//缓冲数组2kB
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    downloadLength += len;
                    downloadInfo.setProgress(downloadLength);
                    e.onNext(downloadInfo);
                }
                fileOutputStream.flush();
                downCalls.remove(url);
            } finally {
                //关闭IO流
                DownloadIO.closeAll(is, fileOutputStream);

            }
            e.onComplete();//完成
        }
    }


    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadInfo.TOTAL_ERROR;
    }
}