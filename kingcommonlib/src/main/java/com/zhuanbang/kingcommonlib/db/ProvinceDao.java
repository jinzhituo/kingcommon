package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.BaseDao;

/**
 * 作者：Jzt on 2019/5/10
 */
public class ProvinceDao extends BaseDao {
    @Override
    public String createDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + "(\n" + "  `id` int(10) NOT NULL ,\n" + "  `provincename` " +
                "" + "varchar(50) ,\n" + "" + "  `shortname` varchar(50) ,\n" + "  PRIMARY KEY (`id`)\n" + ")";
    }

    @Override
    public String createTable() {
        return null;
    }
}
