package com.zhuanbang.kingcommonlib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * 用于V-layout使用
 */
public class BaseDelegateAdapter<T> extends DelegateAdapter.Adapter<BaseViewHolder> {
    protected Context mContext;
    private int layoutId;
    private LayoutHelper mLayoutHelper;
    private int mViewItemType = 0;
    public List<T> mData;
    private Map<Integer, Integer> itemLayoutMap;
    private OnItemClickListener onItemClickListener;
    private OnItemChildClickListener onItemChildClickListener;

    /**
     * 列表点击事件
     */
    public interface OnItemClickListener {
        /**
         * 列表点击
         *
         * @param adapter
         * @param itemView
         * @param position
         */
        void onItemClick(BaseDelegateAdapter adapter, View itemView, int position);
    }

    /**
     * 列表子控件点击事件
     */
    public interface OnItemChildClickListener {
        /**
         * 列表子控件点击事件
         *
         * @param adapter
         * @param viewId
         * @param position
         */
        void onItemChildClick(BaseDelegateAdapter adapter, int viewId, int position);
    }

    public BaseDelegateAdapter(Context context, int layoutId, LayoutHelper layoutHelper, List<T> data) {
        super();
        mContext = context;
        this.layoutId = layoutId;
        mLayoutHelper = layoutHelper;
        mData = data;
    }

    public BaseDelegateAdapter(Context context, LayoutHelper layoutHelper, List<T>
            data) {
        mContext = context;
        mLayoutHelper = layoutHelper;
        mData = data;
    }

    public T getItem(int position) {
        if (mData == null || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (itemLayoutMap == null || itemLayoutMap.size() == 0) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(layoutId, parent, false));
        } else {
            Integer layout = itemLayoutMap.get(viewType);
            if (layout == null || layout == 0)
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(layoutId, parent, false));
            else
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(this, holder.itemView, position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mViewItemType;
    }

    @Override
    public int getItemCount() {
        return mData == null ? -1 : mData.size();
    }


    /**
     * 更新数据源
     *
     * @param data
     */
    public void setNewData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    /**
     * 清空新的原数据，添加新数据
     *
     * @param data
     */
    public void replaceData(List<T> data) {
        // 不是同一个引用才清空列表
        if (data != mData) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 新增数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        int startPosition = mData.size() - 1;
        mData.addAll(data);
        notifyItemRangeChanged(startPosition, data.size());
    }


    protected void addItemType(int itemType, int layoutId) {
        if (itemLayoutMap == null) {
            itemLayoutMap = new HashMap<>();
        }
        itemLayoutMap.put(itemType, layoutId);
    }

}
