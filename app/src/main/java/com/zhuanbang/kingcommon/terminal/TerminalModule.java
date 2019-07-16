package com.zhuanbang.kingcommon.terminal;


import com.zhuanbang.kingcommon.terminal.contract.TerminalContract;
import com.zhuanbang.kingcommon.terminal.model.TerminalModel;
import com.zhuanbang.kingcommonlib.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class TerminalModule {
    TerminalContract.View mView;

    public TerminalModule(TerminalContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    TerminalContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    TerminalContract.Model provideModel(TerminalModel terminalModel) {
        return terminalModel;
    }
}
