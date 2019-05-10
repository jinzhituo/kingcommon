package com.zhuanbang.kingcommonlib.db.model;


import com.zhuanbang.kingcommonlib.db.core.DBFiled;
import com.zhuanbang.kingcommonlib.db.core.DBPrimaryKey;
import com.zhuanbang.kingcommonlib.db.core.DBTable;
import com.zhuanbang.kingcommonlib.widget.IPickViewData;

/**
 * 作者：Jzt on 2019/5/10
 */
@DBTable("businesscircle")
public class BusinessCircle extends IPickViewData {
    @DBPrimaryKey("id")
    Long businessCircleId;//商圈Id
    @DBFiled("circleName")
    String circleName;//商圈名
    @DBFiled("districtId")
    Long districtId;
    @DBFiled("cityId")
    Long cityId;

    public Long getBusinessCircleId() {
        return businessCircleId;
    }

    public void setBusinessCircleId(Long businessCircleId) {
        this.businessCircleId = businessCircleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }



    @Override
    public String getName() {
        return circleName;
    }

    @Override
    public Long getId() {
        return businessCircleId;
    }
}
