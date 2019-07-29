package com.zhuanbang.kingcommonlib.utils;

import android.view.KeyEvent;

/**
 * 拦截扫码枪和外接设备 的输入事件
 * 扫码枪和 外接键盘的处理是一样的
 */
public class ScannerGunManager {
    String codeStr = "";
    OnScanListener listener;

    boolean isInterrupt = true;

    public ScannerGunManager(OnScanListener listener) {
        this.listener = listener;
    }

    /**
     * 处理输入事件
     *
     * @param event
     * @return true 表示消费掉，拦截不在传递， false 不管
     */
    public boolean dispatchKeyEvent(KeyEvent event) {

        /**
         * 系统的软键盘  按下去是 -1, 不管，不拦截
         */
        if (event.getDeviceId() == -1) {
            return false;
        }

        //按下弹起，识别到弹起的话算一次 有效输入
        //只要是 扫码枪的事件  都要把他消费掉 不然会被editText 显示出来
        if (event.getAction() == KeyEvent.ACTION_UP) {

            //ASCII码转换成字符串
            int code = event.getKeyCode();
            if (code != KeyEvent.KEYCODE_ENTER)
                codeStr += keyCodeToChar(code, false);

//            if (code >= KeyEvent.KEYCODE_0 && code <= KeyEvent.KEYCODE_9) {
//                codeStr += (code - KeyEvent.KEYCODE_0);
//            }

            //识别到结束，当下使用的设备是  是还会有个KEYCODE_DPAD_DOWN 事件，不知道其它设备有没有  先忽略
            if (code == KeyEvent.KEYCODE_ENTER) {

                if (listener != null) {
                    listener.onResult(codeStr);
                    codeStr = "";
                }
            }

        }
        //都是扫码枪来的事件，选择消费掉

        return isInterrupt;
    }


    public interface OnScanListener {

        void onResult(String code);
    }

    public void setInterrupt(boolean interrupt) {
        isInterrupt = interrupt;
    }

    public String keyCodeToChar(int code, boolean isShift) {
        switch (code) {

            case KeyEvent.KEYCODE_SHIFT_LEFT:
                return "";

            //数字键10个 + 符号10个
            case KeyEvent.KEYCODE_0:
                return isShift ? ")" : "0";
            case KeyEvent.KEYCODE_1:
                return isShift ? "!" : "1";
            case KeyEvent.KEYCODE_2:
                return isShift ? "@" : "2";
            case KeyEvent.KEYCODE_3:
                return isShift ? "#" : "3";
            case KeyEvent.KEYCODE_4:
                return isShift ? "$" : "4";
            case KeyEvent.KEYCODE_5:
                return isShift ? "%" : "5";
            case KeyEvent.KEYCODE_6:
                return isShift ? "^" : "6";
            case KeyEvent.KEYCODE_7:
                return isShift ? "&" : "7";
            case KeyEvent.KEYCODE_8:
                return isShift ? "*" : "8";
            case KeyEvent.KEYCODE_9:
                return isShift ? "(" : "9";

            //字母键26个小写 + 26个大写
            case KeyEvent.KEYCODE_A:
                return isShift ? "A" : "a";
            case KeyEvent.KEYCODE_B:
                return isShift ? "B" : "b";
            case KeyEvent.KEYCODE_C:
                return isShift ? "C" : "c";
            case KeyEvent.KEYCODE_D:
                return isShift ? "D" : "d";
            case KeyEvent.KEYCODE_E:
                return isShift ? "E" : "e";
            case KeyEvent.KEYCODE_F:
                return isShift ? "F" : "f";
            case KeyEvent.KEYCODE_G:
                return isShift ? "G" : "g";
            case KeyEvent.KEYCODE_H:
                return isShift ? "H" : "h";
            case KeyEvent.KEYCODE_I:
                return isShift ? "I" : "i";
            case KeyEvent.KEYCODE_J:
                return isShift ? "J" : "j";
            case KeyEvent.KEYCODE_K:
                return isShift ? "K" : "k";
            case KeyEvent.KEYCODE_L:
                return isShift ? "L" : "l";
            case KeyEvent.KEYCODE_M:
                return isShift ? "M" : "m";
            case KeyEvent.KEYCODE_N:
                return isShift ? "N" : "n";
            case KeyEvent.KEYCODE_O:
                return isShift ? "O" : "o";
            case KeyEvent.KEYCODE_P:
                return isShift ? "P" : "p";
            case KeyEvent.KEYCODE_Q:
                return isShift ? "Q" : "q";
            case KeyEvent.KEYCODE_R:
                return isShift ? "R" : "r";
            case KeyEvent.KEYCODE_S:
                return isShift ? "S" : "s";
            case KeyEvent.KEYCODE_T:
                return isShift ? "T" : "t";
            case KeyEvent.KEYCODE_U:
                return isShift ? "U" : "u";
            case KeyEvent.KEYCODE_V:
                return isShift ? "V" : "v";
            case KeyEvent.KEYCODE_W:
                return isShift ? "W" : "w";
            case KeyEvent.KEYCODE_X:
                return isShift ? "X" : "x";
            case KeyEvent.KEYCODE_Y:
                return isShift ? "Y" : "y";
            case KeyEvent.KEYCODE_Z:
                return isShift ? "Z" : "z";

            //符号键11个 + 11个
            case KeyEvent.KEYCODE_COMMA:
                return isShift ? "<" : ",";
            case KeyEvent.KEYCODE_PERIOD:
                return isShift ? ">" : ".";
            case KeyEvent.KEYCODE_SLASH:
                return isShift ? "?" : "/";
            case KeyEvent.KEYCODE_BACKSLASH:
                return isShift ? "|" : "\\";
            case KeyEvent.KEYCODE_APOSTROPHE:
                return isShift ? "\"" : "'";
            case KeyEvent.KEYCODE_SEMICOLON:
                return isShift ? ":" : ";";
            case KeyEvent.KEYCODE_LEFT_BRACKET:
                return isShift ? "{" : "[";
            case KeyEvent.KEYCODE_RIGHT_BRACKET:
                return isShift ? "}" : "]";
            case KeyEvent.KEYCODE_GRAVE:
                return isShift ? "~" : "`";
            case KeyEvent.KEYCODE_EQUALS:
                return isShift ? "+" : "=";
            case KeyEvent.KEYCODE_MINUS:
                return isShift ? "_" : "-";

            default:
                return "?";
        }
    }
}
