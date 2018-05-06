package com.example.texttest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 江婷婷 on 2018/5/6.
 */

public class ReadingGlassView extends View {

    private Bitmap bitmap;
    private ShapeDrawable drawable;
    //放大镜的半径
    private static final int RADIUS = 80;
    //放大倍数
    private static final int FACTOR = 3;
    private Matrix matrix = new Matrix();

    public ReadingGlassView(Context context, Activity activity) {
        super(context);
        init(context, activity);

    }

//    public ReadingGlassView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//
//    }
//    public ReadingGlassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//
//    }

    private void init(Context context, Activity activity) {
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Log.d("aaa", "init: " + activity.getWindow().getDecorView());
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        Log.d("aaa", "init: " + bmp);
        bitmap = bmp;
        BitmapShader shader = new BitmapShader(
                Bitmap.createScaledBitmap(bmp, bmp.getWidth() * FACTOR,
                        bmp.getHeight() * FACTOR, true), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //圆形的drawable
        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setShader(shader);
        drawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        //这个位置表示的是，画shader的起始位置
        matrix.setTranslate(RADIUS-x*FACTOR, RADIUS-y*FACTOR);
        drawable.getPaint().getShader().setLocalMatrix(matrix);
        //bounds，就是那个圆的外切矩形
        drawable.setBounds(x-RADIUS, y-RADIUS, x+RADIUS, y+RADIUS);
        invalidate();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        drawable.draw(canvas);
    }

}
