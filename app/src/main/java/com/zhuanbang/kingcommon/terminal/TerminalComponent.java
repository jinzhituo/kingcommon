package com.zhuanbang.kingcommon.terminal;

import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;
import com.zhuanbang.kingcommonlib.dagger.scope.ActivityScope;

import dagger.Component;


@ActivityScope
@Component(modules = TerminalModule.class, dependencies = AppComponent.class)
public interface TerminalComponent {

    void inject(TerminalManagerActivity terminalManagerActivity);
}
