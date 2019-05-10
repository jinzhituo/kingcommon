package com.zhuanbang.kingcommonlib.db.model;


import com.zhuanbang.kingcommonlib.db.core.DBFiled;
import com.zhuanbang.kingcommonlib.db.core.DBPrimaryKey;
import com.zhuanbang.kingcommonlib.db.core.DBTable;
import com.zhuanbang.kingcommonlib.widget.IPickViewData;

/**
 * 作者：Jzt on 2019/5/10
 */
@DBTable("district")
public class District extends IPickViewData {
    @DBPrimaryKey("id")
    Long districtId;//区县Id
    @DBFiled("districtName")
    String districtName;//名称
    @DBFiled("cityId")
    Long cityId;

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(this.districtId);
//        dest.writeString(this.districtName);
//        dest.writeValue(this.cityId);
//    }
//
//    public District() {
//    }
//
//    protected District(Parcel in) {
//        this.districtId = (Long) in.readValue(Long.class.getClassLoader());
//        this.districtName = in.readString();
//        this.cityId = (Long) in.readValue(Long.class.getClassLoader());
//    }
//
//    public static final Creator<District> CREATOR = new Creator<District>() {
//        @Override
//        public District createFromParcel(Parcel source) {
//            return new District(source);
//        }
//
//        @Override
//        public District[] newArray(int size) {
//            return new District[size];
//        }
//    };

    @Override
    public String getName() {
        return districtName;
    }

    @Override
    public Long getId() {
        return districtId;
    }
}
