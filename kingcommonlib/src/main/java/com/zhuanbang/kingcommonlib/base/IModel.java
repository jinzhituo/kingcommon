package com.zhuanbang.kingcommonlib.base;

/**
 * 作者：ZhengQunWei on 2018/6/27 13:40
 */
public interface IModel {

    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    void onDestroy();

}
