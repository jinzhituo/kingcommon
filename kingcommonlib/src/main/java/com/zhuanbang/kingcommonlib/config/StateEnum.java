package com.zhuanbang.kingcommonlib.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static com.zhuanbang.kingcommonlib.config.StateEnum.STATUS_EMPTY;
import static com.zhuanbang.kingcommonlib.config.StateEnum.STATUS_ERROR_NET;
import static com.zhuanbang.kingcommonlib.config.StateEnum.STATUS_NORMAL;
import static com.zhuanbang.kingcommonlib.config.StateEnum.STATUS_PROGRESS;

/**
 * Created by Android Studio.
 * User: Zt丶
 * Date: 2019/7/16 10:19
 * 页面描述：空状态
 */

@IntDef({STATUS_NORMAL, STATUS_EMPTY, STATUS_ERROR_NET, STATUS_PROGRESS})
@Retention(RetentionPolicy.SOURCE)
public @interface StateEnum {
    int STATUS_NORMAL = 0x0001;//正常
    int STATUS_EMPTY = 0x0002;//列表数据为空
    int STATUS_ERROR_NET = 0x0004;//网络错误
    int STATUS_PROGRESS = 0x0008;//显示进度条
}
