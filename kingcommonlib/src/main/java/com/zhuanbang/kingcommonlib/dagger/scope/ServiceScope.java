package com.zhuanbang.kingcommonlib.dagger.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：Jzt on 2019/5/10
 */
@Scope
@Retention(RUNTIME)
public @interface ServiceScope {
}
