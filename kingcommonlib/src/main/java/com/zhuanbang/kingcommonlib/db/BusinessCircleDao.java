package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.BaseDao;

/**
 * 作者：ZhengQunWei on 2017/9/30 14:28
 */
public class BusinessCircleDao extends BaseDao {
    @Override
    public String createDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + "(\n" + "  `id` int(11) NOT NULL ,\n" + "  `circleName` " +
                "varchar(50) ,\n" + "  `districtId` int(10) DEFAULT NULL,\n" + "  `cityId` int(6) NOT NULL,\n" + "  " +
                "PRIMARY KEY (`id`)\n" + ")";
    }

    @Override
    public String createTable() {
        return null;
    }
}
