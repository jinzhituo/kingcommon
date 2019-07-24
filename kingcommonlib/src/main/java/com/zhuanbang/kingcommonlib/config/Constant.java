package com.zhuanbang.kingcommonlib.config;

import android.os.Environment;

/**
 * 定义一些常量Key方便存储和读取数据时使用
 * 作者：Jzt on 2019/5/10
 */
public class Constant {
    /**
     * 用户id常量key-
     */
    public static final String USER_ID = "userId";
    /**
     * 用户信息常量key-
     */
    public static final String USER_INFO = "userInfo";

    /**
     * 用户sessionId
     */
    public static final String SESSION_ID = "sessionId";

    /**
     * 推送的ChannelId
     */
    public static final String CHANNAL_ID = "channelId";


    /**
     * 推送的ChannelId
     */
    public static final String NOTIFICATION_CHANNEL_ID = "com.yimi.wangpayangent";


    /**
     * 使用设备
     */
    public static final String DEVICE_NAME = "pfDevice";
    /**
     * 使用设备需要传的值默认就是这个
     */
    public static final String DEVICE_VALUE = "Android";

    /**
     * app的版本号
     */
    public static final String APP_VERSION = "pfAppVersion";

    /**
     * 网页地址
     */
    public static final String EXTRA_WEB_URL = "extra_web_url";
    public static final String EXTRA_WEB_DATA = "extra_web_data";
    public static final String EXTRA_WEB_BASE_URL = "extra_web_base_url";

    /**
     * 网页标题
     */
    public static final String EXTRA_WEB_TITLE = "extra_web_title";

    /**
     * 是否是activity
     */
    public static final String EXTRA_WEB_IS_ACTIVITY = "extra_web_is_activity";

    /**
     * 数据库版本号
     */
    public static int OLD_VERSION = 1;

    /**
     * 用户名
     */
    public static final String USER_NAME = "user_name";
    /**
     * 用户密码
     */
    public static final String USER_PASS = "user_pass";
    /**
     * 数据库名称
     */
    public static final String DB_PATH = "schema/schema.sql";
    public static String DB_NAME = "mine.db";
    public static final int DB_VERSION = 1;

    /**
     * 下载路径
     */
    public final static String FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
}
