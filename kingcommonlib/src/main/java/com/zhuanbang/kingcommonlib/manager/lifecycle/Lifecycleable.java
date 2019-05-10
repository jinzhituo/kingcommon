package com.zhuanbang.kingcommonlib.manager.lifecycle;

import androidx.annotation.NonNull;
import io.reactivex.subjects.Subject;

/**
 * 作者：ZhengQunWei on 2018/7/4 09:46
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
