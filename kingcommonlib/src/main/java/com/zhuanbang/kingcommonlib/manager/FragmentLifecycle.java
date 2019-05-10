package com.zhuanbang.kingcommonlib.manager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

import com.zhuanbang.kingcommonlib.base.IFragment;
import com.zhuanbang.kingcommonlib.base.delegate.FragmentDelegate;
import com.zhuanbang.kingcommonlib.base.delegate.FragmentDelegateImpl;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * 作者：ZhengQunWei on 2018/7/4 14:46
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {
    private static final String TAG = "FragmentLifecycle";

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        Log.w(TAG, f.toString() + " - onFragmentAttached");
        if (f instanceof IFragment) {
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
                LruCache<String, Object> cache = getCacheFromFragment((IFragment)f);
                fragmentDelegate = new FragmentDelegateImpl(fm, f);
                cache.put(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
            }
            fragmentDelegate.onAttach(context);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Log.w(TAG, f.toString() + " - onFragmentCreated");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        Log.w(TAG, f.toString() + " - onFragmentViewCreated");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreateView(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Log.w(TAG, f.toString() + " - onFragmentActivityCreated");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onActivityCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        Log.w(TAG, f.toString() + " - onFragmentStarted");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        Log.w(TAG, f.toString() + " - onFragmentResumed");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        Log.w(TAG, f.toString() + " - onFragmentPaused");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        Log.w(TAG, f.toString() + " - onFragmentStopped");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        Log.w(TAG, f.toString() + " - onFragmentSaveInstanceState");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        Log.w(TAG, f.toString() + " - onFragmentViewDestroyed");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroyView();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        Log.w(TAG, f.toString() + " - onFragmentDestroyed");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        Log.w(TAG, f.toString() + " - onFragmentDetached");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDetach();
        }
    }

    private FragmentDelegate fetchFragmentDelegate(Fragment fragment) {
        if (fragment instanceof IFragment) {
            LruCache<String, Object> cache = getCacheFromFragment((IFragment) fragment);
            return (FragmentDelegate) cache.get(FragmentDelegate.FRAGMENT_DELEGATE);
        }
        return null;
    }

    @NonNull
    private LruCache<String, Object> getCacheFromFragment(IFragment fragment) {
        return fragment.provideCache();
    }

}
