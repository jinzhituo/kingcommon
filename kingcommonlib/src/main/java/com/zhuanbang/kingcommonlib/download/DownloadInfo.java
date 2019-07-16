package com.zhuanbang.kingcommonlib.download;

/**
 * —————————————————————————————————————
 * About: 下载管理
 * —————————————————————————————————————
 */
public class DownloadInfo {

    /**
     * 下载状态
     */
    public static final int DOWNLOAD = 1;    // 下载中
    public static final int DOWNLOAD_PAUSE = 2; // 下载暂停
    public static final int DOWNLOAD_WAIT = 3;  // 等待下载
    public static final int DOWNLOAD_CANCEL = 4; // 下载取消
    public static final int DOWNLOAD_OVER = 10;    // 下载结束
    public static final int DOWNLOAD_ERROR = 6;  // 下载出错

    public static final long TOTAL_ERROR = -1;//获取进度失败

    private String url;
    private String fileName;
    private int downloadStatus;
    private long total;
    private long progress;

    public DownloadInfo(String url) {
        this.url = url;
    }

    public DownloadInfo(String url, int downloadStatus) {
        this.url = url;
        this.downloadStatus = downloadStatus;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
