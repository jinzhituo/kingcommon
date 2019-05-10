package com.zhuanbang.kingcommon.recyclerview;


import com.zhuanbang.kingcommonlib.widget.section.BaseSection;

public class SuspensionSectionBean extends BaseSection<SuspensionSectionBean.SuspensionSectionTagBean> {

    String title;
    String subtitle;
    SuspensionSectionTagBean tag;

    public SuspensionSectionBean(String title, String subtitle, SuspensionSectionTagBean tag) {
        this.title = title;
        this.subtitle = subtitle;
        this.tag = tag;
    }

    @Override
    public SuspensionSectionTagBean getHeadSection() {
        return tag;
    }

    @Override
    public boolean isEquals(BaseSection<SuspensionSectionBean.SuspensionSectionTagBean> section) {
        if (section == null || section.getHeadSection() == null || section.getHeadSection().subtitle == null ||
                tag == null)
            return false;
        return section.getHeadSection().subtitle.equals(tag.subtitle);
    }

    public static class SuspensionSectionTagBean {
        String subtitle;

        public SuspensionSectionTagBean(String subtitle) {
            this.subtitle = subtitle;
        }
    }
}
