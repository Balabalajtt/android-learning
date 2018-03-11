package com.example.bezierdemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by 江婷婷 on 2018/3/11.
 */

public class PathMorphingBezierView extends View implements View.OnClickListener {

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
    private Paint mPaintFlag;
    private Paint mPaintFlagText;

    private ValueAnimator mValueAnimator;

    public PathMorphingBezierView(Context context) {
        super(context);
    }

    public PathMorphingBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintFlag = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlag.setStrokeWidth(3);
        mPaintFlag.setStyle(Paint.Style.STROKE);

        mPaintFlagText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlagText.setStyle(Paint.Style.STROKE);
        mPaintFlagText.setTextSize(20);
    }

    public PathMorphingBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 600;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 600;

        mFlagPointAX = mStartPointX;
        mFlagPointAY = mStartPointY;
        mFlagPointBX = mEndPointX;
        mFlagPointBY = mEndPointY;

        mPath = new Path();
        mValueAnimator = ValueAnimator.ofFloat(mStartPointY, h);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        mValueAnimator.setDuration(2000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFlagPointAY = (float) valueAnimator.getAnimatedValue();
                mFlagPointBY = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        setOnClickListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        //控制点XY和终点XY 绘制二阶Bezier
        mPath.cubicTo(mFlagPointAX, mFlagPointAY, mFlagPointBX, mFlagPointBY, mEndPointX, mEndPointY);

        canvas.drawPoint(mStartPointX, mStartPointY, mPaintFlag);
        canvas.drawText("起点", mStartPointX, mStartPointY, mPaintFlagText);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintFlag);
        canvas.drawText("终点", mEndPointX, mEndPointY, mPaintFlagText);
        canvas.drawPoint(mFlagPointAX, mFlagPointAY, mPaintFlag);
        canvas.drawText("控制点A", mFlagPointAX, mFlagPointAY, mPaintFlagText);
        canvas.drawPoint(mFlagPointBX, mFlagPointBY, mPaintFlag);
        canvas.drawText("控制点B", mFlagPointBX, mFlagPointBY, mPaintFlagText);

        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointAX, mFlagPointAY, mPaintFlag);
        canvas.drawLine(mFlagPointAX, mFlagPointAY, mFlagPointBX, mFlagPointBY, mPaintFlag);
        canvas.drawLine(mEndPointX, mEndPointY, mFlagPointBX, mFlagPointBY, mPaintFlag);

        canvas.drawPath(mPath, mPaintBezier);

    }

    @Override
    public void onClick(View view) {
        mValueAnimator.start();
    }
}
