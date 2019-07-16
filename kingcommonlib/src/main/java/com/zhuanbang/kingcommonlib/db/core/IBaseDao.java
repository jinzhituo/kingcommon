package com.zhuanbang.kingcommonlib.db.core;

import java.util.List;

public interface IBaseDao<T> {
    /**
     * 插入对象到数据库接口
     *
     * @param entity 需要插入到数据库的对象
     * @return 主键Id
     */
    Long insert(T entity);

    /**
     * 更新对象到数据库接口
     *
     * @param entity 需要更新的对象对象
     * @param where  条件
     * @return 影响的行数
     */
    int update(T entity, T where);


    /**
     * 查询数组
     *
     * @param where 条件
     * @return 对象数组
     */
    List<T> queryList(T where, int pageSize, int pageNum);

    /**
     * 查询单个对象接口
     *
     * @param where 条件
     * @return 对象
     */
    T queryObject(T where);

    /**
     * 删除表中所有数据接口
     *
     * @return 影响行数
     */
    void deleteAll();

    /**
     * 删除单条数据接口
     *
     * @param where 条件
     * @return 影响行数
     */
    void deleteObject(T where);

    String createTable();
}
