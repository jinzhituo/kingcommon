package com.zhuanbang.kingcommon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CocoView extends View {
    private static final String tag = "CocoView";
    private Paint rectPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint linePaint = new Paint();
    private Paint pathPaint = new Paint();
    private Paint textPaint = new Paint();

    private Paint mInnerPaint = new Paint();

    private int pathRadius = 10;

    //倾斜率 0-1
    private float pathIncline = 0.01f;

    public CocoView(Context context) {
        super(context);
        initCocoView();
    }

    public CocoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCocoView();
    }

    private void initCocoView() {
        rectPaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(3);

        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);

        linePaint.setColor(Color.YELLOW);
        linePaint.setStrokeWidth(6);

        pathPaint.setColor(Color.BLUE);
        pathPaint.setStyle(Paint.Style.STROKE); // 还有FILL, FILL_AND_STROKE
        pathPaint.setStrokeWidth(3);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(28);

        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(Color.RED);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setStrokeWidth(1f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(tag, "onDraw : " + getMeasuredWidth() + ", " + getMeasuredHeight());

//        Path path1 = new Path();
//        path.moveTo(getWidth(),0);
//        path.lineTo(getWidth()/pathRadius, 0);
//        path.lineTo(0, getHeight());
//        path.lineTo(getWidth() - (getWidth()/pathRadius),getHeight());
//        path.lineTo(getWidth(), 0);
//        canvas.drawPath(path, mInnerPaint);

        canvas.save();
        RectF rectF = new RectF(60, 0, getWidth()-20, getHeight());
//        canvas.skew(1, 0); // 倾斜
        canvas.skew(-0.5f, 0);
        canvas.drawRoundRect(rectF, 10, 10, rectPaint);
        canvas.restore();

//        path1.moveTo(getWidth() - 10, 0);
//        //第一条直线
////        path1.lineTo(getWidth() / pathRadius + 10, 0);
//        path1.quadTo(getWidth() - 10, 0, getWidth() / pathRadius + 10, 0);
//        path1.quadTo(getWidth() / pathRadius + 10, 0, getWidth() / pathRadius, 10);
//        //第二条直线
////        path1.lineTo(5, getHeight() - 10);
//        path1.quadTo(getWidth() / pathRadius, 10, 5, getHeight() - 10);
//        path1.quadTo(5, getHeight() - 10, 10, getHeight());
//        //第三条直线
////        path1.lineTo(getWidth() - (getWidth() / pathRadius) - 10, getHeight());
//        path1.quadTo(10, getHeight(), getWidth() - (getWidth() / pathRadius) - 10, getHeight());
//        path1.quadTo(getWidth() - (getWidth() / pathRadius) - 10, getHeight(), getWidth() - (getWidth() / pathRadius) + 10, getHeight() - 10);
//        //第四条直线
////        path1.lineTo(getWidth() - 5, 10);
//        path1.quadTo(getWidth() - (getWidth() / pathRadius) + 10, getHeight() - 10, getWidth() - 5, 10);
//        path1.quadTo(getWidth() - 5, 10, getWidth() - 10, 0);
//        canvas.drawPath(path1, mInnerPaint);

//        path1.moveTo(getWidth(), 0);
//        path1.quadTo(getWidth() - 10, 0, getWidth() / pathRadius + 10, 0);
//        path1.quadTo(getWidth() / pathRadius - 10, 10, 10, getHeight() - 10);
//        path1.quadTo(10, getHeight(), getWidth() - (getWidth() / pathRadius) - 10, getHeight());
//        path1.quadTo(getWidth() - (getWidth() / pathRadius) + 10, getHeight() - 10, getWidth() - 10, 10);
//        canvas.drawPath(path1, mInnerPaint);

        //画四边形
//        Path path1 = new Path();
//
//        int incline_length = (int) (getWidth() * pathIncline);
//
//        path1.moveTo(getWidth() / incline_length + pathRadius * 3, 0);
//        path1.lineTo(getWidth() - pathRadius * 2, 0);
////        path1.quadTo(getWidth() - pathRadius * 2, 0, getWidth(), pathRadius * 2);
//
//
//        path1.moveTo(getWidth(), pathRadius * 2);
//        path1.lineTo(getWidth() - (getWidth() / incline_length), getHeight() - pathRadius * 3);
////        path1.quadTo(getWidth() - (getWidth() / incline_length), getHeight() - pathRadius * 3
////                , (getWidth() - (getWidth() / incline_length)) - pathRadius * 3, getHeight());
//
//        path1.moveTo((getWidth() - (getWidth() / incline_length)) - pathRadius * 3, getHeight());
//        path1.lineTo(pathRadius * 2, getHeight());
////        path1.quadTo(pathRadius * 2, getHeight(), 0, getHeight() - pathRadius * 2);
//
//        path1.moveTo(0, getHeight() - pathRadius * 2);
//        path1.lineTo(getWidth() / incline_length, pathRadius * 3);
////        path1.quadTo(getWidth() / incline_length, pathRadius * 3, getWidth() / incline_length + pathRadius * 3, 0);
//
//        canvas.drawPath(path1, mInnerPaint);
//
//        canvas.drawCircle(getWidth() / pathRadius + pathRadius * 2, pathRadius * 2, pathRadius * 2, circlePaint);
//
//        canvas.drawCircle(pathRadius, getHeight() - pathRadius, pathRadius, circlePaint);
//
//        canvas.drawCircle(getWidth() - (getWidth() / pathRadius) - pathRadius * 2, getHeight() - pathRadius * 2, pathRadius * 2, circlePaint);
//
//        canvas.drawCircle(getWidth() - pathRadius, pathRadius, pathRadius, circlePaint);

    }

}
