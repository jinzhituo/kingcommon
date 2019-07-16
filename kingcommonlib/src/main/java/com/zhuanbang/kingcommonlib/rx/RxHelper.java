package com.zhuanbang.kingcommonlib.rx;


import android.util.Log;


import com.zhuanbang.kingcommonlib.http.entity.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;


/**
 * 服务端数据进行预处理判断请求是成功还是失败
 */
public class RxHelper {

    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return baseResponseObservable -> baseResponseObservable.flatMap(tBaseResponse -> {
            if (tBaseResponse != null) {
                if (tBaseResponse.success()) {
                    return createData(tBaseResponse.getData());
                } else {
                    return Observable.error(new BaseCommonException(tBaseResponse.getMsg(), tBaseResponse.getCode()));
                }
            } else {
                return Observable.error(new BaseCommonException("未知错误", BaseCommonException.NET_ERROR));
            }
        });
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create((subscriber -> {
            try {
                Object nu = null;
                if (data == null) {
                    nu = new Boolean(true);
                }
                subscriber.onNext(data == null ? (T) nu : data);
                subscriber.onComplete();
            } catch (Exception e) {
                Log.e("RxHelper", e.getMessage());
                subscriber.onError(e);
            }
        }));

    }
}
