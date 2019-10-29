package com.zhuanbang.kingcommonlib.utils;

import android.view.View;

/**
 * 按钮防止连续点击 ， 以后改成AOP
 * User: Zt丶
 * Date: 2019/10/21 16:13
 */
public class ButtonUtil {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {       //500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
