package com.zhuanbang.kingcommonlib.widget.section;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import com.zhuanbang.kingcommonlib.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 作者：ZhengQunWei on 2018/8/6 16:31
 */
public class SectionDecoration<T> extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";

    //自定义headView
    SectionConvert<T> mSectionConvert;
    //数据
    List<? extends BaseSection<T>> mSectionList = new ArrayList<>();
    boolean isInitHeight;
    RecyclerView mRecyclerView;
    private int textPaddingLeft = DisplayUtil.dip2px(16);//头部文字左边距
    Paint mHeaderTxtPaint;
    //标题字体大小
    int headSize = DisplayUtil.sp2px(18);
    //标题颜色
    int headColor = Color.parseColor("#333333");
    //标题背景颜色
    int headerContentColor = Color.parseColor("#f3f5f8");
    //标题高度
    int headHeight = DisplayUtil.dip2px(36);

    GestureDetector mGestureDetector;

    private SparseArray<Integer> stickyHeaderPosArray = new SparseArray<>();
    SparseArray<View> headViewMap = new SparseArray<>();
    private Paint mHeaderContentPaint;
    private final float txtYAxis;

    public SectionDecoration(List<? extends BaseSection<T>> data) {
        mSectionList = data;
        mHeaderTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeaderTxtPaint.setColor(headColor);
        mHeaderTxtPaint.setTextSize(headSize);
        mHeaderTxtPaint.setTextAlign(Paint.Align.LEFT);


        mHeaderContentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeaderContentPaint.setColor(headerContentColor);
        Paint.FontMetrics fontMetrics = mHeaderTxtPaint.getFontMetrics();
        float total = -fontMetrics.ascent + fontMetrics.descent;
        txtYAxis = total / 2 - fontMetrics.descent;
    }

    public SectionDecoration setSectionConvert(SectionConvert<T> sectionConvert) {
        mSectionConvert = sectionConvert;
        return this;
    }

    /**
     * 设置头部文字大小
     *
     * @param headSize
     */
    public SectionDecoration setHeadSize(int headSize) {
        this.headSize = headSize;
        return this;
    }

    public SectionDecoration setHeadColor(int headColor) {
        this.headColor = headColor;
        return this;
    }

    public SectionDecoration setHeaderContentColor(int headerContentColor) {
        this.headerContentColor = headerContentColor;
        return this;
    }

    public SectionDecoration setHeadHeight(int headHeight) {
        this.headHeight = headHeight;
        return this;
    }

    public SectionDecoration setSectionClickListener(SectionClickListener sectionClickListener) {
        mSectionClickListener = sectionClickListener;
        return this;
    }

    GestureDetector.OnGestureListener mGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mSectionClickListener != null) {
                for (int i = 0; i < stickyHeaderPosArray.size(); i++) {
                    int value = stickyHeaderPosArray.valueAt(i);
                    float y = e.getY();
                    if (value - headHeight <= y && y <= value) {
                        mSectionClickListener.onClick(stickyHeaderPosArray.keyAt(i));
                    }
                }
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };

    SectionClickListener mSectionClickListener;


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (mSectionList == null || mSectionList.size() == 0) return;

        if (mRecyclerView == null) {
            mRecyclerView = parent;
        }

        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(mRecyclerView.getContext(), mGestureListener);
            mRecyclerView.setOnTouchListener((v, event) -> mGestureDetector.onTouchEvent(event));
        }

        int childCount = mRecyclerView.getChildCount();
        int left = mRecyclerView.getLeft() + mRecyclerView.getPaddingLeft();
        int right = mRecyclerView.getRight() - mRecyclerView.getPaddingRight();

        T section = null;
        int firstPos = 0;
        int translateTop = 0;//绘制悬浮头部的偏移量
        /*for循环里面绘制每个分组的头部*/
        for (int i = 0; i < childCount; i++) {
            View view = mRecyclerView.getChildAt(i);
            int pos = mRecyclerView.getChildAdapterPosition(view);
            if (pos<0||pos >= mSectionList.size()) return;
            T currentSection = mSectionList.get(pos).getHeadSection();
            if (i == 0) {
                section = currentSection;
                firstPos = pos;
            }
            //如果头部信息是空则跳出这次循环
            if (currentSection == null) continue;
            int viewTop = view.getTop() + mRecyclerView.getPaddingTop();
            //如果是第一个或者两个头部信息不一致则绘制新的headview
            if (pos == 0 || !mSectionList.get(pos).isEquals(mSectionList.get(pos - 1))) {
                //绘制自定义头部
                if (mSectionConvert != null) {
                    mSectionConvert.drawDecoration(c,left,right,viewTop - headHeight,viewTop,getHeadSection(pos));
//                    drawHeadView(c, left, right, pos, viewTop - headHeight);
                } else {
                    if (currentSection instanceof String) {
                        /*绘制悬浮的头部*/
                        c.drawRect(left, viewTop - headHeight, right, viewTop, mHeaderContentPaint);
                        c.drawText((String) currentSection, left + textPaddingLeft, viewTop - headHeight / 2 +
                                txtYAxis, mHeaderTxtPaint);
                    }
                }

                if (headHeight < viewTop && viewTop <= 2 * headHeight) { //此判断是刚好2个头部碰撞，悬浮头部就要偏移
                    translateTop = viewTop - 2 * headHeight;
//                    Log.d(TAG, "translateTop-->" + translateTop);
                }
                stickyHeaderPosArray.put(pos, viewTop);//将头部信息放进array
//                Log.i(TAG, "绘制各个头部" + pos);
            }
        }
        if (section == null) return;


        c.save();
        c.translate(0, translateTop);
        if (mSectionConvert != null) {//inflater
//            drawHeadView(c, left, right, firstPos, 0);
            mSectionConvert.drawDecoration(c,left,right,0,headHeight,getHeadSection(firstPos));
        } else {
            /*绘制悬浮的头部*/
            if (section instanceof String) {
                /*绘制悬浮的头部*/
                c.drawRect(left, 0, right, headHeight, mHeaderContentPaint);
                c.drawText((String) section, left + textPaddingLeft, headHeight / 2 + txtYAxis, mHeaderTxtPaint);
            }
        }
        c.restore();
//        Log.i(TAG, "绘制悬浮头部");
    }

    /**
     * 绘制自定义头部
     *
     * @param canvas
     * @param left
     * @param right
     * @param pos
     */
    private void drawHeadView(Canvas canvas, int left, int right, int pos, int top) {
//        View headerView = headViewMap.get(pos);
//        if (headerView == null) {
//            headerView = mSectionConvert.drawDecoration(getHeadSection(pos));
//            headerView.measure(View.MeasureSpec.makeMeasureSpec(DisplayUtil.getScreenWidth(mRecyclerView.getContext()
//            ), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//            headerView.setDrawingCacheEnabled(true);
//            headerView.layout(0, 0, right, headHeight);//布局layout
//            headViewMap.put(pos, headerView);
//        }
//        try {
//            canvas.drawBitmap(headerView.getDrawingCache(), left, top, null);
//        } catch (Exception e) {
//
//        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mRecyclerView == null) {
            mRecyclerView = parent;
        }
        if (mSectionList == null || mSectionList.size() == 0) return;
        //使用自定义的head
        if (mSectionConvert != null && !isInitHeight) {
//            mSectionConvert.drawDerocation(c,left,right,viewTop - headHeight,headHeight,getHeadSection(pos));
//            View headView = mSectionConvert.getSectionView(getHeadSection(0));
//            if (headView == null) return;
//            //测量自定义headview的高度
//            headView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
//                    .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//            headHeight = headView.getMeasuredHeight();
            isInitHeight = true;
        }

        //获取当前view的下标
        int pos = mRecyclerView.getChildAdapterPosition(view);
        if (pos<0||pos >= mSectionList.size()) return;

        //获取当前数据的head信息
        BaseSection section = mSectionList.get(pos);
        if (section == null) {
            return;
        }
        //如果是第一个数据或者和上一个数据的head信息不同则需要预留出headview的位置
        if (pos == 0 || !section.isEquals(mSectionList.get(pos - 1))) {
            outRect.top = headHeight;
        }
    }


    /**
     * 获取列表中头部信息
     *
     * @param pos 列表中的下标
     * @return
     */
    private T getHeadSection(int pos) {
        if (mSectionList == null || mSectionList.size() <= pos) {
            return null;
        } else {
            BaseSection section = mSectionList.get(pos);
            if (section == null) return null;
            return mSectionList.get(pos).getHeadSection();
        }
    }


}
