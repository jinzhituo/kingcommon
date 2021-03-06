/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhuanbang.kingcommonlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.base.BaseApplication;
import com.zhuanbang.kingcommonlib.base.IApplication;
import com.zhuanbang.kingcommonlib.config.Constant;
import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;
import com.zhuanbang.kingcommonlib.manager.AppManager;

import java.io.File;
import java.security.MessageDigest;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.zhuanbang.kingcommonlib.manager.AppManager.APP_EXIT;
import static com.zhuanbang.kingcommonlib.manager.AppManager.KILL_ALL;
import static com.zhuanbang.kingcommonlib.manager.AppManager.SHOW_SNACKBAR;

/**
 * ================================================
 * 一些框架常用的工具
 * <p>
 * Created by JessYan on 2015/11/23.
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ArmsUtils {
    static public Toast mToast;


    private ArmsUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 设置hint大小
     *
     * @param size
     * @param v
     * @param res
     */
    public static void setViewHintSize(Context context, int size, TextView v, int res) {
        SpannableString ss = new SpannableString(getResources(context).getString(res));
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        // 附加属性到文本  
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint  
        v.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


    /**
     * dip转pix
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void setText(TextView textView, Object o, String defaultContent) {
        if (textView != null) {
            textView.setText(o == null ? defaultContent : o + "");
        }

    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 得到字符数组
     */
    public static String[] getStringArray(Context context, int id) {
        return getResources(context).getStringArray(id);
    }

    /**
     * pix转dip
     */
    public static int pix2dip(Context context, int pix) {
        final float densityDpi = getResources(context).getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }


    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param id
     * @return
     */
    public static int getDimens(Context context, int id) {
        return (int) getResources(context).getDimension(id);
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param dimenName
     * @return
     */
    public static float getDimens(Context context, String dimenName) {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context
                .getPackageName()));
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(Context context, int stringID) {
        return getResources(context).getString(stringID);
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(Context context, String strName) {
        return getString(context, getResources(context).getIdentifier(strName, "string", context.getPackageName()));
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, View view, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, Activity activity, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }

    /**
     * 根据 layout 名字获得 id
     *
     * @param layoutName
     * @return
     */
    public static int findLayout(Context context, String layoutName) {
        int id = getResources(context).getIdentifier(layoutName, "layout", context.getPackageName());
        return id;
    }

    /**
     * 填充view
     *
     * @param detailScreen
     * @return
     */
    public static View inflate(Context context, int detailScreen) {
        return View.inflate(context, detailScreen, null);
    }

    /**
     * 单例 toast
     *
     * @param string
     */
    public static void makeText(Context context, String string) {
        if (mToast == null) {
            mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        }
        mToast.setText(string);
        mToast.show();
    }

    /**
     * 使用 {@link Snackbar} 显示文本消息
     *
     * @param text
     */
    public static void snackbarText(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 0;
        AppManager.post(message);
    }

    /**
     * 使用 {@link Snackbar} 长时间显示文本消息
     *
     * @param text
     */
    public static void snackbarTextWithLong(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 1;
        AppManager.post(message);
    }


    /**
     * 通过资源id获得drawable
     *
     * @param rID
     * @return
     */
    public static Drawable getDrawablebyResource(Context context, int rID) {
        return getResources(context).getDrawable(rID);
    }


    /**
     * 跳转界面 3
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    /**
     * 跳转界面 4
     *
     * @param
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }


    /**
     * 获得颜色
     */
    public static int getColor(Context context, int rid) {
        return getResources(context).getColor(rid);
    }

    /**
     * 获得颜色
     */
    public static int getColor(Context context, String colorName) {
        return getColor(context, getResources(context).getIdentifier(colorName, "color", context.getPackageName()));
    }

    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    public static void deleteFile(String fileName) {
        if (TextUtils.isEmpty(fileName))
            return;
        SecurityManager checker = new SecurityManager();
        File file = new File(Constant.FILE_PATH, fileName);
        if (file.exists()) {
            checker.checkDelete(file.toString());
            if (file.isFile()) {
                try {
                    file.delete();
                } catch (SecurityException se) {
                    se.printStackTrace();
                }
            }
        }
    }


    /**
     * MD5
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String encodeToMD5(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /**
     * 全屏,并且沉侵式状态栏
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     * @deprecated Use {@link #configRecyclerView(RecyclerView, RecyclerView.LayoutManager)} instead
     */
    @Deprecated
    public static void configRecycleView(final RecyclerView recyclerView, RecyclerView.
            LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecyclerView(final RecyclerView recyclerView, RecyclerView.
            LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public static void configSwipeRefreshViewDefaultColor(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.green_4CAF50, R.color.pink_E91E63, R.color
                    .yellow_FF9800, R.color.blue_448AFF, R.color.green_009688);
        }
    }

    public static void setSearchViewColor(SearchView searchView, int textColor,
                                          int hintTextColor) {
        if (searchView == null) return;
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        if (textView == null) return;
        textView.setTextColor(textColor);
        textView.setHintTextColor(hintTextColor);
    }

    /**
     * 远程遥控 {@link AppManager#killAll()}
     */
    public static void killAll() {
        Message message = new Message();
        message.what = KILL_ALL;
        AppManager.post(message);
    }

    /**
     * 远程遥控 {@link AppManager#appExit()}
     */
    public static void exitApp() {
        Message message = new Message();
        message.what = APP_EXIT;
        AppManager.post(message);
    }

    public static AppComponent obtainAppComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof IApplication, "Application does not implements App");
        return ((IApplication) context.getApplicationContext()).getAppComponent();
    }

    /**
     * 添加fragment
     *
     * @param fragmentManager
     * @param fragment
     * @param container
     */
    public static void addFragment(FragmentManager fragmentManager, Fragment fragment,
                                   @IdRes int container) {
        Preconditions.checkNotNull(fragmentManager).beginTransaction().add(container, fragment).commit();
    }

    /**
     * 隐藏fragment
     *
     * @param fragmentManager
     * @param fragment
     */
    public static void hideFragment(FragmentManager fragmentManager, Fragment fragment) {
        Preconditions.checkNotNull(fragmentManager).beginTransaction().hide(fragment).commit();
    }

    public static int getUsePl() {
        //手机厂商
        String brand = SystemUtils.getSystemBrand();
        if (brand.equals("Xiaomi")) {
            return 4;
        } else if (brand.equals("HUAWEI")) {
            return 5;
        }
        return 2;
    }


    /**
     * 获取app版本号（由于服务器端只支持浮点数类型所以去掉多余的小数点之后的内容）
     *
     * @return
     */
    public static String getVersionCode() {
        String versionName = DeviceUtils.getVersionName(BaseApplication.getAppContext());
        if (TextUtils.isEmpty(versionName)) {
            return null;
        } else {
            String[] stringArr = versionName.split("\\.");
            if (stringArr.length > 2) {
                return stringArr[0] + "." + stringArr[1];
            } else {
                return versionName;
            }
        }
    }

    /**
     * 跳转页面
     *
     * @param url
     */
    public static void sendUrl(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        try {
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "没有地址或地址不正确 !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统播放器
     *
     * @param context 上下文
     * @param path    本地视频路径
     */
    public static void playVideo(Context context, String path) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        File file = new File(path);
////        Uri uri = FileProvide7.getUriForFile(context,file);
////        intent.setDataAndType(uri, "video/*");
//        FileProvide7.setIntentDataAndType(context, intent, "video/*", file, false);
//        context.startActivity(intent);
    }

    /**
     * 电话
     *
     * @param number
     */
    public static void sendTel(Context context, String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        if (!number.contains("tel")) {
            number = "tel:" + number;
        }
        try {
            intent.setData(Uri.parse(number));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "电话号码错误 !", Toast.LENGTH_SHORT).show();
        }
    }

}
