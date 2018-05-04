package com.example.texttest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Selection;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 江婷婷 on 2018/5/3.
 */

public class AlignTextView extends EditText {

    private static final String TAG = "AlignTextView";

    public AlignTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlignTextView(Context context) {
        this(context, null);
    }

    public AlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

//    private void init() {
//        setGravity(Gravity.TOP);
//        setBackgroundColor(Color.WHITE);
//    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {

    }

    @Override
    protected boolean getDefaultEditable() {
        return false;
    }

//    @Override
//    protected MovementMethod getDefaultMovementMethod() {
//        return null;
//    }

    int lineDown = 0;
    int offDown = 0;

    int lineUp = 0;
    int offUp = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        int action = event.getAction();
        Layout layout = getLayout();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lineDown = layout.getLineForVertical(getScrollY() + (int) event.getY());
                offDown = layout.getOffsetForHorizontal(lineDown, (int) event.getX());
                Selection.setSelection(getEditableText(), offDown, offDown + 1);
                Log.d(TAG, "onTouchEvent: " + "按下");
                Log.d(TAG, "onTouchEvent: " + lineDown + " " + offDown);
                break;
            case MotionEvent.ACTION_MOVE:
                lineUp = layout.getLineForVertical(getScrollY() + (int) event.getY());
                offUp = layout.getOffsetForHorizontal(lineUp, (int) event.getX());
                if (offDown < offUp) {
                    Selection.setSelection(getEditableText(), offDown, offUp);
                } else if (offDown > offUp) {
                    Selection.setSelection(getEditableText(), offUp, offDown);
                }
                break;
            case MotionEvent.ACTION_UP:
                lineUp = layout.getLineForVertical(getScrollY() + (int) event.getY());
                offUp = layout.getOffsetForHorizontal(lineUp, (int) event.getX());
                if (offDown < offUp) {
                    Selection.setSelection(getEditableText(), offDown, offUp);
                } else if (offDown > offUp) {
                    Selection.setSelection(getEditableText(), offUp, offDown);
                }
                Log.d(TAG, "onTouchEvent: " + "抬起" + lineUp + " " + offUp);
                break;
        }
        return true;
    }
}
