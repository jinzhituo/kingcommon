package com.zhuanbang.kingcommonlib.download;

import java.io.Closeable;
import java.io.IOException;

/**
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class DownloadIO {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
