package com.example.wechat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by 江婷婷 on 2017/9/14.
 */

public class ViewPagerIndicator extends LinearLayout {

    private Paint mPaint;//画笔
    private Path mPath;//线
    private int mTriangleWidth;//三角形宽
    private int mTriangleHeight;//三角形高

    private static final float RADIO_TRIANGLE_WIDTH = 1/6F;
    private int mInitTranslationX;
    private int mTranslationX;

    private static final int COLOR_TEXT_HIGHLIGHT = 0XFFFFFFFF;
    private static final int COLOR_TEXT_NORMAL = 0X77FFFFFF;




    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = (int) (w / 3 * RADIO_TRIANGLE_WIDTH);
        mInitTranslationX = w / 3 / 2 - mTriangleWidth / 2;

        initTriangle();
    }


    /**
     * 初始化三角形
     */
    private void initTriangle() {

        mTriangleHeight = mTriangleWidth / 3;

        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }


    /**
     * 指示器跟随手指滚动
     * @param position
     * @param positionOffset
     */
    public void scroll(int position, float positionOffset) {
        int tabWidth = getWidth() / 3;
        mTranslationX = (int) (tabWidth * (positionOffset + position));
        invalidate();
    }


    private ViewPager mViewPager;

    /**
     * 设置关联的ViewPager
     * @param viewPager
     * @param pos
     */
    public void setViewPager (ViewPager viewPager, final int pos) {
        mViewPager = viewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //总偏移量 tabWidth*positionOffset + position*tabWidth
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(pos);
        highLightTextView(pos);
    }


    /**
     * 设置Tab文本高亮
     * @param pos
     */
    private void highLightTextView (int pos) {
        resetTextViewColor();
        View view = getChildAt(pos);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
        }
    }

    /**
     * 重置Tab文本颜色
     */
    private void resetTextViewColor () {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    /**
     * 设置Tab点击事件
     */
    public void setItemClickEvent () {
        int childCount = getChildCount();
        for (int i = 0; i <childCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }


}
