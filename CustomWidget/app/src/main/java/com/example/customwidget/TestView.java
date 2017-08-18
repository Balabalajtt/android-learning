package com.example.customwidget;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 江婷婷 on 2017/8/18.
 */

public class TestView extends View implements View.OnClickListener{

    private Paint mPaint;
    private Rect mRect;
    int mNum = 20;

    private int mBackgroundColor;
    private int mTextSize;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mRect = new Rect();
        this.setOnClickListener(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        mBackgroundColor = typedArray.getColor(R.styleable.TestView_backgroundColor, Color.YELLOW);//默认黄色
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TestView_textSize, 18);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(mTextSize);

        String text = String.valueOf(mNum);
        mPaint.getTextBounds(text, 0, text.length(), mRect);

        int textWidth = mRect.width();
        int textHeight = mRect.height();

        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 2, mPaint);

    }

    @Override
    public void onClick(View view) {
        if (mNum > 0) {
            mNum--;
        } else {
            mNum = 20;
        }
        invalidate();//刷新
    }
}
