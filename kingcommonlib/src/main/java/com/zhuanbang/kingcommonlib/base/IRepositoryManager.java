package com.zhuanbang.kingcommonlib.base;


/**
 * 作者：Jzt on 2019/5/10
 */
public interface IRepositoryManager {

    /**
     * 多个BaseUrl和多个service情况下获取不同service
     *
     * @param hostType url的类型（多url类型时需要）
     * @param service  service类型
     * @param <T>
     * @return
     */
    <T> T getRetrofitService(String hostType, Class<T> service);

    /**
     * 单个baseUrl获取不同的service
     *
     * @param service service类型
     * @param <T>
     * @return
     */
    <T> T getRetrofitService(Class<T> service);

//    UploadApi getUploadService();
}
