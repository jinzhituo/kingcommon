package com.zhuanbang.kingcommonlib.db.model;


import com.zhuanbang.kingcommonlib.db.core.DBFiled;
import com.zhuanbang.kingcommonlib.db.core.DBPrimaryKey;
import com.zhuanbang.kingcommonlib.db.core.DBTable;
import com.zhuanbang.kingcommonlib.widget.IPickViewData;

@DBTable("province")
public class Province extends IPickViewData {

    @DBPrimaryKey("id")
    private Long provinceId;
    @DBFiled("provincename")
    private String provinceName;
    @DBFiled("shortname")
    private String shortname;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @Override
    public String getName() {
        return shortname;
    }

    @Override
    public Long getId() {
        return provinceId;
    }
}
