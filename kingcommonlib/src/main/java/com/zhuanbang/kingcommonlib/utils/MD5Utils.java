package com.zhuanbang.kingcommonlib.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

/**
 * 作者：ZhengQunWei on 2017/10/27 16:17
 */
public class MD5Utils {
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getBytesMD5(byte[] bytes) {
        try {

            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            mdInst.update(bytes);

            // 获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回字符串的32位MD5值
     *
     * @param s 字符串
     * @return str MD5值
     */
    public final static String getStringMD5(String s) {
        return getBytesMD5(s.getBytes());
    }

    /**
     * 返回字符串的32位MD5值
     *
     * @param s 字符串
     * @return str MD5值
     */
    public final static String getUpperCaseStringMD5(String s) {
        return getBytesMD5(s.getBytes()).toUpperCase();
    }

    /**
     * 返回字符串的16位MD5值
     *
     * @param s 字符串
     * @return str MD5值
     */
    public final static String getStringMD5_16(String s) {
        return getStringMD5(s).substring(8, 24);
    }

    /**
     * 返回字符串的16位MD5值
     *
     * @param s 字符串
     * @return str MD5值
     */
    public final static String getUpperCaseStringMD5_16(String s) {
        return getStringMD5(s).substring(8, 24).toUpperCase();
    }

    public final static String getBitmapMD5(Bitmap bm) {
        return getBytesMD5(bitmapToBytes(bm));
    }

    public final static String getBitmapMD5_16(Bitmap bm) {
        return getBytesMD5(bitmapToBytes(bm)).substring(8, 24);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
