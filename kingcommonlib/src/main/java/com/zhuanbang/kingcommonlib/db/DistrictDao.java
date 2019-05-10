package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.BaseDao;

/**
 * 作者：ZhengQunWei on 2017/10/10 10:47
 */
public class DistrictDao extends BaseDao {
    public String createDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" + "  `id` int(10) NOT NULL ,\n" + "  `districtName`" +
                " varchar(50) DEFAULT " + "NULL,\n" + "  `cityId` int(10) DEFAULT NULL,\n" + "  PRIMARY KEY (`id`)\n"
                + ")";
    }
    @Override
    public String createTable() {
        return null;
    }
}
