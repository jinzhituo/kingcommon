package com.zhuanbang.kingcommonlib.base;

import android.content.Context;

/**
 * @author ZhengQunWei
 * @date on 2018/6/27
 */
public interface IView {

    /**
     * 错误信息处理
     *
     * @param code    错误码
     * @param message 错误信息
     */
    void showErrorTip(int code, String message);

    /**
     * 停止加载
     */
    void stopLoading();

    /**
     * 停止刷新
     */
    void stopRefresh();

    /**
     * 返回当前上下文
     *
     * @return
     */
    Context getCurrentContext();
}
