package com.zhuanbang.kingcommonlib.base.delegate;

import android.os.Bundle;

/**
 * 作者：ZhengQunWei on 2018/7/3 10:21
 */
public interface ActivityDelegate {
    String ACTIVITY_DELEGATE = "activity_delegate";

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
