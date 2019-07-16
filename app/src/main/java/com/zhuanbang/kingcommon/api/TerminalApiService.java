package com.zhuanbang.kingcommon.api;


import com.zhuanbang.kingcommon.terminal.PosTerminal;
import com.zhuanbang.kingcommonlib.http.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：ZhengQunWei on 2018/6/5 15:21
 */
public interface TerminalApiService {
    /**
     * （查询时） 收款终端
     *
     * @return List<PosTerminal>
     */
    @GET("api/StoreOrder_posTerminalListByQueryOrder")
    Observable<BaseResponse<List<PosTerminal>>> getPosTerminalListForQuery();

    /**
     * 查看设备列表
     *
     * @param pageNo 页码
     * @return List<PosTerminal>
     */
    @GET("edcmanageapi/Ad_sleep")
    Observable<BaseResponse<List<PosTerminal>>> posTerminalList(@Query("pageNo") int pageNo);
}
