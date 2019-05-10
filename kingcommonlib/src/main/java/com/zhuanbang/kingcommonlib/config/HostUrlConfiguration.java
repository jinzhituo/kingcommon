package com.zhuanbang.kingcommonlib.config;

/**
 * 请求BaseUrl配置接口
 * 作者：Jzt on 2019/5/10
 */
public interface HostUrlConfiguration {
    /**
     * 根据不同的hostType返回不同host
     *
     * @param hostType BaseUrl在实现类中的类型
     * @return
     */
    String getHostUrl(String hostType);

    String getUploadHost();

}
