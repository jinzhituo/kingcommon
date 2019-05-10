package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.DaoManagerFactory;
import com.zhuanbang.kingcommonlib.db.core.Session;
import com.zhuanbang.kingcommonlib.db.model.City;
import com.zhuanbang.kingcommonlib.db.model.District;
import com.zhuanbang.kingcommonlib.db.model.Province;

/**
 * 作者：ZhengQunWei on 2018/3/19 14:16
 */
public class CityUtils {
    static Session<City> sCityDao;
    static Session<Province> sProvinceDao;
    static Session<District> sDistrictDao;

    static {
        sCityDao = DaoManagerFactory.getInstance().getDataHelper(City.class);
        sProvinceDao = DaoManagerFactory.getInstance().getDataHelper(Province.class);
        sDistrictDao = DaoManagerFactory.getInstance().getDataHelper(District.class);
    }


    /**
     * 获取城市名称
     *
     * @param cityId
     * @return
     */
    public static String getCityName(Long cityId) {
        if (cityId == null || cityId.equals(0L)) {
            return "";
        } else {
            City where = new City();
            where.setCityId(cityId);
            Object result = sCityDao.queryObject(where);
            if (result == null) return "";
            else return ((City) result).getShortCityName();
        }
    }

    /**
     * 获取省份名称
     *
     * @param provinceId
     * @return
     */
    public static String getProvinceName(Long provinceId) {
        if (provinceId == null || provinceId.equals(0L)) {
            return "";
        } else {
            Province where = new Province();
            where.setProvinceId(provinceId);
            Object result = sProvinceDao.queryObject(where);
            if (result == null) return "";
            else return ((Province) result).getShortname();
        }
    }

    /**
     * 获取地区名称
     *
     * @param districtId
     * @return
     */
    public static String getDistrictName(Long districtId) {
        if (districtId == null || districtId.equals(0L)) {
            return "";
        } else {
            District where = new District();
            where.setDistrictId(districtId);
            Object result = sDistrictDao.queryObject(where);
            if (result == null) return "";
            else return ((District) result).getDistrictName();
        }
    }
}
