package com.zhuanbang.kingcommonlib.base;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.config.StateEnum;

import androidx.core.content.ContextCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by Android Studio.
 * User: Zt丶
 * Date: 2019/7/16 10:11
 */
public class BaseStateViewModel extends BaseObservable {
    private Context mContext = BaseApplication.getAppContext();

    @StateEnum
    private int emptyState = StateEnum.STATUS_NORMAL;

    private boolean empty;

    public int getEmptyState() {
        return emptyState;
    }

    /**
     * 设置状态
     *
     * @param emptyState
     */
    public void setEmptyState(@StateEnum int emptyState) {
        this.emptyState = emptyState;
        notifyChange();
    }

    /**
     * 显示进度条
     *
     * @return
     */
    public boolean isProgress() {
        return this.emptyState == StateEnum.STATUS_PROGRESS;
    }

    /**
     * 根据异常显示状态
     *
     * @param e
     */
    public void bindThrowable(Throwable e) {
//        if (e instanceof EmptyException) {
//            @StateEnum
//            int code = ((EmptyException) e).getCode();
//
//            setEmptyState(code);
//        }
    }

    public boolean isEmpty() {
        return this.emptyState != StateEnum.STATUS_NORMAL;
    }

    /**
     * 空状态信息
     *
     * @return
     */
    @Bindable
    public String getCurrentStateLabel() {

        switch (emptyState) {
            case StateEnum.STATUS_EMPTY:
                return mContext.getString(R.string.empty);
            case StateEnum.STATUS_ERROR_NET:
                return mContext.getString(R.string.net_error);
            default:
                return mContext.getString(R.string.empty);
        }
    }

    /**
     * 空状态图片
     *
     * @return
     */
    @Bindable
    public Drawable getEmptyIconRes() {
        switch (emptyState) {
            case StateEnum.STATUS_EMPTY:
                return ContextCompat.getDrawable(mContext, R.drawable.ic_wrong);
            case StateEnum.STATUS_ERROR_NET:
                return ContextCompat.getDrawable(mContext, R.drawable.ic_wrong);
            default:
                return ContextCompat.getDrawable(mContext, R.drawable.ic_wrong);
        }
    }

}
