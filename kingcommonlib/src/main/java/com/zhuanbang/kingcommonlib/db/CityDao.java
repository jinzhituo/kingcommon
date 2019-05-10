package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.BaseDao;

/**
 * 作者：ZhengQunWei on 2017/9/30 14:28
 */
public class CityDao extends BaseDao {
    public String createDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + "(\n" + "  `id` int(10) NOT NULL,\n" + "  `cityName` " +
                "varchar(50) ,\n" + "  " + "`shortCityName` varchar(50) ,\n" + "  `allspell` varchar(50) ,\n" + "  " +
                "`domainName` varchar(30) ,\n" + "  `provinceid` int(10) DEFAULT NULL,\n" + "  `isorder` int(5) " +
                "DEFAULT '0',\n" + "  PRIMARY KEY " + "(`id`)\n" + ")";
    }

    @Override
    public String createTable() {
        return null;
    }
}
