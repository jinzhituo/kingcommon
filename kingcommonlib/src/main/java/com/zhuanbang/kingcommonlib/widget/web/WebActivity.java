package com.zhuanbang.kingcommonlib.widget.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.KeyEvent;
import android.widget.FrameLayout;


import androidx.fragment.app.FragmentManager;

import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.base.BaseActivity;
import com.zhuanbang.kingcommonlib.config.Constant;
import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;
import com.zhuanbang.kingcommonlib.utils.ArmsUtils;
import com.zhuanbang.kingcommonlib.utils.DataHelper;

import butterknife.BindView;


public class WebActivity extends BaseActivity {

    FrameLayout mContainer;

    private String mUrl, mTitle, mData, mBaseUrl;
    FragmentManager mFragmentManager;
    FragmentWeb mActivityFragment;

    public static void startAction(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("webTitle", title);
        intent.putExtra("webUrl", url.contains("?") ? url : url + "?userId=" + DataHelper.getLongSF(context, Constant.USER_ID) + "&sessionId="
                + DataHelper.getStringSF(context, Constant.SESSION_ID) + "&pfDevice=Android" + "&pfAppVersion=" +
                ArmsUtils.getVersionCode());
        context.startActivity(intent);
    }

    public static void startAction(Context context, String baseUrl, String data, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("webTitle", title);
        intent.putExtra("webBaseUrl", baseUrl);
        intent.putExtra("webData", data);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_web;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        mContainer = findViewById(R.id.container);

        mTitle = getIntent().getStringExtra("webTitle");
        mUrl = getIntent().getStringExtra("webUrl");
        mBaseUrl = getIntent().getStringExtra("webBaseUrl");
        mData = getIntent().getStringExtra("webData");
        mActivityFragment = new FragmentWeb();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_WEB_URL, mUrl);
        bundle.putString(Constant.EXTRA_WEB_TITLE, mTitle);
        bundle.putString(Constant.EXTRA_WEB_BASE_URL, mBaseUrl);
        bundle.putString(Constant.EXTRA_WEB_DATA, mData);
        bundle.putBoolean(Constant.EXTRA_WEB_IS_ACTIVITY, true);
        mActivityFragment.setArguments(bundle);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.container, mActivityFragment).commit();
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }


    public void onDestroy() {
        mActivityFragment.destoryWebView();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mActivityFragment.backUp();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
