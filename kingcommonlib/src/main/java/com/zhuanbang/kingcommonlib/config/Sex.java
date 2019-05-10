package com.zhuanbang.kingcommonlib.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

/**
 * 作者：Jzt on 2019/5/10
 */
public class Sex {
    /**
     * 男
     */
    public static final int MAN = 1;
    /**
     * 女
     */
    public static final int FEMALE = 0;

    @IntDef({MAN, FEMALE,})
    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD,}) //表示注解作用范围，参数注解，成员注解，方法注解
    @Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在 .class 文件中
    public @interface SexType {
    }

}
