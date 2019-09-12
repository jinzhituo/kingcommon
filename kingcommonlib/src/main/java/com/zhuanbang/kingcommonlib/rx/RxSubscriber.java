package com.zhuanbang.kingcommonlib.rx;

import android.app.Activity;
import android.content.Context;
import android.net.ParseException;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.base.BaseApplication;
import com.zhuanbang.kingcommonlib.manager.AppManager;
import com.zhuanbang.kingcommonlib.utils.NetWorkUtils;
import com.zhuanbang.kingcommonlib.utils.ToastUitl;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public abstract class RxSubscriber<T> implements Observer<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private boolean showDelayDialog = true;
    /**
     * 加载数据对话框
     */
    private BasePopupView loadingPopup;
    Disposable mDialogDisposable;
    Disposable mDisposable;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    public RxSubscriber(Context context, String msg, boolean showDialog, boolean showDelayDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
        this.showDelayDialog = showDelayDialog;
    }

    public RxSubscriber(Context context) {
        this(context, "", true, true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), showDialog, false);
    }

    public RxSubscriber(Context context, boolean showDialog, boolean showDelayDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), showDialog, showDelayDialog);
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        _onSubscribe(d);
        if (showDialog) {
            try {
                showDialogForLoading(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (showDelayDialog) {
            Observable.timer(2, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDialogDisposable = d;
                        }

                        @Override
                        public void onNext(Long aLong) {
                            try {
                                showDialogForLoading(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void onComplete() {
        if (showDialog) cancelDialogForLoading();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (loadingPopup != null && loadingPopup.isShow()) cancelDialogForLoading();
            if (e instanceof UnknownHostException) {
                NetConnectErrorUtils.netConnetError(mContext);
                //请求超时
            } else if (e instanceof SocketTimeoutException) {
                if (showDialog)
                    ToastUitl.showToastWithImg(BaseApplication.getAppContext().getString(R.string.socket_time_out), R
                            .drawable.ic_wrong);
                //数据解析报错
            } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException) {
                ToastUitl.showToastWithImg(BaseApplication.getAppContext().getString(R.string.data_format_error), R
                        .drawable.ic_wrong);
            }
            //网络
            if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                if (showDialog)
                    ToastUitl.showToastWithImg(BaseApplication.getAppContext().getString(R.string.no_net), R.drawable
                            .ic_wrong);
            }
            //服务器
            else if (e instanceof BaseCommonException) {
                switch (((BaseCommonException) e).getCode()) {
                    case 0:
                        if (showDialog)
                            ToastUitl.showToastWithImg(e.getMessage(), R.drawable.ic_wrong);
                        _onError((BaseCommonException) e);
                        break;
                    case 2:
                        Message message = new Message();
                        message.what = AppManager.NEED_LOGIN;
                        AppManager.post(message);
                        break;
                    default:
                        _onError((BaseCommonException) e);

                }

            }
            //其它
            else {
                if (showDialog)
                    ToastUitl.showToastWithImg(BaseApplication.getAppContext().getString(R.string.net_error), R.drawable
                            .ic_wrong);
            }
        } catch (Exception e1) {
            Log.e("RxSubscriber", e1.getMessage(), e1);
        }


    }


    protected abstract void _onNext(T t);

    protected abstract void _onError(BaseCommonException e);

    protected abstract void _onSubscribe(Disposable d);

    private BasePopupView showDialogForLoading(String msg) {
        if (mContext == null || ((Activity) mContext).isFinishing()) {
            return null;
        }

        if (TextUtils.isEmpty(msg)) {
            loadingPopup = new XPopup.Builder(mContext)
                    .dismissOnBackPressed(false)
                    .dismissOnTouchOutside(false)
                    .asLoading();
        } else {
            loadingPopup = new XPopup.Builder(mContext)
                    .dismissOnBackPressed(false)
                    .dismissOnTouchOutside(false)
                    .asLoading(msg);
        }

        if (!loadingPopup.isShow())
            loadingPopup.show();

        return loadingPopup;
    }

    /**
     * 关闭加载对话框
     */
    private void cancelDialogForLoading() {
        if (loadingPopup != null && loadingPopup.isShow()) {
            try {
                loadingPopup.dismissWith(() -> {
                    if (mDisposable != null && !mDisposable.isDisposed()) {
                        mDisposable.dispose();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mDialogDisposable != null && !mDialogDisposable.isDisposed()) {
            mDialogDisposable.dispose();
        }
    }

}
