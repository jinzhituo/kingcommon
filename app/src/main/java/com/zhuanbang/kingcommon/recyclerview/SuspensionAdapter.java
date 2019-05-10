package com.zhuanbang.kingcommon.recyclerview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuanbang.kingcommon.R;

import java.util.List;

public class SuspensionAdapter extends BaseQuickAdapter<SuspensionSectionBean, BaseViewHolder> {

    public SuspensionAdapter(List<SuspensionSectionBean> data) {
        super(R.layout.item_suspension, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SuspensionSectionBean item) {
        helper.setText(R.id.tv_title, item.title);
    }
}
