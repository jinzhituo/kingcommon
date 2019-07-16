package com.zhuanbang.kingcommonlib.rx;

public class BaseCommonException extends RuntimeException {

    public static final int NO_DATA = -1;// 没有数据

    public static final int ERROR = 0;

    public static final int NOT_LOGIN = 2;// 没有登录

    public static final int MONEY_NOT_ENOUGH = 3;// 余额不足

    public static final int CAN_NOT_SET = 4;// 无法设置

    public static final int NOT_REAL_NAME_AUTH = 5;// 没有实名认证

    public static final int EXCEPTION = 6;

    public static final int CANCEL = 7;

    public static final int SERVER_ERROR = 8;

    public static final int NET_ERROR = 9;

    public static final int FILE_NOT_EXITS = 10;// 文件不存在

    public static final int NOT_PAY = 11;// 权限过期

    public static final int PAY_FAIL = 12;// 支付失败

    public static final int NOT_OPEN_SHOP = 14;// 未开通对外运营店铺

    public static final int NEED_POLL = 15;//支付状态未知需要轮询检查

    public static final int NO_PERMISSION = 13;//支付状态未知需要轮询检查

    public static final int NO_AUTHORITY = 20;//无权限

    private int code;

    public int getCode() {
        return code;
    }

    public BaseCommonException(String message, int code) {
        super(message);
        this.code = code;
    }
}
