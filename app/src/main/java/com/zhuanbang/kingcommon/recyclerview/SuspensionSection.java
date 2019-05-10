package com.zhuanbang.kingcommon.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.zhuanbang.kingcommonlib.utils.DisplayUtil;
import com.zhuanbang.kingcommonlib.widget.section.SectionConvert;


public class SuspensionSection implements SectionConvert<SuspensionSectionBean.SuspensionSectionTagBean> {
    Paint mBGPaint;
    Paint mTextPaint;
    Paint mLinePaint;
    int dp_16 = DisplayUtil.dip2px(16);

    public SuspensionSection() {
        mBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBGPaint.setColor(Color.parseColor("#f5f5f5"));
        mBGPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#333333"));
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#e1e1e1"));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
    }

    @Override
    public void drawDecoration(Canvas canvas, int left, int right, int top, int bottom, SuspensionSectionBean.SuspensionSectionTagBean suspensionSectionTagBean) {
        //绘制灰色区域
        RectF targetRect = new RectF(left, top, right, top + 60);
        mBGPaint.setColor(Color.parseColor("#f5f5f5"));
        canvas.drawRect(targetRect, mBGPaint);
        //画笔测量
        mTextPaint.setFakeBoldText(false);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        String date = suspensionSectionTagBean.subtitle;
        //文字基准线
        int baseline = (int) ((targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        //绘制日期
        mTextPaint.setTextSize(DisplayUtil.sp2px(16));
        canvas.drawText(date, left + dp_16, baseline, mTextPaint);
    }
}
