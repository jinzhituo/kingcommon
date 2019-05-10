package com.zhuanbang.kingcommonlib.db;


import com.zhuanbang.kingcommonlib.db.core.BaseDao;

/**
 * 作者：Jzt on 2019/5/10
 */
public class ShopCategoryDao extends BaseDao {

    @Override
    public String createDataBase() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " ( 'id' int(10) NOT NULL , 'categoryName' char(50) , " +
                "'categoryImage' char(250) , 'level' int(10) , 'parentId' int(10) , PRIMARY KEY (`id`) )";
    }

    @Override
    public String createTable() {
        return null;
    }
}
