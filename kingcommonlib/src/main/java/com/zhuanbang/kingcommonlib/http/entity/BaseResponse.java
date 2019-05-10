package com.zhuanbang.kingcommonlib.http.entity;

import java.io.Serializable;

/**
 * 服务器返回数据基本结构
 * 作者：Jzt on 2019/5/10
 */
public class BaseResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }

    public boolean success() {
        return code == 1;
    }
}
