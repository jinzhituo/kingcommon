package com.zhuanbang.kingcommonlib.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class BaseModel implements IModel, LifecycleObserver {
    protected IRepositoryManager mRepositoryManager;

    public BaseModel(IRepositoryManager repositoryManager) {
        mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
