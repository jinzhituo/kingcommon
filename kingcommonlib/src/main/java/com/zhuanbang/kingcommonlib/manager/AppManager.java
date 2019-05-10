package com.zhuanbang.kingcommonlib.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.config.NeedLoginConfiguration;
import com.zhuanbang.kingcommonlib.utils.Eyes;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;


/**
 * 所有Activity管理类
 * 作者：ZhengQunWei on 2018/6/29 10:48
 */
@Singleton
public class AppManager {
    protected final String TAG = this.getClass().getSimpleName();
    public static final String APPMANAGER_MESSAGE = "appmanager_message";
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_not_add_activity_list";//true 为不需要加入到 Activity
    // 容器进行统一管理,默认为 false
    public static final int START_ACTIVITY = 5000;
    public static final int SHOW_SNACKBAR = 5001;
    public static final int KILL_ALL = 5002;
    public static final int APP_EXIT = 5003;
    public static final int NEED_LOGIN = -2;
    public static final int CHANGE_STATUS_BAR_COLOR = 5004;
    private Application mApplication;
    //所有Activity列表
    private List<Activity> mActivityList;
    //当前Activity
    private Activity mCurrentActivity;

    private NeedLoginConfiguration mNeedLoginConfiguration;

    @Inject
    public AppManager(Application application, @Nullable NeedLoginConfiguration needLoginConfiguration) {
        mApplication = application;
        mNeedLoginConfiguration = needLoginConfiguration;
        EventBus.getDefault().register(this);
    }

    @Subscriber(tag = APPMANAGER_MESSAGE, mode = ThreadMode.MAIN)
    public void onReceive(Message message) {
        switch (message.what) {
            case SHOW_SNACKBAR:
                if (message.obj == null) break;
                showSnackbar((String) message.obj, message.arg1 == 0 ? false : true);
                break;
            case NEED_LOGIN:
                if (mNeedLoginConfiguration != null) mNeedLoginConfiguration.needLogin(getCurrentActivity());
                break;
            case KILL_ALL:
                killAll();
                break;
            case APP_EXIT:
                appExit();
                break;
            case CHANGE_STATUS_BAR_COLOR:
                if (message.arg1 == 1) {
                    Eyes.setStatusBarColor(mCurrentActivity, getColorPrimaryDark());
                } else {
                    Eyes.setStatusBarLightMode(mCurrentActivity, message.arg2);
                }
                break;
        }
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public  int getColorPrimaryDark() {
        TypedValue typedValue = new TypedValue();
        mCurrentActivity.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 修改头部信息栏颜色
     *
     * @param isLightMode 是否是浅色（文字用黑色）不是浅色模式加载系统colorPrimaryDark颜色
     * @param color       浅色的颜色值
     */
    public static void changeStatusBarColor(boolean isLightMode, int color) {
        Message message = new Message();
        message.what = AppManager.CHANGE_STATUS_BAR_COLOR;
        if (isLightMode) {
            message.arg1 = 0;
            message.arg2 = color;
        } else {
            message.arg1 = 1;
        }
        post(message);
    }

    /**
     * 设置信息栏默认系统颜色
     */
    public static void setStatusBarDefault() {
        changeStatusBarColor(false, 0);
    }


    /**
     * 退出应用
     */
    public void appExit() {
        try {
            killAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void killAll() {
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            iterator.remove();
            next.finish();
        }
    }

    /**
     * 通过此方法远程遥控AppManager执行对应的方法
     *
     * @param message
     */
    public static void post(Message message) {
        EventBus.getDefault().post(message, APPMANAGER_MESSAGE);
    }

    /**
     * 让前台Activity显示文本信息
     *
     * @param message
     * @param isLong
     */
    public void showSnackbar(String message, boolean isLong) {
        if (getCurrentActivity() == null) {
            return;
        }
        View view = getCurrentActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 添加{@Link}Activity到集合
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        synchronized (AppManager.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    /**
     * 返回一个存储所有未销毁的 {@link Activity} 的集合
     *
     * @return
     */
    public List<Activity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }

    /**
     * 获取栈顶的activity
     *
     * @return
     */
    public Activity getTopActivity() {
        if (mActivityList == null) {
            return null;
        }
        return mActivityList.size() > 0 ? mActivityList.get(mActivityList.size() - 1) : null;
    }

    /**
     * 从activity集合中移除某个activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList == null) {
            return;
        }
        synchronized (AppManager.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }


    /**
     * 设置当前可见状态下的activity
     *
     * @param currentActivity
     */
    public void setCurrentActivity(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    /**
     * 获取当前可见状态下的Activity
     */
    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }
}
