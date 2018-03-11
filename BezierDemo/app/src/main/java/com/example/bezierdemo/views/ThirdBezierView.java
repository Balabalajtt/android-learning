package com.example.bezierdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 江婷婷 on 2018/3/11.
 */

public class ThirdBezierView extends View {

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

    private boolean isSecondPoint = false;

    public ThirdBezierView(Context context) {
        super(context);
    }

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs) {
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

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPointAX = w / 2 - 100;
        mFlagPointAY = h / 2 - 500;

        mFlagPointBX = w / 2 + 100;
        mFlagPointBY = h / 2 - 500;

        mPath = new Path();
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                isSecondPoint = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isSecondPoint = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mFlagPointAX = event.getX(0);
                mFlagPointAY = event.getY(0);
                if (isSecondPoint) {
                    mFlagPointBX = event.getX(1);
                    mFlagPointBY = event.getY(1);
                }
                invalidate();
                break;
        }
        return true;
    }
}
