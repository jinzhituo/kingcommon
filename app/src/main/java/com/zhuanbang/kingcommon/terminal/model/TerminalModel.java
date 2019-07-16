package com.zhuanbang.kingcommon.terminal.model;


import com.zhuanbang.kingcommon.api.TerminalApiService;
import com.zhuanbang.kingcommon.terminal.PosTerminal;
import com.zhuanbang.kingcommon.terminal.contract.TerminalContract;
import com.zhuanbang.kingcommonlib.base.BaseModel;
import com.zhuanbang.kingcommonlib.base.IRepositoryManager;
import com.zhuanbang.kingcommonlib.rx.RxHelper;
import com.zhuanbang.kingcommonlib.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * 作者：ZhengQunWei on 2018/6/6 10:14
 */
public class TerminalModel extends BaseModel implements TerminalContract.Model {
    @Inject
    public TerminalModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<PosTerminal>> getTerminalList(int pageNo) {
        return mRepositoryManager.getRetrofitService("http://192.168.1.33:8084/", TerminalApiService.class)
                .posTerminalList(pageNo).compose(RxSchedulers.io_main()).compose(RxHelper.handleResult());
    }

}
