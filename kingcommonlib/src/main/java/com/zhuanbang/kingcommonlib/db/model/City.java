package com.zhuanbang.kingcommonlib.db.model;


import com.zhuanbang.kingcommonlib.db.core.DBFiled;
import com.zhuanbang.kingcommonlib.db.core.DBPrimaryKey;
import com.zhuanbang.kingcommonlib.db.core.DBTable;
import com.zhuanbang.kingcommonlib.widget.IPickViewData;

/**
 * Created by ZhengQunWei
 * on 2017/6/30
 */
@DBTable("city")
public class City extends IPickViewData {

    @DBPrimaryKey("id")
    Long cityId;
    @DBFiled("cityName")
    String cityName;
    @DBFiled("shortCityName")
    String shortCityName;
    @DBFiled("allspell")
    String allspell;
    @DBFiled("domainName")
    String domainName;
    @DBFiled("provinceid")
    Long provinceId;
    @DBFiled("isorder")
    Integer isorder;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShortCityName() {
        return shortCityName;
    }

    public void setShortCityName(String shortCityName) {
        this.shortCityName = shortCityName;
    }

    public String getAllspell() {
        return allspell;
    }

    public void setAllspell(String allspell) {
        this.allspell = allspell;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getIsorder() {
        return isorder;
    }

    public void setIsorder(Integer isorder) {
        this.isorder = isorder;
    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(this.cityId);
//        dest.writeString(this.cityName);
//        dest.writeString(this.shortCityName);
//        dest.writeString(this.allspell);
//        dest.writeString(this.domainName);
//        dest.writeValue(this.provinceId);
//        dest.writeValue(this.isorder);
//    }
//
//    public City() {
//    }
//
//    protected City(Parcel in) {
//        this.cityId = (Long) in.readValue(Long.class.getClassLoader());
//        this.cityName = in.readString();
//        this.shortCityName = in.readString();
//        this.allspell = in.readString();
//        this.domainName = in.readString();
//        this.provinceId = (Long) in.readValue(Long.class.getClassLoader());
//        this.isorder = (Integer) in.readValue(Integer.class.getClassLoader());
//    }
//
//    public static final Creator<City> CREATOR = new Creator<City>() {
//        @Override
//        public City createFromParcel(Parcel source) {
//            return new City(source);
//        }
//
//        @Override
//        public City[] newArray(int size) {
//            return new City[size];
//        }
//    };

    @Override
    public String getName() {
        return shortCityName;
    }

    @Override
    public Long getId() {
        return cityId;
    }
}
