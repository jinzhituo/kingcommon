package com.zhuanbang.kingcommonlib.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.zhuanbang.kingcommonlib.base.IApplication;
import com.zhuanbang.kingcommonlib.base.IFragment;

import org.simple.eventbus.EventBus;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：ZhengQunWei on 2018/7/5 09:25
 */
public class FragmentDelegateImpl implements FragmentDelegate {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment mIFragment;
    private Unbinder mUnbinder;

    public FragmentDelegateImpl(FragmentManager fragmentManager, Fragment fragment) {
        mFragmentManager = fragmentManager;
        mFragment = fragment;
        mIFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mIFragment.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(mFragment);//注册到事件主线
        Log.i("EventBus", "onCreate: " + getClass().getName());
        mIFragment.setupFragmentComponent(((IApplication) mFragment.getActivity().getApplicationContext()).getAppComponent());
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {
        //绑定到butterknife
        if (view != null)
            mUnbinder = ButterKnife.bind(mFragment, view);
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null && mUnbinder != mUnbinder.EMPTY) {
            try {
                mUnbinder.unbind();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                //fix Bindings already cleared
                Log.e("onDestroyView: ", e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mFragment != null && mIFragment.useEventBus()) {
            EventBus.getDefault().unregister(mFragment);
        }
        this.mUnbinder = null;
        this.mFragmentManager = null;
        this.mFragment = null;
        this.mIFragment = null;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public boolean isAdded() {
        return mFragment != null && mFragment.isAdded();
    }
}
