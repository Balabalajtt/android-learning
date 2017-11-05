package com.example.slidingconflicttest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by 江婷婷 on 2017/11/5.
 * 内部拦截
 */

public class ListViewTwo extends ListView {

    private HorizontalScrollViewTwo mHorizontalSrollView;

    private int mLastX = 0;
    private int mLastY = 0;

    public ListViewTwo(Context context) {
        super(context);
    }

    public ListViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmHorizontalSrollView(HorizontalScrollViewTwo mHorizontalSrollView) {
        this.mHorizontalSrollView = mHorizontalSrollView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHorizontalSrollView.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    mHorizontalSrollView.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
