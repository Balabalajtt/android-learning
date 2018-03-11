package com.example.bezierdemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by 江婷婷 on 2018/3/11.
 */

public class WaveBezierView extends View implements View.OnClickListener {

    private float mStartPointX;
    private float mStartPointY;
    private float mEndPointX;
    private float mEndPointY;

    private float mFlagPointAX;
    private float mFlagPointAY;
    private float mFlagPointBX;
    private float mFlagPointBY;

    private Path mPath;

    private Paint mPaintBezier;

    private int mWaveLength;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mCenterY;
    private int mWaveCount;

    private ValueAnimator mValueAnimator;
    private int mOffset;

    public WaveBezierView(Context context) {
        super(context);
    }

    public WaveBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setColor(Color.BLUE);
        mPaintBezier.setStyle(Paint.Style.FILL);

        mWaveLength = 800;

    }

    public WaveBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        setOnClickListener(this);

        mScreenHeight = h;
        mScreenWidth = w;
        mCenterY = h / 2;

        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //屏幕外左边准备的波
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);

        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo(-mWaveLength * 3 / 4 + i * mWaveLength + mOffset, mCenterY + 80, -mWaveLength / 2 + i * mWaveLength + mOffset, mCenterY);
            mPath.quadTo(-mWaveLength / 4 + i * mWaveLength + mOffset, mCenterY - 80, i * mWaveLength + mOffset, mCenterY);
        }
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaintBezier);

    }

    @Override
    public void onClick(View view) {
        mValueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mOffset = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

}
