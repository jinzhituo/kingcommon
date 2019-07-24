package com.zhuanbang.kingcommonlib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.utils.image.ImageSize;

import java.io.File;

public class GlideUtils {

    /**
     * 加载原始图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        if (context == null) return;
        if (imageView.getTag(R.string.album_view) == null || !imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl)).apply(ArmsUtils.obtainAppComponentFromContext(context).imageLoadOption()
                    .clone()).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }
    }

    /**
     * 加载原始图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadImage(Context context, int imageRes, ImageView imageView) {
        if (context == null) return;
        if (imageView.getTag(R.string.album_view) == null || !imageView.getTag(R.string.album_view).equals(imageRes)) {
            Glide.with(context).load(imageRes).apply(ArmsUtils.obtainAppComponentFromContext(context).imageLoadOption()
                    .clone()).into(imageView);
            imageView.setTag(R.string.album_view, imageRes);
        }
    }

    /**
     * 加载原始图片
     *
     * @param file      图片文件
     * @param imageView 图片控件
     */
    public static void loadImage(Context context, File file, ImageView imageView) {
        if (context == null) return;
        if (imageView.getTag(R.string.album_view) == null || !imageView.getTag(R.string.album_view).equals(file.getAbsolutePath())) {
            Glide.with(context).load(file).apply(ArmsUtils.obtainAppComponentFromContext(context).imageLoadOption()
                    .centerCrop().clone()).into(imageView);
            imageView.setTag(R.string.album_view, file.getAbsoluteFile());
        }

    }

    /**
     * 加载240X240尺寸的图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadImage240(Context context, String imageUrl, ImageView imageView) {
        loadImageBySize(context, imageUrl, imageView, ImageSize.X_240);
    }

    /**
     * 加载480X480尺寸的图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadImage430(Context context, String imageUrl, ImageView imageView) {
        loadImageBySize(context, imageUrl, imageView, ImageSize.X_430);
    }

    /**
     * 加载480X285尺寸的图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadImage285(Context context, String imageUrl, ImageView imageView) {
        loadImageBySize(context, imageUrl, imageView, ImageSize.X_285);
    }

    /**
     * 加载不同尺寸的图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     * @param size      图片规格(服务器端规定)
     */
    public static void loadImageBySize(Context context, String imageUrl, ImageView imageView, String size) {
        if (context == null || TextUtils.isEmpty(imageUrl) || imageUrl.equals("")) {
            return;
        }
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl).replace(ImageSize.NOMAL, size)).apply(ArmsUtils
                    .obtainAppComponentFromContext(context).imageLoadOption().clone()).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 加载圆形图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadCircleImage(Context context, String imageUrl, ImageView imageView) {
        if (context == null) return;
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(imageUrl).apply(ArmsUtils.obtainAppComponentFromContext(context).imageLoadOption()
                    .clone().transform(new GlideCircleTransform())).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 加载圆形图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadCircleImage(Context context, String imageUrl, ImageView imageView, int placeImage) {
        if (context == null) return;
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl)).apply(ArmsUtils.obtainAppComponentFromContext(context).imageLoadOption()
                    .clone().placeholder(placeImage).error(placeImage).transform(new GlideCircleTransform())).into
                    (imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 加载圆形图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     */
    public static void loadCircleImageWithSize(Context context, String imageUrl, ImageView imageView, String size) {
        if (context == null || TextUtils.isEmpty(imageUrl) || imageUrl.equals("")) {
            return;
        }
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl).replace(ImageSize.NOMAL, size)).apply(ArmsUtils
                    .obtainAppComponentFromContext(context).imageLoadOption().clone().transform(new GlideCircleTransform
                            ())).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 加载圆角图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     * @param radius    圆角半径
     */
    public static void loadRoundImage(Context context, String imageUrl, ImageView imageView, int radius) {
        if (context == null) return;
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl)).apply(ArmsUtils.obtainAppComponentFromContext(context).imageLoadOption()
                    .clone().transform(new GlideRoundTransform(radius))).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 加载圆角图片(自定义加载图)
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     * @param radius    圆角半径
     */
    public static void loadRoundImage(Context context, String imageUrl, ImageView imageView, int radius, int
            placeImage) {
        if (context == null) return;
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl)).apply(ArmsUtils.obtainAppComponentFromContext(context).imageLoadOption()
                    .clone().placeholder(placeImage).transform(new GlideRoundTransform(radius))).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 加载圆角图片
     *
     * @param imageUrl  图片链接
     * @param imageView 图片控件
     * @param radius    圆角半径
     */
    public static void loadRoundImageWithSize(Context context, String imageUrl, ImageView imageView, int radius,
                                              String size) {
        if (context == null || TextUtils.isEmpty(imageUrl) || imageUrl.equals("")) {
            return;
        }
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl).replace(ImageSize.NOMAL, size)).apply(ArmsUtils
                    .obtainAppComponentFromContext(context).imageLoadOption().clone().transform(new GlideRoundTransform
                            (radius))).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 自定义加载参数
     *
     * @param context
     * @param imageUrl
     * @param requestOptions
     * @param imageView
     */
    public static void loadImage(Context context, String imageUrl, RequestOptions requestOptions, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(imageUrl) || imageUrl.equals("")) {
            return;
        }
        if (!imageUrl.equals(imageView.getTag(R.string.album_view))) {
            Glide.with(context).load(checkUrl(imageUrl)).apply(requestOptions).into(imageView);
            imageView.setTag(R.string.album_view, imageUrl);
        }

    }

    /**
     * 应用于快速滑动时暂停加载场景
     *
     * @param context
     */
    public static void pauseRequests(Context context) {
        if (context == null) {
            return;
        }
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复glide加载
     *
     * @param context
     */
    public static void resumeRequests(Context context) {
        if (context == null) {
            return;
        }
        Glide.with(context).resumeRequests();
    }

    private static String checkUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (!url.contains("https")) {
            return url.replace("http", "https");
        }
        return url;
    }

}
