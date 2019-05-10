package com.zhuanbang.kingcommonlib.widget.section;

/**
 * RecyclerView悬停分割线数据基类
 * 作者：ZhengQunWei on 2018/8/7 10:17
 */
public abstract class BaseSection<T> {

    /**
     * 获取头部信息,根据需要自行设置类型
     *
     * @return
     */
    public abstract T getHeadSection();

    public abstract boolean isEquals(BaseSection<T> section);
}
