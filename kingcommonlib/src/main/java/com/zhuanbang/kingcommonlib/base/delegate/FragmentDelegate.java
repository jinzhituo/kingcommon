package com.zhuanbang.kingcommonlib.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * 作者：ZhengQunWei on 2018/7/4 15:55
 */
public interface FragmentDelegate {
    String FRAGMENT_DELEGATE = "fragment_delegate";

    void onAttach(Context context);

    void onCreate(Bundle savedInstanceState);

    void onCreateView(View view, Bundle savedInstanceState);

    void onActivityCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();

    /**
     * Return true if the fragment is currently added to its activity.
     */
    boolean isAdded();
}
