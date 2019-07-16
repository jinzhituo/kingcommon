package com.zhuanbang.kingcommonlib.rx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.zhuanbang.kingcommonlib.R;

/**
 * 网络连接失败弹出框工具類
 */
public class NetConnectErrorUtils {
    public static BasePopupView sCustomDialog;

    /**
     * 没网络时弹出对话框，点击确认去设置中设置网络
     *
     * @param context
     */
    public static void netConnetError(Context context) {

        if (!(context instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) context;
        if (activity.isFinishing()) {
            return;
        }
        if (sCustomDialog == null) {
            createDialog(activity);
        }
        sCustomDialog.show();
    }

    /**
     * 创建对话框
     *
     * @param mContext
     */
    private static void createDialog(Activity mContext) {
//        sCustomDialog = new CustomDialog.Builder(mContext).setTitle("提醒").setMessage
//                ("当前网络无法访问，请在系统设置中开启Wifi或者蜂窝数据," + "并且确认" + mContext.getString(R.string.app_name) + "有网络访问的权限！")
//                .setNegativeButton("取消", (dialog, which) -> {
//                    sCustomDialog.dismiss();
//                    sCustomDialog = null;
//                }).setPositiveButton("确定", (dialog, which) -> {
//                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);
//                    sCustomDialog.dismiss();
//                    sCustomDialog = null;
//                }).create();
        sCustomDialog = new XPopup.Builder(mContext)
//                         .dismissOnTouchOutside(false)
                // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onShow() {
                        Log.e("tag", "onShow");
                    }

                    @Override
                    public void onDismiss() {
                        Log.e("tag", "onDismiss");
                    }
                }).asConfirm("提醒", "当前网络无法访问，请在系统设置中开启Wifi或者蜂窝数据," + "并且确认" + mContext.getString(R.string.app_name) + "有网络访问的权限！",
                        "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                                sCustomDialog.dismiss();
                            }
                        }, null, false)
                .show();
    }
}
