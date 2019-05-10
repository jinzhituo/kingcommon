package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.BaseDao;

/**
 * 作者：Jzt on 2019/5/10
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
