package com.zhuanbang.kingcommon.terminal.presenter;

import com.zhuanbang.kingcommon.terminal.PosTerminal;
import com.zhuanbang.kingcommon.terminal.contract.TerminalContract;
import com.zhuanbang.kingcommonlib.base.BasePresenter;
import com.zhuanbang.kingcommonlib.rx.BaseCommonException;
import com.zhuanbang.kingcommonlib.rx.RxSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class TerminalPresenter extends BasePresenter<TerminalContract.Model, TerminalContract.View> implements
        TerminalContract.Presenter {
    @Inject
    public TerminalPresenter(TerminalContract.View rootView, TerminalContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void getTerminalList(int pageNo) {
        mModel.getTerminalList(pageNo).subscribe(new RxSubscriber<List<PosTerminal>>(mContext, false, true) {
            @Override
            protected void _onNext(List<PosTerminal> posTerminalList) {
                mRootView.onReturnTerminalList(posTerminalList);
            }

            @Override
            protected void _onError(BaseCommonException e) {
                mRootView.showErrorTip(e.getCode(), e.getMessage());
            }

            @Override
            protected void _onSubscribe(Disposable d) {
                addDispose(d);
            }
        });
    }

}
