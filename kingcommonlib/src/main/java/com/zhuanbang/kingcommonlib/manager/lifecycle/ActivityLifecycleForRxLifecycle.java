package com.zhuanbang.kingcommonlib.manager.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import io.reactivex.subjects.Subject;

@Singleton
public class ActivityLifecycleForRxLifecycle implements Application.ActivityLifecycleCallbacks {

    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycleCallbacks;

    @Inject
    public ActivityLifecycleForRxLifecycle() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.CREATE);
            if (activity instanceof FragmentActivity) {
                if (mFragmentLifecycleCallbacks == null) {
                    mFragmentLifecycleCallbacks = new FragmentLifecycleForRxLifecycle();
                }
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks
                        (mFragmentLifecycleCallbacks, true);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.START);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.RESUME);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.PAUSE);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.STOP);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.DESTROY);
        }
    }

    private Subject<ActivityEvent> obtainSubject(Activity activity) {
        return ((ActivityLifecycleable) activity).provideLifecycleSubject();
    }
}
