package com.example.bezierdemo;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by 江婷婷 on 2018/3/12.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mFlagPoint;

    public BezierEvaluator(PointF flagPoint) {
        mFlagPoint = flagPoint;
    }

    @Override
    public PointF evaluate(float v, PointF point, PointF t1) {
        //返回Bezier曲线上的每一点
        return BezierUtil.CalculateBezierPointForQuadratic(v, point, mFlagPoint, t1);
    }

}
