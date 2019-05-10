package com.zhuanbang.kingcommonlib.widget.section;

import android.graphics.Canvas;

/**
 * 作者：ZhengQunWei on 2018/8/6 16:32
 */
public interface SectionConvert<T> {

    /**
     * 自定义需要绘制的内容
     *
     * @param left   左边距
     * @param right  右边距
     * @param top
     * @param bottom
     * @param t      内容
     */
    void drawDecoration(Canvas canvas, int left, int right, int top, int bottom, T t);
}
