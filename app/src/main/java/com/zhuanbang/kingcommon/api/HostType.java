package com.zhuanbang.kingcommon.api;


import com.zhuanbang.kingcommonlib.config.HostUrlConfiguration;

public class HostType implements HostUrlConfiguration {

    /**
     * 测试配置信息
     */
//    public static final String SERVER_RELEASE = "http://wedev1.163.gg/";
    public static final String TEST_SERVER_RELEASE = "http://192.168.1.33:8080/wangfu/";
    //    public static final String SERVER_RELEASE = "http://192.168.1.91:8080/wangfu/";
//    public static final String SERVER_RELEASE = "http://192.168.1.188:8096/";
    //    public static final String SERVER_RELEASE = "https://pre.163.gg/";
//    public static final String WEB_URL = "http://192.168.1.250/yichengshi/";
//    public static final String WEB_URL = "http://192.168.1.250:8081/wangfu/";
//    public static final String SERVER_RELEASE = "http://192.168.1.200:8096/wangfu/";
    //测试服
//    public static final String EASY_CITY_RELEASE = "http://192.168.1.221:8084/";
//    public static final String SERVER_RELEASE = "http://192.168.1.33:8080/wangfu/";


    /**
     * 测试配置信息-百惠
     */
//    public static final String EASY_CITY_RELEASE = "http://192.168.1.83:8084";
//    public static final String SERVER_RELEASE = "http://192.168.1.33:8080/wangfu/";
//    public static final String WEB_URL = "https://www.163.gg/";
//    public static final String EASY_CITY_WEB_URL = "http://192.168.1.83:8084";
//    public static final String UPLOAD_IMAGE = "https://img.163.gg/";


    /**
     * 测试配置信息-敏耀
     */
//    public static final String EASY_CITY_RELEASE = "https://api.yichengshi.cn/";
//    public static final String SERVER_RELEASE = "http://192.168.1.91:8096/";
//    public static final String WEB_URL = "https://www.163.gg/";
//    public static final String EASY_CITY_WEB_URL = "http://www.yichengshi.cn/";
//    public static final String UPLOAD_IMAGE = "https://img.163.gg/";


    /**
     * 正式配置信息
     */
    public static final String EASY_CITY_RELEASE = "https://api.yichengshi.cn/";
    public static final String SERVER_RELEASE = "https://api.163.gg/";
    public static final String WEB_URL = "https://www.163.gg/";
    public static final String EASY_CITY_WEB_URL = "http://www.yichengshi.cn/";
    public static final String UPLOAD_IMAGE = "https://img.163.gg/";

    @Override
    public String getHostUrl(String hostType) {
        return hostType;
    }

    @Override
    public String getUploadHost() {
        return UPLOAD_IMAGE;
    }
}
