package com.zhuanbang.kingcommonlib.manager.lifecycle;

import androidx.annotation.NonNull;
import io.reactivex.subjects.Subject;

public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
