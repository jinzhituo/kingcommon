package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.BaseDao;

/**
 * 作者：ZhengQunWei on 2017/10/10 10:51
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
