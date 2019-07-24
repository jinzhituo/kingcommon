package com.zhuanbang.kingcommonlib.widget.popupwindow.popup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.zhuanbang.kingcommonlib.utils.DisplayUtil;
import com.zhuanbang.kingcommonlib.widget.KingWrapContentListView;

/**
 * Created by Android Studio.
 * User: Zt丶
 * Date: 2019/7/22 13:40
 */
public class KingListPopup<T extends KingListPopup> extends KingPopup {
    protected ListView mListView;
    protected BaseAdapter mAdapter;
    private boolean mHasDivider;

    /**
     * Constructor.
     *
     * @param context   Context
     * @param direction
     */
    public KingListPopup(Context context, int direction, BaseAdapter adapter) {
        super(context, direction);
        mAdapter = adapter;
    }

    public KingListPopup(Context context, BaseAdapter adapter) {
        super(context);
        mAdapter = adapter;
    }

    /**
     * 创建弹窗
     *
     * @param width               弹窗的宽度
     * @param maxHeight           弹窗最大的高度
     * @param onItemClickListener 列表点击的监听
     * @return
     */
    public T create(int width, int maxHeight, AdapterView.OnItemClickListener onItemClickListener) {
        create(width, maxHeight);
        mListView.setOnItemClickListener(onItemClickListener);
        return (T) this;
    }

    /**
     * 创建弹窗
     *
     * @param width     弹窗的宽度
     * @param maxHeight 弹窗最大的高度
     * @return
     */
    protected T create(int width, int maxHeight) {
        int margin = DisplayUtil.dip2px(5);
        if (maxHeight != 0) {
            mListView = new KingWrapContentListView(getContext(), maxHeight);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, maxHeight);
            lp.setMargins(0, margin, 0, margin);
            mListView.setLayoutParams(lp);
        } else {
            mListView = new KingWrapContentListView(getContext());
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, margin, 0, margin);
            mListView.setLayoutParams(lp);
        }
        mListView.setPadding(margin, 0, margin, 0);
        mListView.setAdapter(mAdapter);
        mListView.setVerticalScrollBarEnabled(false);
        updateListViewDivider(mListView);
        setContentView(mListView);
        return (T) this;
    }

    /**
     * 创建弹窗
     *
     * @param width 弹窗的宽度
     * @return
     */
    protected T create(int width) {
        return create(width, 0);
    }

    /**
     * 设置是否有分割线
     *
     * @param hasDivider
     * @return
     */
    public T setHasDivider(boolean hasDivider) {
        mHasDivider = hasDivider;
        if (mListView != null) {
            updateListViewDivider(mListView);
        }
        return (T) this;
    }

    private void updateListViewDivider(ListView listView) {
        if (mHasDivider) {
//            listView.setDivider(new ColorDrawable(ThemeUtils.resolveColor(getContext(), R.attr.xui_config_color_separator_light)));
//            if (XUI.isTablet()) {
//                listView.setDividerHeight(DisplayUtil.dip2px(getContext(), 1));
//            } else {
//                listView.setDividerHeight(DisplayUtil.dip2px(getContext(), 0.5F));
//            }
        } else {
            listView.setDivider(null);
        }
    }

    /**
     * 设置分割线的资源
     *
     * @param divider
     * @return
     */
    public T setDivider(Drawable divider) {
        mListView.setDivider(divider);
        return (T) this;
    }

    /**
     * 设置分割线的高度
     *
     * @param dividerHeight
     * @return
     */
    public T setDividerHeight(int dividerHeight) {
        mListView.setDividerHeight(dividerHeight);
        return (T) this;
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

}
