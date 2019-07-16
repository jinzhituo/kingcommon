package com.zhuanbang.kingcommonlib.base;

import android.content.Context;
import android.util.Log;


import com.zhuanbang.kingcommonlib.utils.Preconditions;

import org.simple.eventbus.EventBus;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: Zt丶
 * Date: 2019/7/16 10:11
 */
public class BasePresenter<M extends IModel, V extends IView> extends BaseStateViewModel implements IPresenter, LifecycleObserver, BaseStateViewModel.CallBack {

    protected final String TAG = this.getClass().getSimpleName();
    public Context mContext;
    protected V mRootView;
    protected M mModel;
    CompositeDisposable mCompositeDisposable;

    public BasePresenter(V rootView, M model) {
        Preconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        mRootView = rootView;
        mModel = model;
        this.mContext = mRootView.getCurrentContext();
        onStart();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    public BasePresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mRootView = rootView;
        this.mContext = mRootView.getCurrentContext();
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mRootView != null && mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner) mRootView).getLifecycle().addObserver(this);
            if (mModel != null && mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mRootView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().register(this);//注册 Eventbus
        Log.i("EventBus", "onStart: " + getClass().getName());
        attach(this);
    }

    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().unregister(this);//解除注册 Eventbus
        unDispose();
        if (mModel != null) {
            mModel.onDestroy();
        }
        mRootView = null;
        mCompositeDisposable = null;
        mModel = null;
        mContext = null;
        detach();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }

    /**
     * 是否使用 {@link EventBus},默认为使用(true)，
     *
     * @return
     */
    public boolean useEventBus() {
        return true;
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();

        }
        mCompositeDisposable.add(disposable);//将所有dispose加入到集中处理
    }

    /**
     * 停止集合中正在执行的RxJava任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证Activity结束时取消所有正在执行的订阅
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onReload() {

    }
}
