package com.example.slidingconflicttest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.NoSuchElementException;

/**
 * Created by 江婷婷 on 2017/11/5.
 */

public class StickyLayout extends LinearLayout {

    private static final String TAG = "StickyLayout";
    private static final boolean DEBUG = true;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public interface OnGiveUpTouchEventListener {
        public boolean giveUpTouchEvent(MotionEvent event);
    }

    private View mHeader;
    private View mContent;
    private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

    private int mOriginalHeaderHeight;
    private int mHeaderHeight;

//    private int mStatus = STATUS_EXPANDED;
//    public static final int STATUS_EXPANDED = 1;
//    public static final int STATUS_COLLAPSED = 2;

    private int mTouchSlop;
    private int mLastX = 0;
    private int mLastY = 0;

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    // 用来控制滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
//    private static final int TAN = 2;

//    private boolean mIsSticky = true;
    private boolean mInitDataSucceed = false;
//    private boolean mDisallowInterceptTouchEventOnHeader = true;

    public StickyLayout(Context context) {
        super(context);
        init();
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && (mHeader == null || mContent == null)) {
            initData();
        }
    }

    private void initData() {
        int headerId= getResources().getIdentifier("header", "id", getContext().getPackageName());
        int contentId = getResources().getIdentifier("list", "id", getContext().getPackageName());
        if (headerId != 0 && contentId != 0) {
            mHeader = findViewById(headerId);
            mContent = findViewById(contentId);
            mOriginalHeaderHeight = mHeader.getMeasuredHeight();
            mHeaderHeight = mOriginalHeaderHeight;
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            if (mHeaderHeight > 0) {
                mInitDataSucceed = true;
            }
            if (DEBUG) {
                Log.d(TAG, "mTouchSlop = " + mTouchSlop + "mHeaderHeight = " + mHeaderHeight);
            }
        } else {
            throw new NoSuchElementException("Did your view with id \"sticky_header\" or \"sticky_content\" exists?");
        }
    }

    public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
        mGiveUpTouchEventListener = l;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastXIntercept = x;
                mLastYIntercept = y;
                mLastX = x;
                mLastY = y;
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (/*mDisallowInterceptTouchEventOnHeader &&*/
                        y <= getHeaderHeight()) {
                    intercepted = false;
                } else if (Math.abs(deltaY) <= Math.abs(deltaX)) {
                    intercepted = false;
                } else if (/*mStatus == STATUS_EXPANDED && */deltaY <= -mTouchSlop) {
                    intercepted = true;
                } else if (mGiveUpTouchEventListener != null) {
                    if (mGiveUpTouchEventListener.giveUpTouchEvent(ev) /*&&
                            deltaY >= mTouchSlop*/) {
                        intercepted = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                mLastXIntercept = mLastYIntercept = 0;
                break;
        }

//        if (DEBUG) {
//            Log.d(TAG, "intercepted=" + intercepted);
//        }
//        return intercepted != 0 && mIsSticky;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (!mIsSticky) {
//            return true;
//        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                mHeaderHeight += deltaY;
                setHeaderHeight(mHeaderHeight);
                break;
            case MotionEvent.ACTION_UP:
//                int destHeight = 0;
//                if (mHeaderHeight <= mOriginalHeaderHeight * 0.5) {
//                    destHeight = mHeaderHeight;
//                    mStatus = STATUS_EXPANDED;
//                }
////                this.smoothSetHeaderHeight(mHeaderHeight, destHeight, 500);
                smoothScrollBy(0, mHeaderHeight);
                break;
        }
        mLastX = x;
        mLastY = y;
        return  true;
    }

//    public void smoothSetHeaderHeight(final int from, final int to, long duration) {
//        smoothSetHeaderHeight(from, to, duration, false);
//    }
//
//    public void smoothSetHeaderHeight(final int from, final int to, long duration, final boolean modifyOriginalHeaderHeight) {
//        final int frameCount = (int) (duration / 1000f * 30) + 1;
//        final float partation = (to - from) / (float) frameCount;
//        new Thread("Thread#smoothSetHeaderHeight") {
//
//            @Override
//            public void run() {
//                for (int i = 0; i < frameCount; i++) {
//                    final int height;
//                    if (i == frameCount - 1) {
//                        height = to;
//                    } else {
//                        height = (int) (from + partation * i);
//                    }
//                    post(new Runnable() {
//                        public void run() {
//                            setHeaderHeight(height);
//                        }
//                    });
//                    try {
//                        sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (modifyOriginalHeaderHeight) {
//                    setOriginalHeaderHeight(to);
//                }
//            }
//
//        }.start();
//    }

//    public void setOriginalHeaderHeight(int originalHeaderHeight) {
//        mOriginalHeaderHeight = originalHeaderHeight;
//    }

//    public void setHeaderHeight(int height, boolean modifyOriginalHeaderHeight) {
//        if (modifyOriginalHeaderHeight) {
//            setOriginalHeaderHeight(height);
//        }
//        setHeaderHeight(height);
//    }

    public void setHeaderHeight(int height) {
        if (!mInitDataSucceed) {
            initData();
        }

        if (DEBUG) {
            Log.d(TAG, "setHeaderHeight height=" + height);
        }
        if (height <= 0) {
            height = 0;
        } else if (height > mOriginalHeaderHeight) {
            height = mOriginalHeaderHeight;
        }

//        if (height == 0) {
//            mStatus = STATUS_COLLAPSED;
//        } else {
//            mStatus = STATUS_EXPANDED;
//        }

        if (mHeader != null && mHeader.getLayoutParams() != null) {
            mHeader.getLayoutParams().height = height;
            mHeader.requestLayout();
            mHeaderHeight = height;
        } else {
            if (DEBUG) {
                Log.e(TAG, "null LayoutParams when setHeaderHeight");
            }
        }
    }

    public int getHeaderHeight() {
        return mHeaderHeight;
    }

//    public void setSticky(boolean isSticky) {
//        mIsSticky = isSticky;
//    }

//    public void requestDisallowInterceptTouchEventOnHeader(boolean disallowIntercept) {
//        mDisallowInterceptTouchEventOnHeader = disallowIntercept;
//    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(0, getScrollY(), dx, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }

}
