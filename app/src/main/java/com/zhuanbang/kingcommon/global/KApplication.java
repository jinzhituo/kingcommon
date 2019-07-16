package com.zhuanbang.kingcommon.global;

import com.zhuanbang.kingcommonlib.base.BaseApplication;

import java.util.Map;

import androidx.appcompat.app.AppCompatDelegate;

//import android.support.annotation.NonNull;

public class KApplication extends BaseApplication {
    public static Map<String, Long> map;
    private boolean isAidl;
    public static final String TAG = "KApplication";

    public boolean isAidl() {
        return isAidl;
    }

    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }



    //static 代码段可以防止内存泄露
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @NonNull
//            @Override
//            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
//            }
//        });
//        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
//            @NonNull
//            @Override
//            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
//                return new ClassicsFooter(context);
//            }
//        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
