package com.zhuanbang.kingcommonlib.utils;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.base.BaseApplication;
import com.zhuanbang.kingcommonlib.manager.AppManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Toast统一管理类
 */
public class ToastUitl {


    private static Toast toast;
    private static Toast toast2;

    private static Toast initToast(CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getAppContext(), message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
//		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        initToast(BaseApplication.getAppContext().getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        initToast(BaseApplication.getAppContext().getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        initToast(message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context.getResources().getText(strResId), duration).show();
    }

    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImg(final String tvStr, final int imageResource) {
        if (toast2 == null) {
            toast2 = new Toast(BaseApplication.getAppContext());
        }
        View view = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
            iv.setVisibility(View.GONE);
        }
        toast2.setView(view);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
        return toast2;
    }

    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImgForSystem(final String tvStr, final int imageResource) {
        if (toast2 == null) {
            toast2 = new Toast(BaseApplication.getAppContext());
        }

        try {
            //1通过反射获取Toast的getService方法
            Method serviceMethod = Toast.class.getDeclaredMethod("getService");
            serviceMethod.setAccessible(true);
            //2调用 toast 中的getService() 方法 返回INotificationManager类型的Object
            Object iNotificationManagerObj = serviceMethod.invoke(toast2);
            //3反射获取INotificationManager的Class
            Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");
            //4创建 INotificationManager的代理对象 替换Toast中的 INotificationManager
            Object iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(), new Class[]{iNotificationManagerCls},
                    (proxy, method, args) -> {
                        //强制使用系统Toast
                        if ("enqueueToast".equals(method.getName())
                                || "enqueueToastEx".equals(method.getName())) {  //华为p20 pro上为enqueueToastEx
                            //5上文中pkg 为“android”时为系统弹窗
                            args[0] = "android";
                        }
                        Log.e("test", "强制使用系统Toast>>>>>>>>>");
                        return method.invoke(iNotificationManagerObj, args);
                    });
            //6进行替换
            Field sServiceFiled = Toast.class.getDeclaredField("sService");
            sServiceFiled.setAccessible(true);
            sServiceFiled.set(toast, iNotificationManagerProxy);

            toast2.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toast2;
    }

    /**
     * 显示snackBar
     *
     * @param message
     */
    public static void showSnackbar(String message) {
        Message msg = new Message();
        msg.what = AppManager.SHOW_SNACKBAR;
        msg.obj = message;
        AppManager.post(msg);
    }
}
