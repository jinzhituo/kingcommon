package com.zhuanbang.kingcommon.terminal.contract;

import com.zhuanbang.kingcommon.terminal.PosTerminal;
import com.zhuanbang.kingcommonlib.base.IModel;
import com.zhuanbang.kingcommonlib.base.IPresenter;
import com.zhuanbang.kingcommonlib.base.IView;

import java.util.List;

import io.reactivex.Observable;


/**
 * 作者：ZhengQunWei on 2018/6/6 10:14
 */
public interface TerminalContract {
    interface Model extends IModel {

        Observable<List<PosTerminal>> getTerminalList(int pageNo);

    }

    interface View extends IView {

        void onReturnTerminalList(List<PosTerminal> posTerminalList);


    }

    interface Presenter extends IPresenter {

        void getTerminalList(int pageNo);

    }
}
